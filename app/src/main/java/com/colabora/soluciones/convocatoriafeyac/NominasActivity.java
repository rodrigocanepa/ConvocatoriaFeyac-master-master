package com.colabora.soluciones.convocatoriafeyac;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.Modelos.TemplatePDF;

public class NominasActivity extends AppCompatActivity {

    private EditText editSalario;
    private TextInputEditText editAguinaldo;
    private TextInputEditText editPrimaVacacional;
    private TextInputEditText editDiasVacaciones;
    private Button btnCotizar;
    private RadioButton radioMinimo;
    private RadioButton radioBajo;
    private RadioButton radioMedio;

    // GENERACION DE PDF
    private TemplatePDF templatePDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nominas);

        editAguinaldo = (TextInputEditText)findViewById(R.id.editNominaDiasAguinaldo);
        editDiasVacaciones = (TextInputEditText)findViewById(R.id.editNominaDiasVacaciones);
        editPrimaVacacional = (TextInputEditText)findViewById(R.id.editNominaPrimaVacacional);
        editSalario = (EditText)findViewById(R.id.editNominaSalarioDiario);
        btnCotizar = (Button)findViewById(R.id.btnCalcularNomina);
        radioBajo = (RadioButton)findViewById(R.id.radioNominaBajo);
        radioMinimo = (RadioButton)findViewById(R.id.radioNominaMinimo);
        radioMedio = (RadioButton)findViewById(R.id.radioNominaMedio);

        editDiasVacaciones.setText("6");
        editPrimaVacacional.setText("25");
        editAguinaldo.setText("15");

        editPrimaVacacional.setEnabled(false);
        radioMinimo.setChecked(true);

        radioMinimo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    radioBajo.setChecked(false);
                    radioMedio.setChecked(false);
                }

            }
        });

        radioMedio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    radioBajo.setChecked(false);
                    radioMinimo.setChecked(false);
                }

            }
        });


        radioBajo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    radioMinimo.setChecked(false);
                    radioMedio.setChecked(false);
                }

            }
        });


        btnCotizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String diasSalario = editSalario.getText().toString();
                String aguinaldo = editAguinaldo.getText().toString();
                String diasVacaciones = editDiasVacaciones.getText().toString();
                String primaVacacional = editPrimaVacacional.getText().toString();
                double riesgo = 0;

                if(diasSalario.isEmpty()){
                    editSalario.setError("Introduce un valor");
                    return;
                }
                if(aguinaldo.isEmpty()){
                    editAguinaldo.setError("Introduce un valor");
                    return;
                }
                if(diasVacaciones.isEmpty()){
                    editDiasVacaciones.setError("Introduce un valor");
                    return;
                }

                if(radioBajo.isChecked()){
                    riesgo = 0.0113065;
                }
                if(radioMedio.isChecked()){
                    riesgo = 0.025984;
                }
                if(radioMinimo.isChecked()){
                    riesgo = 0.0054355;
                }

                double diasSalario_ = Double.valueOf(diasSalario);
                double aguinaldo_ = Double.valueOf(aguinaldo);
                double diasVacaciones_ = Double.valueOf(diasVacaciones);

                if(diasSalario_ < 88.36 || diasSalario_ > 1200){
                    Toast.makeText(getApplicationContext(), "No puedes ingresar un salario diario menor al salario mínimo el cual es $88.36 o mayor a $1200.00", Toast.LENGTH_LONG).show();
                    return;
                }
                if(diasVacaciones_ < 6 || diasVacaciones_ > 30){
                    Toast.makeText(getApplicationContext(), "No puedes asignar menos de 6 días de vacaciones ni más de 30", Toast.LENGTH_LONG).show();
                    return;
                }
                if(aguinaldo_ < 15 || aguinaldo_ > 60){
                    Toast.makeText(getApplicationContext(), "No puedes asignar menos de 15 días de aguinaldo ni más de 60", Toast.LENGTH_LONG).show();
                    return;
                }

                calcularNomina(riesgo, diasSalario_, aguinaldo_, diasVacaciones_);
            }
        });

    }

    private void calcularNomina(double riesgo, final double saladioNominal, double diasAguinaldo, double diasVacaciones){

        double primaVacacional = 0;
        double factorPrimaVacacional = 0;
        double factorAguinaldo = 0;
        double salarioBaseCotizacion = 0;

        double prestacionesDinero = 0;
        double prestacionesEspecieCuotaFija = 0;
        double prestacionesEspecieCuotaAdicional = 0;
        double prestacionesPensionadosBeneficiarios = 0;
        double invalidez = 0;
        double guarderia = 0;
        double riegoTrabajo = 0;

        double seguroRetiro = 0;
        double cesantia = 0;

        final double infonavit;

        double ISN = 0;

        primaVacacional = diasVacaciones * 0.25;
        factorPrimaVacacional = primaVacacional/365;
        factorAguinaldo = diasAguinaldo/365;
        salarioBaseCotizacion = saladioNominal + (factorPrimaVacacional * saladioNominal) + (factorAguinaldo * saladioNominal);

        final double aguinaldoPrimaVacacional = (salarioBaseCotizacion - saladioNominal) * 30;

        riegoTrabajo = riesgo * salarioBaseCotizacion * 30;
        prestacionesPensionadosBeneficiarios = 0.0105 * salarioBaseCotizacion * 30;
        prestacionesEspecieCuotaFija = 0.204 * 88.36 * 30;
        invalidez = 0.0175 * salarioBaseCotizacion * 30;
        seguroRetiro = 0.02 * salarioBaseCotizacion * 30;
        cesantia = 0.0315 * salarioBaseCotizacion * 30;
        guarderia = 0.01 * salarioBaseCotizacion * 30;
        infonavit = 0.05 * salarioBaseCotizacion * 30;
        prestacionesDinero = 0.007 * salarioBaseCotizacion * 30;

        if(salarioBaseCotizacion > 3 * 88.36){
            prestacionesEspecieCuotaAdicional = (salarioBaseCotizacion - (3*88.36)) * (30 * 0.011);
        }

        ISN = 0.02 * salarioBaseCotizacion * 30;

        double totalIMSS = riegoTrabajo + prestacionesDinero + prestacionesEspecieCuotaAdicional + prestacionesEspecieCuotaFija + prestacionesPensionadosBeneficiarios + invalidez + guarderia + seguroRetiro + cesantia + infonavit;
        final double total = aguinaldoPrimaVacacional + ISN + totalIMSS + (saladioNominal * 30);

        String respuesta = "";
        respuesta += "El salario mensual de su trabajador sería de $" + String.format("%.2f", saladioNominal * 30);
        respuesta += "\nEl salario real invertido por la empresa sería de $" + String.format("%.2f", total);
        respuesta += "\n\nLa prestaciones se dividen en los siguientes conceptos:";
        respuesta += "\nAguinaldo y prima vacacional $" + String.format("%.2f", aguinaldoPrimaVacacional);
        respuesta += "\nImpuesto sobre nómina $" + String.format("%.2f", ISN);
        respuesta += "\nIMSS e INFONAVIT $" + String.format("%.2f", totalIMSS);

        AlertDialog.Builder builder = new AlertDialog.Builder(NominasActivity.this);
        builder.setTitle("Costo del empleado");
        final double finalRiegoTrabajo = riegoTrabajo;
        final double finalPrestacionesPensionadosBeneficiarios = prestacionesPensionadosBeneficiarios;
        final double finalPrestacionesEspecieCuotaFija = prestacionesEspecieCuotaFija;
        final double finalPrestacionesDinero = prestacionesDinero;
        final double finalInvalidez = invalidez;
        final double finalGuarderia = guarderia;
        final double finalSeguroRetiro = seguroRetiro;
        final double finalCesantia = cesantia;
        final double finalISN = ISN;
        final double finalPrestacionesEspecieCuotaAdicional = prestacionesEspecieCuotaAdicional;
        builder.setMessage(respuesta)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton("Ver más detalles", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        templatePDF = new TemplatePDF(getApplicationContext());
                        templatePDF.openDocument("Nomina");
                        templatePDF.addMetaData("Calculo de nómina", "Pyme Assistant", "Soluciones Colabora");
                        //templatePDF.addImage(getApplicationContext());
                        templatePDF.addSectionsCenter("Calculadora de nóminas");
                        templatePDF.addParagraphCenter("De acuerdo al salario mínimo establecido para el 2018 y a las disposiciones legales del IMSS e Infonavit a continuación se desglosan los conceptos en los cuales están distruibuidos sus prestaciones sociales");

                        templatePDF.addSections("Riesgo de trabajo: $" + String.format("%.2f", finalRiegoTrabajo));
                        templatePDF.addSections("Prestaciones en dinero: $" + String.format("%.2f", finalPrestacionesDinero));
                        templatePDF.addSections("Prestaciones en especie cuota fija: $" + String.format("%.2f", finalPrestacionesEspecieCuotaFija));
                        templatePDF.addSections("Prestaciones en especie cuota adicional: $" + String.format("%.2f", finalPrestacionesEspecieCuotaAdicional));
                        templatePDF.addSections("Prestaciones pensionados y beneficiarios: $" + String.format("%.2f", finalPrestacionesPensionadosBeneficiarios));
                        templatePDF.addSections("Invalidez y vida: $" + String.format("%.2f", finalInvalidez));
                        templatePDF.addSections("Guardería y prestaciones sociales: $" + String.format("%.2f", finalGuarderia));
                        templatePDF.addSections("Seguro de retiro: $" + String.format("%.2f", finalSeguroRetiro));
                        templatePDF.addSections("Cesantía: $" + String.format("%.2f", finalCesantia));

                        templatePDF.addLine();
                        templatePDF.addSections("INFONAVIT: $" + String.format("%.2f", infonavit));
                        templatePDF.addSections("Aguinaldo y prima vacacional: $" + String.format("%.2f", aguinaldoPrimaVacacional));
                        templatePDF.addSections("Impuesto sobre la nómina: $" + String.format("%.2f", finalISN));
                        templatePDF.addLine();
                        templatePDF.addSections("Salario con prestaciones: $" + String.format("%.2f", total));
                        double sal = saladioNominal * 30;
                        templatePDF.addSections("Salario sin prestaciones: $" + String.format("%.2f", sal));
                        //templatePDF.addSections("Total descontado: $" + String.format("%.2f", String.format("%.2f", total - (saladioNominal * 30))));

                        templatePDF.closeDocument();

                        templatePDF.viewPDF("Nomina");
                    }
                });
        // Create the AlertDialog object and return it
        builder.create();
        builder.show();

    }
}
