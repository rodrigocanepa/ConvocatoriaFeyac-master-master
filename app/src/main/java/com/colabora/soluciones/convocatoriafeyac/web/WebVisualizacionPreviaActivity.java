package com.colabora.soluciones.convocatoriafeyac.web;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.R;
import com.colabora.soluciones.convocatoriafeyac.VisualizarTarjetaActivity;
import com.colabora.soluciones.convocatoriafeyac.web.aplicaciones.WebAppsSeccion1Activity;
import com.colabora.soluciones.convocatoriafeyac.web.comida.WebsComidaSeccion1;
import com.colabora.soluciones.convocatoriafeyac.web.comida.WebsComidaSeccion6;
import com.colabora.soluciones.convocatoriafeyac.web.moda.WebsModaSeccion1;
import com.colabora.soluciones.convocatoriafeyac.web.moda.WebsModaSeccion4;
import com.colabora.soluciones.convocatoriafeyac.web.moda.WebsModaSeccion7;
import com.colabora.soluciones.convocatoriafeyac.web.productos.WebsProductosSeccion1;
import com.colabora.soluciones.convocatoriafeyac.web.productos.WebsProductosSeccion3;
import com.colabora.soluciones.convocatoriafeyac.web.productos.WebsProductosSeccion5;
import com.colabora.soluciones.convocatoriafeyac.web.productos.WebsProductosSeccion6;
import com.colabora.soluciones.convocatoriafeyac.web.salud.WebsSaludSeccion1;
import com.colabora.soluciones.convocatoriafeyac.web.salud.WebsSaludSeccion7;
import com.colabora.soluciones.convocatoriafeyac.web.servicios.WebsServiciosSeccion1;
import com.colabora.soluciones.convocatoriafeyac.web.servicios.WebsServiciosSeccion2;
import com.colabora.soluciones.convocatoriafeyac.web.servicios.WebsServiciosSeccion3;
import com.colabora.soluciones.convocatoriafeyac.web.servicios.WebsServiciosSeccion6;
import com.colabora.soluciones.convocatoriafeyac.web.servicios.WebsServiciosSeccion7;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.List;

public class WebVisualizacionPreviaActivity extends AppCompatActivity {

    private ZoomageView zoomageView;
    private Button btnComenzar;
    private String tipo_pag = "";
    private ImageView imageView;
    private TextInputEditText txtCheck;
    private String nombre_web = "";
    private FirebaseFirestore db;
    private ProgressDialog progressDialog;
    private String nada;
    private String otronada;

