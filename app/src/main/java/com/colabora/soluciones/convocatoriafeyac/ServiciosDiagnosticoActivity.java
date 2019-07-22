package com.colabora.soluciones.convocatoriafeyac;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ServiciosDiagnosticoActivity extends AppCompatActivity {

    private EditText edit1;
    private EditText edit2;
    private EditText edit3;
    private EditText edit4;
    private EditText edit5;
    private EditText edit6;
    private EditText edit7;
    private EditText edit8;
    private EditText edit9;
    private EditText edit10;
    private Button btnFinalizar;

    private FirebaseFirestore db;
    private Map<String, Object> usuario;
    private Map<String, Object> diagnosticoServicios;

    private SharedPreferences sharedPreferences;
    private String nombreEmpresaMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios_diagnostico);

        edit1 = (EditText)findViewById(R.id.editServicios1);
        edit2 = (EditText)findViewById(R.id.editServicios2);
        edit3 = (EditText)findViewById(R.id.editServicios3);
        edit4 = (EditText)findViewById(R.id.editServicios4);
        edit5 = (EditText)findViewById(R.id.editServicios5);
        edit6 = (EditText)findViewById(R.id.editServicios6);
        edit7 = (EditText)findViewById(R.id.editServicios7);
        edit8 = (EditText)findViewById(R.id.editServicios8);
        edit9 = (EditText)findViewById(R.id.editServicios9);
        edit10 = (EditText)findViewById(R.id.editServicios10);
        btnFinalizar = (Button)findViewById(R.id.serviciosFinalizar);

        db = FirebaseFirestore.getInstance();

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String respuesta1 = edit1.getText().toString();
                String respuesta2 = edit2.getText().toString();
                String respuesta3 = edit3.getText().toString();
                String respuesta4 = edit4.getText().toString();
                String respuesta5 = edit5.getText().toString();
                String respuesta6 = edit6.getText().toString();
                String respuesta7 = edit7.getText().toString();
                String respuesta8 = edit8.getText().toString();
                String respuesta9 = edit9.getText().toString();
                String respuesta10 = edit10.getText().toString();

                if(respuesta1.isEmpty()){
                    edit1.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes responder todas las preguntas para finalizar", Toast.LENGTH_LONG).show();
                    return;
                }
                if(respuesta2.isEmpty()){
                    edit2.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes responder todas las preguntas para finalizar", Toast.LENGTH_LONG).show();
                    return;
                }
                if(respuesta3.isEmpty()){
                    edit3.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes responder todas las preguntas para finalizar", Toast.LENGTH_LONG).show();
                    return;
                }
                if(respuesta4.isEmpty()){
                    edit4.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes responder todas las preguntas para finalizar", Toast.LENGTH_LONG).show();
                    return;
                }
                if(respuesta5.isEmpty()){
                    edit5.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes responder todas las preguntas para finalizar", Toast.LENGTH_LONG).show();
                    return;
                }
                if(respuesta6.isEmpty()){
                    edit6.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes responder todas las preguntas para finalizar", Toast.LENGTH_LONG).show();
                    return;
                }
                if(respuesta7.isEmpty()){
                    edit7.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes responder todas las preguntas para finalizar", Toast.LENGTH_LONG).show();
                    return;
                }
                if(respuesta8.isEmpty()){
                    edit8.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes responder todas las preguntas para finalizar", Toast.LENGTH_LONG).show();
                    return;
                }
                if(respuesta9.isEmpty()){
                    edit9.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes responder todas las preguntas para finalizar", Toast.LENGTH_LONG).show();
                    return;
                }
                if(respuesta10.isEmpty()){
                    edit10.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes responder todas las preguntas para finalizar", Toast.LENGTH_LONG).show();
                    return;
                }

                // Leemos la memoria para ver que tarjetas se han creado
                sharedPreferences = getSharedPreferences("misDatos", 0);
                nombreEmpresaMain = sharedPreferences.getString("nombreEmpresa","");

                if(nombreEmpresaMain.length() > 0){

                    usuario = new HashMap<>();
                    diagnosticoServicios = new HashMap<>();

                    diagnosticoServicios.put("respuesta1", respuesta1);
                    diagnosticoServicios.put("respuesta2", respuesta2);
                    diagnosticoServicios.put("respuesta3", respuesta3);
                    diagnosticoServicios.put("respuesta4", respuesta4);
                    diagnosticoServicios.put("respuesta5", respuesta5);
                    diagnosticoServicios.put("respuesta6", respuesta6);
                    diagnosticoServicios.put("respuesta7", respuesta7);
                    diagnosticoServicios.put("respuesta8", respuesta8);
                    diagnosticoServicios.put("respuesta9", respuesta9);
                    diagnosticoServicios.put("respuesta10", respuesta10);

                    usuario.put("nombreEmpresa",nombreEmpresaMain);
                    usuario.put("diagnosticoServicios", diagnosticoServicios);

                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //FirebaseAuth.getInstance().getCurrentUser;
                    final String uuid = user.getUid();

                    db.collection("pymes").document(uuid)
                            .set(usuario)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(getApplicationContext(),"DocumentSnapshot successfully written!", Toast.LENGTH_LONG).show();

                                    DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
                                    String date = df.format(Calendar.getInstance().getTime());
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(uuid + "diagnosticoMarketing", date);
                                    editor.commit();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(),"DocumentSnapshot failed!", Toast.LENGTH_LONG).show();
                                }
                            });
                }

            }
        });
    }
}
