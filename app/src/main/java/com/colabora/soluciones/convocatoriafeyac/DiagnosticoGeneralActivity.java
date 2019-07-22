package com.colabora.soluciones.convocatoriafeyac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DiagnosticoGeneralActivity extends AppCompatActivity {

    private RelativeLayout relativeLayoutAdmon;
    private RelativeLayout relativeLayoutFinanzas;
    private RelativeLayout relativeLayoutMkt;
    private RelativeLayout relativeLayoutProcesos;
    private RelativeLayout relativeLayoutRH;
    private RelativeLayout relativeLayoutFinanciamiento;
    private RelativeLayout relativeLayoutUsoTech;
    private RelativeLayout relativeLayoutAcomContable;
    private RelativeLayout relativeLayoutAcomLegal;

    private TextView txtAdmon;
    private TextView txtFinanzas;
    private TextView txtMkt;
    private TextView txtProcesos;
    private TextView txtRH;
    private TextView txtFinanciamiento;
    private TextView txtUsoTech;
    private TextView txtAcomContable;
    private TextView txtAcomLegal;

    private TextInputEditText editNombre;
    private TextInputEditText editEdad;
    private TextInputEditText editTelefono;
    private TextInputEditText editCorreo;

    private TextInputEditText editHombres;
    private TextInputEditText editMujeres;
    private TextInputEditText editHombresIMSS;
    private TextInputEditText editMujeresIMSS;
    private TextInputEditText editHombresDiscapacidad;
    private TextInputEditText editMujeresDiscapacidad;

    private Button btnFem;
    private Button btnMasc;

    private Button btnEmpresario;
    private Button btnEmpleado;
    private Button btnDesempleado;
    private Button btnEstudiante;
    private Button btnOtro;

    private Button btnReiniciar;
    private Button btnSiguiente;


    private int contador = 0;
    private String genero = "";
    private String ocupacion = "";

    private String preocupacion_uno = "";
    private String preocupacion_dos = "";
    private String preocupacion_tres = "";

    private List<String> descuentos = new ArrayList<>();

    private Spinner spinner;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico_general);

        relativeLayoutAdmon = (RelativeLayout)findViewById(R.id.relDiagGeneralAdmon);
        relativeLayoutFinanzas = (RelativeLayout)findViewById(R.id.relDiagGeneralFinanzas);
        relativeLayoutMkt = (RelativeLayout)findViewById(R.id.relDiagGeneralMkt);
        relativeLayoutProcesos = (RelativeLayout)findViewById(R.id.relDiagGeneralProcesos);
        relativeLayoutRH = (RelativeLayout)findViewById(R.id.relDiagGeneralRH);
        relativeLayoutFinanciamiento = (RelativeLayout)findViewById(R.id.relDiagGeneralFinanciamiento);
        relativeLayoutUsoTech = (RelativeLayout)findViewById(R.id.relDiagGeneralUsoTech);
        relativeLayoutAcomContable = (RelativeLayout)findViewById(R.id.relDiagGeneralAcomContable);
        relativeLayoutAcomLegal = (RelativeLayout)findViewById(R.id.relDiagGeneralAcomLegal);

        txtAdmon = (TextView) findViewById(R.id.txtDiagGeneralAdmon);
        txtFinanzas = (TextView) findViewById(R.id.txtDiagGeneralFinanzas);
        txtMkt = (TextView) findViewById(R.id.txtDiagGeneralMkt);
        txtProcesos = (TextView) findViewById(R.id.txtDiagGeneralProcesos);
        txtRH = (TextView) findViewById(R.id.txtDiagGeneralRH);
        txtFinanciamiento = (TextView) findViewById(R.id.txtDiagGeneralFinanciamiento);
        txtUsoTech = (TextView) findViewById(R.id.txtDiagGeneralUsoTech);
        txtAcomContable = (TextView) findViewById(R.id.txtDiagGeneralAcomContable);
        txtAcomLegal = (TextView) findViewById(R.id.txtDiagGeneralAcomLegal);

        editNombre = (TextInputEditText)findViewById(R.id.txtDiagGeneralNombre);
        editEdad = (TextInputEditText)findViewById(R.id.txtDiagGeneralEdad);
        editTelefono = (TextInputEditText)findViewById(R.id.txtDiagGeneralTelefono);
        editCorreo = (TextInputEditText)findViewById(R.id.txtDiagGeneralCorreo);

        editHombres = (TextInputEditText)findViewById(R.id.txtDiagGeneralIntegrantesHombres);
        editMujeres = (TextInputEditText)findViewById(R.id.txtDiagGeneralIntegrantesMujeres);
        editHombresIMSS = (TextInputEditText)findViewById(R.id.txtDiagGeneralIntegrantesHombresIMSS);
        editMujeresIMSS = (TextInputEditText)findViewById(R.id.txtDiagGeneralIntegrantesMujeresIMSS);
        editHombresDiscapacidad = (TextInputEditText)findViewById(R.id.txtDiagGeneralIntegrantesHombresDiscapacidad);
        editMujeresDiscapacidad = (TextInputEditText)findViewById(R.id.txtDiagGeneralIntegrantesMujeresDiscapacidad);

        btnFem = (Button)findViewById(R.id.btnDiagGeneralFem);
        btnMasc = (Button)findViewById(R.id.btnDiagGeneralMasc);

        btnEmpresario = (Button)findViewById(R.id.btnDiagGeneralEmpresario);
        btnEmpleado = (Button)findViewById(R.id.btnDiagGeneralEmpleado);
        btnDesempleado = (Button)findViewById(R.id.btnDiagGeneralDesempleado);
        btnEstudiante = (Button)findViewById(R.id.btnDiagGeneralEstudiante);
        btnOtro = (Button)findViewById(R.id.btnDiagGeneralOtro);

        btnReiniciar = (Button)findViewById(R.id.btnDiagGeneralReiniciar);
        btnSiguiente = (Button)findViewById(R.id.btnDiagGeneralContinuar);
        spinner = (Spinner)findViewById(R.id.spinnerGradoEstudio);

        descuentos.add("Seleccione una opción");
        descuentos.add("Primaria");
        descuentos.add("Secundaria");
        descuentos.add("Preparatoria");
        descuentos.add("Carrera técnica");
        descuentos.add("Universidad");
        descuentos.add("Posgrado");

        final ArrayAdapter<String> adapterCategoria = new ArrayAdapter<String>(
                DiagnosticoGeneralActivity.this, R.layout.support_simple_spinner_dropdown_item, descuentos);
        spinner.setAdapter(adapterCategoria);

        relativeLayoutAdmon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contador < 3){
                    relativeLayoutAdmon.setBackgroundResource(R.drawable.borde_no_redondeado_blur);
                /*    relativeLayoutFinanzas.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutMkt.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutProcesos.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutRH.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutFinanciamiento.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutUsoTech.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutAcomContable.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutAcomLegal.setBackgroundResource(R.drawable.borde_no_redondeado_gray);*/

                    contador++;
                    txtAdmon.setText(String.valueOf(contador));

                    if(contador == 1){
                        preocupacion_uno = "Administración";
                    }
                    else if(contador == 2){
                        preocupacion_dos = "Administración";
                    }
                    else if(contador == 3){
                        preocupacion_tres = "Administración";
                    }
                }


            }
        });

        relativeLayoutFinanzas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contador < 3){
                 //   relativeLayoutAdmon.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutFinanzas.setBackgroundResource(R.drawable.borde_no_redondeado_blur);
                 /*   relativeLayoutMkt.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutProcesos.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutRH.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutFinanciamiento.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutUsoTech.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutAcomContable.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutAcomLegal.setBackgroundResource(R.drawable.borde_no_redondeado_gray);*/

                    contador++;
                    txtFinanzas.setText(String.valueOf(contador));


                    if(contador == 1){
                        preocupacion_uno = "Finanzas";
                    }
                    else if(contador == 2){
                        preocupacion_dos = "Finanzas";
                    }
                    else if(contador == 3){
                        preocupacion_tres = "Finanzas";
                    }
                }

            }
        });

        relativeLayoutMkt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contador < 3){
                 /*   relativeLayoutAdmon.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutFinanzas.setBackgroundResource(R.drawable.borde_no_redondeado_gray);*/
                    relativeLayoutMkt.setBackgroundResource(R.drawable.borde_no_redondeado_blur);
                /*    relativeLayoutProcesos.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutRH.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutFinanciamiento.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutUsoTech.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutAcomContable.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutAcomLegal.setBackgroundResource(R.drawable.borde_no_redondeado_gray); */

                    contador++;
                    txtMkt.setText(String.valueOf(contador));

                    if(contador == 1){
                        preocupacion_uno = "Mercadotecnia y ventas";
                    }
                    else if(contador == 2){
                        preocupacion_dos = "Mercadotecnia y ventas";
                    }
                    else if(contador == 3){
                        preocupacion_tres = "Mercadotecnia y ventas";
                    }
                }

            }
        });

        relativeLayoutProcesos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contador < 3){
                 /*   relativeLayoutAdmon.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutFinanzas.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutMkt.setBackgroundResource(R.drawable.borde_no_redondeado_gray);*/
                    relativeLayoutProcesos.setBackgroundResource(R.drawable.borde_no_redondeado_blur);
                 /*   relativeLayoutRH.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutFinanciamiento.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutUsoTech.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutAcomContable.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutAcomLegal.setBackgroundResource(R.drawable.borde_no_redondeado_gray); */

                    contador++;
                    txtProcesos.setText(String.valueOf(contador));

                    if(contador == 1){
                        preocupacion_uno = "Procesos";
                    }
                    else if(contador == 2){
                        preocupacion_dos = "Procesos";
                    }
                    else if(contador == 3){
                        preocupacion_tres = "Procesos";
                    }
                }
            }
        });

        relativeLayoutRH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contador < 3){
                /*    relativeLayoutAdmon.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutFinanzas.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutMkt.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutProcesos.setBackgroundResource(R.drawable.borde_no_redondeado_gray);*/
                    relativeLayoutRH.setBackgroundResource(R.drawable.borde_no_redondeado_blur);
                /*    relativeLayoutFinanciamiento.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutUsoTech.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutAcomContable.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutAcomLegal.setBackgroundResource(R.drawable.borde_no_redondeado_gray);*/

                    contador++;
                    txtRH.setText(String.valueOf(contador));

                    if(contador == 1){
                        preocupacion_uno = "Recursos humanos";
                    }
                    else if(contador == 2){
                        preocupacion_dos = "Recursos humanos";
                    }
                    else if(contador == 3){
                        preocupacion_tres = "Recursos humanos";
                    }
                }


            }
        });

        relativeLayoutFinanciamiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contador < 3){
                  /*  relativeLayoutAdmon.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutFinanzas.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutMkt.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutProcesos.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutRH.setBackgroundResource(R.drawable.borde_no_redondeado_gray);*/
                    relativeLayoutFinanciamiento.setBackgroundResource(R.drawable.borde_no_redondeado_blur);
                 //   relativeLayoutUsoTech.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                 //   relativeLayoutAcomContable.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                 //   relativeLayoutAcomLegal.setBackgroundResource(R.drawable.borde_no_redondeado_gray);

                    contador++;
                    txtFinanciamiento.setText(String.valueOf(contador));

                    if(contador == 1){
                        preocupacion_uno = "Financiamiento";
                    }
                    else if(contador == 2){
                        preocupacion_dos = "Financiamiento";
                    }
                    else if(contador == 3){
                        preocupacion_tres = "Financiamiento";
                    }
                }

            }
        });

        relativeLayoutUsoTech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contador < 3){
                  /*  relativeLayoutAdmon.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutFinanzas.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutMkt.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutProcesos.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutRH.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutFinanciamiento.setBackgroundResource(R.drawable.borde_no_redondeado_gray);*/
                    relativeLayoutUsoTech.setBackgroundResource(R.drawable.borde_no_redondeado_blur);
                 //   relativeLayoutAcomContable.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                 //   relativeLayoutAcomLegal.setBackgroundResource(R.drawable.borde_no_redondeado_gray);

                    contador++;
                    txtUsoTech.setText(String.valueOf(contador));

                    if(contador == 1){
                        preocupacion_uno = "Uso de las tecnologías";
                    }
                    else if(contador == 2){
                        preocupacion_dos = "Uso de las tecnologías";
                    }
                    else if(contador == 3){
                        preocupacion_tres = "Uso de las tecnologías";
                    }
                }

            }
        });

        relativeLayoutAcomContable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contador < 3){
                  /*  relativeLayoutAdmon.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutFinanzas.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutMkt.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutProcesos.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutRH.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutFinanciamiento.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutUsoTech.setBackgroundResource(R.drawable.borde_no_redondeado_gray);*/
                    relativeLayoutAcomContable.setBackgroundResource(R.drawable.borde_no_redondeado_blur);
                   // relativeLayoutAcomLegal.setBackgroundResource(R.drawable.borde_no_redondeado_gray);

                    contador++;
                    txtAcomContable.setText(String.valueOf(contador));

                    if(contador == 1){
                        preocupacion_uno = "Acompañamiento contable";
                    }
                    else if(contador == 2){
                        preocupacion_dos = "Acompañamiento contable";
                    }
                    else if(contador == 3){
                        preocupacion_tres = "Acompañamiento contable";
                    }
                }

            }
        });

        relativeLayoutAcomLegal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contador < 3){
                    /*relativeLayoutAdmon.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutFinanzas.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutMkt.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutProcesos.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutRH.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutFinanciamiento.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutUsoTech.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                    relativeLayoutAcomContable.setBackgroundResource(R.drawable.borde_no_redondeado_gray);*/
                    relativeLayoutAcomLegal.setBackgroundResource(R.drawable.borde_no_redondeado_blur);

                    contador++;
                    txtAcomLegal.setText(String.valueOf(contador));

                    if(contador == 1){
                        preocupacion_uno = "Acompañamiento legal";
                    }
                    else if(contador == 2){
                        preocupacion_dos = "Acompañamiento legal";
                    }
                    else if(contador == 3){
                        preocupacion_tres = "Acompañamiento legal";
                    }
                }
            }
        });

        btnReiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayoutAdmon.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                relativeLayoutFinanzas.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                relativeLayoutMkt.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                relativeLayoutProcesos.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                relativeLayoutRH.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                relativeLayoutFinanciamiento.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                relativeLayoutUsoTech.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                relativeLayoutAcomContable.setBackgroundResource(R.drawable.borde_no_redondeado_gray);
                relativeLayoutAcomLegal.setBackgroundResource(R.drawable.borde_no_redondeado_gray);

                txtAdmon.setText("");
                txtFinanzas.setText("");
                txtMkt.setText("");
                txtProcesos.setText("");
                txtRH.setText("");
                txtFinanciamiento.setText("");
                txtUsoTech.setText("");
                txtAcomContable.setText("");
                txtAcomLegal.setText("");

                contador = 0;

            }
        });

        btnFem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFem.setBackgroundColor(Color.GREEN);
                btnMasc.setBackgroundColor(Color.LTGRAY);
                genero = "Femenino";
            }
        });

        btnMasc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnFem.setBackgroundColor(Color.LTGRAY);
                btnMasc.setBackgroundColor(Color.GREEN);
                genero = "Masculino";
            }
        });

        btnEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDesempleado.setBackgroundColor(Color.LTGRAY);
                btnEmpleado.setBackgroundColor(Color.LTGRAY);
                btnEmpresario.setBackgroundColor(Color.LTGRAY);
                btnEstudiante.setBackgroundColor(Color.GREEN);
                btnOtro.setBackgroundColor(Color.LTGRAY);

                ocupacion = "Estudiante";
            }
        });

        btnDesempleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDesempleado.setBackgroundColor(Color.GREEN);
                btnEmpleado.setBackgroundColor(Color.LTGRAY);
                btnEmpresario.setBackgroundColor(Color.LTGRAY);
                btnEstudiante.setBackgroundColor(Color.LTGRAY);
                btnOtro.setBackgroundColor(Color.LTGRAY);

                ocupacion = "Desempleado";
            }
        });

        btnEmpresario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDesempleado.setBackgroundColor(Color.LTGRAY);
                btnEmpleado.setBackgroundColor(Color.LTGRAY);
                btnEmpresario.setBackgroundColor(Color.GREEN);
                btnEstudiante.setBackgroundColor(Color.LTGRAY);
                btnOtro.setBackgroundColor(Color.LTGRAY);

                ocupacion = "Empresario";
            }
        });

        btnEmpleado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDesempleado.setBackgroundColor(Color.LTGRAY);
                btnEmpleado.setBackgroundColor(Color.GREEN);
                btnEmpresario.setBackgroundColor(Color.LTGRAY);
                btnEstudiante.setBackgroundColor(Color.LTGRAY);
                btnOtro.setBackgroundColor(Color.LTGRAY);

                ocupacion = "Empleado";
            }
        });

        btnOtro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnDesempleado.setBackgroundColor(Color.LTGRAY);
                btnEmpleado.setBackgroundColor(Color.LTGRAY);
                btnEmpresario.setBackgroundColor(Color.LTGRAY);
                btnEstudiante.setBackgroundColor(Color.LTGRAY);
                btnOtro.setBackgroundColor(Color.GREEN);

                ocupacion = "Otro";
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = editNombre.getText().toString();
                String edad = editEdad.getText().toString();
                String correo = editCorreo.getText().toString();
                String telefono = editTelefono.getText().toString();
                String hombres = editHombres.getText().toString();
                String mujeres = editMujeres.getText().toString();
                String hombresIMSS = editHombresIMSS.getText().toString();
                String mujeresIMSS = editMujeresIMSS.getText().toString();
                String hombresDiscapacidad = editHombresDiscapacidad.getText().toString();
                String mujeresDiscapacidad = editMujeresDiscapacidad.getText().toString();

                if(nombre.length() < 1){
                    editNombre.setError("Ingrese un valor");
                    Toast.makeText(getApplicationContext(), "Debe llenar los campos requeridos para continuar", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(edad.length() < 1){
                    editEdad.setError("Ingrese un valor");
                    Toast.makeText(getApplicationContext(), "Debe llenar los campos requeridos para continuar", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(correo.length() < 1){
                    editCorreo.setError("Ingrese un valor");
                    Toast.makeText(getApplicationContext(), "Debe llenar los campos requeridos para continuar", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(telefono.length() < 1){
                    editTelefono.setError("Ingrese un valor");
                    Toast.makeText(getApplicationContext(), "Debe llenar los campos requeridos para continuar", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(genero.length() < 1){
                    Toast.makeText(getApplicationContext(), "Debe seleccionar su genero para continuar", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(ocupacion.length() < 1){
                    Toast.makeText(getApplicationContext(), "Debe seleccionar su ocupación para continuar", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(spinner.getSelectedItem().toString().equals("Seleccione una opción")){
                    Toast.makeText(getApplicationContext(), "Debe seleccionar su grado académico", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(contador < 3){
                    Toast.makeText(getApplicationContext(), "Debe escoger el orden de sus preocupaciones para continuar", Toast.LENGTH_LONG).show();
                    return;
                }
                // Leemos la memoria para ver que tarjetas se han creado
                sharedPreferences = getSharedPreferences("misDatos", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("general_nombre", nombre);
                editor.putString("general_edad", edad);
                editor.putString("general_correo", correo);
                editor.putString("general_telefono", telefono);
                editor.putString("general_ocupacion", ocupacion);
                editor.putString("general_genero", genero);
                editor.putString("general_grado_académico", spinner.getSelectedItem().toString());
                editor.putString("general_preocupacion_uno", preocupacion_uno);
                editor.putString("general_preocupacion_dos", preocupacion_dos);
                editor.putString("general_preocupacion_tres", preocupacion_tres);
                editor.putString("general_mujeres", mujeres);
                editor.putString("general_hombres", hombres);
                editor.putString("general_mujeresIMSS", mujeresIMSS);
                editor.putString("general_hombresIMSS", hombresIMSS);
                editor.putString("general_mujeresDisc", mujeresDiscapacidad);
                editor.putString("general_hombresDisc", hombresDiscapacidad);
                editor.commit();
                // *****************************************************

                Intent i = new Intent(DiagnosticoGeneralActivity.this, DatosEmpresaDiagnosticoActivity.class);
                startActivity(i);
                finish();
            }
        });

    }
}
