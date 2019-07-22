package com.colabora.soluciones.convocatoriafeyac.web.aplicaciones;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;

public class WebsAppsSeccion4 extends AppCompatActivity {

    private Button btnSiguiente;
    private TextInputEditText editTitulo;
    private String titulo = "";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_apps_seccion4);


        btnSiguiente = (Button)findViewById(R.id.btn_web_app_siguiente_seccion4);
        editTitulo = (TextInputEditText)findViewById(R.id.txt_web_apps_seccion4_titulo);

        sharedPreferences = getSharedPreferences("misDatos", 0);
        editTitulo.setText(sharedPreferences.getString("web_apps_titulo_seccion_4", ""));

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = editTitulo.getText().toString();

                if(titulo.length() > 0){
                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("web_apps_titulo_seccion_4", titulo);
                    editor.commit();
                    // ******************************************************************************
                }
                else{
                    Toast.makeText(getApplicationContext(), "Para continuar debes escribir el campo que irá en esta sección", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(WebsAppsSeccion4.this, WebsAppsSeccion5.class);
                startActivity(i);
            }
        });
    }
}
