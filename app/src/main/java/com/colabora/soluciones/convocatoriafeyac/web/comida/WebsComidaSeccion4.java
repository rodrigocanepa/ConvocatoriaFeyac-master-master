package com.colabora.soluciones.convocatoriafeyac.web.comida;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.R;

public class WebsComidaSeccion4 extends AppCompatActivity {

    private Button btnSiguiente;
    private TextInputEditText editDescripcion1;
    private String descripcion = "";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_comida_seccion4);

        editDescripcion1 = (TextInputEditText)findViewById(R.id.txt_web_comida_seccion4_texto1);
        btnSiguiente = (Button)findViewById(R.id.btnComidaSeccion4Siguiente);

        sharedPreferences = getSharedPreferences("misDatos", 0);
        editDescripcion1.setText(sharedPreferences.getString("web_comida_descripcion_seccion_4", ""));

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                descripcion = editDescripcion1.getText().toString();


                if(descripcion.length() > 0){
                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("web_comida_descripcion_seccion_4", descripcion);

                    editor.commit();
                    // ******************************************************************************
                }
                else{
                    Toast.makeText(getApplicationContext(), "Para continuar debes escribir el campo requerido en esta sección", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(WebsComidaSeccion4.this, WebsComidaSeccion5.class);
                startActivity(i);
            }
        });
    }
}
