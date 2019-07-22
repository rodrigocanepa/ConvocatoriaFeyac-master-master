package com.colabora.soluciones.convocatoriafeyac.web.salud;

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
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;

public class WebsSaludSeccion5 extends AppCompatActivity {

    private Button btnSiguiente;
    private TextInputEditText editTitulo;
    private TextInputEditText editDescripcion;
    private TextInputEditText editAutor;
    private String titulo = "";
    private String subtitulo = "";
    private String autor = "";

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_salud_seccion5);


        btnSiguiente = (Button)findViewById(R.id.btnSaludSeccion5Siguiente);
        editTitulo = (TextInputEditText)findViewById(R.id.txtSaludSeccion5Titulo);
        editDescripcion= (TextInputEditText)findViewById(R.id.txtSaludSeccion5Descripcion);
        editAutor = (TextInputEditText)findViewById(R.id.txtSaludSeccion5Autor);

        sharedPreferences = getSharedPreferences("misDatos", 0);

        editTitulo.setText(sharedPreferences.getString("web_salud_titulo_baner", ""));
        editDescripcion.setText(sharedPreferences.getString("web_salud_frase_baner", ""));
        editAutor.setText(sharedPreferences.getString("web_salud_autor_baner", ""));


        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = editTitulo.getText().toString();
                subtitulo = editDescripcion.getText().toString();
                autor = editAutor.getText().toString();


                if(titulo.length() > 0 && subtitulo.length() > 0 && autor.length() > 0){
                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("web_salud_titulo_baner", titulo);
                    editor.putString("web_salud_frase_baner", subtitulo);
                    editor.putString("web_salud_autor_baner", autor);
                    editor.commit();
                    // ******************************************************************************
                }
                else{
                    Toast.makeText(getApplicationContext(), "Para continuar debes escribir los campos requeridos de esta sección", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(WebsSaludSeccion5.this, WebsSaludSeccion6.class);
                startActivity(i);
            }
        });
    }
}
