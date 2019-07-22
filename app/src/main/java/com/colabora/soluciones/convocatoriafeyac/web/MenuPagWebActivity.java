package com.colabora.soluciones.convocatoriafeyac.web;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.renderscript.Sampler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewDebug;
import android.widget.ImageView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.Modelos.EspecialidadesComida;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Horario;
import com.colabora.soluciones.convocatoriafeyac.Modelos.MenuComida;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Usuario;
import com.colabora.soluciones.convocatoriafeyac.Modelos.WebImagen;
import com.colabora.soluciones.convocatoriafeyac.Modelos.caracteristicas_web;
import com.colabora.soluciones.convocatoriafeyac.Modelos.caracteristicas_web_dos;
import com.colabora.soluciones.convocatoriafeyac.Modelos.pagWebs;
import com.colabora.soluciones.convocatoriafeyac.R;
import com.colabora.soluciones.convocatoriafeyac.web.aplicaciones.WebAppsSeccion1Activity;
import com.colabora.soluciones.convocatoriafeyac.web.comida.WebsComidaSeccion1;
import com.colabora.soluciones.convocatoriafeyac.web.moda.WebsModaSeccion1;
import com.colabora.soluciones.convocatoriafeyac.web.moda.WebsModaSeccion7;
import com.colabora.soluciones.convocatoriafeyac.web.productos.WebsProductosSeccion1;
import com.colabora.soluciones.convocatoriafeyac.web.salud.WebsSaludSeccion1;
import com.colabora.soluciones.convocatoriafeyac.web.servicios.WebsServiciosSeccion1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1beta1.Value;

import java.lang.reflect.Array;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import io.github.yavski.fabspeeddial.FabSpeedDial;
import io.github.yavski.fabspeeddial.SimpleMenuListenerAdapter;

public class MenuPagWebActivity extends AppCompatActivity {

    private ImageView imgServicios;
    private ImageView imgProductos;
    private ImageView imgSalud;
    private ImageView imgAplicaciones;
    private ImageView imgModa;
    private ImageView imgComida;
    private FabSpeedDial speedDialView;

    private FirebaseFirestore db;
    private SharedPreferences sharedPreferences;
    private Usuario miUsuario;

