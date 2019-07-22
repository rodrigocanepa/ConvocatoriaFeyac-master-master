package com.colabora.soluciones.convocatoriafeyac.web.comida;

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

import com.colabora.soluciones.convocatoriafeyac.Modelos.EspecialidadesComida;
import com.colabora.soluciones.convocatoriafeyac.Modelos.MenuComida;
import com.colabora.soluciones.convocatoriafeyac.Modelos.caracteristicas_web_dos;
import com.colabora.soluciones.convocatoriafeyac.Modelos.pagWebs;
import com.colabora.soluciones.convocatoriafeyac.R;
import com.colabora.soluciones.convocatoriafeyac.web.productos.WebsProductosSeccion6;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebsComidaSeccion6 extends AppCompatActivity {

    private Button btnSiguiente;
    private TextInputEditText editNombre;
    private TextInputEditText editDireccion;
    private TextInputEditText editCorreo;
    private TextInputEditText editTelefono;
    private TextInputEditText editFacebook;
    private TextInputEditText editInstagram;

    private String nombre = "";
    private String direccion = "";
    private String correo = "";
    private String telefono = "";
    private String facebook = "";
    private String instagram = "";

    private SharedPreferences sharedPreferences;

    private ProgressDialog progressDialog;
    private FirebaseFirestore db;

    private Map<String, Object> web;
    private Map<String, Object> home;
    private Map<String, Object> specials;
    private Map<String, Object> yellow;
    private Map<String, Object> about;
    private Map<String, Object> menu;
    private Map<String, Object> contacto;

    private List<caracteristicas_web_dos> banners = new ArrayList<>();
    private List<EspecialidadesComida> items = new ArrayList<>();
    private List<MenuComida> items_menu = new ArrayList<>();

    // variables home
    private String home_navbar = "";

    // variables about
    private String about_navbar = "Nosotros";
    private String about_header = "Nosotros";
    private String about_descripcion = "";
    private String about_imagen = "";
    private String about_background = "";

    // variables yellow
    private String yellow_titulo1 = "";
    private String yellow_descripcion1 = "";
    private String yellow_titulo2 = "";
    private String yellow_descripcion2 = "";
    private String yellow_titulo3 = "";
    private String yellow_descripcion3 = "";

    // variables specials
    private String specials_navbar = "";
    private String specials_header = "";


    // variables menu
    private String menu_navbar = "";
    private String menu_header = "";

    // variables contacto
    private String contacto_navbar = "";
    private String contacto_header = "";
    private String contacto_nombre_restaurante = "";
    private String contacto_adress = "";
    private String contacto_nombre_reservaciones = "";
    private String contacto_email = "";
    private String contacto_phone = "";
    private String contacto_facebook = "";
    private String contacto_instagram = "";

    private String img_home = "";
    private String texto_home = "";

    private String specials_img = "";
    private String specials_titulo = "";
    private String specials_descripcion = "";
    private int specials_precio = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_comida_seccion6);

        editNombre = (TextInputEditText)findViewById(R.id.txt_web_comida_seccion6_nombre);
        editDireccion = (TextInputEditText)findViewById(R.id.txt_web_comida_seccion6_ubicacion);
        editCorreo = (TextInputEditText)findViewById(R.id.txt_web_comida_seccion6_correo);
        editTelefono = (TextInputEditText)findViewById(R.id.txt_web_comida_seccion6_telefono);
        editFacebook = (TextInputEditText)findViewById(R.id.txt_web_comida_seccion6_facebook);
        editInstagram = (TextInputEditText)findViewById(R.id.txt_web_comida_seccion6_instagram);
        btnSiguiente = (Button)findViewById(R.id.btnComidaSeccion6Siguiente);

        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(WebsComidaSeccion6.this);

        progressDialog.setTitle("Subiendo Información");
        progressDialog.setMessage("Espere un momento mientras el sistema sube su información a la base de datos");

        sharedPreferences = getSharedPreferences("misDatos", 0);
        editNombre.setText(sharedPreferences.getString("web_comida_nombre_seccion_6", ""));
        editDireccion.setText(sharedPreferences.getString("web_comida_direccion_seccion_6", ""));
        editCorreo.setText(sharedPreferences.getString("web_comida_correo_seccion_6", ""));
        editTelefono.setText(sharedPreferences.getString("web_comida_telefono_seccion_6", ""));
        editFacebook.setText(sharedPreferences.getString("web_comida_facebook_seccion_6", ""));
        editInstagram.setText(sharedPreferences.getString("web_comida_instagram_seccion_6", ""));

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = editNombre.getText().toString();
                direccion = editDireccion.getText().toString();
                correo = editCorreo.getText().toString();

                telefono = editTelefono.getText().toString();
                facebook = editFacebook.getText().toString();
                instagram = editInstagram.getText().toString();

                if(nombre.length() > 0 && direccion.length() > 0){
                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("web_comida_nombre_seccion_6", nombre);
                    editor.putString("web_comida_direccion_seccion_6", direccion);
                    editor.putString("web_comida_correo_seccion_6", correo);

                    editor.putString("web_comida_telefono_seccion_6", telefono);
                    editor.putString("web_comida_facebook_seccion_6", facebook);
                    editor.putString("web_comida_instagram_seccion_6", instagram);

                    editor.commit();
                    // ******************************************************************************
                }
                else{
                    Toast.makeText(getApplicationContext(), "Para continuar debes escribir los campos requeridos que llevará esta sección", Toast.LENGTH_LONG).show();
                    return;
                }

                progressDialog.show();

                web = new HashMap<>();
                home = new HashMap<>();
                about = new HashMap<>();
                menu = new HashMap<>();
                yellow = new HashMap<>();
                specials = new HashMap<>();
                contacto = new HashMap<>();

                home_navbar = "Inicio";
                img_home = sharedPreferences.getString("web_comida_img_1_seccion_1","");
                texto_home = sharedPreferences.getString("web_comida_titulo_1_home","");
                banners.add(new caracteristicas_web_dos(img_home, texto_home));

                img_home = sharedPreferences.getString("web_comida_img_2_seccion_1","");
                texto_home = sharedPreferences.getString("web_comida_titulo_2_home","");
                banners.add(new caracteristicas_web_dos(img_home, texto_home));

                if(sharedPreferences.getString("web_comida_img_1_seccion_1", "").length() > 1){
                    img_home = sharedPreferences.getString("web_comida_img_3_seccion_1","");
                    texto_home = sharedPreferences.getString("web_comida_titulo_3_home","");
                    banners.add(new caracteristicas_web_dos(img_home, texto_home));
                }

                specials_navbar = "Especialidades";
                specials_header = "Nuestras Especialidades";
                if(sharedPreferences.getString("web_comida_seccion_3_recycler", "").equals("1")){
                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica1_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                }
                else if(sharedPreferences.getString("web_comida_seccion_3_recycler", "").equals("2")){
                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica1_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica2_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                }

                else if(sharedPreferences.getString("web_comida_seccion_3_recycler", "").equals("3")){
                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica1_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica2_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica3_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                }

                else if(sharedPreferences.getString("web_comida_seccion_3_recycler", "").equals("4")){
                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica1_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica2_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica3_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica4_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));
                }


                else if(sharedPreferences.getString("web_comida_seccion_3_recycler", "").equals("5")){
                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica1_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica2_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica3_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica4_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica5_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica5_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica5_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica5_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));
                }

                else if(sharedPreferences.getString("web_comida_seccion_3_recycler", "").equals("6")){
                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica1_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica2_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica3_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica4_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica5_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica5_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica5_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica5_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

                    specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica6_url","");
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica6_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica6_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica6_precio",0);

                    items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));
                }

                yellow_titulo1 = sharedPreferences.getString("web_comida_titulo_1_seccion_2","");
                yellow_titulo2 = sharedPreferences.getString("web_comida_titulo_2_seccion_2","");
                yellow_titulo3 = sharedPreferences.getString("web_comida_titulo_3_seccion_2","");

                yellow_descripcion1 = sharedPreferences.getString("web_comida_descripcion_1_seccion_2","");
                yellow_descripcion2 = sharedPreferences.getString("web_comida_descripcion_2_seccion_2","");
                yellow_descripcion3 = sharedPreferences.getString("web_comida_descripcion_3_seccion_2","");

                about_descripcion = sharedPreferences.getString("web_comida_descripcion_seccion_4","");

                menu_header = "Menu";
                menu_navbar = "Menu";

                if(sharedPreferences.getString("web_comida_seccion_5_recycler", "").equals("1")){
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica1_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));
                }

                else if(sharedPreferences.getString("web_comida_seccion_5_recycler", "").equals("2")){
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica1_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica2_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                }
                else if(sharedPreferences.getString("web_comida_seccion_5_recycler", "").equals("3")){
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica1_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica2_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica3_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica3_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica3_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                }

                else if(sharedPreferences.getString("web_comida_seccion_5_recycler", "").equals("4")){
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica1_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica2_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica3_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica3_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica3_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica4_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica4_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica4_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                }

                else if(sharedPreferences.getString("web_comida_seccion_5_recycler", "").equals("5")){
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica1_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica2_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica3_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica3_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica3_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica4_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica4_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica4_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica5_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica5_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica5_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                }

                else if(sharedPreferences.getString("web_comida_seccion_5_recycler", "").equals("6")){
                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica1_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica2_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica3_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica3_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica3_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica4_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica4_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica4_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica5_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica5_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica5_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                    specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica6_titulo","");
                    specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica6_descripcion","");
                    specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica6_precio",0);

                    items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

                }

                contacto_navbar = "Contacto";
                contacto_header = "Contacto";
                contacto_email = sharedPreferences.getString("web_comida_correo_seccion_6","");
                contacto_phone = sharedPreferences.getString("web_comida_telefono_seccion_6","");
                contacto_facebook = sharedPreferences.getString("web_comida_facebook_seccion_6","");
                contacto_instagram = sharedPreferences.getString("web_comida_instagram_seccion_6","");
                contacto_nombre_restaurante = sharedPreferences.getString("web_comida_nombre_seccion_6","");
                contacto_adress = sharedPreferences.getString("web_comida_direccion_seccion_6","");

                contacto.put("navbar", contacto_navbar);
                contacto.put("header", contacto_header);
                contacto.put("nombre_restaurante", contacto_nombre_restaurante);
                contacto.put("adress", contacto_adress);
                contacto.put("nombre_reservaciones", "");
                contacto.put("email", contacto_email);
                contacto.put("phone", contacto_phone);
                contacto.put("facebook_url", contacto_facebook);
                contacto.put("instagram_url", contacto_instagram);

                menu.put("navbar", menu_navbar);
                menu.put("header", menu_header);
                menu.put("items", items_menu);

                about.put("navbar", about_navbar);
                about.put("header", about_header);
                about.put("img", about_imagen);
                about.put("background", "");
                about.put("text", about_descripcion);

                yellow.put("titulo1", yellow_titulo1);
                yellow.put("titulo2", yellow_titulo2);
                yellow.put("titulo3", yellow_titulo3);
                yellow.put("descripcion1", yellow_descripcion1);
                yellow.put("descripcion2", yellow_descripcion2);
                yellow.put("descripcion3", yellow_descripcion3);

                specials.put("navbar", specials_navbar);
                specials.put("header", specials_header);
                specials.put("items", items);

                home.put("navbar", home_navbar);
                home.put("banners", banners);

                web.put("home", home);
                web.put("specials", specials);
                web.put("yellow", yellow);
                web.put("about", about);
                web.put("menu", menu);
                web.put("contacto", contacto);

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                user.getUid();
                String url = sharedPreferences.getString("nombrePagWeb", "");
                pagWebs pag = new pagWebs("",web, 1,  user.getUid(), url);

                db.collection("webs").document(sharedPreferences.getString("nombrePagWeb", ""))
                        .set(pag)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString(user.getUid() + "-tipo_mi_pag_web", "1");
                                editor.commit();
                                Toast.makeText(getApplicationContext(),"¡Página web creada exitosamente!", Toast.LENGTH_LONG).show();
                                String url = "http://food.solucionescolabora.com/u/" + sharedPreferences.getString("nombrePagWeb", "");
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
