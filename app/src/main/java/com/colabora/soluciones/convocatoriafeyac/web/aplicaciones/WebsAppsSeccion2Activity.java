package com.colabora.soluciones.convocatoriafeyac.web.aplicaciones;

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


public class WebsAppsSeccion2Activity extends AppCompatActivity {

    private Button btnSiguiente;
    private Button btnSubirFoto;
    private TextInputEditText editTitulo;
    private TextInputEditText editSubtitulo;
    private TextInputEditText editGoogle;
    private TextInputEditText editApple;
    private String titulo = "";
    private String subtitulo = "";
    private String google = "";
    private String apple = "";


    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_apps_seccion2);

        btnSiguiente = (Button)findViewById(R.id.btn_web_app_siguiente_seccion2);
        btnSubirFoto = (Button)findViewById(R.id.btn_web_app_agregar_captura);
        editTitulo = (TextInputEditText)findViewById(R.id.txt_web_apps_seccion2_texto1);
        editSubtitulo = (TextInputEditText)findViewById(R.id.txt_web_apps_seccion2_texto2);
        editGoogle = (TextInputEditText)findViewById(R.id.txt_web_apps_seccion2_google);
        editApple = (TextInputEditText)findViewById(R.id.txt_web_apps_seccion2_apple);

        sharedPreferences = getSharedPreferences("misDatos", 0);

        editTitulo.setText(sharedPreferences.getString("web_apps_titulo_seccion_2", ""));
        editSubtitulo.setText(sharedPreferences.getString("web_apps_subtitulo_seccion_2", ""));
        editApple.setText(sharedPreferences.getString("web_apps_apple_seccion_2", ""));
        editGoogle.setText(sharedPreferences.getString("web_apps_google_seccion_2", ""));

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = editTitulo.getText().toString();
                subtitulo = editSubtitulo.getText().toString();
                google = editGoogle.getText().toString();
                apple = editApple.getText().toString();

                if(titulo.length() > 0 && subtitulo.length() > 0){
                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("web_apps_titulo_seccion_2", titulo);
                    editor.putString("web_apps_subtitulo_seccion_2", subtitulo);
                    editor.putString("web_apps_google_seccion_2", google);
                    editor.putString("web_apps_apple_seccion_2", apple);

                    editor.commit();
                    // ******************************************************************************
                }
                else{
                    Toast.makeText(getApplicationContext(), "Para continuar debes escribir el titulo y la descripción que llevará la sección de home", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(WebsAppsSeccion2Activity.this, WebAppsFeaturesActivity.class);
                startActivity(i);
            }
        });
    }
}