    public static final String PAG_WEB = "com.colabora.soluciones.convocatoriafeyac.web_tipo";
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_visualizacion_previa);

        imageView = (ImageView) findViewById(R.id.imgPrevWeb);
        btnComenzar = (Button)findViewById(R.id.btnPreWebComenzar);

        progressDialog = new ProgressDialog(WebVisualizacionPreviaActivity.this);
        sharedPreferences = getSharedPreferences("misDatos", 0);
        progressDialog.setTitle("Valindando");
        progressDialog.setMessage("Espere un momento mientras el sistema valida el nombre de su página web");

        db = FirebaseFirestore.getInstance();
        Intent i = getIntent();
        tipo_pag = i.getStringExtra(PAG_WEB);

        if(tipo_pag.equals("comida")){
            imageView.setImageResource(R.drawable.web_comida);

        }
        else if(tipo_pag.equals("moda")){
            imageView.setImageResource(R.drawable.web_modas);
        }
        else if(tipo_pag.equals("aplicaciones")){
            imageView.setImageResource(R.drawable.web_aplicaciones_moviles);
        }
        else if(tipo_pag.equals("servicios")){
            imageView.setImageResource(R.drawable.web_servicio);
        }
        else if(tipo_pag.equals("productos")){
            imageView.setImageResource(R.drawable.web_producto);

        }
        else if(tipo_pag.equals("salud")){
            imageView.setImageResource(R.drawable.web_salud);
        }

        btnComenzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if (sharedPreferences.getString(user.getUid() + "-tipo_mi_pag_web", "").length() > 0){

                    Toast.makeText(getApplicationContext(),"Ya tienes una página creada",Toast.LENGTH_LONG).show();

                }
                else{
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    if (sharedPreferences.getString("paginaIniciadaNombre", "").equals(tipo_pag)){

                        if(tipo_pag.equals("comida")){
                            Intent i = new Intent(WebVisualizacionPreviaActivity.this, WebsComidaSeccion1.class);
                            startActivity(i);
                        }
                        else if(tipo_pag.equals("moda")){
                            Intent i = new Intent(WebVisualizacionPreviaActivity.this, WebsModaSeccion1.class);
                            startActivity(i);
                        }
                        else if(tipo_pag.equals("aplicaciones")){
                            Intent i = new Intent(WebVisualizacionPreviaActivity.this, WebAppsSeccion1Activity.class);
                            startActivity(i);

                        }
                        else if(tipo_pag.equals("servicios")){
                            Intent i = new Intent(WebVisualizacionPreviaActivity.this, WebsServiciosSeccion1.class);
                            startActivity(i);
                        }
                        else if(tipo_pag.equals("productos")){
                            Intent i = new Intent(WebVisualizacionPreviaActivity.this, WebsProductosSeccion1.class);
                            startActivity(i);
                        }
                        else if(tipo_pag.equals("salud")){
                            Intent i = new Intent(WebVisualizacionPreviaActivity.this, WebsSaludSeccion1.class);
                            startActivity(i);
                        }
                    }
                    else {

                        final AlertDialog.Builder builder = new AlertDialog.Builder(WebVisualizacionPreviaActivity.this);

                        // Get the layout inflater
                        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View formElementsView = inflater.inflate(R.layout.dialog_check_web,
                                null, false);

                        txtCheck = (TextInputEditText) formElementsView.findViewById(R.id.dialog_check_web);

                        builder.setTitle("Mi página web");
                        builder.setMessage("Por favor, ingrese el nombre que llevará su página web sin acentos, el cual estará disponible en el dominio www.pymeassistant.com/web/su_pagina_web");

                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                nombre_web = txtCheck.getText().toString();

                                if(nombre_web.length() > 0){
                                    progressDialog.show();
                                    nombre_web = nombre_web.replaceAll(" ", "_");

                                    DocumentReference docRef = db.collection("webs").document(nombre_web);
                                    docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                            if (task.isSuccessful()) {

                                                if(progressDialog.isShowing()){
                                                    progressDialog.dismiss();
                                                }

                                                DocumentSnapshot document = task.getResult();
                                                if (document.exists()) {
                                                    Toast.makeText(getApplicationContext(), "El nombre que quiere asignarle a su pagina web ya está en uso por otro usuario, intente con otro por favor", Toast.LENGTH_LONG).show();
                                                } else {

                                                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString("nombrePagWeb", nombre_web);
                                                    editor.commit();
                                                    // ******************************************************************************

                                                    Toast.makeText(getApplicationContext(), "La pagina web esta disponible", Toast.LENGTH_LONG).show();

                                                    if(tipo_pag.equals("comida")){
                                                        editor.putString("paginaIniciadaNombre", "comida");
                                                        editor.commit();
                                                        Intent i = new Intent(WebVisualizacionPreviaActivity.this, WebsComidaSeccion1.class);
                                                        startActivity(i);
                                                    }
                                                    else if(tipo_pag.equals("moda")){
                                                        editor.putString("paginaIniciadaNombre", "moda");
                                                        editor.commit();
                                                        Intent i = new Intent(WebVisualizacionPreviaActivity.this, WebsModaSeccion1.class);
                                                        startActivity(i);
                                                    }
                                                    else if(tipo_pag.equals("aplicaciones")){
                                                        editor.putString("paginaIniciadaNombre", "aplicaciones");
                                                        editor.commit();
                                                        Intent i = new Intent(WebVisualizacionPreviaActivity.this, WebAppsSeccion1Activity.class);
                                                        startActivity(i);
                                                    }
                                                    else if(tipo_pag.equals("servicios")){
                                                        editor.putString("paginaIniciadaNombre", "servicios");
                                                        editor.commit();
                                                        Intent i = new Intent(WebVisualizacionPreviaActivity.this, WebsServiciosSeccion1.class);
                                                        startActivity(i);
                                                    }
                                                    else if(tipo_pag.equals("productos")){
                                                        editor.putString("paginaIniciadaNombre", "productos");
                                                        editor.commit();
                                                        Intent i = new Intent(WebVisualizacionPreviaActivity.this, WebsProductosSeccion1.class);
                                                        startActivity(i);
                                                    }
                                                    else if(tipo_pag.equals("salud")){
                                                        editor.putString("paginaIniciadaNombre", "salud");
                                                        editor.commit();
                                                        Intent i = new Intent(WebVisualizacionPreviaActivity.this, WebsSaludSeccion1.class);
                                                        startActivity(i);
                                                    }
                                                }
                                            }
                                            else {
                                                if(progressDialog.isShowing()){
                                                    progressDialog.dismiss();
                                                }
                                                Toast.makeText(getApplicationContext(), "Ha ocurrido un error, por favor, vuelta a intentarlo", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                                }
                            }
                        });

                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        // Inflate and set the layout for the dialog
                        // Pass null as the parent view because its going in the dialog layout
                        builder.setView(formElementsView);
                        // Add action buttons
                        builder.create();
                        builder.show();
                        // builder.show();

                    }

                }

            }
        });

    }
}
