package com.colabora.soluciones.convocatoriafeyac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

public class CrearEditarTarjetasActivity extends AppCompatActivity {

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private ImageView imageView7;
    private ImageView imageView8;
    private ImageView imageView9;
    private ImageView imageView10;
    private ImageView imageView11;
    private ImageView imageView12;

    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioButton radioButton5;
    private RadioButton radioButton6;
    private RadioButton radioButton7;
    private RadioButton radioButton8;
    private RadioButton radioButton9;
    private RadioButton radioButton10;
    private RadioButton radioButton11;
    private RadioButton radioButton12;

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayout4;
    private LinearLayout linearLayout5;
    private LinearLayout linearLayout6;
    private LinearLayout linearLayout7;
    private LinearLayout linearLayout8;
    private LinearLayout linearLayout9;
    private LinearLayout linearLayout10;
    private LinearLayout linearLayout11;
    private LinearLayout linearLayout12;

    private int seleccion = 0;
    private Button btnSiguiente;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_editar_tarjetas);

        imageView1 = (ImageView)findViewById(R.id.imgTipoTarjeta1);
        imageView2 = (ImageView)findViewById(R.id.imgTipoTarjeta2);
        imageView3 = (ImageView)findViewById(R.id.imgTipoTarjeta3);
        imageView4 = (ImageView)findViewById(R.id.imgTipoTarjeta4);
        imageView5 = (ImageView)findViewById(R.id.imgTipoTarjeta5);
        imageView6 = (ImageView)findViewById(R.id.imgTipoTarjeta6);
        imageView7 = (ImageView)findViewById(R.id.imgTipoTarjeta7);
        imageView8 = (ImageView)findViewById(R.id.imgTipoTarjeta8);
        imageView9 = (ImageView)findViewById(R.id.imgTipoTarjeta9);
        imageView10 = (ImageView)findViewById(R.id.imgTipoTarjeta10);
        imageView11 = (ImageView)findViewById(R.id.imgTipoTarjeta11);
        imageView12 = (ImageView)findViewById(R.id.imgTipoTarjeta12);

        radioButton1 = (RadioButton)findViewById(R.id.radioTipoTarjeta1);
        radioButton2 = (RadioButton)findViewById(R.id.radioTipoTarjeta2);
        radioButton3 = (RadioButton)findViewById(R.id.radioTipoTarjeta3);
        radioButton4 = (RadioButton)findViewById(R.id.radioTipoTarjeta4);
        radioButton5 = (RadioButton)findViewById(R.id.radioTipoTarjeta5);
        radioButton6 = (RadioButton)findViewById(R.id.radioTipoTarjeta6);
        radioButton7 = (RadioButton)findViewById(R.id.radioTipoTarjeta7);
        radioButton8 = (RadioButton)findViewById(R.id.radioTipoTarjeta8);
        radioButton9 = (RadioButton)findViewById(R.id.radioTipoTarjeta9);
        radioButton10 = (RadioButton)findViewById(R.id.radioTipoTarjeta10);
        radioButton11 = (RadioButton)findViewById(R.id.radioTipoTarjeta11);
        radioButton12 = (RadioButton)findViewById(R.id.radioTipoTarjeta12);

        linearLayout1 = (LinearLayout)findViewById(R.id.linearTipoTarjeta1);
        linearLayout2 = (LinearLayout)findViewById(R.id.linearTipoTarjeta2);
        linearLayout3 = (LinearLayout)findViewById(R.id.linearTipoTarjeta3);
        linearLayout4 = (LinearLayout)findViewById(R.id.linearTipoTarjeta4);
        linearLayout5 = (LinearLayout)findViewById(R.id.linearTipoTarjeta5);
        linearLayout6 = (LinearLayout)findViewById(R.id.linearTipoTarjeta6);
        linearLayout7 = (LinearLayout)findViewById(R.id.linearTipoTarjeta7);
        linearLayout8 = (LinearLayout)findViewById(R.id.linearTipoTarjeta8);
        linearLayout9 = (LinearLayout)findViewById(R.id.linearTipoTarjeta9);
        linearLayout10 = (LinearLayout)findViewById(R.id.linearTipoTarjeta10);
        linearLayout11 = (LinearLayout)findViewById(R.id.linearTipoTarjeta11);
        linearLayout12 = (LinearLayout)findViewById(R.id.linearTipoTarjeta12);

        btnSiguiente = (Button)findViewById(R.id.btnTipoTarjetaSiguiente);

        radioButton1.setClickable(false);
        radioButton2.setClickable(false);
        radioButton3.setClickable(false);
        radioButton4.setClickable(false);
        radioButton5.setClickable(false);
        radioButton6.setClickable(false);
        radioButton7.setClickable(false);
        radioButton8.setClickable(false);
        radioButton9.setClickable(false);
        radioButton10.setClickable(false);
        radioButton11.setClickable(false);
        radioButton12.setClickable(false);

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton1.setChecked(true);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                radioButton4.setChecked(false);
                radioButton5.setChecked(false);
                radioButton6.setChecked(false);
                radioButton7.setChecked(false);
                radioButton8.setChecked(false);
                radioButton9.setChecked(false);
                radioButton10.setChecked(false);
                radioButton11.setChecked(false);
                radioButton12.setChecked(false);
                seleccion = 1;
            }
        });

        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton2.setChecked(true);
                radioButton1.setChecked(false);
                radioButton3.setChecked(false);
                radioButton4.setChecked(false);
                radioButton5.setChecked(false);
                radioButton6.setChecked(false);
                radioButton7.setChecked(false);
                radioButton8.setChecked(false);
                radioButton9.setChecked(false);
                radioButton10.setChecked(false);
                radioButton11.setChecked(false);
                radioButton12.setChecked(false);
                seleccion = 2;
            }
        });

        linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton3.setChecked(true);
                radioButton2.setChecked(false);
                radioButton1.setChecked(false);
                radioButton4.setChecked(false);
                radioButton5.setChecked(false);
                radioButton6.setChecked(false);
                radioButton7.setChecked(false);
                radioButton8.setChecked(false);
                radioButton9.setChecked(false);
                radioButton10.setChecked(false);
                radioButton11.setChecked(false);
                radioButton12.setChecked(false);
                seleccion = 3;
            }
        });

        linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton4.setChecked(true);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                radioButton1.setChecked(false);
                radioButton5.setChecked(false);
                radioButton6.setChecked(false);
                radioButton7.setChecked(false);
                radioButton8.setChecked(false);
                radioButton9.setChecked(false);
                radioButton10.setChecked(false);
                radioButton11.setChecked(false);
                radioButton12.setChecked(false);
                seleccion = 4;
            }
        });

        linearLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton5.setChecked(true);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                radioButton4.setChecked(false);
                radioButton1.setChecked(false);
                radioButton6.setChecked(false);
                radioButton7.setChecked(false);
                radioButton8.setChecked(false);
                radioButton9.setChecked(false);
                radioButton10.setChecked(false);
                radioButton11.setChecked(false);
                radioButton12.setChecked(false);
                seleccion = 5;
            }
        });

        linearLayout6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton6.setChecked(true);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                radioButton4.setChecked(false);
                radioButton5.setChecked(false);
                radioButton1.setChecked(false);
                radioButton7.setChecked(false);
                radioButton8.setChecked(false);
                radioButton9.setChecked(false);
                radioButton10.setChecked(false);
                radioButton11.setChecked(false);
                radioButton12.setChecked(false);
                seleccion = 6;
            }
        });

        linearLayout7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton7.setChecked(true);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                radioButton4.setChecked(false);
                radioButton5.setChecked(false);
                radioButton6.setChecked(false);
                radioButton1.setChecked(false);
                radioButton8.setChecked(false);
                radioButton9.setChecked(false);
                radioButton10.setChecked(false);
                radioButton11.setChecked(false);
                radioButton12.setChecked(false);
                seleccion = 7;
            }
        });

        linearLayout8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton8.setChecked(true);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                radioButton4.setChecked(false);
                radioButton5.setChecked(false);
                radioButton6.setChecked(false);
                radioButton7.setChecked(false);
                radioButton1.setChecked(false);
                radioButton9.setChecked(false);
                radioButton10.setChecked(false);
                radioButton11.setChecked(false);
                radioButton12.setChecked(false);
                seleccion = 8;
            }
        });

        linearLayout9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton9.setChecked(true);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                radioButton4.setChecked(false);
                radioButton5.setChecked(false);
                radioButton6.setChecked(false);
                radioButton7.setChecked(false);
                radioButton8.setChecked(false);
                radioButton1.setChecked(false);
                radioButton10.setChecked(false);
                radioButton11.setChecked(false);
                radioButton12.setChecked(false);
                seleccion = 9;
            }
        });

        linearLayout10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton10.setChecked(true);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                radioButton4.setChecked(false);
                radioButton5.setChecked(false);
                radioButton6.setChecked(false);
                radioButton7.setChecked(false);
                radioButton8.setChecked(false);
                radioButton9.setChecked(false);
                radioButton1.setChecked(false);
                radioButton11.setChecked(false);
                radioButton12.setChecked(false);
                seleccion = 10;
            }
        });

        linearLayout11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton11.setChecked(true);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                radioButton4.setChecked(false);
                radioButton5.setChecked(false);
                radioButton6.setChecked(false);
                radioButton7.setChecked(false);
                radioButton8.setChecked(false);
                radioButton9.setChecked(false);
                radioButton10.setChecked(false);
                radioButton1.setChecked(false);
                radioButton12.setChecked(false);
                seleccion = 11;
            }
        });

        linearLayout12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton12.setChecked(true);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                radioButton4.setChecked(false);
                radioButton5.setChecked(false);
                radioButton6.setChecked(false);
                radioButton7.setChecked(false);
                radioButton8.setChecked(false);
                radioButton9.setChecked(false);
                radioButton10.setChecked(false);
                radioButton11.setChecked(false);
                radioButton11.setChecked(false);
                seleccion = 12;
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (seleccion == 0){
                    Toast.makeText(getApplicationContext(), "Debes seleccionar primero un modelo de tarjeta para continuar", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    // Leemos la memoria para ver que tarjetas se han creado
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("tarjetaEstilo", seleccion);
                    editor.commit();
                    Intent i = new Intent(CrearEditarTarjetasActivity.this, VisualizarTarjetaActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
