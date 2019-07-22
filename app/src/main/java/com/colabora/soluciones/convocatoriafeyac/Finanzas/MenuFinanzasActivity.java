package com.colabora.soluciones.convocatoriafeyac.Finanzas;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.colabora.soluciones.convocatoriafeyac.R;

public class MenuFinanzasActivity extends AppCompatActivity {

    private ImageView imgServicios;
    private ImageView imgPrecios;
    private ImageView imgCostos;
    private ImageView imgInversion;
    private ImageView imgPersional;
    private ImageView imgGastosFijos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_finanzas);

        imgServicios = (ImageView)findViewById(R.id.imgMainProductos);
        imgPrecios = (ImageView)findViewById(R.id.imgMainPrecios);
        imgCostos = (ImageView)findViewById(R.id.imgMainCostos);
        imgInversion = (ImageView)findViewById(R.id.imgMainInversion);
        imgPersional = (ImageView)findViewById(R.id.imgMainPersonal);
        imgGastosFijos = (ImageView)findViewById(R.id.imgMainGastosFijos);

        imgServicios.setColorFilter(Color.argb(130,20,20,20), PorterDuff.Mode.DARKEN);
        imgPrecios.setColorFilter(Color.argb(130,20,20,20), PorterDuff.Mode.DARKEN);
        imgCostos.setColorFilter(Color.argb(130,20,20,20), PorterDuff.Mode.DARKEN);
        imgInversion.setColorFilter(Color.argb(130,20,20,20), PorterDuff.Mode.DARKEN);
        imgPersional.setColorFilter(Color.argb(130,20,20,20), PorterDuff.Mode.DARKEN);
        imgGastosFijos.setColorFilter(Color.argb(130,20,20,20), PorterDuff.Mode.DARKEN);

        imgServicios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuFinanzasActivity.this, ProductosServiciosActivity.class);
                startActivity(i);
            }
        });

        imgInversion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i = new Intent(MenuFinanzasActivity.this, InversionActivity.class);
               startActivity(i);
            }
        });

    }
}
