package com.colabora.soluciones.convocatoriafeyac.web.comida;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.R;
import com.google.firebase.storage.FirebaseStorage;

public class WebsComidaSeccion2 extends AppCompatActivity {


    private Button btnSiguiente;
    private TextInputEditText editTitulo;
    private TextInputEditText editTitulo2;
    private TextInputEditText editTitulo3;
    private TextInputEditText editDescripcion1;
    private TextInputEditText editDescripcion2;
    private TextInputEditText editDescripcion3;

    private String titulo = "";
    private String titulo2 = "";
    private String titulo3 = "";
    private String descripcion = "";
    private String descripcion2 = "";
    private String descripcion3 = "";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_comida_seccion2);

        editTitulo = (TextInputEditText)findViewById(R.id.txt_web_comida_seccion2_texto1);
        editTitulo2 = (TextInputEditText)findViewById(R.id.txt_web_comida_seccion2_texto2);
        editTitulo3 = (TextInputEditText)findViewById(R.id.txt_web_comida_seccion2_texto3);
        editDescripcion1 = (TextInputEditText)findViewById(R.id.txt_web_comida_seccion2_descripcion1);
        editDescripcion2 = (TextInputEditText)findViewById(R.id.txt_web_comida_seccion2_descripcion2);
        editDescripcion3 = (TextInputEditText)findViewById(R.id.txt_web_comida_seccion2_descripcion3);
        btnSiguiente = (Button)findViewById(R.id.btnComidaSeccion2Siguiente);

        sharedPreferences = getSharedPreferences("misDatos", 0);
        editTitulo.setText(sharedPreferences.getString("web_comida_titulo_1_seccion_2", ""));
        editTitulo2.setText(sharedPreferences.getString("web_comida_titulo_2_seccion_2", ""));
        editTitulo3.setText(sharedPreferences.getString("web_comida_titulo_3_seccion_2", ""));
        editDescripcion1.setText(sharedPreferences.getString("web_comida_descripcion_1_seccion_2", ""));
        editDescripcion2.setText(sharedPreferences.getString("web_comida_descripcion_2_seccion_2", ""));
        editDescripcion3.setText(sharedPreferences.getString("web_comida_descripcion_3_seccion_2", ""));

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = editTitulo.getText().toString();
                titulo2 = editTitulo2.getText().toString();
                titulo3 = editTitulo3.getText().toString();

                descripcion = editDescripcion1.getText().toString();
                descripcion2 = editDescripcion2.getText().toString();
                descripcion3 = editDescripcion3.getText().toString();

                if(titulo.length() > 0 && titulo2.length() > 0 && titulo3.length() > 0){
                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("web_comida_titulo_1_seccion_2", titulo);
                    editor.putString("web_comida_titulo_2_seccion_2", titulo2);
                    editor.putString("web_comida_titulo_3_seccion_2", titulo3);

                    editor.commit();
                    // ******************************************************************************
                }
                else{
                    Toast.makeText(getApplicationContext(), "Para continuar debes escribir los titulos que llevará esta sección", Toast.LENGTH_LONG).show();
                    return;
                }

                if(descripcion.length() > 0 && descripcion2.length() > 0 && descripcion3.length() > 0){
                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("web_comida_descripcion_1_seccion_2", descripcion);
                    editor.putString("web_comida_descripcion_2_seccion_2", descripcion2);
                    editor.putString("web_comida_descripcion_3_seccion_2", descripcion3);

                    editor.commit();
                    // ******************************************************************************
                }
                else{
                    Toast.makeText(getApplicationContext(), "Para continuar debes escribir las descripciones que llevará esta sección", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(WebsComidaSeccion2.this, WebsComidaSeccion3.class);
                startActivity(i);
            }
        });
    }
}
