package com.colabora.soluciones.convocatoriafeyac.web.servicios;

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
import com.colabora.soluciones.convocatoriafeyac.web.DisenoWebActivity;
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
import com.vansuita.pickimage.enums.EPickType;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;

public class WebsServiciosSeccion1 extends AppCompatActivity {

    private Button btnSiguiente;
    private Button btnSubirFoto;
    private Button btnAddLogo;
    private ImageView logo;
    private TextInputEditText editTitulo;
    private ImageView img;
    private String nombre_web = "";
    private String titulo = "";
    private boolean imgUpoloaded = false;
    private boolean imgUpoloaded2 = false;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_servicios_seccion1);

        btnSiguiente = (Button)findViewById(R.id.btnServiciosSeccion1Siguiente);
        btnSubirFoto = (Button)findViewById(R.id.btnServiciosSeccion1);
        btnAddLogo = (Button)findViewById(R.id.btnServiciosSeccionAddLogo);
        logo = (ImageView)findViewById(R.id.imgServiciosSeccionLogo);
        img = (ImageView)findViewById(R.id.imgServiciosSeccion1);
        editTitulo = (TextInputEditText)findViewById(R.id.txt_web_servicio_seccion1_texto1);

        progressDialog = new ProgressDialog(WebsServiciosSeccion1.this);

        progressDialog.setTitle("Subiendo Información");
        progressDialog.setMessage("Espere un momento mientras el sistema sube su información a la base de datos");

        sharedPreferences = getSharedPreferences("misDatos", 0);
        nombre_web = sharedPreferences.getString("nombrePagWeb","");

        editTitulo.setText(sharedPreferences.getString("web_servicios_titulo_home", ""));
        if (sharedPreferences.getString("web_servicios_img_seccion_1","").length() > 1){
            imgUpoloaded = true;
            Picasso.get().load(sharedPreferences.getString("web_servicios_img_seccion_1","")).into(img);
        }
        if (sharedPreferences.getString("web_servicios_logo_seccion_1","").length() > 1){
            imgUpoloaded2 = true;
            Picasso.get().load(sharedPreferences.getString("web_servicios_logo_seccion_1","")).into(logo);
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

                                StorageReference refSubirImagen = storage.getReferenceFromUrl("gs://pyme-assistant.appspot.com/web/userid").child(nombre_web + "imagen1");

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
                                        editor.putString("web_servicios_img_seccion_1", url.toString());
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

        btnAddLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PickImageDialog.build(new PickSetup().setTitle("Escoge una opción")
                        .setCameraButtonText("Cámara")
                        .setGalleryButtonText("Galería"))
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                //TODO: do what you have to...
                                logo.setImageBitmap(r.getBitmap());
                                progressDialog.show();

                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                r.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                byte[] data = outputStream.toByteArray();

                                StorageReference refSubirImagen = storage.getReferenceFromUrl("gs://pyme-assistant.appspot.com/web/userid").child(nombre_web + "logo");

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
                                        editor.putString("web_servicios_logo_seccion_1", url.toString());
                                        editor.commit();
                                        imgUpoloaded2 = true;
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

                if(!imgUpoloaded){
                    Toast.makeText(getApplicationContext(), "Para continuar debes subir la imagen que irá en la sección de home", Toast.LENGTH_LONG).show();
                    return;
                }

                else if(!imgUpoloaded2){
                    Toast.makeText(getApplicationContext(), "Para continuar debes subir tu logotipo", Toast.LENGTH_LONG).show();
                    return;
                }

                if(titulo.length() > 0){
                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("web_servicios_titulo_home", titulo);
                    editor.commit();
                    // ******************************************************************************
                }
                else{
                    Toast.makeText(getApplicationContext(), "Para continuar debes escribir el titulo que llevará la sección de home", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(WebsServiciosSeccion1.this, WebsServiciosSeccion2.class);
                startActivity(i);
            }
        });

    }
}
