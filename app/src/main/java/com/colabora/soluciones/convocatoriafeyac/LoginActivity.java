package com.colabora.soluciones.convocatoriafeyac;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends Activity {

    private Button btnRegistrar;
    private EditText editCorreo;
    private EditText editContrasena;
    private Button btnLoginFace;
    private Button btnLogin;
    private TextView txtOlvideContrasena;

    FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;
    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;
    private LoginButton loginButton;
    private CallbackManager mCallbackManager;


    private String profileFoto;
    private String email;
    private String firstName;
    private String lastName;
    private TextView txtTerminos;
    private TextView txtVerTerminos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        btnRegistrar = (Button)findViewById(R.id.btnSignUpLogin);
        btnLogin = (Button)findViewById(R.id.btnLogin);
        btnLoginFace = (Button)findViewById(R.id.btnFaceBack);
        editCorreo = (EditText)findViewById(R.id.editEmailLogin);
        editContrasena = (EditText)findViewById(R.id.editPasswordLogin);
        txtOlvideContrasena = (TextView)findViewById(R.id.txtForgotPassword);
        txtVerTerminos = (TextView)findViewById(R.id.txtLoginTermsConditions);

        loginButton = (LoginButton)findViewById(R.id.login_buttonFace);

        // Add code to print out the key hash
        /*try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.colabora.soluciones.convocatoriafeyac",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String key = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }*/

        progressDialog = new ProgressDialog(LoginActivity.this);

        progressDialog.setTitle("Validando");
        progressDialog.setMessage("Espere un momento mientras el sistema inicia sesión");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat
                    .requestPermissions(LoginActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_LOCATION);
        }

        mAuth = FirebaseAuth.getInstance();
        // ***************************** FACEBOOK *************************************
        mCallbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email", "public_profile");

        btnLoginFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                loginButton.performClick();
            }
        });

        txtVerTerminos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("Términos y condiciones");
                // Get the layout inflater
                LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View formElementsView = inflater.inflate(R.layout.dialog_terminos,
                        null, false);

                txtTerminos = (TextView) formElementsView.findViewById(R.id.txtTerminos);

                String terminos = "El uso de la aplicación denominada Pyme Assistant, en lo sucesivo “la aplicación” y/o de los servicios ofrecidos en “la aplicación” implica que el usuario acepta la Política de Privacidad y otros textos legales relacionados con el uso de servicio prestados en esta aplicación.\n\n" +
                        "Consentimiento del Usuario\n\n" +
                        "La simple navegación o el uso de “la aplicación” y de cualquiera de sus componentes o secciones, obras como imágenes, sonidos, contenidos e información, implica la aceptación expresa de los presentes “Términos y Condiciones” y de su Política de Privacidad y Protección de Datos Personales, (en adelante “Política de Privacidad”), disponible en https://www.solucionescolabora.com/empresa\n\n" +
                        "El Usuario se obliga a hacer buen uso de “la aplicación” comprometiéndose en todos los casos, a no causar daños a cualquier contenido o componente del mismo o a contenidos de terceros que aparezcan o estén relacionados a “la aplicación”.\n\n" +
                        "Obligación del Usuario\n\n" +
                        "El usuario se compromete a:\n\n" +
                        "1. No interferir ni interrumpir el acceso, funcionalidad y utilización de “la aplicación”, servidores o redes conectados al mismo, o incumplir los requisitos, procedimientos y regulaciones de la política de conexión de redes.\n\n" +
                        "2. Abstenerse de utilizar cualquiera de los servicios o contenidos con fines o efectos ilícitos prohibidos en los presentes “términos y condiciones”; las leyes nacionales e internacionales aplicables.\n\n" +
                        "3. Abstenerse de realizar cualquier conducta que puedan representar violación a las facultades de y derechos de terceros.\n\n" +
                        "4. Respetar la propiedad intelectual que sea parte o se muestre en “la aplicación”; no violar los derechos de autor y propiedad industrial que refieren las leyes nacionales, acuerdos e instrumentos internacionales.\n\n" +
                        "Limitación de Responsabilidad\n\n" +
                        "En “la aplicación” podrá existir algún hipervínculo, hipertexto, banner, botón o herramienta de búsqueda, servicio o contenido de terceros, que al ser utilizada por el Usuario le puede llevar a un sitio externo, ya sea de gobierno o privadas, que podrá tener sus propios términos y condiciones o autorizaciones respecto de las obras, imágenes, sonidos o contenidos en materia de propiedad intelectual o en materia de privacidad y protección de datos personales. Por lo tanto, el administrador de la “la aplicación” no se hace responsable de dichos sitios o contenidos externos, sus términos y condiciones, y su “Política de Privacidad”.\n\n" +
                        "El administrador de la “la aplicación” se deslinda de cualquier responsabilidad que pueda generar al usuario por cualquier uso inadecuado o contrario a los fines de “la aplicación”. Al utilizar “la aplicación” o cualquiera de sus componentes o contenidos, usted exime de toda responsabilidad por los daños que el uso del mismo o contenidos le pudieran ocasionar de forma directa, incidental o consecuente.\n\n" +
                        "Medidas de seguridad de información\n\n" +
                        "En “la aplicación” nos preocupamos y hacemos todos los esfuerzos posibles para comprobar y poner a prueba todas las etapas del desarrollo y operación de “la aplicación”. Sin embargo, el Usuario debe tomar sus propias precauciones para asegurar que, durante el acceso al mismo, no te expongas a riesgos informáticos; virus informáticos, código informático malicioso u otras formas de software malicioso y práctica inadecuada, que puedan dañar tu propio equipo de cómputo, tu teléfono celular o dispositivo móvil.\n\n" +
                        "El administrador de la “la aplicación” no se hace responsable por cualquier falla para cumplir con estos “términos y condiciones” cuando dicho incumplimiento se deba a circunstancias fuera de nuestro control, caso fortuito o fuerza mayor.\n\n" +
                        "Propiedad Intelectual\n\n" +
                        "El usuario se compromete a respetar los derechos de propiedad intelectual sobre cualquier obra, creación, o elemento que pueda ser susceptible de ser protegido por el derecho de autor o de propiedad industrial que puedan estar presentes o relacionados en “la aplicación”.\n\n" +
                        "Libre Uso: cualquier obra, entre ellas; imagen, sonido, audio, video u otra que aparezca en “la aplicación” y que cuenten con la leyenda “Libre Uso”, lo cual significa que podrá usarse o explotarse en territorio nacional o extranjero, bajo las leyes mexicanas para cualquier fin lícito, siempre que cite la fuente y otorgue el crédito al autor.\n\n" +
                        "Si alguna obra o cualquier elemento que protege la ley aplicable al derecho de autor y la propiedad industrial que sea parte de “la aplicación” o esté publicada en “la aplicación” no cuenta con la leyenda “Libre Uso” y tampoco se señala el nombre específico del autor y fuente, se presume que, previo a cualquier uso o explotación requiere consentimiento expreso de quien cuente con los derechos correspondientes. Para conocer más al respecto, podrá solicitar información al correo de contacto de “la aplicación”.\n\n" +
                        "El administrador de la “la aplicación” deja a salvo los derechos de terceros, por lo que para el uso de “la aplicación” más allá de lo permitido en los presentes “términos y condiciones”, se requiere contar las autorizaciones correspondientes directamente de los titulares de los derechos.\n\n" +
                        "Derechos exclusivos de terceros. Cualquier obra protegida por el derecho de autor, tales como –sin que sea restrictivo a ellas- imágenes, sonidos, texto, edición, video, fotografía, locución, bases de datos, etc., así como cualquier contenido que sea objeto de la protección del derecho de propiedad industrial, como son –sin que sea restrictivo a estos- patentes, modelo de utilidad, diseño industrial, secreto comercial, marcas, aviso comercial –slogans o logotipos-; que se encuentre en “la aplicación” y cuente con mención del autor y/o fuente, o con algún rasgo del que se desprenda que la titularidad no corresponde al administrador de la “la aplicación”, y sí a un tercero, se entenderá que requiere autorización expresa del autor o titular del derecho correspondiente, previo a cualquier uso o explotación que permitan las leyes mexicanas y el derecho internacional.\n\n" +
                        "\n\n" +
                        "Derecho a la propia imagen. Cualquier persona que participe enviando contenido para que se publique y difunda en “la aplicación”, sus redes sociales o cualquier otro medio de comunicación nacional o extranjero; envié alguna fotografía u obra donde se exhiba su imagen corporal o rostro, autoriza tácitamente el uso y explotación de su imagen con la finalidad de vincular la imagen del autor con las obras que se encuentren o se mencionen en “la aplicación”, así como su difusión en cualquier medio de comunicación, nacional y extranjero, en cumplimiento del objetivo de “la aplicación”, los “términos y condiciones” y la normatividad aplicable.\n\n" +
                        "Modificación\n\n" +
                        "El administrador de la “la aplicación” se reserva en todo momento el derecho de modificar los presentes “términos y condiciones” y la “Política de Privacidad”.\n" +
                        "Le sugerimos visitar regularmente este sitio para estar al día en cuanto a los “términos y condiciones”, así como la “Política de Privacidad”.\n" +
                        "Legislación aplicable y jurisdicción\n\n" +
                        "En caso de cualquier situación relativa a “la aplicación”, interpretación o controversia derivada de su uso o en relación con los derechos u obligaciones sobre información, obras, contenidos publicados o la “Política de Privacidad”, usted está de acuerdo expresamente en buscar una solución amistosa, previo a someterse a la jurisdicción de los Tribunales Federales en la ciudad de Mérida, Yucatán, México y a las leyes federales vigentes en territorio de los Estados Unidos Mexicanos.\n\n" +
                        "\n" +
                        "\n";
                txtTerminos.setText(terminos);

                builder.setPositiveButton("Cerrar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                builder.setView(formElementsView);
                // Add action buttons
                builder.create();
                builder.show();
            }
        });

        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {

                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }

                System.out.println("onSuccess");

                String accessToken = loginResult.getAccessToken().getToken();
                Log.i("accessToken", accessToken);

                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {

                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i("LoginActivity", response.toString());
                        // Get facebook data from login
                        Bundle bFacebookData = getFacebookData(object);
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id, first_name, last_name, email,gender, birthday, location"); // Parámetros que pedimos a facebook
                request.setParameters(parameters);
                request.executeAsync();

                Log.d("FACEBOOK", "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Log.d("FACEBOOK", "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                if(progressDialog.isShowing()){
                    progressDialog.dismiss();
                }
                Log.d("FACEBOOK", "facebook:onError", error);
                // ...
            }
        });

        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser(); //FirebaseAuth.getInstance().getCurrentUser;

                if(user != null){
                    Log.i("SESION", "Sesión iniciada con email: " + user.getEmail());
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("UUID", user.getUid());
                    editor.commit();
                    boolean suscripcion = sharedPreferences.getBoolean("suscripcion", false);
                    if(suscripcion){
                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                    else{

                       /* Intent i = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(i);
                        finish();*/

                        Intent i = new Intent(LoginActivity.this, SuscripcionesActivity.class);
                        startActivity(i);
                        finish();
                    }


                }else{
                    Log.i("SESION", "Sesión cerrada");
                }
            }
        };


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideKeyboard();
                checkBlanckSpaces();
            }
        });




    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d("FACEBOOK", "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("FACEBOOK", "signInWithCredential:success");

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("FACEBOOK", "signInWithCredential:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
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

    public void hideKeyboard() {
        // Check if no view has focus:
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void checkBlanckSpaces(){
        boolean error = false;

        if(editCorreo.getText().toString().isEmpty()){
            error = true;
            editCorreo.setError("No se permiten campos vacíos");
        }

        else if(editContrasena.getText().toString().isEmpty()){
            error = true;
            editContrasena.setError("No se permiten campos vacíos");
        }


        if(error == false){
            progressDialog.show();
            IniciarSesion(editCorreo.getText().toString(), editContrasena.getText().toString());
        }

    }

    private void IniciarSesion(String email, String password){
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i("SESION", "Sesión iniciada correctamente");
                } else{
                    Log.e("SESION", task.getException().getMessage() + "");
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                    new AlertDialog.Builder(this)
                            .setTitle("Aviso")
                            .setMessage("Si no concede los permisos la app se cerrará")
                            .setPositiveButton("Cerrar aplicación", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finish();
                                }
                            })
                            .create()
                            .show();

                }
                return;
            }

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private Bundle getFacebookData(JSONObject object) {

        try {
            Bundle bundle = new Bundle();
            String id = object.getString("id");

            try {
                URL profile_pic = new URL("https://graph.facebook.com/" + id + "/picture?width=150&height=150");
                Log.i("profile_pic", profile_pic + "");
                bundle.putString("profile_pic", profile_pic.toString());
                profileFoto = profile_pic.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }

            bundle.putString("idFacebook", id);
            if (object.has("first_name")){
                bundle.putString("first_name", object.getString("first_name"));
                firstName = object.getString("first_name");
            }
            if (object.has("last_name")){
                bundle.putString("last_name", object.getString("last_name"));
                lastName = object.getString("last_name");
            }
            if (object.has("email")){
                bundle.putString("email", object.getString("email"));
                email = object.getString("email");
            }
            if (object.has("gender")){
                bundle.putString("gender", object.getString("gender"));
            }
            if (object.has("birthday")){
                bundle.putString("birthday", object.getString("birthday"));
            }
            if (object.has("location")){
                bundle.putString("location", object.getJSONObject("location").getString("name"));
            }

            return bundle;
        }
        catch(JSONException e) {
            Log.d("JSON","Error parsing JSON");
        }
        return null;
    }

}
