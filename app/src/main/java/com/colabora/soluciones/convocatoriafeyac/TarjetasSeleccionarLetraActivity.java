package com.colabora.soluciones.convocatoriafeyac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class TarjetasSeleccionarLetraActivity extends AppCompatActivity {

    private TextView txtTipo1;
    private TextView txtTipo2;
    private TextView txtTipo3;
    private TextView txtTipo4;
    private TextView txtTipo5;
    private TextView txtTipo6;

    private RadioButton radioButton1;
    private RadioButton radioButton2;
    private RadioButton radioButton3;
    private RadioButton radioButton4;
    private RadioButton radioButton5;
    private RadioButton radioButton6;

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;
    private LinearLayout linearLayout4;
    private LinearLayout linearLayout5;
    private LinearLayout linearLayout6;

    protected Typeface typeface1;
    protected Typeface typeface2;
    protected Typeface typeface3;
    protected Typeface typeface4;
    protected Typeface typeface5;
    protected Typeface typeface6;

    private Button btnSiguiente;
    private int seleccion = 0;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas_seleccionar_letra);

        radioButton1 = (RadioButton)findViewById(R.id.radioTarjetasTipoLetra1);
        radioButton2 = (RadioButton)findViewById(R.id.radioTarjetasTipoLetra2);
        radioButton3 = (RadioButton)findViewById(R.id.radioTarjetasTipoLetra3);
        radioButton4 = (RadioButton)findViewById(R.id.radioTarjetasTipoLetra4);
        radioButton5 = (RadioButton)findViewById(R.id.radioTarjetasTipoLetra5);
        radioButton6 = (RadioButton)findViewById(R.id.radioTarjetasTipoLetra6);

        linearLayout1 = (LinearLayout)findViewById(R.id.linearTipoLetra1);
        linearLayout2 = (LinearLayout)findViewById(R.id.linearTipoLetra2);
        linearLayout3 = (LinearLayout)findViewById(R.id.linearTipoLetra3);
        linearLayout4 = (LinearLayout)findViewById(R.id.linearTipoLetra4);
        linearLayout5 = (LinearLayout)findViewById(R.id.linearTipoLetra5);
        linearLayout6 = (LinearLayout)findViewById(R.id.linearTipoLetra6);

        txtTipo1 = (TextView)findViewById(R.id.txtTarjetasTipoLetra1);
        txtTipo2 = (TextView)findViewById(R.id.txtTarjetasTipoLetra2);
        txtTipo3 = (TextView)findViewById(R.id.txtTarjetasTipoLetra3);
        txtTipo4 = (TextView)findViewById(R.id.txtTarjetasTipoLetra4);
        txtTipo5 = (TextView)findViewById(R.id.txtTarjetasTipoLetra5);
        txtTipo6 = (TextView)findViewById(R.id.txtTarjetasTipoLetra6);

        btnSiguiente = (Button)findViewById(R.id.btnTipoLetraSiguiente);

        typeface1 = Typeface.createFromAsset(getAssets(), "Anton-Regular.ttf");
        typeface2 = Typeface.createFromAsset(getAssets(), "Boogaloo-Regular.ttf");
        typeface3 = Typeface.createFromAsset(getAssets(), "OldStandard-Regular.ttf");
        typeface4 = Typeface.createFromAsset(getAssets(), "PoiretOne-Regular.ttf");
        typeface5 = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
        typeface6 = Typeface.createFromAsset(getAssets(), "Sacramento-Regular.ttf");

        txtTipo1.setTypeface(typeface1);
        txtTipo2.setTypeface(typeface2);
        txtTipo3.setTypeface(typeface3);
        txtTipo4.setTypeface(typeface4);
        txtTipo5.setTypeface(typeface5);
        txtTipo6.setTypeface(typeface6);

        radioButton1.setClickable(false);
        radioButton2.setClickable(false);
        radioButton3.setClickable(false);
        radioButton4.setClickable(false);
        radioButton5.setClickable(false);
        radioButton6.setClickable(false);

        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButton1.setChecked(true);
                radioButton2.setChecked(false);
                radioButton3.setChecked(false);
                radioButton4.setChecked(false);
                radioButton5.setChecked(false);
                radioButton6.setChecked(false);

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

                seleccion = 6;
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(seleccion == 0){
                    Toast.makeText(getApplicationContext(), "Debes seleccionar un tipo de letra para continuar", Toast.LENGTH_LONG).show();
                    return;
                }
                else{
                    // Leemos la memoria para ver que tarjetas se han creado
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("tarjetaLetra", seleccion);
                    editor.commit();

                    Intent i = new Intent(TarjetasSeleccionarLetraActivity.this, CrearEditarTarjetasActivity.class);
                    startActivity(i);
                }
            }
        });
    }
}
