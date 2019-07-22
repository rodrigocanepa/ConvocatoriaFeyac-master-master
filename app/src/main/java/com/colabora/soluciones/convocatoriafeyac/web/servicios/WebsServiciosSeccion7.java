package com.colabora.soluciones.convocatoriafeyac.web.servicios;

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

import com.colabora.soluciones.convocatoriafeyac.Modelos.Usuario;
import com.colabora.soluciones.convocatoriafeyac.Modelos.caracteristicas_web;
import com.colabora.soluciones.convocatoriafeyac.Modelos.pagWebs;
import com.colabora.soluciones.convocatoriafeyac.R;
import com.colabora.soluciones.convocatoriafeyac.web.DisenoWebActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebsServiciosSeccion7 extends AppCompatActivity {

    private TextInputEditText txtUbicacion;
    private TextInputEditText txtTelefono;
    private TextInputEditText txtEmail;
    private Button btnFinalizar;

    private SharedPreferences sharedPreferences;
    private ProgressDialog progressDialog;

    private FirebaseFirestore db;

    private Map<String, Object> web;
    private Map<String, Object> home;
    private Map<String, Object> about;
    //private Map<String, Object> caracteristicas;
    //private Map<String, Object> nosotros;
    private Map<String, Object> servicios;
    //private Map<String, Object> servicio;
    private Map<String, Object> banner;
   // private Map<String, Object> cuadroInfo;
    private Map<String, Object> portafolio;
    private Map<String, Object> contacto;

    private List<caracteristicas_web> caracteristicas = new ArrayList<>();
    private List<caracteristicas_web> nosotros = new ArrayList<>();
    private List<caracteristicas_web> servicio = new ArrayList<>();
    private List<caracteristicas_web> cuadroInfo = new ArrayList<>();
    private List<caracteristicas_web> imagenes = new ArrayList<>();

    // variables home
    private String home_navbar = "";
    private String home_logo = "";
    private String home_imagen = "";
    private String home_titulo = "";

    // variables about
    private String about_navbar = "";
    private String about_titulo = "";
    private String about_descripcion = "";
    private String about_imagen = "";
    private String about_Sdescripcion = "";

    // variables caracteristicas
    private String caracteristicas_imagen = "";
    private String caracteristicas_titulo = "";
    private String caracteristicas_descripcion = "";

    // variables nosotros
    private String nosotros_imagen = "";
    private String nosotros_titulo = "";
    private String nosotros_descripcion = "";

    // variables servicios
    private String servicios_navbar = "";
    private String servicios_titulo = "";
    private String servicios_descripcion = "";
    private String servicios_descripcion_ = "";

    // variables servicio
    private String servicio_imagen = "";
    private String servicio_titulo = "";
    private String servicio_descripcion = "";

    // variables baner
    private String baner_titulo = "";
    private String baner_descripcion = "";

    // variables cuadroInfo
    private String imagen = "";
    private String titulo = "";
    private String descripcion = "";

    // variables portafolio
    private String portafolio_navbar = "";
    private String portafolio_titulo = "";
    private String portafolio_imagenes = "";

    // variables contacto
    private String contacto_navbar = "";
    private String contacto_titulo = "";
    private String contacto_telefono = "";
    private String contacto_email = "";
    private String contacto_lugar = "";

    // variable webData
    private String web_icon = "";
    private int number = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_servicios_seccion7);

        btnFinalizar = (Button)findViewById(R.id.btnServiciosSeccion7Siguiente);
        txtUbicacion = (TextInputEditText) findViewById(R.id.txtServiciosSeccio7Ubacacion);
        txtEmail = (TextInputEditText)findViewById(R.id.txtServiciosSeccio7Email);
        txtTelefono = (TextInputEditText)findViewById(R.id.txtServiciosSeccio7Telefono);

        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(WebsServiciosSeccion7.this);

        progressDialog.setTitle("Subiendo Información");
        progressDialog.setMessage("Espere un momento mientras el sistema sube su información a la base de datos");

        sharedPreferences = getSharedPreferences("misDatos", 0);
        txtTelefono.setText(sharedPreferences.getString("web_servicios_telefono_contacto", ""));
        txtEmail.setText(sharedPreferences.getString("web_servicios_email_contacto", ""));
        txtUbicacion.setText(sharedPreferences.getString("web_servicios_ubicacion_contacto", ""));

        btnFinalizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ubicacion = txtUbicacion.getText().toString();
                String email = txtEmail.getText().toString();
                String telefono = txtTelefono.getText().toString();

                if(ubicacion.length() == 0){
                    Toast.makeText(getApplicationContext(), "Debes completar los campos requeridos", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(email.length() == 0){
                    Toast.makeText(getApplicationContext(), "Debes completar los campos requeridos", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(telefono.length() == 0){
                    Toast.makeText(getApplicationContext(), "Debes completar los campos requeridos", Toast.LENGTH_LONG).show();
                    return;
                }

                // *********** Guardamos los principales datos de los nuevos usuarios *************
                sharedPreferences = getSharedPreferences("misDatos", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("web_servicios_ubicacion_contacto", ubicacion);
                editor.putString("web_servicios_email_contacto", email);
                editor.putString("web_servicios_telefono_contacto", telefono);
                editor.commit();

                progressDialog.show();

                web = new HashMap<>();
                home = new HashMap<>();
                about = new HashMap<>();
                servicios = new HashMap<>();
                banner = new HashMap<>();
                portafolio = new HashMap<>();
                contacto = new HashMap<>();

                home_imagen = sharedPreferences.getString("web_servicios_img_seccion_1", "");
                home_titulo = sharedPreferences.getString("web_servicios_titulo_home", "");
                home_navbar = "Inicio";
                home_logo = sharedPreferences.getString("web_servicios_logo_seccion_1", "");

                about_imagen = sharedPreferences.getString("web_servicios_img_seccion_2", "");
                about_titulo = sharedPreferences.getString("web_servicios_seccion_2_titulo","");
                about_navbar = "Nosotros";
                about_descripcion = sharedPreferences.getString("web_servicios_seccion_2_descripcion", "");
                about_Sdescripcion = sharedPreferences.getString("web_servicios_seccion_2_descripcion_2","");

                if(sharedPreferences.getString("web_servicios_seccion_2_recycler","").equals("1")){
                    caracteristicas_titulo = sharedPreferences.getString("web_servicios_seccion_2_caracteristica1_titulo","");
                    caracteristicas_descripcion = sharedPreferences.getString("web_servicios_seccion_2_caracteristica1_descripcion","");
                    caracteristicas_imagen = "";

                    caracteristicas.add(new caracteristicas_web(caracteristicas_imagen, caracteristicas_titulo, caracteristicas_descripcion));
                }
                else if(sharedPreferences.getString("web_servicios_seccion_2_recycler","").equals("2")){
                    caracteristicas_titulo = sharedPreferences.getString("web_servicios_seccion_2_caracteristica1_titulo","");
                    caracteristicas_descripcion = sharedPreferences.getString("web_servicios_seccion_2_caracteristica1_descripcion","");
                    caracteristicas_imagen = "";

                    caracteristicas.add(new caracteristicas_web(caracteristicas_imagen, caracteristicas_titulo, caracteristicas_descripcion));

                    caracteristicas_titulo = sharedPreferences.getString("web_servicios_seccion_2_caracteristica2_titulo","");
                    caracteristicas_descripcion = sharedPreferences.getString("web_servicios_seccion_2_caracteristica2_descripcion","");
                    caracteristicas_imagen = "";

                    caracteristicas.add(new caracteristicas_web(caracteristicas_imagen, caracteristicas_titulo, caracteristicas_descripcion));
                }
                else if(sharedPreferences.getString("web_servicios_seccion_2_recycler","").equals("3")){
                    caracteristicas_titulo = sharedPreferences.getString("web_servicios_seccion_2_caracteristica1_titulo","");
                    caracteristicas_descripcion = sharedPreferences.getString("web_servicios_seccion_2_caracteristica1_descripcion","");
                    caracteristicas_imagen = "";

                    caracteristicas.add(new caracteristicas_web(caracteristicas_imagen, caracteristicas_titulo, caracteristicas_descripcion));

                    caracteristicas_titulo = sharedPreferences.getString("web_servicios_seccion_2_caracteristica2_titulo","");
                    caracteristicas_descripcion = sharedPreferences.getString("web_servicios_seccion_2_caracteristica2_descripcion","");
                    caracteristicas_imagen = "";

                    caracteristicas.add(new caracteristicas_web(caracteristicas_imagen, caracteristicas_titulo, caracteristicas_descripcion));

                    caracteristicas_titulo = sharedPreferences.getString("web_servicios_seccion_2_caracteristica3_titulo","");
                    caracteristicas_descripcion = sharedPreferences.getString("web_servicios_seccion_2_caracteristica3_descripcion","");
                    caracteristicas_imagen = "";

                    caracteristicas.add(new caracteristicas_web(caracteristicas_imagen, caracteristicas_titulo, caracteristicas_descripcion));

                }

                nosotros.add(new caracteristicas_web(sharedPreferences.getString("web_servicios_img1_seccion_3", ""), sharedPreferences.getString("web_servicios_seccion_3_titulo1",""), sharedPreferences.getString("web_servicios_seccion_3_descripcion1","")));
                nosotros.add(new caracteristicas_web(sharedPreferences.getString("web_servicios_img2_seccion_3", ""), sharedPreferences.getString("web_servicios_seccion_3_titulo2",""), sharedPreferences.getString("web_servicios_seccion_3_descripcion2","")));

                servicios_navbar = "Servicios";
                servicios_titulo = sharedPreferences.getString("web_servicios_seccion_4_titulo","");
                servicios_descripcion_ = sharedPreferences.getString("web_servicios_seccion_4_descripcion","");

                if(sharedPreferences.getString("web_servicios_seccion_4_recycler","").equals("1")){
                    servicio_imagen = "";
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));
                }
                else  if(sharedPreferences.getString("web_servicios_seccion_4_recycler","").equals("2")){
                    servicio_imagen = "";
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));
                }
                else  if(sharedPreferences.getString("web_servicios_seccion_4_recycler","").equals("3")){
                    servicio_imagen = "";
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));
                }
                else  if(sharedPreferences.getString("web_servicios_seccion_4_recycler","").equals("4")){
                    servicio_imagen = "";
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));
                }
                else  if(sharedPreferences.getString("web_servicios_seccion_4_recycler","").equals("5")){
                    servicio_imagen = "";
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica5_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica5_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));
                }
                else  if(sharedPreferences.getString("web_servicios_seccion_4_recycler","").equals("6")){
                    servicio_imagen = "";
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica5_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica5_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica6_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica6_descripcion","");

                    servicio.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));
                }

                baner_titulo = sharedPreferences.getString("web_servicios_seccion_5_titulo","");
                baner_descripcion = sharedPreferences.getString("web_servicios_seccion_5_descripcion","");

                if(sharedPreferences.getString("web_servicios_seccion_5_recycler", "").equals("1")){
                    servicio_imagen = "";
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_5_caracteristica1_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_5_caracteristica1_descripcion","");

                    cuadroInfo.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));
                }
                else if(sharedPreferences.getString("web_servicios_seccion_5_recycler", "").equals("2")){
                    servicio_imagen = "";
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_5_caracteristica1_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_5_caracteristica1_descripcion","");

                    cuadroInfo.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_5_caracteristica2_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_5_caracteristica2_descripcion","");

                    cuadroInfo.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));
                }

                else if(sharedPreferences.getString("web_servicios_seccion_5_recycler", "").equals("3")){
                    servicio_imagen = "";
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_5_caracteristica1_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_5_caracteristica1_descripcion","");

                    cuadroInfo.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_5_caracteristica2_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_5_caracteristica2_descripcion","");

                    cuadroInfo.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_5_caracteristica3_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_5_caracteristica3_descripcion","");

                    cuadroInfo.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));
                }

                portafolio_navbar = "Portafolio";
                portafolio_titulo = "Nuestro Portafolio";

                if(sharedPreferences.getString("web_servicios_seccion_6_recycler", "").equals("1")){
                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));
                }
                else if(sharedPreferences.getString("web_servicios_seccion_6_recycler", "").equals("2")){
                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));

                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));
                }
                else if(sharedPreferences.getString("web_servicios_seccion_6_recycler", "").equals("3")){
                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));

                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));

                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));
                }
                else if(sharedPreferences.getString("web_servicios_seccion_6_recycler", "").equals("4")){
                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));

                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));

                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));

                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));
                }
                else if(sharedPreferences.getString("web_servicios_seccion_6_recycler", "").equals("5")){
                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));

                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));

                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));

                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));

                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica5_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica5_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica5_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));
                }
                else if(sharedPreferences.getString("web_servicios_seccion_6_recycler", "").equals("6")){
                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));

                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));

                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));

                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));

                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica5_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica5_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica5_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));

                    servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica6_url", "");
                    servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica6_titulo","");
                    servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica6_descripcion","");

                    imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicios_descripcion));
                }

                contacto_navbar = "Contacto";
                contacto_email = sharedPreferences.getString("web_servicios_email_contacto","");
                contacto_telefono = sharedPreferences.getString("web_servicios_telefono_contacto","");
                contacto_titulo = "¡Contáctanos!";
                contacto_lugar = sharedPreferences.getString("web_servicios_ubicacion_contacto","");
                // ******************************************************************************

                contacto.put("navbar", contacto_navbar);
                contacto.put("titulo", contacto_titulo);
                contacto.put("telefono", contacto_telefono);
                contacto.put("email", contacto_email);
                contacto.put("lugar", contacto_lugar);

                portafolio.put("navbar", portafolio_navbar);
                portafolio.put("titulo", portafolio_titulo);
                portafolio.put("imagenes", imagenes);

                banner.put("titulo", baner_titulo);
                banner.put("descripcion", baner_descripcion);
                banner.put("cuadroInfo", cuadroInfo);

                servicios.put("navbar", servicios_navbar);
                servicios.put("titulo", servicios_titulo);
                servicios.put("descripcion", servicios_descripcion_);
                servicios.put("servicio", servicio);

                about.put("navbar", about_navbar);
                about.put("titulo", about_titulo);
                about.put("descripcion", about_descripcion);
                about.put("imagen", about_imagen);
                about.put("Sdescripcion", about_Sdescripcion);
                about.put("caracteristicas", caracteristicas);
                about.put("nosotros", nosotros);

                home.put("navbar", home_navbar);
                home.put("titulo", home_titulo);
                home.put("logo", home_logo);
                home.put("imagen", home_imagen);

                web.put("home", home);
                web.put("about", about);
                web.put("servicios", servicios);
                web.put("banner", banner);
                web.put("portafolio", portafolio);
                web.put("contacto", contacto);

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String url = sharedPreferences.getString("nombrePagWeb", "");
                pagWebs pag = new pagWebs("",web, 3, user.getUid(), url);

                db.collection("webs").document(sharedPreferences.getString("nombrePagWeb", ""))
                        .set(pag)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                /*if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }*/

                                String nombreEmpresa = sharedPreferences.getString("nombreEmpresa", "");
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(user.getUid() + "-tipo_mi_pag_web", "3");
                                editor.commit();
                                crearActualizarUsuario(nombreEmpresa, sharedPreferences.getString("nombrePagWeb", ""), "servicios");
                                /*  Toast.makeText(getApplicationContext(),"¡Página web creada exitosamente!", Toast.LENGTH_LONG).show();
                                String url = "http://services.solucionescolabora.com/u/" + sharedPreferences.getString("nombrePagWeb", "");
                                Intent i = new Intent(Intent.ACTION_VIEW);
                                i.setData(Uri.parse(url));
                                startActivity(i);*/
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

    private void crearActualizarUsuario(String nombreEmpresa, String nombrePaginaWeb, String tipoPaginaWeb){

        String id = FirebaseAuth.getInstance().getUid();
        final Usuario usuario = new Usuario(id, nombrePaginaWeb, tipoPaginaWeb, nombreEmpresa);

        db.collection("Usuarios").document(id)
                .set(usuario)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        Toast.makeText(getApplicationContext(),"¡Página web creada exitosamente!", Toast.LENGTH_LONG).show();
                        String url = "http://services.solucionescolabora.com/u/" + sharedPreferences.getString("nombrePagWeb", "");
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
}
