package com.colabora.soluciones.convocatoriafeyac;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.colabora.soluciones.convocatoriafeyac.util.IabBroadcastReceiver;
import com.colabora.soluciones.convocatoriafeyac.util.IabHelper;
import com.colabora.soluciones.convocatoriafeyac.util.IabResult;
import com.colabora.soluciones.convocatoriafeyac.util.Inventory;
import com.colabora.soluciones.convocatoriafeyac.util.Purchase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LauncherActivity extends Activity implements IabBroadcastReceiver.IabBroadcastListener,
        DialogInterface.OnClickListener{

    private RelativeLayout linearAliados;
    private RelativeLayout relativeLayoutDesarrollado;

    FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    private boolean sesion = false;


    // Debug tag, for logging
    static final String TAG = "TrivialDrive";

    // Está el usuario suscrito al plan?
    boolean mSubscribedToInfiniteGas = false;

    // La suscripción se renovará automáticamente?
    boolean mAutoRenewEnabled = false;

    // Rastrea el SKU de gas infinito que se posee actualmente, y las opciones en el cuadro de diálogo Administrar
    String mInfiniteGasSku = "";
    String mFirstChoiceSku = "";
    String mSecondChoiceSku = "";

    // Se utiliza para seleccionar entre la compra de gas en forma mensual o anual
    String mSelectedSubscriptionPeriod = "";

    // SKU for our subscription (infinite gas)
    static final String SKU_BASICO_MONTHLY = "suscripcion_pyme_assistant_mensual";
    //static final String SKU_PREMIUM_YEARLY = "plan_premium";

    // (arbitrary) request code for the purchase flow
    static final int RC_REQUEST = 10001;

    // The helper object
    IabHelper mHelper;

    // Provides purchase notification while this app is running
    IabBroadcastReceiver mBroadcastReceiver;

    private boolean suscripcion = false;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        linearAliados = (RelativeLayout) findViewById(R.id.linealLauncherAliados);
        relativeLayoutDesarrollado = (RelativeLayout)findViewById(R.id.relativeLauncherDesarrollado);

        linearAliados.setVisibility(View.INVISIBLE);
        relativeLayoutDesarrollado.setVisibility(View.INVISIBLE);

        //FirebaseAuth.getInstance().signOut();

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser(); //FirebaseAuth.getInstance().getCurrentUser;

                if(user != null){
                    Log.i("SESION", "Sesión iniciada con email: " + user.getEmail());
                    sesion = true;

                }else{
                    Log.i("SESION", "Sesión cerrada");
                    sesion = false;
                }
            }
        };


        // KEY base 64 obtenida en la consola de developers google con la aplicación desarrollado por nosotros
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjNGiPcEqtqQAnFp0C9jMVMfe1vDe3iSgdWvcxhiTWWDEinjpaohc6ZVaq6hdi3E8skBY31kH7Y9bYK5bSar9DY7BFGfjyGGsT3KCjkbSkcXDeQcZseuWJyl0kM2MYcLL9A1+0rByFutR3l/DcxIFxccE2NRxjMb/o4chOAdgGgtvbR8ov+aixQfJnOsVGRYdJTSQKydtNgpOHHvfbeNBk88DwFG3I08rdIJ6d6GzI5VjEzUj+xm9h7QXZiGLxj8oJLiITSTAPNgQHcVe/m2sBuS9vzzN27BRhEanlHxU0T1LEiq/CzwOM+Sx16YSqFQSrQh+1H0q5G173X5HQ2ZLLQIDAQAB";
        // Create the helper, passing it our context and the public key to verify signatures with
        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(true);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    //complain("Ha ocurrido un problema al iniciar la configuración de los pagos: " + result);
                    return;
                }

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                // Important: Dynamically register for broadcast messages about updated purchases.
                // We register the receiver here instead of as a <receiver> in the Manifest
                // because we always call getPurchases() at startup, so therefore we can ignore
                // any broadcasts sent while the app isn't running.
                // Note: registering this listener in an Activity is a bad idea, but is done here
                // because this is a SAMPLE. Regardless, the receiver must be registered after
                // IabHelper is setup, but before first call to getPurchases().
                mBroadcastReceiver = new IabBroadcastReceiver(LauncherActivity.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                registerReceiver(mBroadcastReceiver, broadcastFilter);

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                   // complain("Error al consultar el inventario. Otra operación asíncrona en progreso.");
                }
            }
        });



        Animation animation = AnimationUtils.loadAnimation(LauncherActivity.this, R.anim.transparentar);
        final Animation animation1 = AnimationUtils.loadAnimation(LauncherActivity.this, R.anim.transparentar);
        relativeLayoutDesarrollado.setAnimation(animation);

        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                relativeLayoutDesarrollado.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                linearAliados.setAnimation(animation1);
                relativeLayoutDesarrollado.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        animation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                linearAliados.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
               if (sesion == true && suscripcion == true){
                   // LANZAMOS SEGUNDA ACTIVITY, ESTO SE QUEDA GUARDADO
                   //final String UUIDUser = user.getUid();
                   Intent i = new Intent(LauncherActivity.this, MainActivity.class);
                   finish();
                   startActivity(i);
               }
               else if(sesion == true && suscripcion == false){
                   Intent i = new Intent(LauncherActivity.this, MainActivity.class);
                   finish();
                   startActivity(i);

                  /* Intent i = new Intent(LauncherActivity.this, SuscripcionesActivity.class);
                   finish();
                   startActivity(i);*/
               }
               else{
                   Intent i = new Intent(LauncherActivity.this, LoginActivity.class);
                   finish();
                   startActivity(i);
               }


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



    }

    // Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                //complain("Error al consultar el inventario: " + result);
                return;
            }

            Log.d(TAG, "Query inventory was successful.");

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            // Do we have the premium upgrade?
            /*
            Purchase premiumPurchase = inventory.getPurchase(SKU_PREMIUM);
            mIsPremium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
            Log.d(TAG, "User is " + (mIsPremium ? "PREMIUM" : "NOT PREMIUM"));
            */

            // First find out which subscription is auto renewing
            Purchase gasMonthly = inventory.getPurchase(SKU_BASICO_MONTHLY);
            //Purchase gasYearly = inventory.getPurchase(SKU_PREMIUM_YEARLY);
            if (gasMonthly != null && gasMonthly.isAutoRenewing()) {
                mInfiniteGasSku = SKU_BASICO_MONTHLY;
                mAutoRenewEnabled = true;
            }
            /*else if (gasYearly != null && gasYearly.isAutoRenewing()) {
                mInfiniteGasSku = SKU_PREMIUM_YEARLY;
                mAutoRenewEnabled = true;
            } */
            else {
                mInfiniteGasSku = "";
                mAutoRenewEnabled = false;
            }

            // The user is subscribed if either subscription exists, even if neither is auto
            // renewing
            mSubscribedToInfiniteGas = (gasMonthly != null && verifyDeveloperPayload(gasMonthly));
            Log.d(TAG, "User " + (mSubscribedToInfiniteGas ? "HAS" : "DOES NOT HAVE")
                    + " infinite gas subscription.");
            if(mSubscribedToInfiniteGas){
                suscripcion = true;
                // *********** Guardamos los principales datos de los nuevos usuarios *************
                sharedPreferences = getSharedPreferences("misDatos", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("suscripcion" , suscripcion);
                editor.commit();
                // ******************************************************************************
            }
            else{
                // *********** Guardamos los principales datos de los nuevos usuarios *************
                sharedPreferences = getSharedPreferences("misDatos", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("suscripcion" , false);
                editor.commit();
                // ******************************************************************************
            }

            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }

    };

    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }

    // We're being destroyed. It's important to dispose of the helper here!
    @Override
    public void onDestroy() {
        super.onDestroy();

        // very important:
        if (mBroadcastReceiver != null) {
            unregisterReceiver(mBroadcastReceiver);
        }

        // very important:
        Log.d(TAG, "Destroying helper.");
        if (mHelper != null) {
            mHelper.disposeWhenFinished();
            mHelper = null;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    @Override
    public void receivedBroadcast() {
// Received a broadcast notification that the inventory of items has changed
        Log.d(TAG, "Received broadcast notification. Querying inventory.");
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            //complain("Error al consultar el inventario. Otra operación asíncrona en progreso.");
        }
    }
}
