package com.colabora.soluciones.convocatoriafeyac;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroActivity extends AppCompatActivity {

    private EditText editCorreo;
    private EditText editContrasena;
    private EditText editConfirmar;
    private Button btnRegistrar;
    private TextView txtRegistrar;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListener;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        editCorreo = (EditText)findViewById(R.id.editEmailRegistro);
        editContrasena = (EditText)findViewById(R.id.editPasswordRegistro);
        editConfirmar = (EditText)findViewById(R.id.editPasswordRegistroConfirmar);
        txtRegistrar = (TextView)findViewById(R.id.txtSignTermsConditions);
        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);

        txtRegistrar.setMovementMethod(LinkMovementMethod.getInstance());

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(RegistroActivity.this);

        progressDialog.setTitle("Validando");
        progressDialog.setMessage("Espere un momento mientras el sistema registra al usuario");

        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser(); //FirebaseAuth.getInstance().getCurrentUser;
                if(user != null){
                    // LANZAMOS SEGUNDA ACTIVITY, ESTO SE QUEDA GUARDADO
                    user.getEmail();
                    user.getDisplayName();
                    Intent i = new Intent(RegistroActivity.this, SuscripcionesActivity.class);
                    startActivity(i);
                    finish();

                }else{
                    Log.i("SESION", "Sesión cerrada");
                }
            }
        };

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
                checkBlanckSpaces();
            }
        });

    }

    private void Registrar(final String email, String password){
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,password).addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.i("SESION", "Usuario creado correctamente");
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
/*
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference tutorialRef = database.getReference(FirebaseReferences.CAUCELAPP_REFERENCE);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //FirebaseAuth.getInstance().getCurrentUser;
                    final String UUIDUser = user.getUid();

                    Usuarios newUser = new Usuarios(UUIDUser, editEmail.getText().toString(), editUsername.getText().toString(), "", "false");
                    tutorialRef.child(FirebaseReferences.USUARIOS_REFERENCE).push().setValue(newUser);
                    Toast.makeText(getApplicationContext(), "Usuario creado con exito", Toast.LENGTH_LONG).show();
                    */


                } else{
                    Log.e("SESION", task.getException().getMessage() + "");
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                    if(progressDialog.isShowing()){
                        progressDialog.dismiss();
                    }
                }
            }
        });
    }

    private void checkBlanckSpaces(){
        boolean error = false;

        if(editConfirmar.getText().toString().equals(editContrasena.getText().toString())){
            error = false;
        }
        else{
            error = true;
            editConfirmar.setError("Las contraseñas no coinciden");
            editContrasena.setError("Las contreseñas no coinciden");
        }

        if(editCorreo.getText().toString().isEmpty()){
            error = true;
            editCorreo.setError("No se permiten campos vacíos");
        }
        else if(editContrasena.getText().toString().isEmpty()){
            error = true;
            editContrasena.setError("No se permiten campos vacíos");
        }
        else if(editContrasena.getText().toString().isEmpty()){
            error = true;
            editContrasena.setError("No se permiten campos vacíos");
        }

        if(error == false){
            progressDialog.show();
            Registrar(editCorreo.getText().toString(), editContrasena.getText().toString());
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
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // updateUI(currentUser);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
    }
}

