package com.colabora.soluciones.convocatoriafeyac.web.salud;

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
import com.colabora.soluciones.convocatoriafeyac.Modelos.caracteristicas_web;
import com.colabora.soluciones.convocatoriafeyac.Modelos.pagWebs;
import com.colabora.soluciones.convocatoriafeyac.R;
import com.colabora.soluciones.convocatoriafeyac.web.servicios.WebsServiciosSeccion7;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebsSaludSeccion7 extends AppCompatActivity {

    private Button btnSiguiente;
    private TextInputEditText editFacebook;
    private TextInputEditText editInstagram;
    private TextInputEditText editTwitter;
    private String facebook = "";
    private String instagram = "";
    private String twitter = "";

    private SharedPreferences sharedPreferences;

    private ProgressDialog progressDialog;

    private FirebaseFirestore db;

    private Map<String, Object> web;
    private Map<String, Object> home;
    private Map<String, Object> servicios;
    private Map<String, Object> horario;
    private Map<String, Object> about;
    private Map<String, Object> baner;
    private Map<String, Object> contacto;
    private Map<String, Object> sociales;

    private List<caracteristicas_web> servicio = new ArrayList<>();
    private List<Horario> hora = new ArrayList<>();
    private List<caracteristicas_web> caracteristicas = new ArrayList<>();

    // variables home
    private String home_navbar = "";
    private String home_logo = "";
    private String home_imagen = "";
    private String home_titulo = "";
    private String home_subtitulo = "";

    // variables servicios
    private String servicios_navbar = "";
    private String servicios_descripcion = "";
    private String servicios_titulo = "";

    // variables horarios
    private String horarios_titulo = "Horarios";

    // variables about
    private String about_navbar = "";
    private String about_titulo = "";
    private String about_descripcion = "";

    // variables banner
    private String banner_titulo = "";
    private String banner_descripcion = "";
    private String banner_autor = "";

    // variables contacto
    private String contacto_navbar = "";
    private String contacto_titulo = "";
    private String contacto_telefono = "";
    private String contacto_email = "";
    private String contacto_ubicacion = "";

    // variables sociales
    private String sociales_titulo = "";
    private String sociales_facebook = "";
    private String sociales_instagram = "";
    private String sociales_twitter = "";

    private String imagen = "";
    private String titulo = "";
    private String descripcion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_salud_seccion7);

        btnSiguiente = (Button)findViewById(R.id.btnSaludSeccion7Siguiente);
        editFacebook = (TextInputEditText)findViewById(R.id.txtSaludSeccion7Facebook);
        editInstagram= (TextInputEditText)findViewById(R.id.txtSaludSeccion7Instagram);
        editTwitter = (TextInputEditText)findViewById(R.id.txtSaludSeccion7Twitter);

        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(WebsSaludSeccion7.this);
        sharedPreferences = getSharedPreferences("misDatos", 0);

        progressDialog.setTitle("Subiendo Información");
        progressDialog.setMessage("Espere un momento mientras el sistema sube su información a la base de datos");

        editInstagram.setText(sharedPreferences.getString("web_salud_instagram_seccion_7", ""));
        editTwitter.setText(sharedPreferences.getString("web_salud_twitter_seccion_7", ""));
        editFacebook.setText(sharedPreferences.getString("web_salud_facebook_seccion_7", ""));

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebook = editFacebook.getText().toString();
                instagram = editInstagram.getText().toString();
                twitter = editTwitter.getText().toString();


                if(facebook.length() > 0 && instagram.length() > 0 && twitter.length() > 0){
                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("web_salud_facebook_seccion_7", facebook);
                    editor.putString("web_salud_instagram_seccion_7", instagram);
                    editor.putString("web_salud_twitter_seccion_7", twitter);
                    editor.commit();
                    // ******************************************************************************

                    progressDialog.show();

                    web = new HashMap<>();
                    home = new HashMap<>();
                    about = new HashMap<>();
                    servicios = new HashMap<>();
                    horario = new HashMap<>();
                    baner = new HashMap<>();
                    contacto = new HashMap<>();
                    sociales = new HashMap<>();

                    home_imagen = sharedPreferences.getString("web_salud_img_seccion_1", "");
                    home_titulo = sharedPreferences.getString("web_salud_titulo_home", "");
                    home_subtitulo = sharedPreferences.getString("web_salud_subtitulo_home", "");
                    home_navbar = "Inicio";
                    home_logo = "";

                    servicios_navbar = "Servicios";
                    servicios_titulo = sharedPreferences.getString("web_salud_seccion_2_titulo", "");
                    servicios_descripcion = sharedPreferences.getString("web_salud_seccion_2_descripcion", "");
                    if(sharedPreferences.getString("web_salud_seccion_2_recycler","").equals("1")){
                        imagen = "";
                        titulo = sharedPreferences.getString("web_salud_seccion_2_caracteristica1_titulo","");
                        descripcion = sharedPreferences.getString("web_salud_seccion_2_caracteristica1_descripcion","");

                        servicio.add(new caracteristicas_web(imagen, titulo, descripcion));
                    }
                    else if(sharedPreferences.getString("web_salud_seccion_2_recycler","").equals("2")){
                        imagen = "";
                        titulo = sharedPreferences.getString("web_salud_seccion_2_caracteristica1_titulo","");
                        descripcion = sharedPreferences.getString("web_salud_seccion_2_caracteristica1_descripcion","");

                        servicio.add(new caracteristicas_web(imagen, titulo, descripcion));

                        imagen = "";
                        titulo = sharedPreferences.getString("web_salud_seccion_2_caracteristica2_titulo","");
                        descripcion = sharedPreferences.getString("web_salud_seccion_2_caracteristica2_descripcion","");

                        servicio.add(new caracteristicas_web(imagen, titulo, descripcion));
                    }
                    else if(sharedPreferences.getString("web_salud_seccion_2_recycler","").equals("3")){
                        imagen = "";
                        titulo = sharedPreferences.getString("web_salud_seccion_2_caracteristica1_titulo","");
                        descripcion = sharedPreferences.getString("web_salud_seccion_2_caracteristica1_descripcion","");

                        servicio.add(new caracteristicas_web(imagen, titulo, descripcion));

                        imagen = "";
                        titulo = sharedPreferences.getString("web_salud_seccion_2_caracteristica2_titulo","");
                        descripcion = sharedPreferences.getString("web_salud_seccion_2_caracteristica2_descripcion","");

                        servicio.add(new caracteristicas_web(imagen, titulo, descripcion));

                        imagen = "";
                        titulo = sharedPreferences.getString("web_salud_seccion_2_caracteristica3_titulo","");
                        descripcion = sharedPreferences.getString("web_salud_seccion_2_caracteristica3_descripcion","");

                        servicio.add(new caracteristicas_web(imagen, titulo, descripcion));
                    }
                    else if(sharedPreferences.getString("web_salud_seccion_2_recycler","").equals("4")){
                        imagen = "";
                        titulo = sharedPreferences.getString("web_salud_seccion_2_caracteristica1_titulo","");
                        descripcion = sharedPreferences.getString("web_salud_seccion_2_caracteristica1_descripcion","");

                        servicio.add(new caracteristicas_web(imagen, titulo, descripcion));

                        imagen = "";
                        titulo = sharedPreferences.getString("web_salud_seccion_2_caracteristica2_titulo","");
                        descripcion = sharedPreferences.getString("web_salud_seccion_2_caracteristica2_descripcion","");

                        servicio.add(new caracteristicas_web(imagen, titulo, descripcion));

                        imagen = "";
                        titulo = sharedPreferences.getString("web_salud_seccion_2_caracteristica3_titulo","");
                        descripcion = sharedPreferences.getString("web_salud_seccion_2_caracteristica3_descripcion","");

                        servicio.add(new caracteristicas_web(imagen, titulo, descripcion));

                        imagen = "";
                        titulo = sharedPreferences.getString("web_salud_seccion_2_caracteristica4_titulo","");
                        descripcion = sharedPreferences.getString("web_salud_seccion_2_caracteristica4_descripcion","");

                        servicio.add(new caracteristicas_web(imagen, titulo, descripcion));
                    }

                    if(sharedPreferences.getString("web_salud_seccion_3_recycler", "").equals("1")){
                        imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica1_titulo","");
                        titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica1_descripcion","");

                        hora.add(new Horario(imagen, titulo, ""));
                    }

                    else if(sharedPreferences.getString("web_salud_seccion_3_recycler", "").equals("2")){
                        imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica1_titulo","");
                        titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica1_descripcion","");

                        hora.add(new Horario(imagen, titulo, ""));

                        imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica2_titulo","");
                        titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica2_descripcion","");

                        hora.add(new Horario(imagen, titulo, ""));
                    }

                    else if(sharedPreferences.getString("web_salud_seccion_3_recycler", "").equals("3")){
                        imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica1_titulo","");
                        titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica1_descripcion","");

                        hora.add(new Horario(imagen, titulo, ""));

                        imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica2_titulo","");
                        titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica2_descripcion","");

                        hora.add(new Horario(imagen, titulo, ""));

                        imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica3_titulo","");
                        titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica3_descripcion","");

                        hora.add(new Horario(imagen, titulo, ""));
                    }

                    else if(sharedPreferences.getString("web_salud_seccion_3_recycler", "").equals("4")){
                        imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica1_titulo","");
                        titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica1_descripcion","");

                        hora.add(new Horario(imagen, titulo, ""));

                        imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica2_titulo","");
                        titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica2_descripcion","");

                        hora.add(new Horario(imagen, titulo, ""));

                        imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica3_titulo","");
                        titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica3_descripcion","");

                        hora.add(new Horario(imagen, titulo, ""));

                        imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica4_titulo","");
                        titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica4_descripcion","");

                        hora.add(new Horario(imagen, titulo, ""));
                    }


                    about_navbar = "Generalidades";
                    about_titulo = sharedPreferences.getString("web_salud_seccion_4_titulo", "");
                    about_descripcion = sharedPreferences.getString("web_salud_seccion_4_descripcion", "");

                    if(sharedPreferences.getString("web_salud_seccion_4_recycler", "").equals("1")){
                        imagen = "";
                        titulo = sharedPreferences.getString("web_salud_seccion_4_caracteristica1_titulo","");
                        descripcion = sharedPreferences.getString("web_salud_seccion_4_caracteristica1_descripcion","");

                        caracteristicas.add(new caracteristicas_web(imagen, titulo, descripcion));
                    }
                    else if(sharedPreferences.getString("web_salud_seccion_4_recycler", "").equals("2")){
                        imagen = "";
                        titulo = sharedPreferences.getString("web_salud_seccion_4_caracteristica1_titulo","");
                        descripcion = sharedPreferences.getString("web_salud_seccion_4_caracteristica1_descripcion","");

                        caracteristicas.add(new caracteristicas_web(imagen, titulo, descripcion));

                        titulo = sharedPreferences.getString("web_salud_seccion_4_caracteristica2_titulo","");
                        descripcion = sharedPreferences.getString("web_salud_seccion_4_caracteristica2_descripcion","");

                        caracteristicas.add(new caracteristicas_web(imagen, titulo, descripcion));
                    }

                    else if(sharedPreferences.getString("web_salud_seccion_4_recycler", "").equals("3")){
                        imagen = "";
                        titulo = sharedPreferences.getString("web_salud_seccion_4_caracteristica1_titulo","");
                        descripcion = sharedPreferences.getString("web_salud_seccion_4_caracteristica1_descripcion","");

                        caracteristicas.add(new caracteristicas_web(imagen, titulo, descripcion));

                        titulo = sharedPreferences.getString("web_salud_seccion_4_caracteristica2_titulo","");
                        descripcion = sharedPreferences.getString("web_salud_seccion_4_caracteristica2_descripcion","");

                        caracteristicas.add(new caracteristicas_web(imagen, titulo, descripcion));

                        titulo = sharedPreferences.getString("web_salud_seccion_4_caracteristica3_titulo","");
                        descripcion = sharedPreferences.getString("web_salud_seccion_4_caracteristica3_descripcion","");

                        caracteristicas.add(new caracteristicas_web(imagen, titulo, descripcion));
                    }

                    banner_titulo = sharedPreferences.getString("web_salud_titulo_baner","");
                    banner_descripcion = sharedPreferences.getString("web_salud_frase_baner","");
                    banner_autor = sharedPreferences.getString("web_salud_autor_baner","");

                    contacto_navbar = "Contacto";
                    contacto_titulo = "Contacto";
                    contacto_email = sharedPreferences.getString("web_salud_email_contacto","");
                    contacto_ubicacion = sharedPreferences.getString("web_salud_ubicacion_contacto","");
                    contacto_telefono = sharedPreferences.getString("web_salud_telefono_contacto","");

                    sociales_titulo = "";
                    sociales_facebook = "";
                    sociales_instagram = "";
                    sociales_twitter = "";

                    sociales.put("titulo", sociales_titulo);
                    sociales.put("facebook", sociales_facebook);
                    sociales.put("twitter", sociales_twitter);
                    sociales.put("instagram", sociales_instagram);

                    contacto.put("navbar", contacto_navbar);
                    contacto.put("titulo", contacto_titulo);
                    contacto.put("telefono", contacto_telefono);
                    contacto.put("email", contacto_email);
                    contacto.put("lugar", contacto_ubicacion);

                    baner.put("titulo",banner_titulo);
                    baner.put("descripcion",banner_descripcion);
                    baner.put("autor",banner_autor);

                    about.put("navbar", about_navbar);
                    about.put("titulo", about_titulo);
                    about.put("descripcion", about_descripcion);
                    about.put("caracteristicas", caracteristicas);

                    horario.put("titulo", horarios_titulo);
                    horario.put("hora", hora);

                    servicios.put("navbar",servicios_navbar);
                    servicios.put("titulo",servicios_titulo);
                    servicios.put("descripcion",servicios_descripcion);
                    servicios.put("servicio",servicio);

                    home.put("navbar", home_navbar);
                    home.put("logo", home_logo);
                    home.put("imagen", home_imagen);
                    home.put("titulo", home_titulo);
                    home.put("subtitulo", home_subtitulo);

                    web.put("home", home);
                    web.put("servicios", servicios);
                    web.put("horario", horario);
                    web.put("about", about);
                    web.put("banner", baner);
                    web.put("contacto", contacto);
                    web.put("sociales", sociales);

                    final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String url = sharedPreferences.getString("nombrePagWeb", "");
                    pagWebs pag = new pagWebs("",web, 5, user.getUid(), url);

                    db.collection("webs").document(sharedPreferences.getString("nombrePagWeb", ""))
                            .set(pag)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    if(progressDialog.isShowing()){
                                        progressDialog.dismiss();
                                    }
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(user.getUid() + "-tipo_mi_pag_web", "5");
                                    editor.commit();
                                    Toast.makeText(getApplicationContext(),"¡Página web creada exitosamente!", Toast.LENGTH_LONG).show();
                                    String url = "http://health.solucionescolabora.com/u/" + sharedPreferences.getString("nombrePagWeb", "");
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
                else{
                    Toast.makeText(getApplicationContext(), "Para continuar debes escribir los campos requeridos", Toast.LENGTH_LONG).show();
                    return;
                }

            }
        });
    }
}
