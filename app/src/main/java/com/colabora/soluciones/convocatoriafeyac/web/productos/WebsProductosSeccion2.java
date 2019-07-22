package com.colabora.soluciones.convocatoriafeyac.web.productos;

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

public class WebsProductosSeccion2 extends AppCompatActivity {

    private Button btnSiguiente;
    private Button btnSubirFoto;
    private TextInputEditText editTitulo;
    private TextInputEditText editDescripcion;
    private TextInputEditText editDescripcio2;
    private ImageView img;
    private String nombre_web = "";
    private String titulo = "";
    private String subtitulo = "";
    private String descripcion = "";
    private boolean imgUpoloaded = false;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_productos_seccion2);

        btnSiguiente = (Button)findViewById(R.id.btnProductoSeccion2Siguiente);
        btnSubirFoto = (Button)findViewById(R.id.btnProductoSeccion2);

        img = (ImageView)findViewById(R.id.imgProductoSeccion2);

        editTitulo = (TextInputEditText)findViewById(R.id.txt_web_producto_seccion2_texto1);
        editDescripcion = (TextInputEditText)findViewById(R.id.txt_web_producto_seccion2_descripcion1);
        editDescripcio2 = (TextInputEditText)findViewById(R.id.txt_web_producto_seccion2_descripcion2);

        progressDialog = new ProgressDialog(WebsProductosSeccion2.this);

        progressDialog.setTitle("Subiendo Información");
        progressDialog.setMessage("Espere un momento mientras el sistema sube su información a la base de datos");

        sharedPreferences = getSharedPreferences("misDatos", 0);
        nombre_web = sharedPreferences.getString("nombrePagWeb","");

        editTitulo.setText(sharedPreferences.getString("web_productos_titulo_seccion_2", ""));
        editDescripcion.setText(sharedPreferences.getString("web_productos_subtitulo_seccion_2", ""));
        editDescripcio2.setText(sharedPreferences.getString("web_productos_descripcion_seccion_2", ""));
        if (sharedPreferences.getString("web_productos_img_seccion_2","").length() > 1){
            imgUpoloaded = true;
            Picasso.get().load(sharedPreferences.getString("web_productos_img_seccion_2","")).into(img);
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
                                        Toast.makeText(getApplicationContext(), "Imagen subida exitosamente", Toast.LENGTH_LONG).show();
                                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                        while(!uri.isComplete());
                                        Uri url = uri.getResult();
                                        // *********** Guardamos los principales datos de los nuevos usuarios *************
                                        sharedPreferences = getSharedPreferences("misDatos", 0);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("web_productos_img_seccion_2", url.toString());
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
                subtitulo = editDescripcion.getText().toString();
                descripcion = editDescripcio2.getText().toString();

                if(!imgUpoloaded){
                    Toast.makeText(getApplicationContext(), "Para continuar debes subir la imagen que irá en esta sección", Toast.LENGTH_LONG).show();
                    return;
                }

                if(titulo.length() > 0 && subtitulo.length() > 0 && descripcion.length() > 0){
                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("web_productos_titulo_seccion_2", titulo);
                    editor.putString("web_productos_subtitulo_seccion_2", subtitulo);
                    editor.putString("web_productos_descripcion_seccion_2", descripcion);
                    editor.commit();
                    // ******************************************************************************
                }
                else{
                    Toast.makeText(getApplicationContext(), "Para continuar debes escribir los campos requeridos en esta sección", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent i = new Intent(WebsProductosSeccion2.this, WebsProductosSeccion3.class);
                startActivity(i);
            }
        });
    }
}
