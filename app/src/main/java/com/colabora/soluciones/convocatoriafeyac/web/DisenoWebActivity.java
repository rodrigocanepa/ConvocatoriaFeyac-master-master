package com.colabora.soluciones.convocatoriafeyac.web;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class DisenoWebActivity extends AppCompatActivity {

    private Button btnSubirFoto;
    private Button btnSubirFoto2;
    private Button btnGuardar;
    private Button btnGuardar2;
    private Button btnGuardar3;
    private TextInputEditText editTitulo1;
    private TextInputEditText editTexto;
    private ImageView imgFoto1;
    private ImageView imgFoto2;
    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private TextInputEditText editTitulo2;
    private TextInputEditText editTexto2;
    private TextInputEditText editTituloDevice;
    private TextInputEditText editTextoDevice;
    private TextInputEditText editTituloFlexible;
    private TextInputEditText editTextoFlexible;

    private FirebaseFirestore db;

    private Map<String, Object> web;
    private Map<String, Object> secciones;
    private Map<String, Object> contact;
    private Map<String, Object> cta;
    private Map<String, Object> download;
    private Map<String, Object> features;
    private Map<String, Object> home;

    private String nombreUrl = "pymeapp";

    // variables contacto
    private String contacto_facebook = "";
    private String contacto_instagram = "";
    private String contacto_navbar = "Contacto";
    private String contacto_title = "Nosotros amamos a los nuevos amigos";
    private String contacto_twitter = "";
    private String contacto_web = "";

    // variables cta
    private String cta_button = "Unete";
    private String cta_title = "Podemos hacerlo";

    // variables download
    private String download_appstore = "";
    private String download_color = "#fafafa";
    private String download_googleplay = "";
    private String download_navbar = "Descargar";
    private String download_subtitle = "";
    private String download_title = "";

    // variables features
    private String features_buttonDevice = "";
    private String features_camera = "";
    private String features_camerasub = "";
    private String features_device = "";
    private String features_devicesub = "";
    private String features_gift = "";
    private String features_giftsub = "";
    private String features_imgDevice = "";
    private String features_lock = "";
    private String features_lockSub = "";
    private String features_navbar = "Servicios";
    private String features_subtitle = "";
    private String features_title = "";

    // variables home
    private String home_button = "";
    private String home_buttonDevice = "tooltip";
    private int home_gradiente = 10;
    private String home_imgDevice = "";
    private String home_title = "";

    FirebaseStorage storage = FirebaseStorage.getInstance();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseno_web);

        btnSubirFoto = (Button)findViewById(R.id.btnSubirFoto1);
        btnSubirFoto2 = (Button)findViewById(R.id.btnSubirFoto2);
        btnGuardar = (Button)findViewById(R.id.btnGuardar1);
        btnGuardar2 = (Button)findViewById(R.id.btnGuardar2);
        btnGuardar3 = (Button)findViewById(R.id.btnGuardar3);
        editTitulo1 = (TextInputEditText)findViewById(R.id.txtTitulo1);
        editTexto = (TextInputEditText)findViewById(R.id.txtBoton1);
        editTitulo2 = (TextInputEditText)findViewById(R.id.txtTitulo2);
        editTexto2 = (TextInputEditText)findViewById(R.id.txtBoton2);
        editTituloDevice = (TextInputEditText)findViewById(R.id.txtTituloDevice);
        editTextoDevice = (TextInputEditText)findViewById(R.id.txtSubtituloDevice);
        editTituloFlexible = (TextInputEditText)findViewById(R.id.txtTituloFlexible);
        editTextoFlexible = (TextInputEditText)findViewById(R.id.txtSubtituloFlexible);
        linearLayout1 = (LinearLayout)findViewById(R.id.linear1);
        linearLayout2 = (LinearLayout)findViewById(R.id.linear2);
        linearLayout3 = (LinearLayout)findViewById(R.id.linear3);

        imgFoto1 = (ImageView)findViewById(R.id.imgImagen1);
        imgFoto2 = (ImageView)findViewById(R.id.imgImagen2);

        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(DisenoWebActivity.this);

        progressDialog.setTitle("Subiendo Información");
        progressDialog.setMessage("Espere un momento mientras el sistema sube su información a la base de datos");

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
                                imgFoto1.setImageBitmap(r.getBitmap());

                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                r.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                byte[] data = outputStream.toByteArray();

                                StorageReference refSubirImagen = storage.getReferenceFromUrl("gs://pyme-assistant.appspot.com/web/userid").child(nombreUrl + "imagen1");

                                UploadTask uploadTask = refSubirImagen.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        if (progressDialog.isShowing()){
                                            progressDialog.dismiss();
                                        }
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                        while(!uri.isComplete());
                                        Uri url = uri.getResult();
                                        home_imgDevice = url.toString();
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

        btnSubirFoto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup().setTitle("Escoge una opción")
                        .setCameraButtonText("Cámara")
                        .setGalleryButtonText("Galería"))
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                //TODO: do what you have to...
                                imgFoto2.setImageBitmap(r.getBitmap());

                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                r.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                byte[] data = outputStream.toByteArray();

                                StorageReference refSubirImagen = storage.getReferenceFromUrl("gs://pyme-assistant.appspot.com/web/userid").child(nombreUrl + "imagen2");

                                UploadTask uploadTask = refSubirImagen.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        if (progressDialog.isShowing()){
                                            progressDialog.dismiss();
                                        }
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                        while(!uri.isComplete());
                                        Uri url = uri.getResult();
                                        features_imgDevice = url.toString();
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

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                home_button = editTexto.getText().toString();
                home_title = editTitulo1.getText().toString();
                linearLayout1.setVisibility(View.INVISIBLE);
                linearLayout2.setVisibility(View.VISIBLE);
                linearLayout3.setVisibility(View.INVISIBLE);

                /*
                Map<String, Object> city = new HashMap<>();
                city.put("name", "Los Angeles");
                city.put("state", "CA");
                city.put("country", "USA");

                db.collection("cities").document("LA")
                        .set(city)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"DocumentSnapshot successfully written!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"DocumentSnapshot failed!", Toast.LENGTH_LONG).show();
                            }
                        });

                        */
            }
        });

        btnGuardar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                download_title = editTitulo2.getText().toString();
                download_subtitle = editTexto2.getText().toString();
                linearLayout1.setVisibility(View.INVISIBLE);
                linearLayout2.setVisibility(View.INVISIBLE);
                linearLayout3.setVisibility(View.VISIBLE);

            }
        });

        btnGuardar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                features_device = editTituloDevice.getText().toString();
                features_devicesub = editTextoDevice.getText().toString();

                features_camera = editTituloFlexible.getText().toString();
                features_camerasub = editTextoFlexible.getText().toString();

                web = new HashMap<>();
                secciones = new HashMap<>();
                home = new HashMap<>();
                features = new HashMap<>();
                download = new HashMap<>();
                cta = new HashMap<>();
                contact = new HashMap<>();

                contact.put("facebook", contacto_facebook);
                contact.put("instagram", contacto_instagram);
                contact.put("navbar", contacto_navbar);
                contact.put("title", contacto_title);
                contact.put("twitter", contacto_twitter);
                contact.put("web", contacto_web);

                cta.put("button", cta_button);
                cta.put("title", cta_title);

                download.put("appstore", download_appstore);
                download.put("color", download_color);
                download.put("googleplay", download_googleplay);
                download.put("navbar", download_navbar);
                download.put("subtitle", download_subtitle);
                download.put("title", download_title);

                features.put("button-device", features_buttonDevice);
                features.put("camera", features_camera);
                features.put("camerasub", features_camerasub);
                features.put("device", features_device);
                features.put("devicesub", features_devicesub);
                features.put("gift", features_gift);
                features.put("giftsub", features_giftsub);
                features.put("imgdevice", features_imgDevice);
                features.put("lock", features_lock);
                features.put("locksub", features_lockSub);
                features.put("navbar", features_navbar);
                features.put("subtitle", features_subtitle);
                features.put("title", features_title);

                home.put("button", home_button);
                home.put("button-device", home_buttonDevice);
                home.put("gradiente", home_gradiente);
                home.put("imgdevice", home_imgDevice);
                home.put("title", home_title);

                secciones.put("contact", contact);
                secciones.put("cta", cta);
                secciones.put("download", download);
                secciones.put("features", features);
                secciones.put("home", home);

                web.put("icon", "url");
                web.put("secciones", secciones);
                web.put("title", "Go Healthy");

                db.collection("webs").document(nombreUrl)
                        .set(web)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"DocumentSnapshot successfully written!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"DocumentSnapshot failed!", Toast.LENGTH_LONG).show();
                            }
                        });


                linearLayout1.setVisibility(View.INVISIBLE);
                linearLayout2.setVisibility(View.INVISIBLE);
                linearLayout3.setVisibility(View.VISIBLE);

            }
        });
    }
}
