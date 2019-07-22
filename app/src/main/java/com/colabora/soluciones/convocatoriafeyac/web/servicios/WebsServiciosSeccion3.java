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

public class WebsServiciosSeccion3 extends AppCompatActivity {

    private ImageView img1;
    private ImageView img2;
    private TextInputEditText txtTitulo1;
    private TextInputEditText txtDescripcion1;
    private TextInputEditText txtTitulo2;
    private TextInputEditText txtDescripcion2;
    private Button btnImg1;
    private Button btnImg2;
    private Button btnSiguiente;

    private boolean imgUploaded1 = false;
    private boolean imgUploaded2 = false;

    private String nombre_web = "";
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_servicios_seccion3);

        img1 = (ImageView)findViewById(R.id.img1ServicioSeccion3Img1);
        img2 = (ImageView)findViewById(R.id.img1ServicioSeccion3Img2);
        txtTitulo1 = (TextInputEditText) findViewById(R.id.txtServiciosSeccion3Titulo1);
        txtTitulo2 = (TextInputEditText)findViewById(R.id.txtServiciosSeccion3Titulo2);
        txtDescripcion1 = (TextInputEditText)findViewById(R.id.txtServiciosSeccion3Descripcion1);
        txtDescripcion2 = (TextInputEditText)findViewById(R.id.txtServiciosSeccion3Descripcion2);
        btnImg1 = (Button) findViewById(R.id.btnServiciosSeccion3AddImg1);
        btnImg2 = (Button) findViewById(R.id.btnServiciosSeccion3AddImg2);
        btnSiguiente = (Button) findViewById(R.id.btnServiciosSeccion3Siguiente);

        sharedPreferences = getSharedPreferences("misDatos", 0);
        nombre_web = sharedPreferences.getString("nombrePagWeb","");

        progressDialog = new ProgressDialog(WebsServiciosSeccion3.this);

        progressDialog.setTitle("Subiendo Información");
        progressDialog.setMessage("Espere un momento mientras el sistema sube su información a la base de datos");

        txtTitulo1.setText(sharedPreferences.getString("web_servicios_seccion_3_titulo1", ""));
        txtTitulo2.setText(sharedPreferences.getString("web_servicios_seccion_3_titulo2", ""));
        txtDescripcion1.setText(sharedPreferences.getString("web_servicios_seccion_3_descripcion1", ""));
        txtDescripcion2.setText(sharedPreferences.getString("web_servicios_seccion_3_descripcion2", ""));
        if (sharedPreferences.getString("web_servicios_img1_seccion_3","").length() > 1){
            imgUploaded1 = true;
            Picasso.get().load(sharedPreferences.getString("web_servicios_img1_seccion_3","")).into(img1);
        }
        if (sharedPreferences.getString("web_servicios_img2_seccion_3","").length() > 1){
            imgUploaded2 = true;
            Picasso.get().load(sharedPreferences.getString("web_servicios_img2_seccion_3","")).into(img2);
        }



        btnImg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup().setTitle("Escoge una opción")
                        .setCameraButtonText("Cámara")
                        .setGalleryButtonText("Galería"))
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                //TODO: do what you have to...
                                img1.setImageBitmap(r.getBitmap());

                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                r.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                byte[] data = outputStream.toByteArray();

                                StorageReference refSubirImagen = storage.getReferenceFromUrl("gs://pyme-assistant.appspot.com/web/userid").child(nombre_web + "imagen3");

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
                                        Toast.makeText(getApplicationContext(), "Foto subida exitosamente", Toast.LENGTH_LONG).show();
                                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                        while(!uri.isComplete());
                                        Uri url = uri.getResult();
                                        // *********** Guardamos los principales datos de los nuevos usuarios *************
                                        sharedPreferences = getSharedPreferences("misDatos", 0);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("web_servicios_img1_seccion_3", url.toString());
                                        editor.commit();
                                        imgUploaded1 = true;
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

        btnImg2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup().setTitle("Escoge una opción")
                        .setCameraButtonText("Cámara")
                        .setGalleryButtonText("Galería"))
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                //TODO: do what you have to...
                                img2.setImageBitmap(r.getBitmap());

                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                r.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                byte[] data = outputStream.toByteArray();

                                StorageReference refSubirImagen = storage.getReferenceFromUrl("gs://pyme-assistant.appspot.com/web/userid").child(nombre_web + "imagen4");

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
                                        Toast.makeText(getApplicationContext(), "Foto subida exitosamente", Toast.LENGTH_LONG).show();
                                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                        while(!uri.isComplete());
                                        Uri url = uri.getResult();
                                        // *********** Guardamos los principales datos de los nuevos usuarios *************
                                        sharedPreferences = getSharedPreferences("misDatos", 0);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("web_servicios_img2_seccion_3", url.toString());
                                        editor.commit();
                                        imgUploaded2 = true;
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
                String titulo1 = txtTitulo1.getText().toString();
                String titulo2 = txtTitulo2.getText().toString();
                String descripcion1 = txtDescripcion1.getText().toString();
                String descripcion2 = txtDescripcion2.getText().toString();

                if(titulo1.length() == 0){
                    Toast.makeText(getApplicationContext(), "Debes completar los campos requeridos", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(titulo2.length() == 0){
                    Toast.makeText(getApplicationContext(), "Debes completar los campos requeridos", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(descripcion1.length() == 0){
                    Toast.makeText(getApplicationContext(), "Debes completar los campos requeridos", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(descripcion2.length() == 0){
                    Toast.makeText(getApplicationContext(), "Debes completar los campos requeridos", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!imgUploaded1){
                    Toast.makeText(getApplicationContext(), "Debes subir las imagenes correspondientes", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!imgUploaded2){
                    Toast.makeText(getApplicationContext(), "Debes subir las imagenes correspondientes", Toast.LENGTH_LONG).show();
                    return;
                }

                // *********** Guardamos los principales datos de los nuevos usuarios *************
                sharedPreferences = getSharedPreferences("misDatos", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("web_servicios_seccion_3_titulo1", titulo1);
                editor.putString("web_servicios_seccion_3_titulo2", titulo2);
                editor.putString("web_servicios_seccion_3_descripcion1", descripcion1);
                editor.putString("web_servicios_seccion_3_descripcion2", descripcion2);
                editor.commit();
                // ******************************************************************************

                Intent i = new Intent(WebsServiciosSeccion3.this, WebsServiciosSeccion4.class);
                startActivity(i);
            }
        });
    }
}
