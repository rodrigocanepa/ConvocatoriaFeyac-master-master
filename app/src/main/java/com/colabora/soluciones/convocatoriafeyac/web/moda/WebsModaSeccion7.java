package com.colabora.soluciones.convocatoriafeyac.web.moda;

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

import com.colabora.soluciones.convocatoriafeyac.Modelos.caracteristicas_web;
import com.colabora.soluciones.convocatoriafeyac.Modelos.pagWebs;
import com.colabora.soluciones.convocatoriafeyac.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebsModaSeccion7 extends AppCompatActivity {

    private Button btnSiguiente;
    private TextInputEditText editFacebook;
    private TextInputEditText editTwitter;
    private TextInputEditText editInstagram;
    private String nombre_web = "";
    private String facebook = "";
    private String twitter = "";
    private String instagram = "";

    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;

    private Map<String, Object> web;
    private Map<String, Object> home;
    private Map<String, Object> about;
    //private Map<String, Object> work;
    private Map<String, Object> services;
    private Map<String, Object> gallery;
    private Map<String, Object> contact;
    private Map<String, Object> social;

    private List<caracteristicas_web> work = new ArrayList<>();
    private List<caracteristicas_web> list = new ArrayList<>();
    private List<caracteristicas_web> imagen = new ArrayList<>();

    // variables home
    private String home_navbar = "";
    private String home_subtitulo = "";
    private String home_imagen = "";
    private String home_titulo = "";
    private String home_text = "";

    // variables about
    private String about_navbar = "";
    private String about_titulo = "";
    private String about_subtitulo = "";
    private String about_descripcion = "";
    private String about_imagen = "";

    // variables servicios
    private String servicios_navbar = "";
    private String servicios_imagen = "";

    // variables galleria
    private String galeria_navbar = "";
    private String galeria_titulo = "";
    private String galeria_subtitulo = "";;

    // variables contacto
    private String contacto_navbar = "";
    private String contacto_titulo = "";
    private String contacto_telefono = "";
    private String contacto_email = "";
    private String contacto_lugar = "";
    private String contacto_img = "";

    // variables social
    private String social_facebook = "";
    private String social_twitter = "";
    private String social_instagram = "";

    private String img = "";
    private String titulo = "";
    private String descripcion = "";

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_moda_seccion7);

        btnSiguiente = (Button)findViewById(R.id.btnModaSeccion7Siguiente);
        editFacebook = (TextInputEditText)findViewById(R.id.txt_web_moda_seccion7_texto1);
        editInstagram = (TextInputEditText)findViewById(R.id.txt_web_moda_seccion7_descripcion_1);
        editTwitter = (TextInputEditText)findViewById(R.id.txt_web_moda_seccion7_subitutlo_1);

        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(WebsModaSeccion7.this);

        progressDialog.setTitle("Subiendo Información");
        progressDialog.setMessage("Espere un momento mientras el sistema sube su información a la base de datos");

        sharedPreferences = getSharedPreferences("misDatos", 0);
        nombre_web = sharedPreferences.getString("nombrePagWeb","");

        editTwitter.setText(sharedPreferences.getString("web_moda_twitter_seccion7", ""));
        editInstagram.setText(sharedPreferences.getString("web_moda_instagram_seccion7", ""));
        editFacebook.setText(sharedPreferences.getString("web_moda_facebook_seccion7", ""));

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebook = editFacebook.getText().toString();
                instagram = editInstagram.getText().toString();
                twitter = editTwitter.getText().toString();

                // *********** Guardamos los principales datos de los nuevos usuarios *************
                sharedPreferences = getSharedPreferences("misDatos", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("web_moda_facebook_seccion7", facebook);
                editor.putString("web_moda_instagram_seccion7", instagram);
                editor.putString("web_moda_twitter_seccion7", twitter);
                editor.commit();
                // ******************************************************************************

                progressDialog.show();

                web = new HashMap<>();
                home = new HashMap<>();
                about = new HashMap<>();
                services = new HashMap<>();
                gallery = new HashMap<>();
                contact = new HashMap<>();
                social = new HashMap<>();

                home_navbar = "Inicio";
                home_titulo = sharedPreferences.getString("web_moda_titulo_home", "");
                home_subtitulo = sharedPreferences.getString("web_moda_subtitulo_home","");
                home_text = sharedPreferences.getString("web_moda_descripcion_home","");
                home_imagen = sharedPreferences.getString("web_moda_img_seccion_1", "");

                about_navbar = "Empresa";
                about_titulo = sharedPreferences.getString("web_moda_titulo_seccion_2", "");
                about_subtitulo = sharedPreferences.getString("web_moda_subtitulo_seccion_2", "");
                about_descripcion = sharedPreferences.getString("web_moda_descripcion_seccion_2", "");
                about_imagen = sharedPreferences.getString("web_moda_img_seccion_2", "");

                if(sharedPreferences.getString("web_moda_seccion_3_recycler", "").equals("1")){
                    img = sharedPreferences.getString("web_moda_seccion_3_caracteristica1_url","");
                    titulo = sharedPreferences.getString("web_moda_seccion_3_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_moda_seccion_3_caracteristica1_descripcion","");

                    work.add(new caracteristicas_web(img, titulo, descripcion));
                }
                else if(sharedPreferences.getString("web_moda_seccion_3_recycler", "").equals("2")){
                    img = sharedPreferences.getString("web_moda_seccion_3_caracteristica1_url","");
                    titulo = sharedPreferences.getString("web_moda_seccion_3_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_moda_seccion_3_caracteristica1_descripcion","");

                    work.add(new caracteristicas_web(img, titulo, descripcion));

                    img = sharedPreferences.getString("web_moda_seccion_3_caracteristica2_url","");
                    titulo = sharedPreferences.getString("web_moda_seccion_3_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_moda_seccion_3_caracteristica2_descripcion","");

                    work.add(new caracteristicas_web(img, titulo, descripcion));
                }
                else if(sharedPreferences.getString("web_moda_seccion_3_recycler", "").equals("3")){
                    img = sharedPreferences.getString("web_moda_seccion_3_caracteristica1_url","");
                    titulo = sharedPreferences.getString("web_moda_seccion_3_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_moda_seccion_3_caracteristica1_descripcion","");

                    work.add(new caracteristicas_web(img, titulo, descripcion));

                    img = sharedPreferences.getString("web_moda_seccion_3_caracteristica2_url","");
                    titulo = sharedPreferences.getString("web_moda_seccion_3_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_moda_seccion_3_caracteristica2_descripcion","");

                    work.add(new caracteristicas_web(img, titulo, descripcion));

                    img = sharedPreferences.getString("web_moda_seccion_3_caracteristica3_url","");
                    titulo = sharedPreferences.getString("web_moda_seccion_3_caracteristica3_titulo","");
                    descripcion = sharedPreferences.getString("web_moda_seccion_3_caracteristica3_descripcion","");

                    work.add(new caracteristicas_web(img, titulo, descripcion));
                }

                servicios_navbar = "Servicios";
                servicios_imagen = sharedPreferences.getString("web_moda_img_seccion_4","");

                if(sharedPreferences.getString("web_servicios_seccion_4_recycler", "").equals("1")){
                    img = "";
                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));
                }
                else if(sharedPreferences.getString("web_servicios_seccion_4_recycler", "").equals("2")){
                    img = "";
                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));
                }
                else if(sharedPreferences.getString("web_servicios_seccion_4_recycler", "").equals("3")){
                    img = "";
                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));
                }
                else if(sharedPreferences.getString("web_servicios_seccion_4_recycler", "").equals("4")){
                    img = "";
                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));
                }

                else if(sharedPreferences.getString("web_servicios_seccion_4_recycler", "").equals("5")){
                    img = "";
                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica5_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica5_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));
                }

                else if(sharedPreferences.getString("web_servicios_seccion_4_recycler", "").equals("6")){
                    img = "";
                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica5_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica5_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica6_titulo","");
                    descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica6_descripcion","");

                    list.add(new caracteristicas_web(img, titulo, descripcion));
                }

                galeria_navbar = "Galería";
                galeria_titulo = sharedPreferences.getString("web_moda_titulo_seccion5","");
                galeria_subtitulo = sharedPreferences.getString("web_moda_subtitulo_seccion5","");

                imagen.add(new caracteristicas_web(sharedPreferences.getString("web_moda_img_1_seccion_5", ""), "", ""));
                imagen.add(new caracteristicas_web(sharedPreferences.getString("web_moda_img_2_seccion_5", ""), "", ""));
                imagen.add(new caracteristicas_web(sharedPreferences.getString("web_moda_img_3_seccion_5", ""), "", ""));

                if(sharedPreferences.getString("web_moda_img_4_seccion_5", "").length() > 2){
                    imagen.add(new caracteristicas_web(sharedPreferences.getString("web_moda_img_4_seccion_5", ""), "", ""));
                }
                if(sharedPreferences.getString("web_moda_img_5_seccion_5", "").length() > 2){
                    imagen.add(new caracteristicas_web(sharedPreferences.getString("web_moda_img_5_seccion_5", ""), "", ""));
                }
                if(sharedPreferences.getString("web_moda_img_6_seccion_5", "").length() > 2){
                    imagen.add(new caracteristicas_web(sharedPreferences.getString("web_moda_img_6_seccion_5", ""), "", ""));
                }
                if(sharedPreferences.getString("web_moda_img_7_seccion_5", "").length() > 2){
                    imagen.add(new caracteristicas_web(sharedPreferences.getString("web_moda_img_7_seccion_5", ""), "", ""));
                }
                if(sharedPreferences.getString("web_moda_img_8_seccion_5", "").length() > 2){
                    imagen.add(new caracteristicas_web(sharedPreferences.getString("web_moda_img_8_seccion_5", ""), "", ""));
                }
                if(sharedPreferences.getString("web_moda_img_9_seccion_5", "").length() > 2){
                    imagen.add(new caracteristicas_web(sharedPreferences.getString("web_moda_img_9_seccion_5", ""), "", ""));
                }

                contacto_lugar = sharedPreferences.getString("web_moda_direccion_seccion6", "");
                contacto_email = sharedPreferences.getString("web_moda_email_seccion6", "");
                contacto_telefono = sharedPreferences.getString("web_moda_telefono_seccion6", "");
                contacto_titulo = sharedPreferences.getString("web_moda_titulo_seccion6", "");
                contacto_navbar = "Contacto";
                contacto_img = sharedPreferences.getString("web_moda_img_seccion_6", "");

                social_facebook = sharedPreferences.getString("web_moda_facebook_seccion7","");
                social_instagram = sharedPreferences.getString("web_moda_instagram_seccion7","");
                social_twitter = sharedPreferences.getString("web_moda_twitter_seccion7","");

                social.put("facebook", social_facebook);
                social.put("twitter", social_twitter);
                social.put("instagram", social_instagram);

                contact.put("navbar", contacto_navbar);
                contact.put("title", contacto_titulo);
                contact.put("address", contacto_lugar);
                contact.put("phone", contacto_telefono);
                contact.put("email", contacto_email);
                contact.put("img", contacto_img);

                gallery.put("navbar", galeria_navbar);
                gallery.put("title", galeria_titulo);
                gallery.put("subtitle", galeria_subtitulo);
                gallery.put("categories", "");
                gallery.put("imagen", imagen);

                services.put("navbar", servicios_navbar);
                services.put("img", servicios_imagen);
                services.put("list", list);

                about.put("navbar", about_navbar);
                about.put("title", about_titulo);
                about.put("subtitle", about_subtitulo);
                about.put("text", about_descripcion);
                about.put("button", "");
                about.put("img", about_imagen);

                home.put("navbar", home_navbar);
                home.put("background", home_imagen);
                home.put("title", home_titulo);
                home.put("subtitle", home_subtitulo);
                home.put("text", home_text);

                web.put("home", home);
                web.put("about", about);
                web.put("work", work);
                web.put("services", services);
                web.put("gallery", gallery);
                web.put("contact", contact);
                web.put("social", social);

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                String url = sharedPreferences.getString("nombrePagWeb", "");
                pagWebs pag = new pagWebs("",web, 4, user.getUid(), url);


                db.collection("webs").document(sharedPreferences.getString("nombrePagWeb", ""))
                        .set(pag)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(user.getUid() + "-tipo_mi_pag_web", "4");
                                editor.commit();
                                Toast.makeText(getApplicationContext(),"¡Página web creada exitosamente!", Toast.LENGTH_LONG).show();
                                String url = "http://fashion.solucionescolabora.com/u/" + sharedPreferences.getString("nombrePagWeb", "");
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(getApplicationContext(),"Ha ocurrido un error, favor de volver a intentar", Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });

    }
}
