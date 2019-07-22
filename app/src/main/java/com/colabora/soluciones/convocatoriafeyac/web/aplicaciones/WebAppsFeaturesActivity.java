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

public class WebAppsFeaturesActivity extends AppCompatActivity {

    private Button btnSiguiente;
    private Button btnSubirFoto;
    private TextInputEditText editTitulo;
    private TextInputEditText editTitulo1;
    private TextInputEditText editSubtitulo1;
    private TextInputEditText editTitulo2;
    private TextInputEditText editSubtitulo2;
    private TextInputEditText editTitulo3;
    private TextInputEditText editSubtitulo3;
    private TextInputEditText editTitulo4;
    private TextInputEditText editSubtitulo4;
    private String titulo = "";
    private String titulo1 = "";
    private String subtitulo1 = "";
    private String titulo2 = "";
    private String subtitulo2 = "";
    private String titulo3 = "";
    private String subtitulo3 = "";
    private String titulo4 = "";
    private String subtitulo4 = "";
    private ImageView img;
    private String nombre_web = "";

    private boolean imgUpoloaded = false;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_apps_features);

        img = (ImageView)findViewById(R.id.img_web_app_seccion3);
        btnSiguiente = (Button)findViewById(R.id.btn_web_app_siguiente_seccion3);
        btnSubirFoto = (Button)findViewById(R.id.btn_web_app_agregar_captura);
        editTitulo = (TextInputEditText)findViewById(R.id.txt_web_apps_seccion3_titulo);
        editTitulo1 = (TextInputEditText)findViewById(R.id.txt_web_apps_seccion3_titulo_1);
        editSubtitulo1 = (TextInputEditText)findViewById(R.id.txt_web_apps_seccion3_descripcion_1);
        editTitulo2 = (TextInputEditText)findViewById(R.id.txt_web_apps_seccion3_titulo_2);
        editSubtitulo2 = (TextInputEditText)findViewById(R.id.txt_web_apps_seccion3_descripcion_2);
        editTitulo3 = (TextInputEditText)findViewById(R.id.txt_web_apps_seccion3_titulo_3);
        editSubtitulo3 = (TextInputEditText)findViewById(R.id.txt_web_apps_seccion3_descripcion_3);
        editTitulo4 = (TextInputEditText)findViewById(R.id.txt_web_apps_seccion3_titulo_4);
        editSubtitulo4 = (TextInputEditText)findViewById(R.id.txt_web_apps_seccion3_descripcion_4);

        sharedPreferences = getSharedPreferences("misDatos", 0);
        nombre_web = sharedPreferences.getString("nombrePagWeb","");

        progressDialog = new ProgressDialog(WebAppsFeaturesActivity.this);

        progressDialog.setTitle("Subiendo Información");
        progressDialog.setMessage("Espere un momento mientras el sistema sube su información a la base de datos");

        editTitulo.setText(sharedPreferences.getString("web_apps_titulo_seccion_3", ""));
        editTitulo1.setText(sharedPreferences.getString("web_apps_titulo_1_seccion_3", ""));
        editSubtitulo1.setText(sharedPreferences.getString("web_apps_subtitulo_1_seccion_3", ""));
        editTitulo2.setText(sharedPreferences.getString("web_apps_titulo_2_seccion_3", ""));
        editSubtitulo2.setText(sharedPreferences.getString("web_apps_subtitulo_2_seccion_3", ""));
        editTitulo3.setText(sharedPreferences.getString("web_apps_titulo_3_seccion_3", ""));
        editSubtitulo3.setText(sharedPreferences.getString("web_apps_subtitulo_3_seccion_3", ""));
        editTitulo4.setText(sharedPreferences.getString("web_apps_titulo_4_seccion_3", ""));
        editSubtitulo4.setText(sharedPreferences.getString("web_apps_subtitulo_4_seccion_3", ""));

        if (sharedPreferences.getString("web_apps_img_seccion_3","").length() > 1){
            imgUpoloaded = true;
            Picasso.get().load(sharedPreferences.getString("web_apps_img_seccion_3","")).into(img);
        }

        btnSubirFoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup().setTitle("Escoge una opción")
                        .setCameraButtonText("Cámara")
                        .setGalleryButtonText("Galería"))
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                //TODO: do what you have to...
                                img.setImageBitmap(r.getBitmap());
                                progressDialog.show();

                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                r.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                byte[] data = outputStream.toByteArray();

                                StorageReference refSubirImagen = storage.getReferenceFromUrl("gs://pyme-assistant.appspot.com/web/userid").child(nombre_web + "imagen2");

                                UploadTask uploadTask = refSubirImagen.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        if (progressDialog.isShowing()){
                                            progressDialog.dismiss();
                                        }
                                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error, por favor vuelva a intentarlo", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        if (progressDialog.isShowing()){
                                            progressDialog.dismiss();
                                        }
                                        Toast.makeText(getApplicationContext(), "Imagen subida exitosamente", Toast.LENGTH_LONG).show();
                                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                        while(!uri.isComplete());
                                        Uri url = uri.getResult();
                                        // *********** Guardamos los principales datos de los nuevos usuarios *************
                                        sharedPreferences = getSharedPreferences("misDatos", 0);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("web_apps_img_seccion_3", url.toString());
                                        editor.commit();
                                        imgUpoloaded = true;
                                        // ******************************************************************************
                                    }
                                });

                            }
                        })
                        .setOnPickCancel(new IPickCancel() {
                            @Override
                            public void onCancelClick() {
                                //TODO: do what you have to if user clicked cancel
                            }
                        }).show(getSupportFragmentManager());
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titulo = editTitulo.getText().toString();
                titulo1 = editTitulo1.getText().toString();
                subtitulo1 = editSubtitulo1.getText().toString();
                titulo2 = editTitulo2.getText().toString();
                subtitulo2 = editSubtitulo2.getText().toString();
                titulo3 = editTitulo3.getText().toString();
                subtitulo3 = editSubtitulo3.getText().toString();
                titulo4 = editTitulo4.getText().toString();
                subtitulo4 = editSubtitulo4.getText().toString();

                if(!imgUpoloaded){
                    Toast.makeText(getApplicationContext(), "Para continuar debes subir la captura de imagen que irá en esta sección", Toast.LENGTH_LONG).show();
                    return;
                }

                if(titulo1.length() > 0 && subtitulo1.length() > 0 && titulo2.length() > 0 && subtitulo2.length() > 0 && titulo3.length() > 0 && subtitulo3.length() > 0 && titulo4.length() > 0 && subtitulo4.length() > 0 && titulo.length() > 0){
                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("web_apps_titulo_seccion_3", titulo);
                    editor.putString("web_apps_titulo_1_seccion_3", titulo1);
                    editor.putString("web_apps_subtitulo_1_seccion_3", subtitulo1);
                    editor.putString("web_apps_titulo_2_seccion_3", titulo2);
                    editor.putString("web_apps_subtitulo_2_seccion_3", subtitulo2);
                    editor.putString("web_apps_titulo_3_seccion_3", titulo3);
                    editor.putString("web_apps_subtitulo_3_seccion_3", subtitulo3);
                    editor.putString("web_apps_titulo_4_seccion_3", titulo4);
                    editor.putString("web_apps_subtitulo_4_seccion_3", subtitulo4);

                    editor.commit();
                    // ******************************************************************************
                }
                else{
                    Toast.makeText(getApplicationContext(), "Para continuar debes llenar los campos requeridos", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(WebAppsFeaturesActivity.this, WebsAppsSeccion4.class);
                startActivity(i);
            }
        });
    }
}
