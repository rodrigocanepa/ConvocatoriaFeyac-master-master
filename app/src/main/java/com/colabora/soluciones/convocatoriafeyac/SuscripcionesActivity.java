package com.colabora.soluciones.convocatoriafeyac;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.util.IabBroadcastReceiver;
import com.colabora.soluciones.convocatoriafeyac.util.IabHelper;
import com.colabora.soluciones.convocatoriafeyac.util.IabResult;
import com.colabora.soluciones.convocatoriafeyac.util.Inventory;
import com.colabora.soluciones.convocatoriafeyac.util.Purchase;

import java.util.ArrayList;
import java.util.List;

public class SuscripcionesActivity extends Activity implements IabBroadcastReceiver.IabBroadcastListener,
        DialogInterface.OnClickListener{

    private ImageView imgSuscripciones;
    private Button btnSuscripciones;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suscripciones);

        imgSuscripciones = (ImageView)findViewById(R.id.imgSuscripciones);
        btnSuscripciones = (Button)findViewById(R.id.btnSuscripcion);

        imgSuscripciones.setImageResource(R.drawable.pyme_pagos);
        imgSuscripciones.setColorFilter(Color.argb(180,20,20,20), PorterDuff.Mode.DARKEN);

        // KEY base 64 obtenida en la consola de developers google con la aplicación desarrollado por nosotros
        String base64EncodedPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAjNGiPcEqtqQAnFp0C9jMVMfe1vDe3iSgdWvcxhiTWWDEinjpaohc6ZVaq6hdi3E8skBY31kH7Y9bYK5bSar9DY7BFGfjyGGsT3KCjkbSkcXDeQcZseuWJyl0kM2MYcLL9A1+0rByFutR3l/DcxIFxccE2NRxjMb/o4chOAdgGgtvbR8ov+aixQfJnOsVGRYdJTSQKydtNgpOHHvfbeNBk88DwFG3I08rdIJ6d6GzI5VjEzUj+xm9h7QXZiGLxj8oJLiITSTAPNgQHcVe/m2sBuS9vzzN27BRhEanlHxU0T1LEiq/CzwOM+Sx16YSqFQSrQh+1H0q5G173X5HQ2ZLLQIDAQAB";
        // Create the helper, passing it our context and the public key to verify signatures with
        Log.d(TAG, "Creating IAB helper.");
        mHelper = new IabHelper(this, base64EncodedPublicKey);

        // enable debug logging (for a production application, you should set this to false).
        mHelper.enableDebugLogging(false);

        // Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    complain("Ha ocurrido un problema al iniciar la configuración de los pagos: " + result);
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
                mBroadcastReceiver = new IabBroadcastReceiver(SuscripcionesActivity.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                registerReceiver(mBroadcastReceiver, broadcastFilter);

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                Log.d(TAG, "Setup successful. Querying inventory.");
                try {
                    mHelper.queryInventoryAsync(mGotInventoryListener);
                } catch (IabHelper.IabAsyncInProgressException e) {
                    complain("Error al consultar el inventario. Otra operación asíncrona en progreso.");
                }
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
                complain("Error al consultar el inventario: " + result);
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
                Intent i = new Intent(SuscripcionesActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }

            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }

    };

    public void suscripcionCliked(View arg0) {
        if (!mHelper.subscriptionsSupported()) {
            complain("Subscriptions not supported on your device yet. Sorry!");
            return;
        }

        String payload = "";


        List<String> oldSkus = null;
        //oldSkus =  new ArrayList<>();
        //oldSkus.add(SKU_BASICO_MONTHLY);

        Log.d(TAG, "Launching purchase flow for gas subscription.");
        try {
            mHelper.launchPurchaseFlow(this, SKU_BASICO_MONTHLY, IabHelper.ITEM_TYPE_SUBS,
                    oldSkus, RC_REQUEST, mPurchaseFinishedListener, payload);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error al iniciar el flujo de compra. Otra operación asíncrona en progreso.");
        }
        // Reset the dialog options
        mSelectedSubscriptionPeriod = "";
        mFirstChoiceSku = "";
        mSecondChoiceSku = "";

    }

    @Override
    public void onClick(DialogInterface dialog, int id) {

    }

    // Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);

            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                complain("Error comprando: " + result);
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                complain("Error al comprar. Falló la verificación de autenticidad.");
                return;
            }

            Log.d(TAG, "Purchase successful.");

            if (purchase.getSku().equals(SKU_BASICO_MONTHLY)) {
                // bought the infinite gas subscription
                Log.d(TAG, "Infinite gas subscription purchased.");
                Toast.makeText(getApplicationContext(),"¡Gracias por suscribirte! Recuerda que tienes un período gratis por 30 días", Toast.LENGTH_LONG).show();
                mSubscribedToInfiniteGas = true;
                mAutoRenewEnabled = purchase.isAutoRenewing();
                mInfiniteGasSku = purchase.getSku();

                if(mSubscribedToInfiniteGas){
                    Intent i = new Intent(SuscripcionesActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
            }
        }
    };


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
    public void receivedBroadcast() {
        // Received a broadcast notification that the inventory of items has changed
        Log.d(TAG, "Received broadcast notification. Querying inventory.");
        try {
            mHelper.queryInventoryAsync(mGotInventoryListener);
        } catch (IabHelper.IabAsyncInProgressException e) {
            complain("Error al consultar el inventario. Otra operación asíncrona en progreso.");
        }
    }

    void complain(String message) {
        Log.e(TAG, "Pyme Assitant Error: " + message);
        alert("Error: " + message);
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

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
}
