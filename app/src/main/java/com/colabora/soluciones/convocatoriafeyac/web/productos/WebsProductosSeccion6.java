package com.colabora.soluciones.convocatoriafeyac.web.productos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.Modelos.Horario;
import com.colabora.soluciones.convocatoriafeyac.Modelos.WebImagen;
import com.colabora.soluciones.convocatoriafeyac.Modelos.caracteristicas_web;
import com.colabora.soluciones.convocatoriafeyac.Modelos.pagWebs;
import com.colabora.soluciones.convocatoriafeyac.R;
import com.colabora.soluciones.convocatoriafeyac.web.salud.WebsSaludSeccion6;
import com.colabora.soluciones.convocatoriafeyac.web.salud.WebsSaludSeccion7;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebsProductosSeccion6 extends AppCompatActivity {

    private Button btnSiguiente;
    private TextInputEditText editUbicacion;
    private TextInputEditText editEmail;
    private TextInputEditText editTelefono;
    private String ubicacion = "";
    private String email = "";
    private String telefono = "";

    private SharedPreferences sharedPreferences;

    private ProgressDialog progressDialog;

    private FirebaseFirestore db;

    private Map<String, Object> web;
    private Map<String, Object> home;
    private Map<String, Object> about;
    private Map<String, Object> servicios;
    private Map<String, Object> imagencontacto;
    private Map<String, Object> galeria;
    private Map<String, Object> contacto;

    private List<caracteristicas_web> servicio = new ArrayList<>();
    private List<caracteristicas_web> imagenes = new ArrayList<>();
    private List<WebImagen> imagen = new ArrayList<>();

    // variables home
    private String home_navbar = "";
    private String home_imagen = "";
    private String home_titulo = "";
    private String home_subtitulo = "";

    // variables about
    private String about_navbar = "";
    private String about_titulo = "";
    private String about_descripcion = "";
    private String about_imagen = "";
    private String about_subtitulo = "";

    // variables servicios
    private String servicios_navbar = "";
    private String servicios_titulo = "";


    // variables imagencontacto
    private String imagencontacto_titulo = "";
    private String imagencontacto_subtitulo = "";
    private String imagencontacto_imagen = "";

    // variables galeria
    private String galeria_titulo = "";
    private String galeria_navbar = "";

    // variables contacto
    private String contacto_navbar = "";
    private String contacto_titulo = "";
    private String contacto_telefono = "";
    private String contacto_email = "";
    private String contacto_ubicacion = "";

    private String imagen_ = "";
    private String titulo = "";
    private String descripcion = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_productos_seccion6);

        btnSiguiente = (Button)findViewById(R.id.btnProductosSeccion6Siguiente);
        editUbicacion = (TextInputEditText)findViewById(R.id.txtProductosSeccion6Ubicacion);
        editEmail= (TextInputEditText)findViewById(R.id.txtProductosSeccion6Email);
        editTelefono = (TextInputEditText)findViewById(R.id.txtProductosSeccion6Telefono);

        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(WebsProductosSeccion6.this);

        progressDialog.setTitle("Subiendo Información");
        progressDialog.setMessage("Espere un momento mientras el sistema sube su información a la base de datos");
        sharedPreferences = getSharedPreferences("misDatos", 0);

        editEmail.setText(sharedPreferences.getString("web_productos_email_contacto", ""));
        editUbicacion.setText(sharedPreferences.getString("web_productos_ubicacion_contacto", ""));
        editTelefono.setText(sharedPreferences.getString("web_productos_telefono_contacto", ""));


        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ubicacion = editUbicacion.getText().toString();
                telefono = editTelefono.getText().toString();
                email = editEmail.getText().toString();


                if(ubicacion.length() > 0 && telefono.length() > 0 && email.length() > 0){
                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("web_productos_ubicacion_contacto", ubicacion);
                    editor.putString("web_productos_email_contacto", email);
                    editor.putString("web_productos_telefono_contacto", telefono);
                    editor.commit();
                    // ******************************************************************************
                }
                else{
                    Toast.makeText(getApplicationContext(), "Para continuar debes escribir los campos requeridos de contacto", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog.show();

                web = new HashMap<>();
                home = new HashMap<>();
                about = new HashMap<>();
                servicios = new HashMap<>();
                imagencontacto = new HashMap<>();
                galeria = new HashMap<>();
                contacto = new HashMap<>();

                home_titulo = sharedPreferences.getString("web_productos_titulo_home", "");
                home_subtitulo = sharedPreferences.getString("web_productos_subtitulo_home", "");
                home_navbar = "Inicio";
                imagen.add(new WebImagen(sharedPreferences.getString("web_productos_img_1_seccion_1","")));
                imagen.add(new WebImagen(sharedPreferences.getString("web_productos_img_2_seccion_1","")));
                if(sharedPreferences.getString("web_productos_img_3_seccion_1", "").length() > 2){
                    imagen.add(new WebImagen(sharedPreferences.getString("web_productos_img_3_seccion_1","")));
                }

                about_navbar = "Nosotros";
                about_titulo = sharedPreferences.getString("web_productos_titulo_seccion_2", "");
                about_imagen = sharedPreferences.getString("web_productos_img_seccion_2", "");
                about_subtitulo = sharedPreferences.getString("web_productos_subtitulo_seccion_2", "");
                about_descripcion = sharedPreferences.getString("web_productos_descripcion_seccion_2", "");

                servicios_navbar = "Servicios";
                servicios_titulo = sharedPreferences.getString("web_productos_seccion_3_titulo", "");
                if(sharedPreferences.getString("web_productos_seccion_3_recycler","").equals("1")){
                    imagen_ = "";
                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica1_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));
                }
                else if(sharedPreferences.getString("web_productos_seccion_3_recycler","").equals("2")){
                    imagen_ = "";
                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica1_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = "";
                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica2_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                }

                else if(sharedPreferences.getString("web_productos_seccion_3_recycler","").equals("3")){
                    imagen_ = "";
                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica1_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = "";
                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica2_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica3_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica3_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                }

                else if(sharedPreferences.getString("web_productos_seccion_3_recycler","").equals("4")){
                    imagen_ = "";
                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica1_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = "";
                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica2_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica3_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica3_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica4_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica4_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                }

                else if(sharedPreferences.getString("web_productos_seccion_3_recycler","").equals("5")){
                    imagen_ = "";
                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica1_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = "";
                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica2_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica3_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica3_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica4_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica4_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica5_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica5_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                }

                else if(sharedPreferences.getString("web_productos_seccion_3_recycler","").equals("6")){
                    imagen_ = "";
                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica1_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = "";
                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica2_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica3_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica3_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica4_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica4_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica5_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica5_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    titulo = sharedPreferences.getString("web_productos_seccion_3_caracteristica6_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_3_caracteristica6_descripcion","");

                    servicio.add(new caracteristicas_web(imagen_, titulo, descripcion));

                }

                imagencontacto_imagen = sharedPreferences.getString("web_productos_img_seccion_4", "");
                imagencontacto_titulo = sharedPreferences.getString("web_productos_titulo_seccion_4", "");
                imagencontacto_subtitulo = sharedPreferences.getString("web_productos_subtitulo_seccion_4", "");

                galeria_navbar = "Galería";
                galeria_titulo = "Nuestro Portafolio";

                if(sharedPreferences.getString("web_productos_seccion_5_recycler","").equals("1")){
                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));
                }

                else if(sharedPreferences.getString("web_productos_seccion_5_recycler","").equals("2")){
                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica2_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica2_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));
                }

                else if(sharedPreferences.getString("web_productos_seccion_5_recycler","").equals("3")){
                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica2_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica2_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica3_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica3_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica3_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));
                }
                else if(sharedPreferences.getString("web_productos_seccion_5_recycler","").equals("4")){
                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica2_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica2_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica3_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica3_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica3_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica4_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica4_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica4_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));
                }
                else if(sharedPreferences.getString("web_productos_seccion_5_recycler","").equals("5")){
                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica2_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica2_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica3_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica3_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica3_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica4_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica4_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica4_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica5_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica5_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica5_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));
                }

                else if(sharedPreferences.getString("web_productos_seccion_5_recycler","").equals("6")){
                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica1_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica2_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica2_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica2_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica3_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica3_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica3_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica4_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica4_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica4_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica5_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica5_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica5_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));

                    imagen_ = sharedPreferences.getString("web_productos_seccion_5_caracteristica6_url", "");
                    titulo = sharedPreferences.getString("web_productos_seccion_5_caracteristica6_titulo","");
                    descripcion = sharedPreferences.getString("web_productos_seccion_5_caracteristica6_descripcion","");

                    imagenes.add(new caracteristicas_web(imagen_, titulo, descripcion));
                }

                contacto_navbar = "Contacto";
                contacto_titulo = "Contacto";
                contacto_telefono = sharedPreferences.getString("web_productos_telefono_contacto","");
                contacto_email = sharedPreferences.getString("web_productos_email_contacto","");
                contacto_ubicacion = sharedPreferences.getString("web_productos_ubicacion_contacto","");

                home.put("navbar", home_navbar);
                home.put("titulo", home_titulo);
                home.put("subtitulo", home_subtitulo);
                home.put("imagen", imagen);

                about.put("navbar", about_navbar);
                about.put("titulo", about_titulo);
                about.put("imagen", about_imagen);
                about.put("subtitulo", about_subtitulo);
                about.put("descripcion", about_descripcion);

                servicios.put("navbar", servicios_navbar);
                servicios.put("titulo", servicios_navbar);
                servicios.put("servicio", servicio);

                imagencontacto.put("imagen",imagencontacto_imagen);
                imagencontacto.put("titulo",imagencontacto_titulo);
                imagencontacto.put("subtitulo",imagencontacto_subtitulo);

                galeria.put("navbar", galeria_navbar);
                galeria.put("titulo", galeria_titulo);
                galeria.put("imagenes", imagenes);

                contacto.put("navbar", contacto_navbar);
                contacto.put("titulo", contacto_titulo);
                contacto.put("telefono", contacto_telefono);
                contacto.put("email", contacto_email);
                contacto.put("lugar", contacto_ubicacion);

                web.put("home", home);
                web.put("about", about);
                web.put("servicios", servicios);
                web.put("imagencontacto", imagencontacto);
                web.put("galeria", galeria);
                web.put("contacto", contacto);

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String url = sharedPreferences.getString("nombrePagWeb", "");
                pagWebs pag = new pagWebs("",web, 2, user.getUid(), url);

                db.collection("webs").document(sharedPreferences.getString("nombrePagWeb", ""))
                        .set(pag)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(user.getUid() + "-tipo_mi_pag_web", "2");
                                editor.commit();
                                Toast.makeText(getApplicationContext(),"¡Página web creada exitosamente!", Toast.LENGTH_LONG).show();
                                String url = "http://products.solucionescolabora.com/u/" + sharedPreferences.getString("nombrePagWeb", "");
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
