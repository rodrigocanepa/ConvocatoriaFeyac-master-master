package com.colabora.soluciones.convocatoriafeyac.PlanDeNegocios;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.Modelos.Cliente;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Plan;
import com.colabora.soluciones.convocatoriafeyac.Modelos.TemplatePDF;
import com.colabora.soluciones.convocatoriafeyac.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AdmonPlanActivity extends AppCompatActivity {

    private EditText plan1;
    private EditText plan2_1;
    private EditText plan2_Fortalezas_1;
    private EditText plan2_Fortalezas_2;
    private EditText plan2_Fortalezas_3;
    private EditText plan2_Fortalezas_4;
    private EditText plan2_Debilidades_1;
    private EditText plan2_Debilidades_2;
    private EditText plan2_Debilidades_3;
    private EditText plan2_Debilidades_4;
    private EditText plan2_Oportunidades_1;
    private EditText plan2_Oportunidades_2;
    private EditText plan2_Oportunidades_3;
    private EditText plan2_Oportunidades_4;
    private EditText plan2_Amenazas_1;
    private EditText plan2_Amenazas_2;
    private EditText plan2_Amenazas_3;
    private EditText plan2_Amenazas_4;
    private EditText plan2_3;
    private EditText plan2_4;
    private EditText plan2_5;
    private EditText plan3_1;
    private EditText plan3_2;
    private EditText plan3_3;
    private EditText plan3_4;
    private EditText plan4_1;
    private EditText plan4_2;
    private EditText plan4_3;
    private EditText plan4_4;
    private EditText plan4_5;
    private EditText plan4_6;
    private EditText plan4_7;
    private EditText plan4_8;
    private EditText plan5_0_tipo;
    private EditText plan5_1_tipo;
    private EditText plan5_2_tipo;
    private EditText plan5_3_tipo;
    private EditText plan5_0_canal;
    private EditText plan5_1_canal;
    private EditText plan5_2_canal;
    private EditText plan5_3_canal;
    private EditText plan5_0_calidad;
    private EditText plan5_1_calidad;
    private EditText plan5_2_calidad;
    private EditText plan5_3_calidad;
    private EditText plan5_0_precio;
    private EditText plan5_1_precio;
    private EditText plan5_2_precio;
    private EditText plan5_3_precio;
    private EditText plan5_0_tiempo;
    private EditText plan5_1_tiempo;
    private EditText plan5_2_tiempo;
    private EditText plan5_3_tiempo;
    private EditText plan6_1;
    private EditText plan6_2;
    private EditText plan6_3;
    private EditText plan6_4;
    private EditText plan7_1_1;
    private EditText plan7_1_2;
    private EditText plan7_2_1;
    private EditText plan7_2_2;
    private EditText plan7_2_3;
    private EditText plan7_3;
    private EditText plan7_4;
    private EditText plan7_5;
    private EditText plan8_1;
    private EditText plan8_2_1;
    private EditText plan8_2_2;
    private EditText plan8_2_3;
    private EditText plan8_2_4;
    private EditText plan8_2_5;
    private EditText plan9_aliados_1;
    private EditText plan9_aliados_2;
    private EditText plan9_aliados_3;
    private EditText plan9_aliados_4;
    private EditText plan9_aliados_5;
    private EditText plan9_actividades_1;
    private EditText plan9_actividades_2;
    private EditText plan9_actividades_3;
    private EditText plan9_recursos_1;
    private EditText plan9_recursos_2;
    private EditText plan9_recursos_3;
    private EditText plan9_propuesta_1;
    private EditText plan9_propuesta_2;
    private EditText plan9_propuesta_3;
    private EditText plan9_relaciones_1;
    private EditText plan9_relaciones_2;
    private EditText plan9_relaciones_3;
    private EditText plan9_canales_1;
    private EditText plan9_canales_2;
    private EditText plan9_canales_3;
    private EditText plan9_segmento_1;
    private EditText plan9_segmento_2;
    private EditText plan9_segmento_3;
    private EditText plan9_segmento_4;
    private EditText plan9_segmento_5;
    private EditText plan9_estructura_1;
    private EditText plan9_estructura_2;
    private EditText plan9_estructura_3;
    private EditText plan9_estructura_4;
    private EditText plan9_estructura_5;
    private EditText plan9_fuente_1;
    private EditText plan9_fuente_2;
    private EditText plan9_fuente_3;
    private EditText plan9_fuente_4;
    private EditText plan9_fuente_5;
    private TextView planTitulo;
    private SharedPreferences sharedPreferences;

    private FloatingActionButton floatingActionButton;
    private TemplatePDF templatePDF;

    private Map<String, Object> general;
    private Map<String, Object> empresa;
    private Map<String, Object> identidad;
    private Map<String, Object> mercado;
    private Map<String, Object> competencia;
    private Map<String, Object> oferta;
    private Map<String, Object> ventas;
    private Map<String, Object> publicidad;
    private Map<String, Object> canvas;
    private Map<String, Object> aliados;
    private Map<String, Object> actividades;
    private Map<String, Object> recursos;
    private Map<String, Object> propuesta;
    private Map<String, Object> relaciones;
    private Map<String, Object> canales;
    private Map<String, Object> segmento;
    private Map<String, Object> estructura;
    private Map<String, Object> fuente;
    private Map<String, Object> plan;

    private FirebaseFirestore db;
    private String nombre;
    private EditText editBuscar;
    private Button btnBuscar;
    private Button btnPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admon_plan);

        plan1 = (EditText)findViewById(R.id.plan1);
        plan2_1 = (EditText)findViewById(R.id.plan2_1);
        plan2_Fortalezas_1 = (EditText)findViewById(R.id.plan2_Fotalezas_1);
        plan2_Fortalezas_2 = (EditText)findViewById(R.id.plan2_Fotalezas_2);
        plan2_Fortalezas_3 = (EditText)findViewById(R.id.plan2_Fotalezas_3);
        plan2_Fortalezas_4 = (EditText)findViewById(R.id.plan2_Fotalezas_4);
        plan2_Debilidades_1 = (EditText)findViewById(R.id.plan2_Debilidades_1);
        plan2_Debilidades_2 = (EditText)findViewById(R.id.plan2_Debilidades_2);
        plan2_Debilidades_3 = (EditText)findViewById(R.id.plan2_Debilidades_3);
        plan2_Debilidades_4 = (EditText)findViewById(R.id.plan2_Debilidades_4);
        plan2_Oportunidades_1 = (EditText)findViewById(R.id.plan2_Oportunidades_1);
        plan2_Oportunidades_2 = (EditText)findViewById(R.id.plan2_Oportunidades_2);
        plan2_Oportunidades_3 = (EditText)findViewById(R.id.plan2_Oportunidades_3);
        plan2_Oportunidades_4 = (EditText)findViewById(R.id.plan2_Oportunidades_4);
        plan2_Amenazas_1 = (EditText)findViewById(R.id.plan2_Amenazas_1);
        plan2_Amenazas_2 = (EditText)findViewById(R.id.plan2_Amenazas_2);
        plan2_Amenazas_3 = (EditText)findViewById(R.id.plan2_Amenazas_3);
        plan2_Amenazas_4 = (EditText)findViewById(R.id.plan2_Amenazas_4);
        plan2_3 = (EditText)findViewById(R.id.plan2_3);
        plan2_4 = (EditText)findViewById(R.id.plan2_4);
        plan2_5 = (EditText)findViewById(R.id.plan2_5);
        plan3_1 = (EditText)findViewById(R.id.plan3_1);
        plan3_2 = (EditText)findViewById(R.id.plan3_2);
        plan3_3 = (EditText)findViewById(R.id.plan3_3);
        plan3_4 = (EditText)findViewById(R.id.plan3_4);
        plan4_1 = (EditText)findViewById(R.id.plan4_1);
        plan4_2 = (EditText)findViewById(R.id.plan4_2);
        plan4_3 = (EditText)findViewById(R.id.plan4_3);
        plan4_4 = (EditText)findViewById(R.id.plan4_4);
        plan4_5 = (EditText)findViewById(R.id.plan4_5);
        plan4_6 = (EditText)findViewById(R.id.plan4_6);
        plan4_7 = (EditText)findViewById(R.id.plan4_7);
        plan4_8 = (EditText)findViewById(R.id.plan4_8);
        plan5_0_tipo = (EditText)findViewById(R.id.plan5_0_tipo);
        plan5_1_tipo = (EditText)findViewById(R.id.plan5_1_tipo);
        plan5_2_tipo = (EditText)findViewById(R.id.plan5_2_tipo);
        plan5_3_tipo = (EditText)findViewById(R.id.plan5_3_tipo);
        plan5_0_canal = (EditText)findViewById(R.id.plan5_0_canal);
        plan5_1_canal = (EditText)findViewById(R.id.plan5_1_canal);
        plan5_2_canal = (EditText)findViewById(R.id.plan5_2_canal);
        plan5_3_canal = (EditText)findViewById(R.id.plan5_3_canal);
        plan5_0_calidad = (EditText)findViewById(R.id.plan5_0_calidad);
        plan5_1_calidad = (EditText)findViewById(R.id.plan5_1_calidad);
        plan5_2_calidad = (EditText)findViewById(R.id.plan5_2_calidad);
        plan5_3_calidad = (EditText)findViewById(R.id.plan5_3_calidad);
        plan5_0_precio = (EditText)findViewById(R.id.plan5_0_precio);
        plan5_1_precio = (EditText)findViewById(R.id.plan5_1_precio);
        plan5_2_precio = (EditText)findViewById(R.id.plan5_2_precio);
        plan5_3_precio = (EditText)findViewById(R.id.plan5_3_precio);
        plan5_0_tiempo = (EditText)findViewById(R.id.plan5_0_tiempo);
        plan5_1_tiempo = (EditText)findViewById(R.id.plan5_1_tiempo);
        plan5_2_tiempo = (EditText)findViewById(R.id.plan5_2_tiempo);
        plan5_3_tiempo = (EditText)findViewById(R.id.plan5_3_tiempo);
        plan6_1 = (EditText)findViewById(R.id.plan6_1);
        plan6_2 = (EditText)findViewById(R.id.plan6_2);
        plan6_3 = (EditText)findViewById(R.id.plan6_3);
        plan6_4 = (EditText)findViewById(R.id.plan6_4);
        plan7_1_1 = (EditText)findViewById(R.id.plan7_1_1);
        plan7_1_2 = (EditText)findViewById(R.id.plan7_1_2);
        plan7_2_1 = (EditText)findViewById(R.id.plan7_2_1);
        plan7_2_2 = (EditText)findViewById(R.id.plan7_2_2);
        plan7_2_3 = (EditText)findViewById(R.id.plan7_2_3);
        plan7_3 = (EditText)findViewById(R.id.plan7_3);
        plan7_4 = (EditText)findViewById(R.id.plan7_4);
        plan7_5 = (EditText)findViewById(R.id.plan7_5);
        plan8_1 = (EditText)findViewById(R.id.plan8_1);
        plan8_2_1 = (EditText)findViewById(R.id.plan8_2_1);
        plan8_2_2 = (EditText)findViewById(R.id.plan8_2_2);
        plan8_2_3 = (EditText)findViewById(R.id.plan8_2_3);
        plan8_2_4 = (EditText)findViewById(R.id.plan8_2_4);
        plan8_2_5 = (EditText)findViewById(R.id.plan8_2_5);
        plan9_aliados_1 = (EditText)findViewById(R.id.plan9_alidados_1);
        plan9_aliados_2 = (EditText)findViewById(R.id.plan9_alidados_2);
        plan9_aliados_3 = (EditText)findViewById(R.id.plan9_alidados_3);
        plan9_aliados_4 = (EditText)findViewById(R.id.plan9_alidados_4);
        plan9_aliados_5 = (EditText)findViewById(R.id.plan9_alidados_5);
        plan9_actividades_1 = (EditText)findViewById(R.id.plan9_actividades_1);
        plan9_actividades_2 = (EditText)findViewById(R.id.plan9_actividades_2);
        plan9_actividades_3 = (EditText)findViewById(R.id.plan9_actividades_3);
        plan9_recursos_1 = (EditText)findViewById(R.id.plan9_recursos_1);
        plan9_recursos_2 = (EditText)findViewById(R.id.plan9_recursos_2);
        plan9_recursos_3 = (EditText)findViewById(R.id.plan9_recursos_3);
        plan9_propuesta_1 = (EditText)findViewById(R.id.plan9_propuesta_1);
        plan9_propuesta_2 = (EditText)findViewById(R.id.plan9_propuesta_2);
        plan9_propuesta_3 = (EditText)findViewById(R.id.plan9_propuesta_3);
        plan9_relaciones_1 = (EditText)findViewById(R.id.plan9_relaciones_1);
        plan9_relaciones_2 = (EditText)findViewById(R.id.plan9_relaciones_2);
        plan9_relaciones_3 = (EditText)findViewById(R.id.plan9_relaciones_3);
        plan9_canales_1 = (EditText)findViewById(R.id.plan9_canales_1);
        plan9_canales_2 = (EditText)findViewById(R.id.plan9_canales_2);
        plan9_canales_3 = (EditText)findViewById(R.id.plan9_canales_3);
        plan9_segmento_1 = (EditText)findViewById(R.id.plan9_segmento_1);
        plan9_segmento_2 = (EditText)findViewById(R.id.plan9_segmento_2);
        plan9_segmento_3 = (EditText)findViewById(R.id.plan9_segmento_3);
        plan9_segmento_4 = (EditText)findViewById(R.id.plan9_segmento_4);
        plan9_segmento_5 = (EditText)findViewById(R.id.plan9_segmento_5);
        plan9_estructura_1 = (EditText)findViewById(R.id.plan9_estructura_1);
        plan9_estructura_2 = (EditText)findViewById(R.id.plan9_estructura_2);
        plan9_estructura_3 = (EditText)findViewById(R.id.plan9_estructura_3);
        plan9_estructura_4 = (EditText)findViewById(R.id.plan9_estructura_4);
        plan9_estructura_5 = (EditText)findViewById(R.id.plan9_estructura_5);
        plan9_fuente_1 = (EditText)findViewById(R.id.plan9_fuente_1);
        plan9_fuente_2 = (EditText)findViewById(R.id.plan9_fuente_2);
        plan9_fuente_3 = (EditText)findViewById(R.id.plan9_fuente_3);
        plan9_fuente_4 = (EditText)findViewById(R.id.plan9_fuente_4);
        plan9_fuente_5 = (EditText)findViewById(R.id.plan9_fuente_5);
        planTitulo = (TextView)findViewById(R.id.planTitutlo);
        btnBuscar = (Button)findViewById(R.id.admonBtnBuscar);
        editBuscar = (EditText)findViewById(R.id.editAdmonBuscar);
        btnPDF = (Button)findViewById(R.id.admonGenerarPDF);

        db = FirebaseFirestore.getInstance();
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fabSavePlan);

        //leerDatos();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editBuscar.getText().toString().length() > 0){
                    leerDatos(editBuscar.getText().toString());
                }
                else{
                    Toast.makeText(getApplicationContext(), "Debes introducir un nombre para buscar", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                templatePDF = new TemplatePDF(getApplicationContext());
                templatePDF.openDocument("Plan");
                templatePDF.addMetaData("Plan de negocios", "Pyme Assistant", "Soluciones Colabora");
                //templatePDF.addImage(getApplicationContext());
                templatePDF.addSectionsCenter("Plan de negocios");
                templatePDF.addParagraphCenter(editBuscar.getText().toString());

                templatePDF.addSections("1. Descripción y justificación de la empresa");
                templatePDF.addParagraph(plan1.getText().toString());
                templatePDF.addSections("2.1 Empresa: Descripción de la necesidad o problemática a atender");
                templatePDF.addParagraph(plan2_1.getText().toString());
                templatePDF.addSections("2.2 Empresa: Análisis de las fuerzas y debilidades así como amenazas y oportunidades");
                templatePDF.addLine();
                templatePDF.addParagraph("Fortaleza 1: " + plan2_Fortalezas_1.getText().toString());
                templatePDF.addParagraph("Fortaleza 2: " + plan2_Fortalezas_2.getText().toString());
                templatePDF.addParagraph("Fortaleza 3: " + plan2_Fortalezas_3.getText().toString());
                templatePDF.addParagraph("Fortaleza 4: " + plan2_Fortalezas_4.getText().toString());
                templatePDF.addLine();
                templatePDF.addParagraph("Debilidades 1: " + plan2_Debilidades_1.getText().toString());
                templatePDF.addParagraph("Debilidades 2: " + plan2_Debilidades_2.getText().toString());
                templatePDF.addParagraph("Debilidades 3: " + plan2_Debilidades_3.getText().toString());
                templatePDF.addParagraph("Debilidades 4: " + plan2_Debilidades_4.getText().toString());
                templatePDF.addLine();
                templatePDF.addParagraph("Oportunidades 1: " + plan2_Oportunidades_1.getText().toString());
                templatePDF.addParagraph("Oportunidades 2: " + plan2_Oportunidades_2.getText().toString());
                templatePDF.addParagraph("Oportunidades 3: " + plan2_Oportunidades_3.getText().toString());
                templatePDF.addParagraph("Oportunidades 4: " + plan2_Oportunidades_4.getText().toString());
                templatePDF.addLine();
                templatePDF.addParagraph("Amenzas 1: " + plan2_Amenazas_1.getText().toString());
                templatePDF.addParagraph("Amenzas 2: " + plan2_Amenazas_2.getText().toString());
                templatePDF.addParagraph("Amenzas 3: " + plan2_Amenazas_3.getText().toString());
                templatePDF.addParagraph("Amenzas 4: " + plan2_Amenazas_4.getText().toString());
                templatePDF.addLine();
                templatePDF.addSections("2.3 Empresa: Misión");
                templatePDF.addParagraph(plan2_3.getText().toString());
                templatePDF.addSections("2.4 Empresa: Visión");
                templatePDF.addParagraph(plan2_3.getText().toString());
                templatePDF.addSections("2.4 Empresa: Misión");
                templatePDF.addParagraph(plan2_3.getText().toString());
                templatePDF.addSections("2.5 Empresa: Valores. Escriba sus valores separándolos por una coa");
                templatePDF.addParagraph(plan2_5.getText().toString());
                templatePDF.addSections("3.1 Identidad corporativa: Nombre o marca comercial");
                templatePDF.addParagraph(plan3_1.getText().toString());
                templatePDF.addSections("3.2 Identidad corporativa: Estatus actual de la marca sobre IMPI");
                templatePDF.addParagraph(plan3_2.getText().toString());
                templatePDF.addSections("3.3 Identidad corporativa: Escriba sus dos princiaples colores de identidad del cliente separados por una coma");
                templatePDF.addParagraph(plan3_3.getText().toString());
                templatePDF.addSections("3.4 Identidad corporativa: Escriba SOLO UNA palabra clave de la identidad de su marca");
                templatePDF.addParagraph(plan3_4.getText().toString());
                templatePDF.addSections("4.1 Mercado: Edad de sus clientes");
                templatePDF.addParagraph(plan4_1.getText().toString());
                templatePDF.addSections("4.2 Mercado: Género de sus clientes");
                templatePDF.addParagraph(plan4_2.getText().toString());
                templatePDF.addSections("4.3 Mercado: Nivel socioeconómico de sus clientes");
                templatePDF.addParagraph(plan4_3.getText().toString());
                templatePDF.addSections("4.4 Mercado: Estado civil de sus clientes");
                templatePDF.addParagraph(plan4_4.getText().toString());
                templatePDF.addSections("4.5 Mercado: Escriba sus gustos separados por una coma");
                templatePDF.addParagraph(plan4_5.getText().toString());
                templatePDF.addSections("4.6 Mercado: Ubicación geográfica de tus clientes personales");
                templatePDF.addParagraph(plan4_6.getText().toString());
                templatePDF.addSections("4.7 Mercado: Porcentaje de la población objetivo de su segmento de mercado");
                templatePDF.addParagraph(plan4_7.getText().toString());
                templatePDF.addSections("4.8 Mercado: Porcentaje de la tendencia de su segmento de mercado");
                templatePDF.addParagraph(plan4_8.getText().toString());
                templatePDF.addSections("5 Competencia");
                templatePDF.addLine();
                templatePDF.addParagraph(plan5_0_tipo.getText().toString());
                templatePDF.addParagraph("Canal: " + plan5_0_canal.getText().toString());
                templatePDF.addParagraph("Calidad %: " + plan5_0_calidad.getText().toString());
                templatePDF.addParagraph("Precio estándar: " +plan5_0_precio.getText().toString());
                templatePDF.addParagraph("Servicio: " + plan5_0_tiempo.getText().toString());
                templatePDF.addLine();
                templatePDF.addParagraph(plan5_1_tipo.getText().toString());
                templatePDF.addParagraph("Canal: " + plan5_1_canal.getText().toString());
                templatePDF.addParagraph("Calidad %: " + plan5_1_calidad.getText().toString());
                templatePDF.addParagraph("Precio estándar: " +plan5_1_precio.getText().toString());
                templatePDF.addParagraph("Servicio: " + plan5_1_tiempo.getText().toString());
                templatePDF.addLine();
                templatePDF.addParagraph(plan5_2_tipo.getText().toString());
                templatePDF.addParagraph("Canal: " + plan5_2_canal.getText().toString());
                templatePDF.addParagraph("Calidad %: " + plan5_2_calidad.getText().toString());
                templatePDF.addParagraph("Precio estándar: " +plan5_2_precio.getText().toString());
                templatePDF.addParagraph("Servicio: " + plan5_2_tiempo.getText().toString());
                templatePDF.addLine();
                templatePDF.addParagraph(plan5_3_tipo.getText().toString());
                templatePDF.addParagraph("Canal: " + plan5_3_canal.getText().toString());
                templatePDF.addParagraph("Calidad %: " + plan5_3_calidad.getText().toString());
                templatePDF.addParagraph("Precio estándar: " +plan5_3_precio.getText().toString());
                templatePDF.addParagraph("Servicio: " + plan5_3_tiempo.getText().toString());
                templatePDF.addLine();
                templatePDF.addSections("6.1 Oferta y demanda: Capacidad de oferta del valor global de tu giro comercial");
                templatePDF.addParagraph(plan6_1.getText().toString());
                templatePDF.addSections("6.2 Oferta y demanda: Capacidad de oferta del valor local de tu giro comercial");
                templatePDF.addParagraph(plan6_2.getText().toString());
                templatePDF.addSections("6.3 Oferta y demanda: Capacidad de demanda del valor global de tu giro comercial");
                templatePDF.addParagraph(plan6_3.getText().toString());
                templatePDF.addSections("6.4 Oferta y demanda: Capacidad de demanda del valor local de tu giro comercial");
                templatePDF.addParagraph(plan6_4.getText().toString());
                templatePDF.addSections("7.1.1 Ventas: Ingrese los meses de mayor venta");
                templatePDF.addParagraph(plan7_1_1.getText().toString());
                templatePDF.addSections("7.1.2 Ventas: Ingrese los meses de menor venta");
                templatePDF.addParagraph(plan7_1_2.getText().toString());
                templatePDF.addSections("7.2 Ventas: Porcentaje promedio de venta esperado el año 1, 2 y 3");
                templatePDF.addParagraph("Año 2020 (%): " + plan7_2_1.getText().toString());
                templatePDF.addParagraph("Año 2021 (%): " + plan7_2_2.getText().toString());
                templatePDF.addParagraph("Año 2022 (%): " + plan7_2_3.getText().toString());
                templatePDF.addSections("7.4 Ventas: Área geográfica de alcance");
                templatePDF.addParagraph(plan7_4.getText().toString());
                templatePDF.addSections("7.5 Ventas: Puntos de ventas y distribución");
                templatePDF.addParagraph(plan7_5.getText().toString());
                templatePDF.addSections("8.1 Publicidad y promoción: Imagen (nombre comercial, logotipo y slogan)");
                templatePDF.addParagraph(plan8_1.getText().toString());
                templatePDF.addSections("8.2 Publicidad y promoción: Actividades clave de marketing");
                templatePDF.addParagraph("Estrategia 1: " +  plan8_2_1.getText().toString());
                templatePDF.addParagraph("Estrategia 2: " +  plan8_2_2.getText().toString());
                templatePDF.addParagraph("Estrategia 3: " +  plan8_2_3.getText().toString());
                templatePDF.addParagraph("Estrategia 4: " +  plan8_2_4.getText().toString());
                templatePDF.addParagraph("Estrategia 5: " +  plan8_2_5.getText().toString());
                templatePDF.addLine();
                templatePDF.addSections("9 Modelo Canvas: Aliados Estratégicos");
                templatePDF.addParagraph("1: " +  plan9_aliados_1.getText().toString());
                templatePDF.addParagraph("2: " +  plan9_aliados_2.getText().toString());
                templatePDF.addParagraph("3: " +  plan9_aliados_3.getText().toString());
                templatePDF.addParagraph("4: " +  plan9_aliados_4.getText().toString());
                templatePDF.addParagraph("5: " +  plan9_aliados_5.getText().toString());
                templatePDF.addLine();
                templatePDF.addSections("9 Modelo Canvas: Actividades Clave");
                templatePDF.addParagraph("1: " +  plan9_actividades_1.getText().toString());
                templatePDF.addParagraph("2: " +  plan9_actividades_2.getText().toString());
                templatePDF.addParagraph("3: " +  plan9_actividades_3.getText().toString());
                templatePDF.addLine();
                templatePDF.addSections("9 Modelo Canvas: Recursos Clave");
                templatePDF.addParagraph("1: " +  plan9_recursos_1.getText().toString());
                templatePDF.addParagraph("2: " +  plan9_recursos_2.getText().toString());
                templatePDF.addParagraph("3: " +  plan9_recursos_3.getText().toString());
                templatePDF.addLine();
                templatePDF.addSections("9 Modelo Canvas: Propuesta de valor");
                templatePDF.addParagraph("1: " +  plan9_propuesta_1.getText().toString());
                templatePDF.addParagraph("2: " +  plan9_propuesta_2.getText().toString());
                templatePDF.addParagraph("3: " +  plan9_propuesta_3.getText().toString());
                templatePDF.addLine();
                templatePDF.addSections("9 Modelo Canvas: Relaciones con el cliente");
                templatePDF.addParagraph("1: " +  plan9_relaciones_1.getText().toString());
                templatePDF.addParagraph("2: " +  plan9_relaciones_2.getText().toString());
                templatePDF.addParagraph("3: " +  plan9_relaciones_3.getText().toString());
                templatePDF.addLine();
                templatePDF.addSections("9 Modelo Canvas: Canales de distribución");
                templatePDF.addParagraph("1: " +  plan9_canales_1.getText().toString());
                templatePDF.addParagraph("2: " +  plan9_canales_2.getText().toString());
                templatePDF.addParagraph("3: " +  plan9_canales_3.getText().toString());
                templatePDF.addLine();
                templatePDF.addSections("9 Modelo Canvas: Segmento de mercado");
                templatePDF.addParagraph("Edad: " +  plan9_segmento_1.getText().toString());
                templatePDF.addParagraph("Género: " +  plan9_segmento_2.getText().toString());
                templatePDF.addParagraph("Nivel socioeconómico: " +  plan9_segmento_3.getText().toString());
                templatePDF.addParagraph("Estado civil: " +  plan9_segmento_4.getText().toString());
                templatePDF.addParagraph("Gustos: " +  plan9_segmento_5.getText().toString());
                templatePDF.addLine();
                templatePDF.addSections("9 Modelo Canvas: Estructura de costos");
                templatePDF.addParagraph("1: " +  plan9_estructura_1.getText().toString());
                templatePDF.addParagraph("2: " +  plan9_estructura_2.getText().toString());
                templatePDF.addParagraph("3: " +  plan9_estructura_3.getText().toString());
                templatePDF.addParagraph("4: " +  plan9_estructura_4.getText().toString());
                templatePDF.addParagraph("5: " +  plan9_estructura_5.getText().toString());
                templatePDF.addLine();
                templatePDF.addSections("9 Modelo Canvas: Fuente de ingresos");
                templatePDF.addParagraph("1: " +  plan9_fuente_1.getText().toString());
                templatePDF.addParagraph("2: " +  plan9_fuente_2.getText().toString());
                templatePDF.addParagraph("3: " +  plan9_fuente_3.getText().toString());
                templatePDF.addParagraph("4: " +  plan9_fuente_4.getText().toString());
                templatePDF.addParagraph("5: " +  plan9_fuente_5.getText().toString());


                templatePDF.closeDocument();

                templatePDF.viewPDF2("Plan");
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                general = new HashMap<>();
                empresa = new HashMap<>();
                identidad = new HashMap<>();
                mercado = new HashMap<>();
                competencia = new HashMap<>();
                oferta = new HashMap<>();
                ventas = new HashMap<>();
                publicidad = new HashMap<>();
                canvas = new HashMap<>();
                aliados = new HashMap<>();
                actividades = new HashMap<>();
                recursos = new HashMap<>();
                propuesta = new HashMap<>();
                relaciones = new HashMap<>();
                canales = new HashMap<>();
                segmento = new HashMap<>();
                estructura = new HashMap<>();
                fuente = new HashMap<>();
                plan = new HashMap<>();

                general.put("plan1", plan1.getText().toString());
                empresa.put("plan2_1", plan2_1.getText().toString());
                empresa.put("plan2_Fortalezas_1", plan2_Fortalezas_1.getText().toString());
                empresa.put("plan2_Fortalezas_2", plan2_Fortalezas_2.getText().toString());
                empresa.put("plan2_Fortalezas_3", plan2_Fortalezas_3.getText().toString());
                empresa.put("plan2_Fortalezas_4", plan2_Fortalezas_4.getText().toString());
                empresa.put("plan2_Debilidades_1", plan2_Debilidades_1.getText().toString());
                empresa.put("plan2_Debilidades_2", plan2_Debilidades_2.getText().toString());
                empresa.put("plan2_Debilidades_3", plan2_Debilidades_3.getText().toString());
                empresa.put("plan2_Debilidades_4", plan2_Debilidades_4.getText().toString());
                empresa.put("plan2_Oportunidades_1", plan2_Oportunidades_1.getText().toString());
                empresa.put("plan2_Oportunidades_2", plan2_Oportunidades_2.getText().toString());
                empresa.put("plan2_Oportunidades_3", plan2_Oportunidades_3.getText().toString());
                empresa.put("plan2_Oportunidades_4", plan2_Oportunidades_4.getText().toString());
                empresa.put("plan2_Amenazas_1", plan2_Amenazas_1.getText().toString());
                empresa.put("plan2_Amenazas_2", plan2_Amenazas_2.getText().toString());
                empresa.put("plan2_Amenazas_3", plan2_Amenazas_3.getText().toString());
                empresa.put("plan2_Amenazas_4", plan2_Amenazas_4.getText().toString());
                empresa.put("plan2_3", plan2_3.getText().toString());
                empresa.put("plan2_4", plan2_4.getText().toString());
                empresa.put("plan2_5", plan2_5.getText().toString());
                identidad.put("plan3_1", plan3_1.getText().toString());
                identidad.put("plan3_2", plan3_2.getText().toString());
                identidad.put("plan3_3", plan3_3.getText().toString());
                identidad.put("plan3_4", plan3_4.getText().toString());
                mercado.put("plan4_1", plan4_1.getText().toString());
                mercado.put("plan4_2", plan4_2.getText().toString());
                mercado.put("plan4_3", plan4_3.getText().toString());
                mercado.put("plan4_4", plan4_4.getText().toString());
                mercado.put("plan4_5", plan4_5.getText().toString());
                mercado.put("plan4_6", plan4_6.getText().toString());
                mercado.put("plan4_7", plan4_7.getText().toString());
                mercado.put("plan4_8", plan4_8.getText().toString());
                competencia.put("plan5_0_tipo", plan5_0_tipo.getText().toString());
                competencia.put("plan5_1_tipo", plan5_1_tipo.getText().toString());
                competencia.put("plan5_2_tipo", plan5_2_tipo.getText().toString());
                competencia.put("plan5_3_tipo", plan5_3_tipo.getText().toString());
                competencia.put("plan5_0_canal", plan5_0_canal.getText().toString());
                competencia.put("plan5_1_canal", plan5_1_canal.getText().toString());
                competencia.put("plan5_2_canal", plan5_2_canal.getText().toString());
                competencia.put("plan5_3_canal", plan5_3_canal.getText().toString());
                competencia.put("plan5_0_calidad", plan5_0_calidad.getText().toString());
                competencia.put("plan5_1_calidad", plan5_1_calidad.getText().toString());
                competencia.put("plan5_2_calidad", plan5_2_calidad.getText().toString());
                competencia.put("plan5_3_calidad", plan5_3_calidad.getText().toString());
                competencia.put("plan5_0_precio", plan5_0_precio.getText().toString());
                competencia.put("plan5_1_precio", plan5_1_precio.getText().toString());
                competencia.put("plan5_2_precio", plan5_2_precio.getText().toString());
                competencia.put("plan5_3_precio", plan5_3_precio.getText().toString());
                competencia.put("plan5_0_tiempo", plan5_0_tiempo.getText().toString());
                competencia.put("plan5_1_tiempo", plan5_1_tiempo.getText().toString());
                competencia.put("plan5_2_tiempo", plan5_2_tiempo.getText().toString());
                competencia.put("plan5_3_tiempo", plan5_3_tiempo.getText().toString());
                oferta.put("plan6_1", plan6_1.getText().toString());
                oferta.put("plan6_2", plan6_2.getText().toString());
                oferta.put("plan6_3", plan6_3.getText().toString());
                oferta.put("plan6_4", plan6_4.getText().toString());
                ventas.put("plan7_1_1", plan7_1_1.getText().toString());
                ventas.put("plan7_1_2", plan7_1_2.getText().toString());
                ventas.put("plan7_2_1", plan7_2_1.getText().toString());
                ventas.put("plan7_2_2", plan7_2_2.getText().toString());
                ventas.put("plan7_2_3", plan7_2_3.getText().toString());
                ventas.put("plan7_3", plan7_3.getText().toString());
                ventas.put("plan7_4", plan7_4.getText().toString());
                ventas.put("plan7_5", plan7_5.getText().toString());
                publicidad.put("plan8_1", plan8_1.getText().toString());
                publicidad.put("plan8_2_1", plan8_2_1.getText().toString());
                publicidad.put("plan8_2_2", plan8_2_2.getText().toString());
                publicidad.put("plan8_2_3", plan8_2_3.getText().toString());
                publicidad.put("plan8_2_4", plan8_2_4.getText().toString());
                publicidad.put("plan8_2_5", plan8_2_5.getText().toString());
                aliados.put("plan9_aliados_1", plan9_aliados_1.getText().toString());
                aliados.put("plan9_aliados_2", plan9_aliados_2.getText().toString());
                aliados.put("plan9_aliados_3", plan9_aliados_3.getText().toString());
                aliados.put("plan9_aliados_4", plan9_aliados_4.getText().toString());
                aliados.put("plan9_aliados_5", plan9_aliados_5.getText().toString());
                actividades.put("plan9_actividades_1", plan9_actividades_1.getText().toString());
                actividades.put("plan9_actividades_2", plan9_actividades_2.getText().toString());
                actividades.put("plan9_actividades_3", plan9_actividades_3.getText().toString());
                recursos.put("plan9_recursos_1", plan9_recursos_1.getText().toString());
                recursos.put("plan9_recursos_2", plan9_recursos_2.getText().toString());
                recursos.put("plan9_recursos_3", plan9_recursos_3.getText().toString());
                propuesta.put("plan9_propuesta_1", plan9_propuesta_1.getText().toString());
                propuesta.put("plan9_propuesta_2", plan9_propuesta_2.getText().toString());
                propuesta.put("plan9_propuesta_3", plan9_propuesta_3.getText().toString());
                relaciones.put("plan9_relaciones_1", plan9_relaciones_1.getText().toString());
                relaciones.put("plan9_relaciones_2", plan9_relaciones_2.getText().toString());
                relaciones.put("plan9_relaciones_3", plan9_relaciones_3.getText().toString());
                canales.put("plan9_canales_1", plan9_canales_1.getText().toString());
                canales.put("plan9_canales_2", plan9_canales_2.getText().toString());
                canales.put("plan9_canales_3", plan9_canales_3.getText().toString());
                segmento.put("plan9_segmento_1", plan9_segmento_1.getText().toString());
                segmento.put("plan9_segmento_2", plan9_segmento_2.getText().toString());
                segmento.put("plan9_segmento_3", plan9_segmento_3.getText().toString());
                segmento.put("plan9_segmento_4", plan9_segmento_4.getText().toString());
                segmento.put("plan9_segmento_5", plan9_segmento_5.getText().toString());
                estructura.put("plan9_estructura_1", plan9_estructura_1.getText().toString());
                estructura.put("plan9_estructura_2", plan9_estructura_2.getText().toString());
                estructura.put("plan9_estructura_3", plan9_estructura_3.getText().toString());
                estructura.put("plan9_estructura_4", plan9_estructura_4.getText().toString());
                estructura.put("plan9_estructura_5", plan9_estructura_5.getText().toString());
                fuente.put("plan9_fuente_1", plan9_fuente_1.getText().toString());
                fuente.put("plan9_fuente_2", plan9_fuente_2.getText().toString());
                fuente.put("plan9_fuente_3", plan9_fuente_3.getText().toString());
                fuente.put("plan9_fuente_4", plan9_fuente_4.getText().toString());
                fuente.put("plan9_fuente_5", plan9_fuente_5.getText().toString());

                canvas.put("alidados", aliados);
                canvas.put("actividades", actividades);
                canvas.put("recursos", recursos);
                canvas.put("propuesta", propuesta);
                canvas.put("relaciones", relaciones);
                canvas.put("canales", canales);
                canvas.put("segmento", segmento);
                canvas.put("estructura", estructura);
                canvas.put("fuente", fuente);

                plan.put("general", general);
                plan.put("empresa", empresa);
                plan.put("identidad", identidad);
                plan.put("mercado", mercado);
                plan.put("competencia", competencia);
                plan.put("oferta", oferta);
                plan.put("ventas", ventas);
                plan.put("publicidad", publicidad);
                plan.put("canvas", canvas);

                //salvarDatos();

                db.collection("planes").document(nombre)
                        .set(plan)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(getApplicationContext(),"¡Datos subidos exitosamente!", Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getApplicationContext(),"Ha sucedido un error", Toast.LENGTH_LONG).show();
                            }
                        });



            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //salvarDatos();

    }

    @Override
    protected void onResume() {
        super.onResume();
        //leerDatos();
    }

    private void salvarDatos(){

        // Leemos la memoria para ver que tarjetas se han creado
        sharedPreferences = getSharedPreferences("misDatos", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("plan1", plan1.getText().toString());
        editor.putString("plan2_1", plan2_1.getText().toString());
        editor.putString("plan2_Fortalezas_1", plan2_Fortalezas_1.getText().toString());
        editor.putString("plan2_Fortalezas_2", plan2_Fortalezas_2.getText().toString());
        editor.putString("plan2_Fortalezas_3", plan2_Fortalezas_3.getText().toString());
        editor.putString("plan2_Fortalezas_4", plan2_Fortalezas_4.getText().toString());
        editor.putString("plan2_Debilidades_1", plan2_Debilidades_1.getText().toString());
        editor.putString("plan2_Debilidades_2", plan2_Debilidades_2.getText().toString());
        editor.putString("plan2_Debilidades_3", plan2_Debilidades_3.getText().toString());
        editor.putString("plan2_Debilidades_4", plan2_Debilidades_4.getText().toString());
        editor.putString("plan2_Oportunidades_1", plan2_Oportunidades_1.getText().toString());
        editor.putString("plan2_Oportunidades_2", plan2_Oportunidades_2.getText().toString());
        editor.putString("plan2_Oportunidades_3", plan2_Oportunidades_3.getText().toString());
        editor.putString("plan2_Oportunidades_4", plan2_Oportunidades_4.getText().toString());
        editor.putString("plan2_Amenazas_1", plan2_Amenazas_1.getText().toString());
        editor.putString("plan2_Amenazas_2", plan2_Amenazas_2.getText().toString());
        editor.putString("plan2_Amenazas_3", plan2_Amenazas_3.getText().toString());
        editor.putString("plan2_Amenazas_4", plan2_Amenazas_4.getText().toString());
        editor.putString("plan2_3", plan2_3.getText().toString());
        editor.putString("plan2_4", plan2_4.getText().toString());
        editor.putString("plan2_5", plan2_5.getText().toString());
        editor.putString("plan3_1", plan3_1.getText().toString());
        editor.putString("plan3_2", plan3_2.getText().toString());
        editor.putString("plan3_3", plan3_3.getText().toString());
        editor.putString("plan3_4", plan3_4.getText().toString());
        editor.putString("plan4_1", plan4_1.getText().toString());
        editor.putString("plan4_2", plan4_2.getText().toString());
        editor.putString("plan4_3", plan4_3.getText().toString());
        editor.putString("plan4_4", plan4_4.getText().toString());
        editor.putString("plan4_5", plan4_5.getText().toString());
        editor.putString("plan4_6", plan4_6.getText().toString());
        editor.putString("plan4_7", plan4_7.getText().toString());
        editor.putString("plan4_8", plan4_8.getText().toString());
        editor.putString("plan5_0_tipo", plan5_0_tipo.getText().toString());
        editor.putString("plan5_1_tipo", plan5_1_tipo.getText().toString());
        editor.putString("plan5_2_tipo", plan5_2_tipo.getText().toString());
        editor.putString("plan5_3_tipo", plan5_3_tipo.getText().toString());
        editor.putString("plan5_0_canal", plan5_0_canal.getText().toString());
        editor.putString("plan5_1_canal", plan5_1_canal.getText().toString());
        editor.putString("plan5_2_canal", plan5_2_canal.getText().toString());
        editor.putString("plan5_3_canal", plan5_3_canal.getText().toString());
        editor.putString("plan5_0_calidad", plan5_0_calidad.getText().toString());
        editor.putString("plan5_1_calidad", plan5_1_calidad.getText().toString());
        editor.putString("plan5_2_calidad", plan5_2_calidad.getText().toString());
        editor.putString("plan5_3_calidad", plan5_3_calidad.getText().toString());
        editor.putString("plan5_0_precio", plan5_0_precio.getText().toString());
        editor.putString("plan5_1_precio", plan5_1_precio.getText().toString());
        editor.putString("plan5_2_precio", plan5_2_precio.getText().toString());
        editor.putString("plan5_3_precio", plan5_3_precio.getText().toString());
        editor.putString("plan5_0_tiempo", plan5_0_tiempo.getText().toString());
        editor.putString("plan5_1_tiempo", plan5_1_tiempo.getText().toString());
        editor.putString("plan5_2_tiempo", plan5_2_tiempo.getText().toString());
        editor.putString("plan5_3_tiempo", plan5_3_tiempo.getText().toString());
        editor.putString("plan6_1", plan6_1.getText().toString());
        editor.putString("plan6_2", plan6_2.getText().toString());
        editor.putString("plan6_3", plan6_3.getText().toString());
        editor.putString("plan6_4", plan6_4.getText().toString());
        editor.putString("plan7_1_1", plan7_1_1.getText().toString());
        editor.putString("plan7_1_2", plan7_1_2.getText().toString());
        editor.putString("plan7_2_1", plan7_2_1.getText().toString());
        editor.putString("plan7_2_2", plan7_2_2.getText().toString());
        editor.putString("plan7_2_3", plan7_2_3.getText().toString());
        editor.putString("plan7_3", plan7_3.getText().toString());
        editor.putString("plan7_4", plan7_4.getText().toString());
        editor.putString("plan7_5", plan7_5.getText().toString());
        editor.putString("plan8_1", plan8_1.getText().toString());
        editor.putString("plan8_2_1", plan8_2_1.getText().toString());
        editor.putString("plan8_2_2", plan8_2_2.getText().toString());
        editor.putString("plan8_2_3", plan8_2_3.getText().toString());
        editor.putString("plan8_2_4", plan8_2_4.getText().toString());
        editor.putString("plan8_2_5", plan8_2_5.getText().toString());
        editor.putString("plan9_aliados_1", plan9_aliados_1.getText().toString());
        editor.putString("plan9_aliados_2", plan9_aliados_2.getText().toString());
        editor.putString("plan9_aliados_3", plan9_aliados_3.getText().toString());
        editor.putString("plan9_aliados_4", plan9_aliados_4.getText().toString());
        editor.putString("plan9_aliados_5", plan9_aliados_5.getText().toString());
        editor.putString("plan9_actividades_1", plan9_actividades_1.getText().toString());
        editor.putString("plan9_actividades_2", plan9_actividades_2.getText().toString());
        editor.putString("plan9_actividades_3", plan9_actividades_3.getText().toString());
        editor.putString("plan9_recursos_1", plan9_recursos_1.getText().toString());
        editor.putString("plan9_recursos_2", plan9_recursos_2.getText().toString());
        editor.putString("plan9_recursos_3", plan9_recursos_3.getText().toString());
        editor.putString("plan9_propuesta_1", plan9_propuesta_1.getText().toString());
        editor.putString("plan9_propuesta_2", plan9_propuesta_2.getText().toString());
        editor.putString("plan9_propuesta_3", plan9_propuesta_3.getText().toString());
        editor.putString("plan9_relaciones_1", plan9_relaciones_1.getText().toString());
        editor.putString("plan9_relaciones_2", plan9_relaciones_2.getText().toString());
        editor.putString("plan9_relaciones_3", plan9_relaciones_3.getText().toString());
        editor.putString("plan9_canales_1", plan9_canales_1.getText().toString());
        editor.putString("plan9_canales_2", plan9_canales_2.getText().toString());
        editor.putString("plan9_canales_3", plan9_canales_3.getText().toString());
        editor.putString("plan9_segmento_1", plan9_segmento_1.getText().toString());
        editor.putString("plan9_segmento_2", plan9_segmento_2.getText().toString());
        editor.putString("plan9_segmento_3", plan9_segmento_3.getText().toString());
        editor.putString("plan9_segmento_4", plan9_segmento_4.getText().toString());
        editor.putString("plan9_segmento_5", plan9_segmento_5.getText().toString());
        editor.putString("plan9_estructura_1", plan9_estructura_1.getText().toString());
        editor.putString("plan9_estructura_2", plan9_estructura_2.getText().toString());
        editor.putString("plan9_estructura_3", plan9_estructura_3.getText().toString());
        editor.putString("plan9_estructura_4", plan9_estructura_4.getText().toString());
        editor.putString("plan9_estructura_5", plan9_estructura_5.getText().toString());
        editor.putString("plan9_fuente_1", plan9_fuente_1.getText().toString());
        editor.putString("plan9_fuente_2", plan9_fuente_2.getText().toString());
        editor.putString("plan9_fuente_3", plan9_fuente_3.getText().toString());
        editor.putString("plan9_fuente_4", plan9_fuente_4.getText().toString());
        editor.putString("plan9_fuente_5", plan9_fuente_5.getText().toString());
        editor.commit();
    }

    private void leerDatos(String id){

        final DocumentReference docRef = db.collection("planes").document(id);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("PLAN", "DocumentSnapshot data: " + document.getData());
                        Plan plan_objeto = document.toObject(Plan.class);

                        plan1.setText(plan_objeto.getGeneral().getPlan1());
                        plan2_1.setText(plan_objeto.getEmpresa().getPlan2_1());
                        plan2_Fortalezas_1.setText(plan_objeto.getEmpresa().getPlan2_Fortalezas_1());
                        plan2_Fortalezas_2.setText(plan_objeto.getEmpresa().getPlan2_Fortalezas_2());
                        plan2_Fortalezas_3.setText(plan_objeto.getEmpresa().getPlan2_Fortalezas_3());
                        plan2_Fortalezas_4.setText(plan_objeto.getEmpresa().getPlan2_Fortalezas_4());
                        plan2_Debilidades_1.setText(plan_objeto.getEmpresa().getPlan2_Debilidades_1());
                        plan2_Debilidades_2.setText(plan_objeto.getEmpresa().getPlan2_Debilidades_2());
                        plan2_Debilidades_3.setText(plan_objeto.getEmpresa().getPlan2_Debilidades_3());
                        plan2_Debilidades_4.setText(plan_objeto.getEmpresa().getPlan2_Debilidades_4());
                        plan2_Oportunidades_1.setText(plan_objeto.getEmpresa().getPlan2_Oportunidades_1());
                        plan2_Oportunidades_2.setText(plan_objeto.getEmpresa().getPlan2_Oportunidades_2());
                        plan2_Oportunidades_3.setText(plan_objeto.getEmpresa().getPlan2_Oportunidades_3());
                        plan2_Oportunidades_4.setText(plan_objeto.getEmpresa().getPlan2_Oportunidades_4());
                        plan2_Amenazas_1.setText(plan_objeto.getEmpresa().getPlan2_Amenazas_1());
                        plan2_Amenazas_2.setText(plan_objeto.getEmpresa().getPlan2_Amenazas_2());
                        plan2_Amenazas_3.setText(plan_objeto.getEmpresa().getPlan2_Amenazas_3());
                        plan2_Amenazas_4.setText(plan_objeto.getEmpresa().getPlan2_Amenazas_4());
                        plan2_3.setText(plan_objeto.getEmpresa().getPlan2_3());
                        plan2_4.setText(plan_objeto.getEmpresa().getPlan2_4());
                        plan2_5.setText(plan_objeto.getEmpresa().getPlan2_5());
                        plan3_1.setText(plan_objeto.getIdentidad().getPlan3_1());
                        plan3_2.setText(plan_objeto.getIdentidad().getPlan3_2());
                        plan3_3.setText(plan_objeto.getIdentidad().getPlan3_3());
                        plan3_4.setText(plan_objeto.getIdentidad().getPlan3_4());
                        plan4_1.setText(plan_objeto.getMercado().getPlan4_1());
                        plan4_2.setText(plan_objeto.getMercado().getPlan4_2());
                        plan4_3.setText(plan_objeto.getMercado().getPlan4_3());
                        plan4_4.setText(plan_objeto.getMercado().getPlan4_4());
                        plan4_5.setText(plan_objeto.getMercado().getPlan4_5());
                        plan4_6.setText(plan_objeto.getMercado().getPlan4_6());
                        plan4_7.setText(plan_objeto.getMercado().getPlan4_7());
                        plan4_8.setText(plan_objeto.getMercado().getPlan4_8());
                        plan5_0_tipo.setText(plan_objeto.getCompetencia().getPlan5_0_tipo());
                        plan5_1_tipo.setText(plan_objeto.getCompetencia().getPlan5_1_tipo());
                        plan5_2_tipo.setText(plan_objeto.getCompetencia().getPlan5_2_tipo());
                        plan5_3_tipo.setText(plan_objeto.getCompetencia().getPlan5_3_tipo());
                        plan5_0_canal.setText(plan_objeto.getCompetencia().getPlan5_0_canal());
                        plan5_1_canal.setText(plan_objeto.getCompetencia().getPlan5_1_canal());
                        plan5_2_canal.setText(plan_objeto.getCompetencia().getPlan5_2_canal());
                        plan5_3_canal.setText(plan_objeto.getCompetencia().getPlan5_3_canal());
                        plan5_0_calidad.setText(plan_objeto.getCompetencia().getPlan5_0_calidad());
                        plan5_1_calidad.setText(plan_objeto.getCompetencia().getPlan5_1_calidad());
                        plan5_2_calidad.setText(plan_objeto.getCompetencia().getPlan5_2_calidad());
                        plan5_3_calidad.setText(plan_objeto.getCompetencia().getPlan5_3_calidad());
                        plan5_0_precio.setText(plan_objeto.getCompetencia().getPlan5_0_precio());
                        plan5_1_precio.setText(plan_objeto.getCompetencia().getPlan5_1_precio());
                        plan5_2_precio.setText(plan_objeto.getCompetencia().getPlan5_2_precio());
                        plan5_3_precio.setText(plan_objeto.getCompetencia().getPlan5_3_precio());
                        plan5_0_tiempo.setText(plan_objeto.getCompetencia().getPlan5_0_tiempo());
                        plan5_1_tiempo.setText(plan_objeto.getCompetencia().getPlan5_1_tiempo());
                        plan5_2_tiempo.setText(plan_objeto.getCompetencia().getPlan5_2_tiempo());
                        plan5_3_tiempo.setText(plan_objeto.getCompetencia().getPlan5_3_tiempo());
                        plan6_1.setText(plan_objeto.getOferta().getPlan6_1());
                        plan6_2.setText(plan_objeto.getOferta().getPlan6_2());
                        plan6_3.setText(plan_objeto.getOferta().getPlan6_3());
                        plan6_4.setText(plan_objeto.getOferta().getPlan6_4());
                        plan7_1_1.setText(plan_objeto.getVentas().getPlan7_1_1());
                        plan7_1_2.setText(plan_objeto.getVentas().getPlan7_1_2());
                        plan7_2_1.setText(plan_objeto.getVentas().getPlan7_2_1());
                        plan7_2_2.setText(plan_objeto.getVentas().getPlan7_2_2());
                        plan7_2_3.setText(plan_objeto.getVentas().getPlan7_2_3());
                        plan7_3.setText(plan_objeto.getVentas().getPlan7_3());
                        plan7_4.setText(plan_objeto.getVentas().getPlan7_4());
                        plan7_5.setText(plan_objeto.getVentas().getPlan7_5());
                        plan8_1.setText(plan_objeto.getPublicidad().getPlan8_1());
                        plan8_2_1.setText(plan_objeto.getPublicidad().getPlan8_2_1());
                        plan8_2_2.setText(plan_objeto.getPublicidad().getPlan8_2_2());
                        plan8_2_3.setText(plan_objeto.getPublicidad().getPlan8_2_3());
                        plan8_2_4.setText(plan_objeto.getPublicidad().getPlan8_2_4());
                        plan8_2_5.setText(plan_objeto.getPublicidad().getPlan8_2_5());
                        plan9_aliados_1.setText(plan_objeto.getCanvas().getAlidados().getPlan9_aliados_1());
                        plan9_aliados_2.setText(plan_objeto.getCanvas().getAlidados().getPlan9_aliados_2());
                        plan9_aliados_3.setText(plan_objeto.getCanvas().getAlidados().getPlan9_aliados_3());
                        plan9_aliados_4.setText(plan_objeto.getCanvas().getAlidados().getPlan9_aliados_4());
                        plan9_aliados_5.setText(plan_objeto.getCanvas().getAlidados().getPlan9_aliados_5());
                        plan9_actividades_1.setText(plan_objeto.getCanvas().getActividades().getPlan9_actividades_1());
                        plan9_actividades_2.setText(plan_objeto.getCanvas().getActividades().getPlan9_actividades_2());
                        plan9_actividades_3.setText(plan_objeto.getCanvas().getActividades().getPlan9_actividades_3());
                        plan9_recursos_1.setText(plan_objeto.getCanvas().getRecursos().getPlan9_recursos_1());
                        plan9_recursos_2.setText(plan_objeto.getCanvas().getRecursos().getPlan9_recursos_2());
                        plan9_recursos_3.setText(plan_objeto.getCanvas().getRecursos().getPlan9_recursos_3());
                        plan9_propuesta_1.setText(plan_objeto.getCanvas().getPropuesta().getPlan9_propuesta_1());
                        plan9_propuesta_2.setText(plan_objeto.getCanvas().getPropuesta().getPlan9_propuesta_2());
                        plan9_propuesta_3.setText(plan_objeto.getCanvas().getPropuesta().getPlan9_propuesta_3());
                        plan9_relaciones_1.setText(plan_objeto.getCanvas().getRelaciones().getPlan9_relaciones_1());
                        plan9_relaciones_2.setText(plan_objeto.getCanvas().getRelaciones().getPlan9_relaciones_2());
                        plan9_relaciones_3.setText(plan_objeto.getCanvas().getRelaciones().getPlan9_relaciones_3());
                        plan9_canales_1.setText(plan_objeto.getCanvas().getCanales().getPlan9_canales_1());
                        plan9_canales_2.setText(plan_objeto.getCanvas().getCanales().getPlan9_canales_2());
                        plan9_canales_3.setText(plan_objeto.getCanvas().getCanales().getPlan9_canales_3());
                        plan9_segmento_1.setText(plan_objeto.getCanvas().getSegmento().getPlan9_segmento_1());
                        plan9_segmento_2.setText(plan_objeto.getCanvas().getSegmento().getPlan9_segmento_2());
                        plan9_segmento_3.setText(plan_objeto.getCanvas().getSegmento().getPlan9_segmento_3());
                        plan9_segmento_4.setText(plan_objeto.getCanvas().getSegmento().getPlan9_segmento_4());
                        plan9_segmento_5.setText(plan_objeto.getCanvas().getSegmento().getPlan9_segmento_5());
                        plan9_estructura_1.setText(plan_objeto.getCanvas().getEstructura().getPlan9_estructura_1());
                        plan9_estructura_2.setText(plan_objeto.getCanvas().getEstructura().getPlan9_estructura_2());
                        plan9_estructura_3.setText(plan_objeto.getCanvas().getEstructura().getPlan9_estructura_3());
                        plan9_estructura_4.setText(plan_objeto.getCanvas().getEstructura().getPlan9_estructura_4());
                        plan9_estructura_5.setText(plan_objeto.getCanvas().getEstructura().getPlan9_estructura_5());
                        plan9_fuente_1.setText(plan_objeto.getCanvas().getFuente().getPlan9_fuente_1());
                        plan9_fuente_2.setText(plan_objeto.getCanvas().getFuente().getPlan9_fuente_2());
                        plan9_fuente_3.setText(plan_objeto.getCanvas().getFuente().getPlan9_fuente_3());
                        plan9_fuente_4.setText(plan_objeto.getCanvas().getFuente().getPlan9_fuente_4());
                        plan9_fuente_5.setText(plan_objeto.getCanvas().getFuente().getPlan9_fuente_5());

                    } else {
                        Log.d("PLAN", "No such document");
                        Toast.makeText(getApplicationContext(), "No se encontró a la empresa", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.d("PLAN", "get failed with ", task.getException());
                }
            }
        });

    }
}