    private ProgressDialog progressDialog;
    private pagWebs miPagina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_pag_web);
        setTitle("Crear mi página web");

        imgServicios = (ImageView)findViewById(R.id.imgWebServicios);
        imgProductos = (ImageView)findViewById(R.id.imgWebProductos);
        imgSalud = (ImageView)findViewById(R.id.imgWebSalud);
        imgAplicaciones = (ImageView)findViewById(R.id.imgWebApps);
        imgModa = (ImageView)findViewById(R.id.imgWebModa);
        imgComida = (ImageView)findViewById(R.id.imgWebComida);
        speedDialView = (FabSpeedDial)findViewById(R.id.speedDial);

        sharedPreferences = getSharedPreferences("misDatos", 0);
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(MenuPagWebActivity.this);
        progressDialog.setMessage("Checando información en la base de datos");

        imgServicios.setColorFilter(Color.argb(150,20,20,20), PorterDuff.Mode.DARKEN);
        imgProductos.setColorFilter(Color.argb(150,20,20,20), PorterDuff.Mode.DARKEN);
        imgSalud.setColorFilter(Color.argb(150,20,20,20), PorterDuff.Mode.DARKEN);
        imgAplicaciones.setColorFilter(Color.argb(150,20,20,20), PorterDuff.Mode.DARKEN);
        imgModa.setColorFilter(Color.argb(150,20,20,20), PorterDuff.Mode.DARKEN);
        imgComida.setColorFilter(Color.argb(150,20,20,20), PorterDuff.Mode.DARKEN);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        progressDialog.show();

        db.collection("webs")
                .whereEqualTo("idUsuario", user.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                miPagina = document.toObject(pagWebs.class);
                            }

                            if(miPagina != null){

                                Toast.makeText(getApplicationContext(),"Tu página web se ha cargado correctamente, puedes editarla o visualizarla oprimiendo el botón verde",Toast.LENGTH_LONG).show();

                                if(miPagina.getTipo() == 1){
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(user.getUid()+"-tipo_mi_pag_web", "1");
                                    editor.putString("nombrePagWeb", miPagina.getUrl());

                                    //COMIDA

                                     Map<String, Object> web;
                                     Map<String, Object> home;
                                     Map<String, Object> specials;
                                     Map<String, Object> yellow;
                                     Map<String, Object> about;
                                     Map<String, Object> menu;
                                     Map<String, Object> contacto;

                                    web = miPagina.getSecciones();
                                    home = (Map)web.get("home");
                                    specials = (Map)web.get("specials");
                                    yellow = (Map)web.get("yellow");
                                    about = (Map)web.get("about");
                                    menu = (Map)web.get("menu");
                                    contacto = (Map)web.get("contacto");

                                    List<caracteristicas_web_dos> banners = new ArrayList<>();
                                    List<EspecialidadesComida> items = new ArrayList<>();
                                    List<MenuComida> items_menu = new ArrayList<>();

                                    // variables home
                                    String home_navbar = "";
                                    home_navbar = (String)home.get("navbar");

                                    Object prueba  = home.get("banners");
                                    List<?> datos = convertObjectToList(prueba);

                                    if(datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] banerCortado = frase.split(",");
                                        String banerTexto = banerCortado[0];
                                        String banerImg = banerCortado[1];

                                        String[] banerTextoCortado = banerTexto.split("=");
                                        String banerTextoFinal = banerTextoCortado[1];

                                        String[] banerImgCortado = banerImg.split("mg=");
                                        String banerImgFinal = banerImgCortado[1];
                                        banerImgFinal = banerImgFinal.substring(0, banerImgFinal.length() - 1);

                                        editor.putString("web_comida_titulo_1_home", banerTextoFinal);
                                        editor.putString("web_comida_img_1_seccion_1", banerImgFinal);
                                    }
                                    else if(datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] banerCortado = frase.split(",");
                                        String banerTexto = banerCortado[0];
                                        String banerImg = banerCortado[1];

                                        String[] banerTextoCortado = banerTexto.split("=");
                                        String banerTextoFinal;
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        String[] banerImgCortado = banerImg.split("mg=");
                                        String banerImgFinal;
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                            banerImgFinal = banerImgFinal.substring(0, banerImgFinal.length() - 1);
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_titulo_1_home", banerTextoFinal);
                                        editor.putString("web_comida_img_1_seccion_1", banerImgFinal);

                                        frase = datos.get(1).toString();

                                        banerCortado = frase.split(",");
                                        banerTexto = banerCortado[0];
                                        banerImg = banerCortado[1];

                                        banerTextoCortado = banerTexto.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }
                                        banerImgCortado = banerImg.split("mg=");
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                            banerImgFinal = banerImgFinal.substring(0, banerImgFinal.length() - 1);
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_titulo_2_home", banerTextoFinal);
                                        editor.putString("web_comida_img_2_seccion_1", banerImgFinal);
                                    }
                                    else if(datos.size() == 3){

                                        String frase = datos.get(0).toString();

                                        String[] banerCortado = frase.split(",");
                                        String banerTexto = banerCortado[0];
                                        String banerImg = banerCortado[1];

                                        String[] banerTextoCortado = banerTexto.split("=");
                                        String banerTextoFinal;
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        String[] banerImgCortado = banerImg.split("mg=");

                                        String banerImgFinal;
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                            banerImgFinal = banerImgFinal.substring(0, banerImgFinal.length() - 1);
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_titulo_1_home", banerTextoFinal);
                                        editor.putString("web_comida_img_1_seccion_1", banerImgFinal);

                                        frase = datos.get(1).toString();

                                        banerCortado = frase.split(",");
                                        banerTexto = banerCortado[0];
                                        banerImg = banerCortado[1];

                                        banerTextoCortado = banerTexto.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }
                                        banerImgCortado = banerImg.split("mg=");
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                            banerImgFinal = banerImgFinal.substring(0, banerImgFinal.length() - 1);
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_titulo_2_home", banerTextoFinal);
                                        editor.putString("web_comida_img_2_seccion_1", banerImgFinal);

                                        frase = datos.get(2).toString();

                                        banerCortado = frase.split(",");
                                        banerTexto = banerCortado[0];
                                        banerImg = banerCortado[1];

                                        banerTextoCortado = banerTexto.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }
                                        banerImgCortado = banerImg.split("mg=");
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                            banerImgFinal = banerImgFinal.substring(0, banerImgFinal.length() - 1);
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_titulo_3_home", banerTextoFinal);
                                        editor.putString("web_comida_img_3_seccion_1", banerImgFinal);
                                    }

                                    // variables about
                                    String about_navbar = "Nosotros";
                                    String about_header = "Nosotros";
                                    String about_descripcion = "";
                                    String about_imagen = "";

                                    about_navbar = (String)about.get("navbar");
                                    about_header = (String)about.get("header");
                                    about_descripcion = (String)about.get("text");
                                    about_imagen = (String)about.get("img");

                                    editor.putString("web_comida_descripcion_seccion_4", about_descripcion);

                                    // variables yellow
                                    String yellow_titulo1 = "";
                                    String yellow_descripcion1 = "";
                                    String yellow_titulo2 = "";
                                    String yellow_descripcion2 = "";
                                    String yellow_titulo3 = "";
                                    String yellow_descripcion3 = "";

                                    yellow_titulo1 = (String)yellow.get("titulo1");
                                    yellow_titulo2 = (String)yellow.get("titulo2");
                                    yellow_titulo3 = (String)yellow.get("titulo3");
                                    yellow_descripcion1 = (String)yellow.get("descripcion1");
                                    yellow_descripcion2 = (String)yellow.get("descripcion2");
                                    yellow_descripcion3 = (String)yellow.get("descripcion3");

                                    editor.putString("web_comida_titulo_1_seccion_2", yellow_titulo1);
                                    editor.putString("web_comida_titulo_2_seccion_2", yellow_titulo2);
                                    editor.putString("web_comida_titulo_3_seccion_2", yellow_titulo3);
                                    editor.putString("web_comida_descripcion_1_seccion_2", yellow_descripcion1);
                                    editor.putString("web_comida_descripcion_2_seccion_2", yellow_descripcion2);
                                    editor.putString("web_comida_descripcion_3_seccion_2", yellow_descripcion3);

                                    // variables specials
                                    String specials_navbar = "";
                                    String specials_header = "";
                                    String specials_img = "";
                                    String specials_titulo = "";
                                    String specials_descripcion = "";
                                    int specials_precio = 0;

                                    specials_navbar = (String)specials.get("navbar");
                                    specials_header = (String)specials.get("header");

                                    prueba  = specials.get("items");
                                    datos = convertObjectToList(prueba);

                                    String frase2 = datos.get(0).toString();

                                    if (datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String espDescripcion = itemCortado[0];
                                        String espTitulo = itemCortado[3];
                                        String espPrecio = itemCortado[2];
                                        String espImg = itemCortado[1];

                                        String[] banerTextoCortado = espDescripcion.split("=");
                                        String banerTextoFinal = banerTextoCortado[1];

                                        String[] TextoCortado = espTitulo.split("=");
                                        String espeTituloFinal = TextoCortado[1];
                                        espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);

                                        String[] extoCortado = espPrecio.split("=");
                                        String espePrecioFinal = extoCortado[1];
                                        int espfinal = Integer.parseInt(espePrecioFinal);

                                        String[] banerImgCortado = espImg.split("mg=");
                                        String banerImgFinal = banerImgCortado[1];

                                        editor.putString("web_comida_seccion_3_recycler", "1");
                                        editor.putString("web_comida_seccion_3_caracteristica1_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica1_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica1_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica1_precio", espfinal);

                                    }
                                    else if (datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String espDescripcion = itemCortado[0];
                                        String espTitulo = itemCortado[3];
                                        String espPrecio = itemCortado[2];
                                        String espImg = itemCortado[1];

                                        String[] banerTextoCortado = espDescripcion.split("=");
                                        String banerTextoFinal = banerTextoCortado[1];

                                        String[] TextoCortado = espTitulo.split("=");
                                        String espeTituloFinal = TextoCortado[1];
                                        espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);

                                        String[] extoCortado = espPrecio.split("=");
                                        String espePrecioFinal = extoCortado[1];
                                        int espfinal = Integer.parseInt(espePrecioFinal);

                                        String[] banerImgCortado = espImg.split("mg=");
                                        String banerImgFinal = banerImgCortado[1];

                                        editor.putString("web_comida_seccion_3_recycler", "1");
                                        editor.putString("web_comida_seccion_3_caracteristica1_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica1_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica1_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica1_precio", espfinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[3];
                                        espPrecio = itemCortado[2];
                                        espImg = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        banerTextoFinal = banerTextoCortado[1];

                                        TextoCortado = espTitulo.split("=");
                                        espeTituloFinal = TextoCortado[1];
                                        espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);

                                        extoCortado = espPrecio.split("=");
                                        espePrecioFinal = extoCortado[1];
                                        espfinal = Integer.parseInt(espePrecioFinal);

                                        banerImgCortado = espImg.split("mg=");
                                        banerImgFinal = banerImgCortado[1];

                                        editor.putString("web_comida_seccion_3_recycler", "2");
                                        editor.putString("web_comida_seccion_3_caracteristica2_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica2_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica2_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica2_precio", espfinal);

                                    }
                                    else if (datos.size() == 3){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String espDescripcion = itemCortado[0];
                                        String espTitulo = itemCortado[3];
                                        String espPrecio = itemCortado[2];
                                        String espImg = itemCortado[1];

                                        String[] banerTextoCortado = espDescripcion.split("=");
                                        String banerTextoFinal = banerTextoCortado[1];

                                        String[] TextoCortado = espTitulo.split("=");
                                        String espeTituloFinal = TextoCortado[1];
                                        espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);

                                        String[] extoCortado = espPrecio.split("=");
                                        String espePrecioFinal = extoCortado[1];
                                        int espfinal = Integer.parseInt(espePrecioFinal);

                                        String[] banerImgCortado = espImg.split("mg=");
                                        String banerImgFinal = banerImgCortado[1];

                                        editor.putString("web_comida_seccion_3_recycler", "1");
                                        editor.putString("web_comida_seccion_3_caracteristica1_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica1_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica1_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica1_precio", espfinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[3];
                                        espPrecio = itemCortado[2];
                                        espImg = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        banerTextoFinal = banerTextoCortado[1];

                                        TextoCortado = espTitulo.split("=");
                                        espeTituloFinal = TextoCortado[1];
                                        espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);

                                        extoCortado = espPrecio.split("=");
                                        espePrecioFinal = extoCortado[1];
                                        espfinal = Integer.parseInt(espePrecioFinal);

                                        banerImgCortado = espImg.split("mg=");
                                        banerImgFinal = banerImgCortado[1];

                                        editor.putString("web_comida_seccion_3_recycler", "2");
                                        editor.putString("web_comida_seccion_3_caracteristica2_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica2_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica2_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica2_precio", espfinal);

                                        prueba  = specials.get("items");
                                        datos = convertObjectToList(prueba);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[3];
                                        espPrecio = itemCortado[2];
                                        espImg = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        banerTextoFinal = banerTextoCortado[1];

                                        TextoCortado = espTitulo.split("=");
                                        espeTituloFinal = TextoCortado[1];
                                        espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);

                                        extoCortado = espPrecio.split("=");
                                        espePrecioFinal = extoCortado[1];
                                        espfinal = Integer.parseInt(espePrecioFinal);

                                        banerImgCortado = espImg.split("mg=");
                                        banerImgFinal = banerImgCortado[1];

                                        editor.putString("web_comida_seccion_3_recycler", "3");
                                        editor.putString("web_comida_seccion_3_caracteristica3_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica3_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica3_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica3_precio", espfinal);

                                    }
                                    else if (datos.size() == 4){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String espDescripcion = itemCortado[0];
                                        String espTitulo = itemCortado[3];
                                        String espPrecio = itemCortado[2];
                                        String espImg = itemCortado[1];

                                        String[] banerTextoCortado = espDescripcion.split("=");
                                        String banerTextoFinal;
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        String[] TextoCortado = espTitulo.split("=");
                                        String espeTituloFinal;
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        String[] extoCortado = espPrecio.split("=");
                                        String espePrecioFinal;
                                        int espfinal;
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        String[] banerImgCortado = espImg.split("mg=");
                                        String banerImgFinal;

                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_seccion_3_recycler", "1");
                                        editor.putString("web_comida_seccion_3_caracteristica1_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica1_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica1_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica1_precio", espfinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[3];
                                        espPrecio = itemCortado[2];
                                        espImg = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        banerImgCortado = espImg.split("mg=");
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_seccion_3_recycler", "2");
                                        editor.putString("web_comida_seccion_3_caracteristica2_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica2_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica2_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica2_precio", espfinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[3];
                                        espPrecio = itemCortado[2];
                                        espImg = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        banerImgCortado = espImg.split("mg=");
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_seccion_3_recycler", "3");
                                        editor.putString("web_comida_seccion_3_caracteristica3_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica3_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica3_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica3_precio", espfinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[3];
                                        espPrecio = itemCortado[2];
                                        espImg = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        banerImgCortado = espImg.split("mg=");
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_seccion_3_recycler", "4");
                                        editor.putString("web_comida_seccion_3_caracteristica4_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica4_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica4_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica4_precio", espfinal);

                                    }
                                    else if (datos.size() == 5){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String espDescripcion = itemCortado[0];
                                        String espTitulo = itemCortado[3];
                                        String espPrecio = itemCortado[2];
                                        String espImg = itemCortado[1];

                                        String[] banerTextoCortado = espDescripcion.split("=");
                                        String banerTextoFinal;
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        String[] TextoCortado = espTitulo.split("=");
                                        String espeTituloFinal;
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        String[] extoCortado = espPrecio.split("=");
                                        String espePrecioFinal;
                                        int espfinal;
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        String[] banerImgCortado = espImg.split("mg=");
                                        String banerImgFinal;

                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_seccion_3_recycler", "1");
                                        editor.putString("web_comida_seccion_3_caracteristica1_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica1_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica1_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica1_precio", espfinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[3];
                                        espPrecio = itemCortado[2];
                                        espImg = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        banerImgCortado = espImg.split("mg=");
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_seccion_3_recycler", "2");
                                        editor.putString("web_comida_seccion_3_caracteristica2_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica2_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica2_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica2_precio", espfinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[3];
                                        espPrecio = itemCortado[2];
                                        espImg = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        banerImgCortado = espImg.split("mg=");
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_seccion_3_recycler", "3");
                                        editor.putString("web_comida_seccion_3_caracteristica3_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica3_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica3_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica3_precio", espfinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[3];
                                        espPrecio = itemCortado[2];
                                        espImg = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        banerImgCortado = espImg.split("mg=");
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_seccion_3_recycler", "4");
                                        editor.putString("web_comida_seccion_3_caracteristica4_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica4_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica4_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica4_precio", espfinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[3];
                                        espPrecio = itemCortado[2];
                                        espImg = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        banerImgCortado = espImg.split("mg=");
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_seccion_3_recycler", "5");
                                        editor.putString("web_comida_seccion_3_caracteristica5_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica5_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica5_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica5_precio", espfinal);

                                    }
                                    else if (datos.size() == 6){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String espDescripcion = itemCortado[0];
                                        String espTitulo = itemCortado[3];
                                        String espPrecio = itemCortado[2];
                                        String espImg = itemCortado[1];

                                        String[] banerTextoCortado = espDescripcion.split("=");
                                        String banerTextoFinal;
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        String[] TextoCortado = espTitulo.split("=");
                                        String espeTituloFinal;
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        String[] extoCortado = espPrecio.split("=");
                                        String espePrecioFinal;
                                        int espfinal;
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        String[] banerImgCortado = espImg.split("mg=");
                                        String banerImgFinal;

                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_seccion_3_recycler", "1");
                                        editor.putString("web_comida_seccion_3_caracteristica1_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica1_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica1_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica1_precio", espfinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[3];
                                        espPrecio = itemCortado[2];
                                        espImg = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        banerImgCortado = espImg.split("mg=");
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_seccion_3_recycler", "2");
                                        editor.putString("web_comida_seccion_3_caracteristica2_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica2_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica2_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica2_precio", espfinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[3];
                                        espPrecio = itemCortado[2];
                                        espImg = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        banerImgCortado = espImg.split("mg=");
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_seccion_3_recycler", "3");
                                        editor.putString("web_comida_seccion_3_caracteristica3_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica3_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica3_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica3_precio", espfinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[3];
                                        espPrecio = itemCortado[2];
                                        espImg = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        banerImgCortado = espImg.split("mg=");
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_seccion_3_recycler", "4");
                                        editor.putString("web_comida_seccion_3_caracteristica4_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica4_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica4_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica4_precio", espfinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[3];
                                        espPrecio = itemCortado[2];
                                        espImg = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        banerImgCortado = espImg.split("mg=");
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_seccion_3_recycler", "5");
                                        editor.putString("web_comida_seccion_3_caracteristica5_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica5_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica5_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica5_precio", espfinal);

                                        frase = datos.get(5).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[3];
                                        espPrecio = itemCortado[2];
                                        espImg = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        banerImgCortado = espImg.split("mg=");
                                        if(banerImgCortado.length > 1){
                                            banerImgFinal = banerImgCortado[1];
                                        }
                                        else{
                                            banerImgFinal = "";
                                        }

                                        editor.putString("web_comida_seccion_3_recycler", "6");
                                        editor.putString("web_comida_seccion_3_caracteristica6_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica6_descripcion", banerTextoFinal);
                                        editor.putString("web_comida_seccion_3_caracteristica6_url", banerImgFinal);
                                        editor.putInt("web_comida_seccion_3_caracteristica6_precio", espfinal);

                                    }

                                    // variables menu
                                    String menu_navbar = "";
                                    String menu_header = "";

                                    menu_navbar = (String)menu.get("navbar");
                                    menu_header = (String)menu.get("header");

                                    prueba  = menu.get("items");
                                    datos = convertObjectToList(prueba);

                                    String frase3 = datos.get(0).toString();

                                    if (datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String espDescripcion = itemCortado[0];
                                        String espTitulo = itemCortado[2];
                                        String espPrecio = itemCortado[1];

                                        String[] banerTextoCortado = espDescripcion.split("=");
                                        String banerTextoFinal = banerTextoCortado[1];

                                        String[] TextoCortado = espTitulo.split("=");
                                        String espeTituloFinal = TextoCortado[1];
                                        espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);

                                        String[] extoCortado = espPrecio.split("=");
                                        String espePrecioFinal = extoCortado[1];
                                        int espfinal = Integer.parseInt(espePrecioFinal);

                                        editor.putString("web_comida_seccion_5_recycler", "1");
                                        editor.putString("web_comida_seccion_5_caracteristica1_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica1_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica1_precio", espfinal);

                                    }
                                    else if (datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String espDescripcion = itemCortado[0];
                                        String espTitulo = itemCortado[2];
                                        String espPrecio = itemCortado[1];

                                        String[] banerTextoCortado = espDescripcion.split("=");
                                        String banerTextoFinal = banerTextoCortado[1];

                                        String[] TextoCortado = espTitulo.split("=");
                                        String espeTituloFinal = TextoCortado[1];
                                        espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);

                                        String[] extoCortado = espPrecio.split("=");
                                        String espePrecioFinal = extoCortado[1];
                                        int espfinal = Integer.parseInt(espePrecioFinal);

                                        editor.putString("web_comida_seccion_5_recycler", "1");
                                        editor.putString("web_comida_seccion_5_caracteristica1_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica1_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica1_precio", espfinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[2];
                                        espPrecio = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        banerTextoFinal = banerTextoCortado[1];

                                        TextoCortado = espTitulo.split("=");
                                        espeTituloFinal = TextoCortado[1];
                                        espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);

                                        extoCortado = espPrecio.split("=");
                                        espePrecioFinal = extoCortado[1];
                                        espfinal = Integer.parseInt(espePrecioFinal);

                                        editor.putString("web_comida_seccion_5_recycler", "2");
                                        editor.putString("web_comida_seccion_5_caracteristica2_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica2_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica2_precio", espfinal);

                                    }
                                    else if (datos.size() == 3){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String espDescripcion = itemCortado[0];
                                        String espTitulo = itemCortado[2];
                                        String espPrecio = itemCortado[1];

                                        String[] banerTextoCortado = espDescripcion.split("=");
                                        String banerTextoFinal;
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        String[] TextoCortado = espTitulo.split("=");
                                        String espeTituloFinal;
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        String[] extoCortado = espPrecio.split("=");
                                        String espePrecioFinal;
                                        int espfinal;
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "1");
                                        editor.putString("web_comida_seccion_5_caracteristica1_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica1_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica1_precio", espfinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[2];
                                        espPrecio = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "2");
                                        editor.putString("web_comida_seccion_5_caracteristica2_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica2_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica2_precio", espfinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[2];
                                        espPrecio = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "3");
                                        editor.putString("web_comida_seccion_5_caracteristica3_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica3_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica3_precio", espfinal);

                                    }
                                    else if (datos.size() == 4){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String espDescripcion = itemCortado[0];
                                        String espTitulo = itemCortado[2];
                                        String espPrecio = itemCortado[1];

                                        String[] banerTextoCortado = espDescripcion.split("=");
                                        String banerTextoFinal;
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        String[] TextoCortado = espTitulo.split("=");
                                        String espeTituloFinal;
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        String[] extoCortado = espPrecio.split("=");
                                        String espePrecioFinal;
                                        int espfinal;
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "1");
                                        editor.putString("web_comida_seccion_5_caracteristica1_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica1_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica1_precio", espfinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[2];
                                        espPrecio = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "2");
                                        editor.putString("web_comida_seccion_5_caracteristica2_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica2_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica2_precio", espfinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[2];
                                        espPrecio = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "3");
                                        editor.putString("web_comida_seccion_5_caracteristica3_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica3_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica3_precio", espfinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[2];
                                        espPrecio = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "4");
                                        editor.putString("web_comida_seccion_5_caracteristica4_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica4_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica4_precio", espfinal);

                                    }
                                    else if (datos.size() == 5){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String espDescripcion = itemCortado[0];
                                        String espTitulo = itemCortado[2];
                                        String espPrecio = itemCortado[1];

                                        String[] banerTextoCortado = espDescripcion.split("=");
                                        String banerTextoFinal;
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        String[] TextoCortado = espTitulo.split("=");
                                        String espeTituloFinal;
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        String[] extoCortado = espPrecio.split("=");
                                        String espePrecioFinal;
                                        int espfinal;
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "1");
                                        editor.putString("web_comida_seccion_5_caracteristica1_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica1_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica1_precio", espfinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[2];
                                        espPrecio = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "2");
                                        editor.putString("web_comida_seccion_5_caracteristica2_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica2_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica2_precio", espfinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[2];
                                        espPrecio = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "3");
                                        editor.putString("web_comida_seccion_5_caracteristica3_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica3_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica3_precio", espfinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[2];
                                        espPrecio = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "4");
                                        editor.putString("web_comida_seccion_5_caracteristica4_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica4_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica4_precio", espfinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[2];
                                        espPrecio = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "5");
                                        editor.putString("web_comida_seccion_5_caracteristica5_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica5_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica5_precio", espfinal);

                                    }
                                    else if (datos.size() == 6){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String espDescripcion = itemCortado[0];
                                        String espTitulo = itemCortado[2];
                                        String espPrecio = itemCortado[1];

                                        String[] banerTextoCortado = espDescripcion.split("=");
                                        String banerTextoFinal;
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        String[] TextoCortado = espTitulo.split("=");
                                        String espeTituloFinal;
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        String[] extoCortado = espPrecio.split("=");
                                        String espePrecioFinal;
                                        int espfinal;
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "1");
                                        editor.putString("web_comida_seccion_5_caracteristica1_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica1_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica1_precio", espfinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[2];
                                        espPrecio = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "2");
                                        editor.putString("web_comida_seccion_5_caracteristica2_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica2_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica2_precio", espfinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[2];
                                        espPrecio = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "3");
                                        editor.putString("web_comida_seccion_5_caracteristica3_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica3_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica3_precio", espfinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[2];
                                        espPrecio = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "4");
                                        editor.putString("web_comida_seccion_5_caracteristica4_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica4_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica4_precio", espfinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[2];
                                        espPrecio = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "5");
                                        editor.putString("web_comida_seccion_5_caracteristica5_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica5_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica5_precio", espfinal);

                                        frase = datos.get(5).toString();

                                        itemCortado = frase.split(",");
                                        espDescripcion = itemCortado[0];
                                        espTitulo = itemCortado[2];
                                        espPrecio = itemCortado[1];

                                        banerTextoCortado = espDescripcion.split("=");
                                        if(banerTextoCortado.length > 1){
                                            banerTextoFinal = banerTextoCortado[1];
                                        }
                                        else{
                                            banerTextoFinal = "";
                                        }

                                        TextoCortado = espTitulo.split("=");
                                        if(TextoCortado.length > 1){
                                            espeTituloFinal = TextoCortado[1];
                                            espeTituloFinal = espeTituloFinal.substring(0, espeTituloFinal.length() - 1);
                                        }
                                        else{
                                            espeTituloFinal = "";
                                        }

                                        extoCortado = espPrecio.split("=");
                                        if(extoCortado.length > 1){
                                            espePrecioFinal = extoCortado[1];
                                            espfinal = Integer.parseInt(espePrecioFinal);
                                        }
                                        else{
                                            espfinal = 0;
                                        }

                                        editor.putString("web_comida_seccion_5_recycler", "6");
                                        editor.putString("web_comida_seccion_5_caracteristica6_titulo", espeTituloFinal);
                                        editor.putString("web_comida_seccion_5_caracteristica6_descripcion", banerTextoFinal);
                                        editor.putInt("web_comida_seccion_5_caracteristica6_precio", espfinal);

                                    }

                                    // variables contacto
                                    String contacto_navbar = "";
                                    String contacto_header = "";
                                    String contacto_nombre_restaurante = "";
                                    String contacto_adress = "";
                                    String contacto_nombre_reservaciones = "";
                                    String contacto_email = "";
                                    String contacto_phone = "";
                                    String contacto_facebook = "";
                                    String contacto_instagram = "";

                                    contacto_navbar = (String)contacto.get("navbar");
                                    contacto_header = (String)contacto.get("header");
                                    contacto_nombre_restaurante = (String)contacto.get("nombre_restaurante");
                                    contacto_adress = (String)contacto.get("adress");
                                    contacto_nombre_reservaciones = (String)contacto.get("nombre_reservaciones");
                                    contacto_email = (String)contacto.get("email");
                                    contacto_phone = (String)contacto.get("phone");
                                    contacto_facebook = (String)contacto.get("facebook_url");
                                    contacto_instagram = (String)contacto.get("instagram_url");

                                    editor.putString("web_comida_nombre_seccion_6", contacto_nombre_restaurante);
                                    editor.putString("web_comida_direccion_seccion_6", contacto_adress);
                                    editor.putString("web_comida_correo_seccion_6", contacto_email);

                                    editor.putString("web_comida_telefono_seccion_6", contacto_phone);
                                    editor.putString("web_comida_facebook_seccion_6", contacto_facebook);
                                    editor.putString("web_comida_instagram_seccion_6", contacto_instagram);

                                    editor.commit();

                                }
                                else if(miPagina.getTipo() == 2){

                                    SharedPreferences.Editor editor = sharedPreferences.edit();

                                    editor.putString(user.getUid()+"-tipo_mi_pag_web", "2");
                                    editor.putString("nombrePagWeb", miPagina.getUrl());

                                    //PRODUCTOS

                                    Map<String, Object> web;
                                    Map<String, Object> home;
                                    Map<String, Object> about;
                                    Map<String, Object> servicios;
                                    Map<String, Object> imagencontacto;
                                    Map<String, Object> galeria;
                                    Map<String, Object> contacto;

                                    web = miPagina.getSecciones();
                                    home = (Map)web.get("home");
                                    about = (Map)web.get("about");
                                    servicios = (Map)web.get("servicios");
                                    imagencontacto = (Map)web.get("imagencontacto");
                                    galeria = (Map)web.get("galeria");
                                    contacto = (Map)web.get("contacto");

                                    List<caracteristicas_web> servicio = new ArrayList<>();
                                    List<caracteristicas_web> imagenes = new ArrayList<>();
                                    List<WebImagen> imagen = new ArrayList<>();

                                    // variables home

                                    String home_titulo = "";
                                    String home_subtitulo = "";

                                    home_titulo = (String)home.get("titulo");
                                    home_subtitulo = (String)home.get("subtitulo");

                                    editor.putString("web_productos_titulo_home", home_titulo);
                                    editor.putString("web_productos_subtitulo_home", home_subtitulo);

                                    Object prueba  = home.get("imagen");
                                    List<?> datos = convertObjectToList(prueba);

                                    if (datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] ImgCortado = frase.split("gen=");
                                        String ImgFinal = ImgCortado[1];
                                        ImgFinal = ImgFinal.substring(0, ImgFinal.length() - 1);

                                        editor.putString("web_productos_img_1_seccion_1", ImgFinal);

                                    }
                                    else if (datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] ImgCortado = frase.split("gen=");
                                        String ImgFinal = ImgCortado[1];
                                        ImgFinal = ImgFinal.substring(0, ImgFinal.length() - 1);

                                        editor.putString("web_productos_img_1_seccion_1", ImgFinal);

                                        frase = datos.get(1).toString();

                                        ImgCortado = frase.split("gen=");
                                        ImgFinal = ImgCortado[1];
                                        ImgFinal = ImgFinal.substring(0, ImgFinal.length() - 1);

                                        editor.putString("web_productos_img_2_seccion_1", ImgFinal);
                                    }
                                    else if (datos.size() == 3){

                                        String frase = datos.get(0).toString();

                                        String[] ImgCortado = frase.split("gen=");
                                        String ImgFinal = ImgCortado[1];
                                        ImgFinal = ImgFinal.substring(0, ImgFinal.length() - 1);

                                        editor.putString("web_productos_img_1_seccion_1", ImgFinal);

                                        frase = datos.get(1).toString();

                                        ImgCortado = frase.split("gen=");
                                        ImgFinal = ImgCortado[1];
                                        ImgFinal = ImgFinal.substring(0, ImgFinal.length() - 1);

                                        editor.putString("web_productos_img_2_seccion_1", ImgFinal);

                                        frase = datos.get(2).toString();

                                        ImgCortado = frase.split("gen=");
                                        ImgFinal = ImgCortado[1];
                                        ImgFinal = ImgFinal.substring(0, ImgFinal.length() - 1);

                                        editor.putString("web_productos_img_3_seccion_1", ImgFinal);

                                    }

                                    // variables about
                                    String about_navbar = "";
                                    String about_titulo = "";
                                    String about_descripcion = "";
                                    String about_imagen = "";
                                    String about_subtitulo = "";

                                    about_navbar = (String)about.get("navbar");
                                    about_titulo = (String)about.get("titulo");
                                    about_descripcion = (String)about.get("descripcion");
                                    about_imagen = (String)about.get("imagen");
                                    about_subtitulo = (String)about.get("subtitulo");

                                    editor.putString("web_productos_img_seccion_2", about_imagen);
                                    editor.putString("web_productos_titulo_seccion_2", about_titulo);
                                    editor.putString("web_productos_subtitulo_seccion_2", about_subtitulo);
                                    editor.putString("web_productos_descripcion_seccion_2", about_descripcion);

                                    // variables servicios

                                    String servicios_titulo = "";
                                    String imagen_ = "";
                                    String titulo = "";
                                    String descripcion = "";

                                    servicios_titulo = (String)servicios.get("titulo");

                                    editor.putString("web_productos_seccion_3_titulo", servicios_titulo);

                                    prueba  = servicios.get("servicio");
                                    datos = convertObjectToList(prueba);

                                    if (datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String descripcionTexto = itemCortado[0];
                                        String ImgTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal = descripcionCortado[1];

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal = tituloCortado[1];
                                        tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);

                                        editor.putString("web_productos_seccion_3_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica1_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String descripcionTexto = itemCortado[0];
                                        String ImgTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal = descripcionCortado[1];

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal = tituloCortado[1];
                                        tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);

                                        editor.putString("web_productos_seccion_3_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        ImgTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        descripTextoFinal = descripcionCortado[1];

                                        tituloCortado = tituloTexto.split("=");
                                        tituloFinal = tituloCortado[1];
                                        tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);

                                        editor.putString("web_productos_seccion_3_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica2_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 3){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String descripcionTexto = itemCortado[0];
                                        String ImgTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        ImgTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        ImgTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica3_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 4){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String descripcionTexto = itemCortado[0];
                                        String ImgTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        ImgTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        ImgTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica3_descripcion", descripTextoFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        ImgTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica4_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica4_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 5){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String descripcionTexto = itemCortado[0];
                                        String ImgTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        ImgTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        ImgTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica3_descripcion", descripTextoFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        ImgTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica4_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica4_descripcion", descripTextoFinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        ImgTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica5_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica5_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 6){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String descripcionTexto = itemCortado[0];
                                        String ImgTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        ImgTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        ImgTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica3_descripcion", descripTextoFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        ImgTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica4_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica4_descripcion", descripTextoFinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        ImgTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica5_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica5_descripcion", descripTextoFinal);

                                        frase = datos.get(5).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        ImgTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if(descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else{
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if(tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else{
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_productos_seccion_3_caracteristica6_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_3_caracteristica6_descripcion", descripTextoFinal);

                                    }

                                    // variables imagencontacto
                                    String imagencontacto_titulo = "";
                                    String imagencontacto_subtitulo = "";
                                    String imagencontacto_imagen = "";

                                    imagencontacto_titulo = (String)imagencontacto.get("titulo");
                                    imagencontacto_subtitulo = (String)imagencontacto.get("subtitulo");
                                    imagencontacto_imagen = (String)imagencontacto.get("imagen");

                                    editor.putString("web_productos_img_seccion_4", imagencontacto_imagen);
                                    editor.putString("web_productos_titulo_seccion_4", imagencontacto_titulo);
                                    editor.putString("web_productos_subtitulo_seccion_4", imagencontacto_subtitulo);

                                    // variables galeria
                                    String galeria_titulo = "";
                                    String galeria_navbar = "";

                                    galeria_navbar = (String)galeria.get("navbar");
                                    galeria_titulo = (String)galeria.get("titulo");

                                    prueba  = galeria.get("imagenes");
                                    datos = convertObjectToList(prueba);

                                    if (datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[1];
                                        String descripcionTexto = itemCortado[0];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal = descripcionCortado[1];

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal = tituloCortado[1];
                                        tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal = imagenCortado[1];

                                        editor.putString("web_productos_seccion_5_recycler", "1");
                                        editor.putString("web_productos_seccion_5_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica1_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica1_url", imagenFinal);

                                    }
                                    else if (datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[1];
                                        String descripcionTexto = itemCortado[0];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal = descripcionCortado[1];

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal = tituloCortado[1];
                                        tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal = imagenCortado[1];

                                        editor.putString("web_productos_seccion_5_recycler", "1");
                                        editor.putString("web_productos_seccion_5_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica1_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica1_url", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[1];
                                        descripcionTexto = itemCortado[0];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        descripTextoFinal = descripcionCortado[1];

                                        tituloCortado = tituloTexto.split("=");
                                        tituloFinal = tituloCortado[1];
                                        tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);

                                        imagenCortado = imagenTexto.split("gen=");
                                        imagenFinal = imagenCortado[1];

                                        editor.putString("web_productos_seccion_5_recycler", "2");
                                        editor.putString("web_productos_seccion_5_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica2_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica2_url", imagenFinal);

                                    }
                                    else if (datos.size() == 3){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[1];
                                        String descripcionTexto = itemCortado[0];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "1");
                                        editor.putString("web_productos_seccion_5_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica1_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica1_url", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[1];
                                        descripcionTexto = itemCortado[0];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "2");
                                        editor.putString("web_productos_seccion_5_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica2_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica2_url", imagenFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[1];
                                        descripcionTexto = itemCortado[0];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "3");
                                        editor.putString("web_productos_seccion_5_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica3_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica3_url", imagenFinal);

                                    }
                                    else if (datos.size() == 4){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[1];
                                        String descripcionTexto = itemCortado[0];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "1");
                                        editor.putString("web_productos_seccion_5_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica1_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica1_url", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[1];
                                        descripcionTexto = itemCortado[0];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "2");
                                        editor.putString("web_productos_seccion_5_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica2_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica2_url", imagenFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[1];
                                        descripcionTexto = itemCortado[0];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "3");
                                        editor.putString("web_productos_seccion_5_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica3_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica3_url", imagenFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[1];
                                        descripcionTexto = itemCortado[0];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "4");
                                        editor.putString("web_productos_seccion_5_caracteristica4_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica4_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica4_url", imagenFinal);

                                    }
                                    else if (datos.size() == 5){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[1];
                                        String descripcionTexto = itemCortado[0];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "1");
                                        editor.putString("web_productos_seccion_5_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica1_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica1_url", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[1];
                                        descripcionTexto = itemCortado[0];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "2");
                                        editor.putString("web_productos_seccion_5_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica2_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica2_url", imagenFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[1];
                                        descripcionTexto = itemCortado[0];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "3");
                                        editor.putString("web_productos_seccion_5_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica3_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica3_url", imagenFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[1];
                                        descripcionTexto = itemCortado[0];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "4");
                                        editor.putString("web_productos_seccion_5_caracteristica4_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica4_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica4_url", imagenFinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[1];
                                        descripcionTexto = itemCortado[0];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "5");
                                        editor.putString("web_productos_seccion_5_caracteristica5_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica5_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica5_url", imagenFinal);

                                    }
                                    else if (datos.size() == 6){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[1];
                                        String descripcionTexto = itemCortado[0];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "1");
                                        editor.putString("web_productos_seccion_5_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica1_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica1_url", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[1];
                                        descripcionTexto = itemCortado[0];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "2");
                                        editor.putString("web_productos_seccion_5_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica2_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica2_url", imagenFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[1];
                                        descripcionTexto = itemCortado[0];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "3");
                                        editor.putString("web_productos_seccion_5_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica3_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica3_url", imagenFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[1];
                                        descripcionTexto = itemCortado[0];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "4");
                                        editor.putString("web_productos_seccion_5_caracteristica4_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica4_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica4_url", imagenFinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[1];
                                        descripcionTexto = itemCortado[0];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "5");
                                        editor.putString("web_productos_seccion_5_caracteristica5_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica5_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica5_url", imagenFinal);

                                        frase = datos.get(5).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[1];
                                        descripcionTexto = itemCortado[0];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal ="";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal ="";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal ="";
                                        }

                                        editor.putString("web_productos_seccion_5_recycler", "6");
                                        editor.putString("web_productos_seccion_5_caracteristica6_titulo", tituloFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica6_descripcion", descripTextoFinal);
                                        editor.putString("web_productos_seccion_5_caracteristica6_url", imagenFinal);

                                    }

                                    // variables contacto
                                    String contacto_navbar = "";
                                    String contacto_titulo = "";
                                    String contacto_telefono = "";
                                    String contacto_email = "";
                                    String contacto_ubicacion = "";

                                    contacto_navbar = (String)contacto.get("navbar");
                                    contacto_titulo = (String)contacto.get("titulo");
                                    contacto_telefono = (String)contacto.get("telefono");
                                    contacto_email = (String)contacto.get("email");
                                    contacto_ubicacion = (String)contacto.get("lugar");

                                    editor.putString("web_productos_ubicacion_contacto", contacto_ubicacion);
                                    editor.putString("web_productos_email_contacto", contacto_email);
                                    editor.putString("web_productos_telefono_contacto", contacto_telefono);

                                    editor.commit();
                                }
                                else if(miPagina.getTipo() == 3){

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(user.getUid()+"-tipo_mi_pag_web", "3");
                                    editor.putString("nombrePagWeb", miPagina.getUrl());

                                    //SERVICIOS

                                    Map<String, Object> web;
                                    Map<String, Object> home;
                                    Map<String, Object> about;
                                    Map<String, Object> servicios;
                                    Map<String, Object> banner;
                                    Map<String, Object> portafolio;
                                    Map<String, Object> contacto;

                                    web = miPagina.getSecciones();
                                    home = (Map)web.get("home");
                                    about = (Map)web.get("about");
                                    servicios = (Map)web.get("servicios");
                                    banner = (Map)web.get("banner");
                                    portafolio = (Map)web.get("portafolio");
                                    contacto = (Map)web.get("contacto");

                                    List<caracteristicas_web> caracteristicas = new ArrayList<>();
                                    List<caracteristicas_web> nosotros = new ArrayList<>();
                                    List<caracteristicas_web> servicio = new ArrayList<>();
                                    List<caracteristicas_web> cuadroInfo = new ArrayList<>();
                                    List<caracteristicas_web> imagenes = new ArrayList<>();

                                    // variables home
                                    String home_navbar = "";
                                    String home_logo = "";
                                    String home_imagen = "";
                                    String home_titulo = "";

                                    home_navbar = (String)home.get("navbar");
                                    home_logo = (String)home.get("logo");
                                    home_imagen = (String)home.get("imagen");
                                    home_titulo = (String)home.get("titulo");

                                    editor.putString("web_servicios_img_seccion_1", home_imagen);
                                    editor.putString("web_servicios_logo_seccion_1",home_logo);
                                    editor.putString("web_servicios_titulo_home", home_titulo);

                                    // variables about
                                    String about_navbar = "";
                                    String about_titulo = "";
                                    String about_descripcion = "";
                                    String about_imagen = "";
                                    String about_Sdescripcion = "";

                                    // variables caracteristicas
                                    String caracteristicas_imagen = "";
                                    String caracteristicas_titulo = "";
                                    String caracteristicas_descripcion = "";

                                    about_navbar = (String)about.get("navbar");
                                    about_titulo = (String)about.get("titulo");
                                    about_descripcion = (String)about.get("descripcion");
                                    about_imagen = (String)about.get("imagen");
                                    about_Sdescripcion = (String)about.get("Sdescripcion");

                                    editor.putString("web_servicios_img_seccion_2", about_imagen);
                                    editor.putString("web_servicios_seccion_2_titulo", about_titulo);
                                    editor.putString("web_servicios_seccion_2_descripcion", about_descripcion);
                                    editor.putString("web_servicios_seccion_2_descripcion_2", about_Sdescripcion);

                                    Object prueba  = about.get("nosotros");
                                    List<?> datos = convertObjectToList(prueba);

                                    if (datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];


                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal = descripcionCortado[1];

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal = tituloCortado[1];
                                        tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal = imagenCortado[1];

                                        editor.putString("web_servicios_img1_seccion_3",imagenFinal);
                                        editor.putString("web_servicios_seccion_3_titulo1", tituloFinal);
                                        editor.putString("web_servicios_seccion_3_descripcion1", descripTextoFinal);

                                    }
                                    else if (datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_img1_seccion_3",imagenFinal);
                                        editor.putString("web_servicios_seccion_3_titulo1", tituloFinal);
                                        editor.putString("web_servicios_seccion_3_descripcion1", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_img2_seccion_3",imagenFinal);
                                        editor.putString("web_servicios_seccion_3_titulo2", tituloFinal);
                                        editor.putString("web_servicios_seccion_3_descripcion2", descripTextoFinal);

                                    }

                                    prueba  = about.get("caracteristicas");
                                    datos = convertObjectToList(prueba);

                                    if (datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_2_recycler", "1");
                                        editor.putString("web_servicios_seccion_2_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_2_caracteristica1_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_2_recycler", "1");
                                        editor.putString("web_servicios_seccion_2_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_2_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_2_recycler", "2");
                                        editor.putString("web_servicios_seccion_2_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_2_caracteristica2_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 3){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_2_recycler", "1");
                                        editor.putString("web_servicios_seccion_2_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_2_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_2_recycler", "2");
                                        editor.putString("web_servicios_seccion_2_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_2_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_2_recycler", "3");
                                        editor.putString("web_servicios_seccion_2_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_2_caracteristica3_descripcion", descripTextoFinal);

                                    }

                                    // variables servicios
                                    String servicios_navbar = "";
                                    String servicios_titulo = "";
                                    String servicios_descripcion = "";
                                    String servicios_descripcion_ = "";

                                    // variables servicio
                                    String servicio_imagen = "";
                                    String servicio_titulo = "";
                                    String servicio_descripcion = "";

                                    servicios_navbar = (String)servicios.get("navbar");
                                    servicio_titulo = (String)servicios.get("titulo");
                                    servicios_descripcion_ = (String)servicios.get("descripcion");

                                    editor.putString("web_servicios_seccion_4_titulo", servicio_titulo);
                                    editor.putString("web_servicios_seccion_4_descripcion", servicios_descripcion_);

                                    prueba  = servicios.get("servicio");
                                    datos = convertObjectToList(prueba);

                                    if (datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "1");
                                        editor.putString("web_servicios_seccion_4_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "1");
                                        editor.putString("web_servicios_seccion_4_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "2");
                                        editor.putString("web_servicios_seccion_4_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica2_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 3){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "1");
                                        editor.putString("web_servicios_seccion_4_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "2");
                                        editor.putString("web_servicios_seccion_4_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "3");
                                        editor.putString("web_servicios_seccion_4_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica3_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 4){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "1");
                                        editor.putString("web_servicios_seccion_4_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "2");
                                        editor.putString("web_servicios_seccion_4_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "3");
                                        editor.putString("web_servicios_seccion_4_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica3_descripcion", descripTextoFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "4");
                                        editor.putString("web_servicios_seccion_4_caracteristica4_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica4_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 5){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "1");
                                        editor.putString("web_servicios_seccion_4_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "2");
                                        editor.putString("web_servicios_seccion_4_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "3");
                                        editor.putString("web_servicios_seccion_4_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica3_descripcion", descripTextoFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "4");
                                        editor.putString("web_servicios_seccion_4_caracteristica4_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica4_descripcion", descripTextoFinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "5");
                                        editor.putString("web_servicios_seccion_4_caracteristica5_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica5_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 6){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "1");
                                        editor.putString("web_servicios_seccion_4_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "2");
                                        editor.putString("web_servicios_seccion_4_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "3");
                                        editor.putString("web_servicios_seccion_4_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica3_descripcion", descripTextoFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "4");
                                        editor.putString("web_servicios_seccion_4_caracteristica4_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica4_descripcion", descripTextoFinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "5");
                                        editor.putString("web_servicios_seccion_4_caracteristica5_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica5_descripcion", descripTextoFinal);

                                        frase = datos.get(5).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "6");
                                        editor.putString("web_servicios_seccion_4_caracteristica6_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica6_descripcion", descripTextoFinal);

                                    }

                                    // variables baner
                                    String baner_titulo = "";
                                    String baner_descripcion = "";

                                    baner_titulo = (String)banner.get("titulo");
                                    baner_descripcion = (String)banner.get("descripcion");

                                    editor.putString("web_servicios_seccion_5_titulo", baner_titulo);
                                    editor.putString("web_servicios_seccion_5_descripcion", baner_descripcion);

                                    prueba  = banner.get("cuadroInfo");
                                    datos = convertObjectToList(prueba);

                                    if (datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_5_recycler", "1");
                                        editor.putString("web_servicios_seccion_5_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_5_caracteristica1_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_5_recycler", "1");
                                        editor.putString("web_servicios_seccion_5_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_5_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_5_recycler", "2");
                                        editor.putString("web_servicios_seccion_5_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_5_caracteristica2_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 3){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_5_recycler", "1");
                                        editor.putString("web_servicios_seccion_5_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_5_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_5_recycler", "2");
                                        editor.putString("web_servicios_seccion_5_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_5_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_5_recycler", "3");
                                        editor.putString("web_servicios_seccion_5_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_5_caracteristica3_descripcion", descripTextoFinal);

                                    }

                                    // variables portafolio
                                    String portafolio_navbar = "";
                                    String portafolio_titulo = "";

                                    portafolio_navbar = (String)portafolio.get("navbar");
                                    portafolio_titulo = (String)portafolio.get("titulo");

                                    prueba  = portafolio.get("imagenes");
                                    datos = convertObjectToList(prueba);

                                    if (datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "1");
                                        editor.putString("web_servicios_seccion_6_caracteristica1_titulo", tituloFinal);
                                       // editor.putString("web_servicios_seccion_6_caracteristica1_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica1_url", imagenFinal);

                                    }
                                    else if (datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "1");
                                        editor.putString("web_servicios_seccion_6_caracteristica1_titulo", tituloFinal);
                                       // editor.putString("web_servicios_seccion_6_caracteristica1_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica1_url", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "2");
                                        editor.putString("web_servicios_seccion_6_caracteristica2_titulo", tituloFinal);
                                        //editor.putString("web_servicios_seccion_6_caracteristica2_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica2_url", imagenFinal);

                                    }
                                    else if (datos.size() == 3){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "1");
                                        editor.putString("web_servicios_seccion_6_caracteristica1_titulo", tituloFinal);
                                  //      editor.putString("web_servicios_seccion_6_caracteristica1_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica1_url", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "2");
                                        editor.putString("web_servicios_seccion_6_caracteristica2_titulo", tituloFinal);
                                   //     editor.putString("web_servicios_seccion_6_caracteristica2_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica2_url", imagenFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "3");
                                        editor.putString("web_servicios_seccion_6_caracteristica3_titulo", tituloFinal);
                                 //       editor.putString("web_servicios_seccion_6_caracteristica3_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica3_url", imagenFinal);

                                    }
                                    else if (datos.size() == 4){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "1");
                                        editor.putString("web_servicios_seccion_6_caracteristica1_titulo", tituloFinal);
                                 //       editor.putString("web_servicios_seccion_6_caracteristica1_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica1_url", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "2");
                                        editor.putString("web_servicios_seccion_6_caracteristica2_titulo", tituloFinal);
                                     //   editor.putString("web_servicios_seccion_6_caracteristica2_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica2_url", imagenFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "3");
                                        editor.putString("web_servicios_seccion_6_caracteristica3_titulo", tituloFinal);
                                   //     editor.putString("web_servicios_seccion_6_caracteristica3_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica3_url", imagenFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "4");
                                        editor.putString("web_servicios_seccion_6_caracteristica4_titulo", tituloFinal);
                                 //       editor.putString("web_servicios_seccion_6_caracteristica4_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica4_url", imagenFinal);

                                    }
                                    else if (datos.size() == 5){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "1");
                                        editor.putString("web_servicios_seccion_6_caracteristica1_titulo", tituloFinal);
                             //           editor.putString("web_servicios_seccion_6_caracteristica1_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica1_url", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "2");
                                        editor.putString("web_servicios_seccion_6_caracteristica2_titulo", tituloFinal);
                              //          editor.putString("web_servicios_seccion_6_caracteristica2_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica2_url", imagenFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "3");
                                        editor.putString("web_servicios_seccion_6_caracteristica3_titulo", tituloFinal);
                                //        editor.putString("web_servicios_seccion_6_caracteristica3_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica3_url", imagenFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "4");
                                        editor.putString("web_servicios_seccion_6_caracteristica4_titulo", tituloFinal);
                                 //       editor.putString("web_servicios_seccion_6_caracteristica4_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica4_url", imagenFinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "5");
                                        editor.putString("web_servicios_seccion_6_caracteristica5_titulo", tituloFinal);
                                    //    editor.putString("web_servicios_seccion_6_caracteristica5_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica5_url", imagenFinal);

                                    }
                                    else if (datos.size() == 6){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "1");
                                        editor.putString("web_servicios_seccion_6_caracteristica1_titulo", tituloFinal);
                                 //       editor.putString("web_servicios_seccion_6_caracteristica1_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica1_url", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "2");
                                        editor.putString("web_servicios_seccion_6_caracteristica2_titulo", tituloFinal);
                                     //   editor.putString("web_servicios_seccion_6_caracteristica2_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica2_url", imagenFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "3");
                                        editor.putString("web_servicios_seccion_6_caracteristica3_titulo", tituloFinal);
                                   //     editor.putString("web_servicios_seccion_6_caracteristica3_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica3_url", imagenFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "4");
                                        editor.putString("web_servicios_seccion_6_caracteristica4_titulo", tituloFinal);
                                    //    editor.putString("web_servicios_seccion_6_caracteristica4_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica4_url", imagenFinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "5");
                                        editor.putString("web_servicios_seccion_6_caracteristica5_titulo", tituloFinal);
                                     //   editor.putString("web_servicios_seccion_6_caracteristica5_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica5_url", imagenFinal);

                                        frase = datos.get(5).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_6_recycler", "6");
                                        editor.putString("web_servicios_seccion_6_caracteristica6_titulo", tituloFinal);
                                      //  editor.putString("web_servicios_seccion_6_caracteristica6_descripcion", servicios_descripcion);
                                        editor.putString("web_servicios_seccion_6_caracteristica6_url", imagenFinal);

                                    }

                                    // variables contacto
                                    String contacto_navbar = "";
                                    String contacto_titulo = "";
                                    String contacto_telefono = "";
                                    String contacto_email = "";
                                    String contacto_lugar = "";

                                    contacto_navbar = (String)contacto.get("navbar");
                                    contacto_titulo = (String)contacto.get("titulo");
                                    contacto_telefono = (String)contacto.get("telefono");
                                    contacto_email = (String)contacto.get("email");
                                    contacto_lugar = (String)contacto.get("lugar");

                                    editor.putString("web_servicios_ubicacion_contacto", contacto_lugar);
                                    editor.putString("web_servicios_email_contacto", contacto_email);
                                    editor.putString("web_servicios_telefono_contacto", contacto_telefono);

                                    editor.commit();

                                }
                                else if(miPagina.getTipo() == 4){

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(user.getUid()+"-tipo_mi_pag_web", "4");
                                    editor.putString("nombrePagWeb", miPagina.getUrl());
                                    //MODA

                                    List<caracteristicas_web> work = new ArrayList<>();
                                    List<caracteristicas_web> list = new ArrayList<>();
                                    List<caracteristicas_web> imagen = new ArrayList<>();

                                    Map<String, Object> web;
                                    Map<String, Object> home;
                                    Map<String, Object> about;
                                    Map<String, Object> services;
                                    Map<String, Object> gallery;
                                    Map<String, Object> contact;
                                    Map<String, Object> social;

                                    web = miPagina.getSecciones();
                                    home = (Map)web.get("home");
                                    about = (Map)web.get("about");

                                    services = (Map)web.get("services");
                                    gallery = (Map)web.get("gallery");
                                    contact = (Map)web.get("contact");
                                    social = (Map)web.get("social");

                                    // variables home
                                    String home_navbar = "";
                                    String home_subtitulo = "";
                                    String home_imagen = "";
                                    String home_titulo = "";
                                    String home_text = "";

                                    home_navbar = (String)home.get("navbar");
                                    home_subtitulo = (String)home.get("subtitle");
                                    home_imagen = (String)home.get("background");
                                    home_titulo = (String)home.get("title");
                                    home_text = (String)home.get("text");

                                    editor.putString("web_moda_img_seccion_1", home_imagen);
                                    editor.putString("web_moda_titulo_home", home_titulo);
                                    editor.putString("web_moda_subtitulo_home", home_subtitulo);
                                    editor.putString("web_moda_descripcion_home", home_text);

                                    // variables about
                                    String about_navbar = "";
                                    String about_titulo = "";
                                    String about_subtitulo = "";
                                    String about_descripcion = "";
                                    String about_imagen = "";

                                    about_navbar = (String)about.get("navbar");
                                    about_titulo = (String)about.get("title");
                                    about_subtitulo = (String)about.get("subtitle");
                                    about_descripcion = (String)about.get("text");
                                    about_imagen = (String)about.get("img");

                                    editor.putString("web_moda_img_seccion_2",about_imagen);
                                    editor.putString("web_moda_titulo_seccion_2", about_titulo);
                                    editor.putString("web_moda_subtitulo_seccion_2", about_subtitulo);
                                    editor.putString("web_moda_descripcion_seccion_2", about_descripcion);

                                    // variables servicios
                                    String servicios_navbar = "";
                                    String servicios_imagen = "";

                                    String img = "";
                                    String titulo = "";
                                    String descripcion = "";

                                    servicios_navbar = (String)services.get("navbar");
                                    servicios_imagen = (String)services.get("img");

                                    editor.putString("web_moda_img_seccion_4", servicios_imagen);

                                    Object prueba  = services.get("list");
                                    List<?> datos = convertObjectToList(prueba);

                                    if (datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "1");
                                        editor.putString("web_servicios_seccion_4_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "1");
                                        editor.putString("web_servicios_seccion_4_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "2");
                                        editor.putString("web_servicios_seccion_4_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica2_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 3){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "1");
                                        editor.putString("web_servicios_seccion_4_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "2");
                                        editor.putString("web_servicios_seccion_4_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "3");
                                        editor.putString("web_servicios_seccion_4_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica3_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 4){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "1");
                                        editor.putString("web_servicios_seccion_4_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "2");
                                        editor.putString("web_servicios_seccion_4_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "3");
                                        editor.putString("web_servicios_seccion_4_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica3_descripcion", descripTextoFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "4");
                                        editor.putString("web_servicios_seccion_4_caracteristica4_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica4_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 5){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "1");
                                        editor.putString("web_servicios_seccion_4_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "2");
                                        editor.putString("web_servicios_seccion_4_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "3");
                                        editor.putString("web_servicios_seccion_4_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica3_descripcion", descripTextoFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "4");
                                        editor.putString("web_servicios_seccion_4_caracteristica4_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica4_descripcion", descripTextoFinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "5");
                                        editor.putString("web_servicios_seccion_4_caracteristica5_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica5_descripcion", descripTextoFinal);

                                        editor.commit();

                                    }
                                    else if (datos.size() == 6){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "1");
                                        editor.putString("web_servicios_seccion_4_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "2");
                                        editor.putString("web_servicios_seccion_4_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "3");
                                        editor.putString("web_servicios_seccion_4_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica3_descripcion", descripTextoFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "4");
                                        editor.putString("web_servicios_seccion_4_caracteristica4_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica4_descripcion", descripTextoFinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "5");
                                        editor.putString("web_servicios_seccion_4_caracteristica5_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica5_descripcion", descripTextoFinal);

                                        frase = datos.get(5).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_servicios_seccion_4_recycler", "6");
                                        editor.putString("web_servicios_seccion_4_caracteristica6_titulo", tituloFinal);
                                        editor.putString("web_servicios_seccion_4_caracteristica6_descripcion", descripTextoFinal);

                                        editor.commit();

                                    }

                                    //work

                                    prueba  = web.get("work");
                                    datos = convertObjectToList(prueba);

                                    if (datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_seccion_3_recycler", "1");
                                        editor.putString("web_moda_seccion_3_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_moda_seccion_3_caracteristica1_descripcion", descripTextoFinal);
                                        editor.putString("web_moda_seccion_3_caracteristica1_url", imagenFinal);
                                    }
                                    else if (datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_seccion_3_recycler", "1");
                                        editor.putString("web_moda_seccion_3_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_moda_seccion_3_caracteristica1_descripcion", descripTextoFinal);
                                        editor.putString("web_moda_seccion_3_caracteristica1_url", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_seccion_3_recycler", "2");
                                        editor.putString("web_moda_seccion_3_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_moda_seccion_3_caracteristica2_descripcion", descripTextoFinal);
                                        editor.putString("web_moda_seccion_3_caracteristica2_url", imagenFinal);

                                    }
                                    else if (datos.size() == 3){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_seccion_3_recycler", "1");
                                        editor.putString("web_moda_seccion_3_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_moda_seccion_3_caracteristica1_descripcion", descripTextoFinal);
                                        editor.putString("web_moda_seccion_3_caracteristica1_url", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_seccion_3_recycler", "2");
                                        editor.putString("web_moda_seccion_3_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_moda_seccion_3_caracteristica2_descripcion", descripTextoFinal);
                                        editor.putString("web_moda_seccion_3_caracteristica2_url", imagenFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_seccion_3_recycler", "3");
                                        editor.putString("web_moda_seccion_3_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_moda_seccion_3_caracteristica3_descripcion", descripTextoFinal);
                                        editor.putString("web_moda_seccion_3_caracteristica3_url", imagenFinal);

                                    }

                                    // variables galleria

                                    String galeria_navbar = "";
                                    String galeria_titulo = "";
                                    String galeria_subtitulo = "";

                                    galeria_navbar = (String)gallery.get("navbar");
                                    galeria_titulo = (String)gallery.get("title");
                                    galeria_subtitulo = (String)gallery.get("subtitle");

                                    editor.putString("web_moda_titulo_seccion5", galeria_titulo);
                                    editor.putString("web_moda_subtitulo_seccion5", galeria_subtitulo);

                                    prueba  = gallery.get("imagen");
                                    datos = convertObjectToList(prueba);

                                    if (datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_1_seccion_5", imagenFinal);

                                    }
                                    else if (datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_1_seccion_5", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_2_seccion_5", imagenFinal);


                                    }
                                    else if (datos.size() == 3){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_1_seccion_5", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_2_seccion_5", imagenFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_3_seccion_5", imagenFinal);

                                    }
                                    else if (datos.size() == 4){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_1_seccion_5", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_2_seccion_5", imagenFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_3_seccion_5", imagenFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_4_seccion_5", imagenFinal);

                                    }
                                    else if (datos.size() == 5){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_1_seccion_5", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_2_seccion_5", imagenFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_3_seccion_5", imagenFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_4_seccion_5", imagenFinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_5_seccion_5", imagenFinal);

                                    }
                                    else if (datos.size() == 6){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_1_seccion_5", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_2_seccion_5", imagenFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_3_seccion_5", imagenFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_4_seccion_5", imagenFinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_5_seccion_5", imagenFinal);

                                        frase = datos.get(5).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_6_seccion_5", imagenFinal);

                                    }
                                    else if (datos.size() == 7){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_1_seccion_5", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_2_seccion_5", imagenFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_3_seccion_5", imagenFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_4_seccion_5", imagenFinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_5_seccion_5", imagenFinal);

                                        frase = datos.get(5).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_6_seccion_5", imagenFinal);

                                        frase = datos.get(6).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_7_seccion_5", imagenFinal);

                                    }
                                    else if (datos.size() == 8){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_1_seccion_5", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_2_seccion_5", imagenFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_3_seccion_5", imagenFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_4_seccion_5", imagenFinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_5_seccion_5", imagenFinal);

                                        frase = datos.get(5).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_6_seccion_5", imagenFinal);

                                        frase = datos.get(6).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_7_seccion_5", imagenFinal);

                                        frase = datos.get(7).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_8_seccion_5", imagenFinal);

                                    }
                                    else if (datos.size() == 9){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String imagenTexto = itemCortado[0];
                                        String descripcionTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] imagenCortado = imagenTexto.split("gen=");
                                        String imagenFinal;
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_1_seccion_5", imagenFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_2_seccion_5", imagenFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_3_seccion_5", imagenFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_4_seccion_5", imagenFinal);

                                        frase = datos.get(4).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_5_seccion_5", imagenFinal);

                                        frase = datos.get(5).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_6_seccion_5", imagenFinal);

                                        frase = datos.get(6).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_7_seccion_5", imagenFinal);

                                        frase = datos.get(7).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_8_seccion_5", imagenFinal);

                                        frase = datos.get(8).toString();

                                        itemCortado = frase.split(",");
                                        imagenTexto = itemCortado[0];
                                        descripcionTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        imagenCortado = imagenTexto.split("gen=");
                                        if (imagenCortado.length > 1){
                                            imagenFinal = imagenCortado[1];
                                        }
                                        else {
                                            imagenFinal = "";
                                        }

                                        editor.putString("web_moda_img_9_seccion_5", imagenFinal);

                                    }

                                    // variables contacto
                                    String contacto_navbar = "";
                                    String contacto_titulo = "";
                                    String contacto_telefono = "";
                                    String contacto_email = "";
                                    String contacto_lugar = "";
                                    String contacto_img = "";

                                    contacto_navbar = (String)contact.get("navbar");
                                    contacto_titulo = (String)contact.get("title");
                                    contacto_telefono = (String)contact.get("phone");
                                    contacto_email = (String)contact.get("email");
                                    contacto_lugar = (String)contact.get("address");
                                    contacto_img = (String)contact.get("img");

                                    editor.putString("web_moda_img_seccion_6", contacto_img);
                                    editor.putString("web_moda_titulo_seccion6", contacto_titulo);
                                    editor.putString("web_moda_direccion_seccion6", contacto_lugar);
                                    editor.putString("web_moda_telefono_seccion6", contacto_telefono);
                                    editor.putString("web_moda_email_seccion6", contacto_email);

                                    // variables social
                                    String social_facebook = "";
                                    String social_twitter = "";
                                    String social_instagram = "";

                                    social_facebook = (String)social.get("facebook");
                                    social_twitter = (String)social.get("twitter");
                                    social_instagram = (String)social.get("instagram");

                                    editor.putString("web_moda_facebook_seccion7", social_facebook);
                                    editor.putString("web_moda_instagram_seccion7", social_instagram);
                                    editor.putString("web_moda_twitter_seccion7", social_twitter);

                                    editor.commit();

                                }
                                else if(miPagina.getTipo() == 5){

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(user.getUid()+"-tipo_mi_pag_web", "5");
                                    editor.putString("nombrePagWeb", miPagina.getUrl());

                                    //SALUD

                                    Map<String, Object> web;
                                    Map<String, Object> home;
                                    Map<String, Object> servicios;
                                    Map<String, Object> horario;
                                    Map<String, Object> about;
                                    Map<String, Object> baner;
                                    Map<String, Object> contacto;
                                    Map<String, Object> sociales;

                                    web = miPagina.getSecciones();
                                    home = (Map)web.get("home");
                                    servicios = (Map)web.get("servicios");
                                    horario = (Map)web.get("horario");
                                    about = (Map)web.get("about");
                                    baner = (Map)web.get("banner");
                                    contacto = (Map)web.get("contacto");
                                    sociales = (Map)web.get("sociales");

                                    List<caracteristicas_web> servicio = new ArrayList<>();
                                    List<Horario> hora = new ArrayList<>();
                                    List<caracteristicas_web> caracteristicas = new ArrayList<>();

                                    // variables home
                                    String home_navbar = "";
                                    String home_logo = "";
                                    String home_imagen = "";
                                    String home_titulo = "";
                                    String home_subtitulo = "";

                                    home_navbar = (String)home.get("navbar");
                                    home_logo = (String)home.get("logo");
                                    home_imagen = (String)home.get("imagen");
                                    home_titulo = (String)home.get("titulo");
                                    home_subtitulo = (String)home.get("subtitulo");

                                    editor.putString("web_salud_img_seccion_1", home_imagen);
                                    editor.putString("web_salud_titulo_home", home_titulo);
                                    editor.putString("web_salud_subtitulo_home", home_subtitulo);

                                    // variables servicios
                                    String servicios_navbar = "";
                                    String servicios_descripcion = "";
                                    String servicios_titulo = "";

                                    String imagen = "";
                                    String titulo = "";
                                    String descripcion = "";

                                    servicios_navbar = (String)servicios.get("navbar");
                                    servicios_descripcion = (String)servicios.get("descripcion");
                                    servicios_titulo = (String)servicios.get("titulo");

                                    editor.putString("web_salud_seccion_2_titulo", servicios_titulo);
                                    editor.putString("web_salud_seccion_2_descripcion", servicios_descripcion);

                                    Object prueba  = servicios.get("servicio");
                                    List<?> datos = convertObjectToList(prueba);

                                    if (datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String descripcionTexto = itemCortado[0];
                                        String imagenTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_2_recycler", "1");
                                        editor.putString("web_salud_seccion_2_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_salud_seccion_2_caracteristica1_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");

                                        String descripcionTexto = itemCortado[0];
                                        String imagenTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_2_recycler", "1");
                                        editor.putString("web_salud_seccion_2_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_salud_seccion_2_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");

                                        descripcionTexto = itemCortado[0];
                                        imagenTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_2_recycler", "2");
                                        editor.putString("web_salud_seccion_2_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_salud_seccion_2_caracteristica2_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 3){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");

                                        String descripcionTexto = itemCortado[0];
                                        String imagenTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_2_recycler", "1");
                                        editor.putString("web_salud_seccion_2_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_salud_seccion_2_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");

                                        descripcionTexto = itemCortado[0];
                                        imagenTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_2_recycler", "2");
                                        editor.putString("web_salud_seccion_2_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_salud_seccion_2_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");

                                        descripcionTexto = itemCortado[0];
                                        imagenTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_2_recycler", "3");
                                        editor.putString("web_salud_seccion_2_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_salud_seccion_2_caracteristica3_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 4){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");

                                        String descripcionTexto = itemCortado[0];
                                        String imagenTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_2_recycler", "1");
                                        editor.putString("web_salud_seccion_2_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_salud_seccion_2_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");

                                        descripcionTexto = itemCortado[0];
                                        imagenTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_2_recycler", "2");
                                        editor.putString("web_salud_seccion_2_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_salud_seccion_2_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");

                                        descripcionTexto = itemCortado[0];
                                        imagenTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_2_recycler", "3");
                                        editor.putString("web_salud_seccion_2_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_salud_seccion_2_caracteristica3_descripcion", descripTextoFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");

                                        descripcionTexto = itemCortado[0];
                                        imagenTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_2_recycler", "4");
                                        editor.putString("web_salud_seccion_2_caracteristica4_titulo", tituloFinal);
                                        editor.putString("web_salud_seccion_2_caracteristica4_descripcion", descripTextoFinal);

                                    }

                                    // variables horarios
                                    String horarios_titulo = "Horarios";

                                    horarios_titulo = (String)horario.get("titulo");

                                    prueba  = horario.get("hora");
                                    datos = convertObjectToList(prueba);

                                    if (datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String horaTexto = itemCortado[0];
                                        String horaHastaTexto = itemCortado[1];
                                        String diaTexto = itemCortado[2];

                                        String[] horaCortado = horaTexto.split("=");
                                        String horaFinal;
                                        if (horaCortado.length > 1){
                                            horaFinal = horaCortado[1];
                                        }
                                        else {
                                            horaFinal = "";
                                        }

                                        String[] diaCortado = diaTexto.split("=");
                                        String diaFinal;
                                        if (diaCortado.length > 1){
                                            diaFinal = diaCortado[1];
                                            diaFinal = diaFinal.substring(0, diaFinal.length() - 1);
                                        }
                                        else {
                                            diaFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_3_recycler", "1");
                                        editor.putString("web_salud_seccion_3_caracteristica1_titulo", diaFinal);
                                        editor.putString("web_salud_seccion_3_caracteristica1_descripcion", horaFinal);

                                    }
                                    else if (datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String horaTexto = itemCortado[0];
                                        String horaHastaTexto = itemCortado[1];
                                        String diaTexto = itemCortado[2];

                                        String[] horaCortado = horaTexto.split("=");
                                        String horaFinal;
                                        if (horaCortado.length > 1){
                                            horaFinal = horaCortado[1];
                                        }
                                        else {
                                            horaFinal = "";
                                        }

                                        String[] diaCortado = diaTexto.split("=");
                                        String diaFinal;
                                        if (diaCortado.length > 1){
                                            diaFinal = diaCortado[1];
                                            diaFinal = diaFinal.substring(0, diaFinal.length() - 1);
                                        }
                                        else {
                                            diaFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_3_recycler", "1");
                                        editor.putString("web_salud_seccion_3_caracteristica1_titulo", diaFinal);
                                        editor.putString("web_salud_seccion_3_caracteristica1_descripcion", horaFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        horaTexto = itemCortado[0];
                                        horaHastaTexto = itemCortado[1];
                                        diaTexto = itemCortado[2];

                                        horaCortado = horaTexto.split("=");
                                        if (horaCortado.length > 1){
                                            horaFinal = horaCortado[1];
                                        }
                                        else {
                                            horaFinal = "";
                                        }

                                        diaCortado = diaTexto.split("=");
                                        if (diaCortado.length > 1){
                                            diaFinal = diaCortado[1];
                                            diaFinal = diaFinal.substring(0, diaFinal.length() - 1);
                                        }
                                        else {
                                            diaFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_3_recycler", "2");
                                        editor.putString("web_salud_seccion_3_caracteristica2_titulo", diaFinal);
                                        editor.putString("web_salud_seccion_3_caracteristica2_descripcion", horaFinal);

                                    }
                                    else if (datos.size() == 3){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String horaTexto = itemCortado[0];
                                        String horaHastaTexto = itemCortado[1];
                                        String diaTexto = itemCortado[2];

                                        String[] horaCortado = horaTexto.split("=");
                                        String horaFinal;
                                        if (horaCortado.length > 1){
                                            horaFinal = horaCortado[1];
                                        }
                                        else {
                                            horaFinal = "";
                                        }

                                        String[] diaCortado = diaTexto.split("=");
                                        String diaFinal;
                                        if (diaCortado.length > 1){
                                            diaFinal = diaCortado[1];
                                            diaFinal = diaFinal.substring(0, diaFinal.length() - 1);
                                        }
                                        else {
                                            diaFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_3_recycler", "1");
                                        editor.putString("web_salud_seccion_3_caracteristica1_titulo", diaFinal);
                                        editor.putString("web_salud_seccion_3_caracteristica1_descripcion", horaFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        horaTexto = itemCortado[0];
                                        horaHastaTexto = itemCortado[1];
                                        diaTexto = itemCortado[2];

                                        horaCortado = horaTexto.split("=");
                                        if (horaCortado.length > 1){
                                            horaFinal = horaCortado[1];
                                        }
                                        else {
                                            horaFinal = "";
                                        }

                                        diaCortado = diaTexto.split("=");
                                        if (diaCortado.length > 1){
                                            diaFinal = diaCortado[1];
                                            diaFinal = diaFinal.substring(0, diaFinal.length() - 1);
                                        }
                                        else {
                                            diaFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_3_recycler", "2");
                                        editor.putString("web_salud_seccion_3_caracteristica2_titulo", diaFinal);
                                        editor.putString("web_salud_seccion_3_caracteristica2_descripcion", horaFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        horaTexto = itemCortado[0];
                                        horaHastaTexto = itemCortado[1];
                                        diaTexto = itemCortado[2];

                                        horaCortado = horaTexto.split("=");
                                        if (horaCortado.length > 1){
                                            horaFinal = horaCortado[1];
                                        }
                                        else {
                                            horaFinal = "";
                                        }

                                        diaCortado = diaTexto.split("=");
                                        if (diaCortado.length > 1){
                                            diaFinal = diaCortado[1];
                                            diaFinal = diaFinal.substring(0, diaFinal.length() - 1);
                                        }
                                        else {
                                            diaFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_3_recycler", "3");
                                        editor.putString("web_salud_seccion_3_caracteristica3_titulo", diaFinal);
                                        editor.putString("web_salud_seccion_3_caracteristica3_descripcion", horaFinal);

                                    }
                                    else if (datos.size() == 4){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String horaTexto = itemCortado[0];
                                        String horaHastaTexto = itemCortado[1];
                                        String diaTexto = itemCortado[2];

                                        String[] horaCortado = horaTexto.split("=");
                                        String horaFinal;
                                        if (horaCortado.length > 1){
                                            horaFinal = horaCortado[1];
                                        }
                                        else {
                                            horaFinal = "";
                                        }

                                        String[] diaCortado = diaTexto.split("=");
                                        String diaFinal;
                                        if (diaCortado.length > 1){
                                            diaFinal = diaCortado[1];
                                            diaFinal = diaFinal.substring(0, diaFinal.length() - 1);
                                        }
                                        else {
                                            diaFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_3_recycler", "1");
                                        editor.putString("web_salud_seccion_3_caracteristica1_titulo", diaFinal);
                                        editor.putString("web_salud_seccion_3_caracteristica1_descripcion", horaFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        horaTexto = itemCortado[0];
                                        horaHastaTexto = itemCortado[1];
                                        diaTexto = itemCortado[2];

                                        horaCortado = horaTexto.split("=");
                                        if (horaCortado.length > 1){
                                            horaFinal = horaCortado[1];
                                        }
                                        else {
                                            horaFinal = "";
                                        }

                                        diaCortado = diaTexto.split("=");
                                        if (diaCortado.length > 1){
                                            diaFinal = diaCortado[1];
                                            diaFinal = diaFinal.substring(0, diaFinal.length() - 1);
                                        }
                                        else {
                                            diaFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_3_recycler", "2");
                                        editor.putString("web_salud_seccion_3_caracteristica2_titulo", diaFinal);
                                        editor.putString("web_salud_seccion_3_caracteristica2_descripcion", horaFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        horaTexto = itemCortado[0];
                                        horaHastaTexto = itemCortado[1];
                                        diaTexto = itemCortado[2];

                                        horaCortado = horaTexto.split("=");
                                        if (horaCortado.length > 1){
                                            horaFinal = horaCortado[1];
                                        }
                                        else {
                                            horaFinal = "";
                                        }

                                        diaCortado = diaTexto.split("=");
                                        if (diaCortado.length > 1){
                                            diaFinal = diaCortado[1];
                                            diaFinal = diaFinal.substring(0, diaFinal.length() - 1);
                                        }
                                        else {
                                            diaFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_3_recycler", "3");
                                        editor.putString("web_salud_seccion_3_caracteristica3_titulo", diaFinal);
                                        editor.putString("web_salud_seccion_3_caracteristica3_descripcion", horaFinal);

                                        frase = datos.get(3).toString();

                                        itemCortado = frase.split(",");
                                        horaTexto = itemCortado[0];
                                        horaHastaTexto = itemCortado[1];
                                        diaTexto = itemCortado[2];

                                        horaCortado = horaTexto.split("=");
                                        if (horaCortado.length > 1){
                                            horaFinal = horaCortado[1];
                                        }
                                        else {
                                            horaFinal = "";
                                        }

                                        diaCortado = diaTexto.split("=");
                                        if (diaCortado.length > 1){
                                            diaFinal = diaCortado[1];
                                            diaFinal = diaFinal.substring(0, diaFinal.length() - 1);
                                        }
                                        else {
                                            diaFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_3_recycler", "4");
                                        editor.putString("web_salud_seccion_3_caracteristica4_titulo", diaFinal);
                                        editor.putString("web_salud_seccion_3_caracteristica4_descripcion", horaFinal);

                                    }

                                    // variables about
                                    String about_navbar = "";
                                    String about_titulo = "";
                                    String about_descripcion = "";

                                    about_navbar = (String)about.get("navbar");
                                    about_titulo = (String)about.get("titulo");
                                    about_descripcion = (String)about.get("descripcion");

                                    editor.putString("web_salud_seccion_4_titulo", about_titulo);
                                    editor.putString("web_salud_seccion_4_descripcion", about_descripcion);

                                    prueba  = about.get("caracteristicas");
                                    datos = convertObjectToList(prueba);

                                    if (datos.size() == 1){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String descripcionTexto = itemCortado[0];
                                        String imagenTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_4_recycler", "1");
                                        editor.putString("web_salud_seccion_4_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_salud_seccion_4_caracteristica1_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 2){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String descripcionTexto = itemCortado[0];
                                        String imagenTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_4_recycler", "1");
                                        editor.putString("web_salud_seccion_4_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_salud_seccion_4_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        imagenTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_4_recycler", "2");
                                        editor.putString("web_salud_seccion_4_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_salud_seccion_4_caracteristica2_descripcion", descripTextoFinal);

                                    }
                                    else if (datos.size() == 3){

                                        String frase = datos.get(0).toString();

                                        String[] itemCortado = frase.split(",");
                                        String descripcionTexto = itemCortado[0];
                                        String imagenTexto = itemCortado[1];
                                        String tituloTexto = itemCortado[2];

                                        String[] descripcionCortado = descripcionTexto.split("=");
                                        String descripTextoFinal;
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        String[] tituloCortado = tituloTexto.split("=");
                                        String tituloFinal;
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_4_recycler", "1");
                                        editor.putString("web_salud_seccion_4_caracteristica1_titulo", tituloFinal);
                                        editor.putString("web_salud_seccion_4_caracteristica1_descripcion", descripTextoFinal);

                                        frase = datos.get(1).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        imagenTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_4_recycler", "2");
                                        editor.putString("web_salud_seccion_4_caracteristica2_titulo", tituloFinal);
                                        editor.putString("web_salud_seccion_4_caracteristica2_descripcion", descripTextoFinal);

                                        frase = datos.get(2).toString();

                                        itemCortado = frase.split(",");
                                        descripcionTexto = itemCortado[0];
                                        imagenTexto = itemCortado[1];
                                        tituloTexto = itemCortado[2];

                                        descripcionCortado = descripcionTexto.split("=");
                                        if (descripcionCortado.length > 1){
                                            descripTextoFinal = descripcionCortado[1];
                                        }
                                        else {
                                            descripTextoFinal = "";
                                        }

                                        tituloCortado = tituloTexto.split("=");
                                        if (tituloCortado.length > 1){
                                            tituloFinal = tituloCortado[1];
                                            tituloFinal = tituloFinal.substring(0, tituloFinal.length() - 1);
                                        }
                                        else {
                                            tituloFinal = "";
                                        }

                                        editor.putString("web_salud_seccion_4_recycler", "3");
                                        editor.putString("web_salud_seccion_4_caracteristica3_titulo", tituloFinal);
                                        editor.putString("web_salud_seccion_4_caracteristica3_descripcion", descripTextoFinal);

                                    }

                                    // variables banner
                                    String banner_titulo = "";
                                    String banner_descripcion = "";
                                    String banner_autor = "";

                                    banner_titulo = (String)baner.get("titulo");
                                    banner_descripcion = (String)baner.get("descripcion");
                                    banner_autor = (String)baner.get("autor");

                                    editor.putString("web_salud_titulo_baner", banner_titulo);
                                    editor.putString("web_salud_frase_baner", banner_descripcion);
                                    editor.putString("web_salud_autor_baner", banner_autor);

                                    // variables contacto
                                    String contacto_navbar = "";
                                    String contacto_titulo = "";
                                    String contacto_telefono = "";
                                    String contacto_email = "";
                                    String contacto_ubicacion = "";

                                    contacto_navbar = (String)contacto.get("navbar");
                                    contacto_titulo = (String)contacto.get("titulo");
                                    contacto_telefono = (String)contacto.get("telefono");
                                    contacto_email = (String)contacto.get("email");
                                    contacto_ubicacion = (String)contacto.get("lugar");

                                    editor.putString("web_salud_ubicacion_contacto", contacto_ubicacion);
                                    editor.putString("web_salud_email_contacto", contacto_email);
                                    editor.putString("web_salud_telefono_contacto", contacto_telefono);

                                    // variables sociales
                                    String sociales_titulo = "";
                                    String sociales_facebook = "";
                                    String sociales_instagram = "";
                                    String sociales_twitter = "";

                                    sociales_titulo = (String)sociales.get("titulo");
                                    sociales_facebook = (String)sociales.get("facebook");
                                    sociales_instagram = (String)sociales.get("instagram");
                                    sociales_twitter = (String)sociales.get("twitter");

                                    editor.putString("web_salud_facebook_seccion_7", sociales_facebook);
                                    editor.putString("web_salud_instagram_seccion_7", sociales_instagram);
                                    editor.putString("web_salud_twitter_seccion_7", sociales_twitter);

                                    editor.commit();

                                }
                                else if(miPagina.getTipo() == 6){

                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString(user.getUid()+"-tipo_mi_pag_web", "6");
                                    editor.putString("nombrePagWeb", miPagina.getUrl());

                                    //APPS

                                    Map<String, Object> web;
                                    Map<String, Object> banner;
                                    Map<String, Object> contacto;
                                    Map<String, Object> descargas;
                                    Map<String, Object> home;
                                    Map<String, Object> servicios;

                                    web = miPagina.getSecciones();
                                    home = (Map)web.get("home");
                                    servicios = (Map)web.get("servicios");
                                    banner = (Map)web.get("banner");
                                    descargas = (Map)web.get("descargas");
                                    contacto = (Map)web.get("contacto");

                                    //variables home     __
                                    String home_titulo = "";
                                    String home_imagen = "";

                                    home_titulo = (String)home.get("titulo");
                                    home_imagen = (String)home.get("imagen");

                                    editor.putString("web_apps_img_seccion_1", home_imagen);
                                    editor.putString("web_apps_titulo_home", home_titulo);

                                    //variables descarga
                                    String descargas_google = "";
                                    String descargas_apple = "";
                                    String descargas_titulo = "";
                                    String descargas_subtitulo = "";

                                    descargas_google = (String)descargas.get("botonplay");
                                    descargas_apple = (String)descargas.get("botonaps");
                                    descargas_titulo = (String)descargas.get("titulo");
                                    descargas_subtitulo = (String)descargas.get("subtitulo");

                                    editor.putString("web_apps_titulo_seccion_2", descargas_titulo);
                                    editor.putString("web_apps_subtitulo_seccion_2", descargas_subtitulo);
                                    editor.putString("web_apps_google_seccion_2", descargas_google);
                                    editor.putString("web_apps_apple_seccion_2", descargas_apple);

                                    // variables servicios
                                    String servicios_titulo1 = "";
                                    String servicios_titulo2 = "";
                                    String servicios_titulo3 = "";
                                    String servicios_titulo4 = "";
                                    String servicios_descripcion1 = "";
                                    String servicios_descripcion2 = "";
                                    String servicios_descripcion3 = "";
                                    String servicios_descripcion4 = "";
                                    String servicios_imagen = "";
                                    String servicios_titulo = "";

                                    servicios_titulo1 = (String)servicios.get("serviciuno");
                                    servicios_titulo2 = (String)servicios.get("serviciodos");
                                    servicios_titulo3 = (String)servicios.get("serviciotres");
                                    servicios_titulo4 = (String)servicios.get("serviciocuatro");
                                    servicios_descripcion1 = (String)servicios.get("subtitulouno");
                                    servicios_descripcion2 = (String)servicios.get("subtitulodos");
                                    servicios_descripcion3 = (String)servicios.get("subtitulotres");
                                    servicios_descripcion4 = (String)servicios.get("subtitulocuatro");
                                    servicios_titulo = (String)servicios.get("titulo");
                                    servicios_imagen = (String)servicios.get("imagen");

                                    editor.putString("web_apps_img_seccion_3", servicios_imagen);
                                    editor.putString("web_apps_titulo_seccion_3", servicios_titulo);
                                    editor.putString("web_apps_titulo_1_seccion_3", servicios_titulo1);
                                    editor.putString("web_apps_subtitulo_1_seccion_3", servicios_descripcion1);
                                    editor.putString("web_apps_titulo_2_seccion_3", servicios_titulo2);
                                    editor.putString("web_apps_subtitulo_2_seccion_3", servicios_descripcion2);
                                    editor.putString("web_apps_titulo_3_seccion_3", servicios_titulo3);
                                    editor.putString("web_apps_subtitulo_3_seccion_3", servicios_descripcion3);
                                    editor.putString("web_apps_titulo_4_seccion_3", servicios_titulo4);
                                    editor.putString("web_apps_subtitulo_4_seccion_3", servicios_descripcion4);

                                    //variable banner
                                    String banner_titulo = "";
                                    banner_titulo = (String)banner.get("titulo");

                                    editor.putString("web_apps_titulo_seccion_4", banner_titulo);

                                    //variable contacto
                                    String contacto_facebook = "";
                                    String contacto_twitter = "";
                                    String contacto_google = "";
                                    String contacto_titulo = "";

                                    contacto_facebook = (String)contacto.get("facebook");
                                    contacto_twitter = (String)contacto.get("twitter");
                                    contacto_google = (String)contacto.get("google");
                                    contacto_titulo = (String)contacto.get("titulo");

                                    editor.putString("web_apps_facebook_seccion_5", contacto_facebook);
                                    editor.putString("web_apps_twitter_seccion_5", contacto_twitter);
                                    editor.putString("web_apps_titulo_seccion_5", contacto_titulo);

                                    editor.commit();
                                }
                            }
                            else {
                                Toast.makeText(getApplicationContext(),"Puedes crear tu página web, elige una de estas categorías",Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });

        speedDialView.setMenuListener(new SimpleMenuListenerAdapter() {
            @Override
            public boolean onMenuItemSelected(MenuItem menuItem) {
                //TODO: Start some activity
                int id = menuItem.getItemId();

                if(id == R.id.action_ver_mi_pag){
                    if(sharedPreferences.getString(user.getUid()+ "-tipo_mi_pag_web", "").equals("1")){
                        if(sharedPreferences.getString("nombrePagWeb","").length() > 0){
                            String url = "http://food.solucionescolabora.com/u/" + sharedPreferences.getString("nombrePagWeb", "");
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    }
                    else if(sharedPreferences.getString(user.getUid()+"-tipo_mi_pag_web", "").equals("2")){
                        if(sharedPreferences.getString("nombrePagWeb","").length() > 0){
                            String url = "http://products.solucionescolabora.com/u/" + sharedPreferences.getString("nombrePagWeb", "");
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    }
                    else if(sharedPreferences.getString(user.getUid()+"-tipo_mi_pag_web", "").equals("3")){
                        if(sharedPreferences.getString("nombrePagWeb","").length() > 0){
                            String url = "http://services.solucionescolabora.com/u/" + sharedPreferences.getString("nombrePagWeb", "");
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    }
                    else if(sharedPreferences.getString(user.getUid()+"-tipo_mi_pag_web", "").equals("4")){
                        if(sharedPreferences.getString("nombrePagWeb","").length() > 0){
                            String url = "http://fashion.solucionescolabora.com/u/" + sharedPreferences.getString("nombrePagWeb", "");
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    }
                    else if(sharedPreferences.getString(user.getUid()+"-tipo_mi_pag_web", "").equals("5")){
                        if(sharedPreferences.getString("nombrePagWeb","").length() > 0){
                            String url = "http://health.solucionescolabora.com/u/" + sharedPreferences.getString("nombrePagWeb", "");
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    }
                    else if(sharedPreferences.getString(user.getUid()+"-tipo_mi_pag_web", "").equals("6")){
                        if(sharedPreferences.getString("nombrePagWeb","").length() > 0){
                            String url = "http://apps.solucionescolabora.com/u/" + sharedPreferences.getString("nombrePagWeb", "");
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        }
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Aún no tienes desarrollada tu página web", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                else if(id == R.id.action_editar_mi_pag){
                    if(sharedPreferences.getString(user.getUid()+"-tipo_mi_pag_web", "").equals("1")){
                        Intent i = new Intent(MenuPagWebActivity.this, WebsComidaSeccion1.class);
                        startActivity(i);
                    }
                    else if(sharedPreferences.getString(user.getUid()+"-tipo_mi_pag_web", "").equals("2")){
                        Intent i = new Intent(MenuPagWebActivity.this, WebsProductosSeccion1.class);
                        startActivity(i);
                    }
                    else if(sharedPreferences.getString(user.getUid()+"-tipo_mi_pag_web", "").equals("3")){
                        Intent i = new Intent(MenuPagWebActivity.this, WebsServiciosSeccion1.class);
                        startActivity(i);
                    }
                    else if(sharedPreferences.getString(user.getUid()+"-tipo_mi_pag_web", "").equals("4")){
                        Intent i = new Intent(MenuPagWebActivity.this, WebsModaSeccion1.class);
                        startActivity(i);
                    }
                    else if(sharedPreferences.getString(user.getUid()+"-tipo_mi_pag_web", "").equals("5")){
                        Intent i = new Intent(MenuPagWebActivity.this, WebsSaludSeccion1.class);
                        startActivity(i);
                    }
                    else if(sharedPreferences.getString(user.getUid()+"-tipo_mi_pag_web", "").equals("6")){
                        Intent i = new Intent(MenuPagWebActivity.this, WebAppsSeccion1Activity.class);
                        startActivity(i);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Aún no tienes desarrollada tu página web", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }
                return false;
            }
        });

        imgComida.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPagWebActivity.this, WebVisualizacionPreviaActivity.class);
                i.putExtra(WebVisualizacionPreviaActivity.PAG_WEB, "comida");
                startActivity(i);
            }
        });

        imgServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPagWebActivity.this, WebVisualizacionPreviaActivity.class);
                i.putExtra(WebVisualizacionPreviaActivity.PAG_WEB, "servicios");
                startActivity(i);
            }
        });

        imgProductos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPagWebActivity.this, WebVisualizacionPreviaActivity.class);
                i.putExtra(WebVisualizacionPreviaActivity.PAG_WEB, "productos");
                startActivity(i);
            }
        });

        imgSalud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPagWebActivity.this, WebVisualizacionPreviaActivity.class);
                i.putExtra(WebVisualizacionPreviaActivity.PAG_WEB, "salud");
                startActivity(i);
            }
        });

        imgAplicaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPagWebActivity.this, WebVisualizacionPreviaActivity.class);
                i.putExtra(WebVisualizacionPreviaActivity.PAG_WEB, "aplicaciones");
                startActivity(i);
            }
        });

        imgModa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuPagWebActivity.this, WebVisualizacionPreviaActivity.class);
                i.putExtra(WebVisualizacionPreviaActivity.PAG_WEB, "moda");
                startActivity(i);
            }
        });
    }

    private void verificarUsername(){

        String id = FirebaseAuth.getInstance().getUid();
        miUsuario = null;
        progressDialog.show();

        db.collection("Usuarios")
                .whereEqualTo("id", id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d("Mooch_info", document.getId() + " => " + document.getData());
                                miUsuario = document.toObject(Usuario.class);
                            }

                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }

                            if(miUsuario != null){

                                if(miUsuario.getTipoPagWeb().equals("servicios")){

                                }

                                else if(miUsuario.getTipoPagWeb().equals("servicios")){

                                }
                                else if(miUsuario.getTipoPagWeb().equals("servicios")){

                                }
                                else if(miUsuario.getTipoPagWeb().equals("servicios")){

                                }
                                else if(miUsuario.getTipoPagWeb().equals("servicios")){

                                }
                                else if(miUsuario.getTipoPagWeb().equals("servicios")){

                                }
                            }
                            else{
                                // Aqui guardo que ya no es necesario seguir buscando
                            }

                        } else {
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                            Toast.makeText(getApplicationContext(), "Ha ocurrido un error, por favor verifique su conexión a internet", Toast.LENGTH_LONG).show();
                            Log.d("Mooch_info", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void getInfoPagWeb(String nombrePagWeb){
        db.collection("webs").document(nombrePagWeb).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        //Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                    } else {
                        //Log.d(TAG, "No such document");

                    }
                } else {
                    //Log.d(TAG, "get failed with ", task.getException());

                }
            }
        });
    }

    public static List<?> convertObjectToList(Object obj) {
        List<?> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((Object[])obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<>((Collection<?>)obj);
        }
        return list;
    }

}
