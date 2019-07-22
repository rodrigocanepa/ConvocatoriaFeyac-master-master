package com.colabora.soluciones.convocatoriafeyac.web.salud;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.R;

public class WebsSaludSeccion6 extends AppCompatActivity {

    private Button btnSiguiente;
    private TextInputEditText editUbicacion;
    private TextInputEditText editEmail;
    private TextInputEditText editTelefono;
    private String ubicacion = "";
    private String email = "";
    private String telefono = "";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_salud_seccion6);

        btnSiguiente = (Button)findViewById(R.id.btnSaludSeccion6Siguiente);
        editUbicacion = (TextInputEditText)findViewById(R.id.txtSaludSeccion6Ubicacion);
        editEmail= (TextInputEditText)findViewById(R.id.txtSaludSeccion6Email);
        editTelefono = (TextInputEditText)findViewById(R.id.txtSaludSeccion6Telefono);

        sharedPreferences = getSharedPreferences("misDatos", 0);
        editTelefono.setText(sharedPreferences.getString("web_salud_telefono_contacto", ""));
        editEmail.setText(sharedPreferences.getString("web_salud_email_contacto", ""));
        editUbicacion.setText(sharedPreferences.getString("web_salud_ubicacion_contacto", ""));


        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubicacion = editUbicacion.getText().toString();
                telefono = editTelefono.getText().toString();
                email = editEmail.getText().toString();


                if(ubicacion.length() > 0 && telefono.length() > 0 && email.length() > 0){
                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("web_salud_ubicacion_contacto", ubicacion);
                    editor.putString("web_salud_email_contacto", email);
                    editor.putString("web_salud_telefono_contacto", telefono);
                    editor.commit();
                    // ******************************************************************************
                }
                else{
                    Toast.makeText(getApplicationContext(), "Para continuar debes escribir los campos requeridos de contacto", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(WebsSaludSeccion6.this, WebsSaludSeccion7.class);
                startActivity(i);
            }
        });
    }
}
