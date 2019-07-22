package com.colabora.soluciones.convocatoriafeyac;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.Modelos.PreguntasDiagnostico;
import com.colabora.soluciones.convocatoriafeyac.Modelos.RadarMarkerView;
import com.colabora.soluciones.convocatoriafeyac.Modelos.TemplatePDF;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.data.RadarDataSet;
import com.github.mikephil.charting.data.RadarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IRadarDataSet;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DatosEmpresaDiagnosticoActivity extends AppCompatActivity {

    private TextView txtArea;
    private TextView txtPregunta;
    private Button btnSi;
    private Button btnNo;
    private Button btnNoSe;
    private Button btnUno;
    private Button btnDos;
    private Button btnSiguiente;

    private int contador = 0;
    private int contadorPlaneacion = 0;
    private int contadorVentas = 0;
    private int contadorContabilidad = 0;
    private int contadorLegal = 0;
    private int contadorInnovacion = 0;
    private int contadorProcuracion = 0;
    private List<PreguntasDiagnostico> preguntas = new ArrayList<>();
    private List<String> actividades = new ArrayList<>();
    private List<String> actividades2 = new ArrayList<>();
    private List<String> actividades3 = new ArrayList<>();

    private boolean respondida = false;
    private SharedPreferences misDatos;

    private LinearLayout linearLayoutDos;
    private LinearLayout linearLayoutTres;

    // GENERACION DE PDF
    private TemplatePDF templatePDF;
    private String formattedDate ="";
    private String[]headerPreguntas = {"Pregunta", "Información"};

    protected Typeface mMontserrat;
    private String UUIDUser = "";

    private int calificacion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_empresa_diagnostico);

        txtArea = (TextView)findViewById(R.id.txtDatosEmpresaActual);
        txtPregunta = (TextView)findViewById(R.id.txtDatosEmpresaPregunta);
        btnSi = (Button) findViewById(R.id.btnEstatusActualTresSi);
        btnNo = (Button) findViewById(R.id.btnEstatusActualTresNo);
        btnNoSe = (Button) findViewById(R.id.btnEstatusActualTresNoSe);
        btnUno = (Button) findViewById(R.id.btnEstatusActualDos1);
        btnDos = (Button) findViewById(R.id.btnEstatusActualDos2);
        linearLayoutDos = (LinearLayout)findViewById(R.id.linearDatosEmpresaDos);
        linearLayoutTres = (LinearLayout)findViewById(R.id.linearDatosEmpresaTres);

        btnSiguiente = (Button) findViewById(R.id.btnDatosEmpresaSigPregunta);

        // ***********************************************************************************************************
        preguntas.add(new PreguntasDiagnostico("", "1. Giro del negocio.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "2. Estatus del proyecto.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "3. ¿Cuánto tiempo de experiencia tiene de su negocio o proyecto?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "4. ¿Estás dado de alta en hacienda?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "4.1. Si en la anterior mencionó 'SI' ¿Bajo que régimen se dio de alta?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "5. ¿Cuenta con local o establecimiento?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "5.1. ¿Cuentas con uso de suelo?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "5.2. ¿Cuentas con licencia de funcionamiento?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "6. Número de integrantes del proyecto (poner # por genero).", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "7. ¿Algún integrante cuenta con una discapacidad? (poner # por genero).", false, ""));

        preguntas.add(new PreguntasDiagnostico("", "8. ¿Tienes a tu personal dado de alta ante IMSS (incluyendote)? (poner # por genero).", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "6. ¿Cuenta con logotipo?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "6.1. En caso de contar con logotipo responder si está registrado ante el IMPI.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "7. ¿Cuenta con página web?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "8. ¿Cuenta con red o redes sociales?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "9. ¿Tiene tu proyecto un plan de negocios?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "10. ¿Conoces a tus clientes?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "11. ¿Conoces a tus competencia?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "12. ¿Tu negocio ya genera ingresos fijos mensuales?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "13. ¿Alguna vez has tomado cursos o talleres?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "14. ¿Has sido acreedor de fondo o financiamiento?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "15. ¿Conoces los costos de producción de tus productos y precio de venta?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "16. ¿Tienes un catalogo de tus productos o servicios?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "17. ¿Cuentas con un contador?.", false, ""));
        preguntas.add(new PreguntasDiagnostico("", "18. ¿Formas parte de una comunidad empresarial? (cámara, asociación, movimiento, etc..).", false, ""));

        // ********************************************************** DECLARAMOS LAS ACTIVIDADES A REALIZAR **************************************************************
        actividades.add("");
        actividades.add("Si tu negocio está en idea es momento que busques como hacerlo realidad. Correr riesgos son uno de los puntos clave que los empresarios exitosos nos recomiendan.");
        actividades.add("Las empresas menores a un año deben hacer un esfuerzo mayor ya que están en su período de prueba, encontrarás muchos retos que te darán experiencias, no te desesperes todos pasan por esos procesos. Haz un plan de trabajo, un programa de ventas, amplia tus canales y revisa tus procesos de atención al cliente, ¡no dejes de buscar ayuda y considera que es la etapa de trabajar duro!.");
        actividades.add("");
        actividades.add("");
        actividades.add("");
        actividades.add("");
        actividades.add("");
        actividades.add("");
        actividades.add("");

        actividades.add("");
        actividades.add("");
        actividades.add("");
        actividades.add("");
        actividades.add("Cuida el proceso de respuesta y atención al cliente, revisa la ortografía antes de cada publicación, no dejes de monitorear los resultados de las estadísticas y crea un calendario de publicaciones para medir los impactos.");
        actividades.add("");
        actividades.add("");
        actividades.add("");
        actividades.add("");
        actividades.add("");
        actividades.add("");
        actividades.add("");
        actividades.add("");
        actividades.add("");
        actividades.add("");

        actividades2.add("");
        actividades2.add("Ya te falta poco para formalizar tu negocio, no dejes pasar la oprtunidad de crecer.");
        actividades2.add("Estás en la mitad del camino, las estádisticas dicen que los que superen los primeros años durarán mucho más, ya cuentas con experiencia, sólo trata de darte tiempo para desarrollar estrategias de mejora continua. Diseña una estrategia de canales y venta, mide tus tiempos de cotización y contratación para ponerte más metas, analiza tus procesos de producción para reducir gastos y agilizar procesos, no descuides la atención de tus clientes.");
        actividades2.add("Si aún no estás dado de alta en hacienda considera que será complicado contar con los beneficios empresariales como: autoemplearte con prestaciones o a tu capital humano, no podrás ser beneficiario de crédito o algún programa de apoyo público para pequeños negocios.");
        actividades2.add("");
        actividades2.add("Si estás buscando un lugar, trata de estar cerca de tus clientes potenciales, facilita la logística de llegada, busca puntos de referencia cercanos, trata de estar en un área visual, si rentas no olvides contar con un contrato y revisar si cuenta con todos los permisos para operar un negocio.");
        actividades2.add("Si cumples con estos requisitos podrás ser acreedor a financiamientos públicos, además de que te evitarás multas o problemas con tu municipio.");
        actividades2.add("Si cumples con estos requisitos podrás ser acreedor a financiamientos públicos, además de que te evitarás multas o problemas con tu municipio.");
        actividades2.add("");
        actividades2.add("");

        actividades2.add("");
        actividades2.add("Esta es la identidad de tu negocio, recuerda buscar una gama de tonos en tu logotipo usando la psicología del color (qué buscas de tus clientes o ellos de ti), el nombre tiene que decir lo que ofreces o que sea fácil de recordar.");
        actividades2.add("Estás a unos pasos de proteger lo que te pertenece y por lo que has trabajado, trata de hacer previamente las búsquedas adecuadas que el IMPI recomienda para no gastar tu recurso en vano.");
        actividades2.add("Asegurate de tener un dominio limpio, entendible y directo a tu segmento de mercado, procura comprar paquetes que te incluyan ser sitio seguro y por lo menos te deje generar un correo institucional.");
        actividades2.add("Cuida el proceso de respuesta y atención al cliente, revisa la ortografía antes de cada publicación, no dejes de monitorear los resultados de las estadísticas y crea un calendario de publicaciones para medir los impactos.");
        actividades2.add("Te ayudará a transmitir el modelo de negocio a tu personal de manera más sencilla así como a terceros para la identificación de un recurso.");
        actividades2.add("Sirve para diseñar estrategias de ventas más directa. ");
        actividades2.add("Sirve para cuidar tu espalda y mejorar tus estrategias de venta.");
        actividades2.add("Recuerda hacer un feedback o encuesta de satisfacción para buscar siempre mejoras y no perder a tu cliente.");
        actividades2.add("Reforzar la mente ayuda a identificar mejores estrategias para tu negocio, no dejes de aprovechar las herramientas que puedes aprender.");
        actividades2.add("");
        actividades2.add("No olvides usar tablas comparativas de costos vs precio de venta para conocer el margen de utilidad o ganancia que tendrás (y toma en cuenta si pagarás a terceros como puntos de venta o comisionistas).");
        actividades2.add("Busca un diseño claro y entendible, no olvides redactar las principales caracteristicas de cada producto o servicio.");
        actividades2.add("Si no estás dado de alta ante hacienda aún, no debes preocuparte, ¡pero en caso contrario estamos en un modo alerta!");
        actividades2.add("Las comunidades empresariales te ayudarán a buscar clientes potenciales, ampliar tus alianzas o aprender de las experiencias de otros. Busca la asociación o cámara que mejor te represente.");

        actividades3.add("");
        actividades3.add("Ya estás en el barco activamente económico, ahora sólo debemos analizar si contamos con una empresa sana y productiva.");
        actividades3.add("Ya estás brincando el periodo de prueba, pero no te confíes nuevos retos están por lllegar. Analiza tu competencia, no descuides a tus clientes, diseña estrategias de fidelización, busca ayuda en apertura de nuevos canales de publicidad y venta, mejora siempre tus procesos, ten cuidado con la contabilidad.");
        actividades3.add("");
        actividades3.add("");
        actividades3.add("Para generar confianza en tus clientes debes contar con un espacio físico donde te puedan ubicar, además, contar con un domicilio fiscal o de trabajo te ayudará a gestionar trámites, sobre todo los de financiamiento.");
        actividades3.add("Si cumples con estos requisitos podrás ser acreedor a financiamientos públicos, además de que te evitarás multas o problemas con tu municipio.");
        actividades3.add("Si cumples con estos requisitos podrás ser acreedor a financiamientos públicos, además de que te evitarás multas o problemas con tu municipio.");
        actividades3.add("");
        actividades3.add("");

        actividades3.add("");
        actividades3.add("Esta es la identidad de tu negocio, si no tienes una imagen que te represente es díficil que tus clientes puedan acordarse de ti o referenciarte, recuerda buscar una gama de tonos en tu logotipo usando la psicología del color (que buscas de tus clientes o ellos de ti), el nombre tiene que decir lo que ofreces o que sea facil de recordar.");
        actividades3.add("Evita que alguien más robe tu identidad.");
        actividades3.add("Innovar o morir, hoy en día el mayor número de búsqueda de negocios es en línea.");
        actividades3.add("Aprovecha los medios digitales para crear contenido publicitario gratuito.");
        actividades3.add("Te ayudará a transmitir el modelo de negocio a tu personal de manera más sencilla así como a terceros para la identificación de un recurso.");
        actividades3.add("Sirve para diseñar estrategias de ventas más directa.");
        actividades3.add("Sirve para cuidar tu espalda y mejorar tus estrategias de venta.");
        actividades3.add("Ponte una meta de venta ¡Tú puedes!");
        actividades3.add("Reforzar la mente ayuda a identificar mejores estrategias para tu negocio, no dejes de aprovechar las herramientas que puedes aprender.");
        actividades3.add("");
        actividades3.add("Es momento de medir costos: cuanto me cuesta producir cada producto contando la mano de obra, empaque, envío, materia prima, insumos, etc… O si ofrezco un servicio la inversión de la publicidad, honorarios, insumos, herramientas de trabajo, gastos varios.");
        actividades3.add("El catálogo le da una idea clara a tu cliente de la variedad de lo que puedes ofrecer.");
        actividades3.add("Si no estás dado de alta ante hacienda aún, no debes preocuparte, pero en caso contrario estamos en un modo alerta!.");
        actividades3.add("Las comunidades empresariales te ayudarán a buscar clientes potenciales, ampliar tus alianzas o aprender de las experiencias de otros.");

        setPreguntas();

        btnSi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSi.setBackgroundColor(Color.GREEN);
                btnNo.setBackgroundColor(Color.LTGRAY);
                btnNoSe.setBackgroundColor(Color.LTGRAY);
                respondida = true;
                if(contador<25){
                    preguntas.get(contador).setTitulo(actividades.get(contador));
                    preguntas.get(contador).setRespuesta(btnSi.getText().toString());
                    preguntas.get(contador).setEstaRespondida(true);
                }
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSi.setBackgroundColor(Color.LTGRAY);
                btnNo.setBackgroundColor(Color.GREEN);
                btnNoSe.setBackgroundColor(Color.LTGRAY);
                respondida = true;
                if(contador<25){
                    preguntas.get(contador).setTitulo(actividades2.get(contador));
                    preguntas.get(contador).setRespuesta(btnNo.getText().toString());
                    preguntas.get(contador).setEstaRespondida(true);
                }
            }
        });

        btnNoSe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnSi.setBackgroundColor(Color.LTGRAY);
                btnNo.setBackgroundColor(Color.LTGRAY);
                btnNoSe.setBackgroundColor(Color.GREEN);
                respondida = true;
                if(contador<25){
                    preguntas.get(contador).setTitulo(actividades3.get(contador));
                    preguntas.get(contador).setRespuesta(btnNoSe.getText().toString());
                    preguntas.get(contador).setEstaRespondida(true);
                }
            }
        });

        btnUno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUno.setBackgroundColor(Color.GREEN);
                btnDos.setBackgroundColor(Color.LTGRAY);
                respondida = true;
                if(contador<25){
                    preguntas.get(contador).setRespuesta(btnUno.getText().toString());
                    preguntas.get(contador).setEstaRespondida(true);
                    preguntas.get(contador).setTitulo(actividades.get(contador));

                }
            }
        });

        btnDos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnUno.setBackgroundColor(Color.LTGRAY);
                btnDos.setBackgroundColor(Color.GREEN);
                respondida = true;
                if(contador<25){
                    preguntas.get(contador).setRespuesta(btnDos.getText().toString());
                    preguntas.get(contador).setEstaRespondida(true);
                    preguntas.get(contador).setTitulo(actividades2.get(contador));
                }
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(respondida == true){

                            if(contador == 24){
                                // *********** Guardamos el dato de la ubicación actual del usuario *************
                                misDatos = getSharedPreferences("misDatos", 0);
                                SharedPreferences.Editor editor = misDatos.edit();

                                DateFormat df = new SimpleDateFormat("dd/MM/yyyy, HH:mm");
                                String date = df.format(Calendar.getInstance().getTime());

                                ArrayList<String[]> respuestas = new ArrayList<>();
                                ArrayList<String[]> respuestasRepresentante = new ArrayList<>();

                                templatePDF = new TemplatePDF(getApplicationContext());
                                templatePDF.openDocument("Diagnostico");
                                templatePDF.addMetaData("Diagnostico Estatus Actual", "Metodo Asesores", "Soluciones Colabora");
                                templatePDF.addImage(getApplicationContext());
                                templatePDF.addSectionsCenter("DATOS DE LA EMPRESA");
                                templatePDF.addParagraph("Las siguientes preguntas que realizó son para tener un diagnostico personalizado sobre el área de mercadotecnia con el fin de ayudarlo a conocer más sobre su negocio. Esto será analizado por consultores expertos. Toda la información proporcionada será unicamente para fines de análisis empresarial y no será publicado o reproducido por algún otro medio. A partir de este momento nos comprometemos a su confidencialidad.");
                                //templatePDF.addTitles("DIAGNÓSTICO: DATOS DE LA EMPRESA", "Las siguientes preguntas que realizará son para tener un diagnostico personalizado sobre el área de mercadotecnia para ayudarlo a conocer más sobre su cliente. Esto será analizado por consultores expertos. Toda la información proporcionada será unicamente para fines de análisis empresarial y no será publicado o reproducido por algún otro medio. A partir de este momento nos comprometemos a su confidencialidad.",date);
                                //templatePDF.addLine();

                                for(int i = 0; i < 25; i ++){

                                    if(preguntas.get(i).getRespuesta().equals("Si")){
                                        calificacion = calificacion + 100;
                                    }
                                    else if(preguntas.get(i).getRespuesta().equals("En proceso")){
                                        calificacion = calificacion + 50;
                                    }
                                    else if(preguntas.get(i).getRespuesta().equals("No")){
                                        calificacion = calificacion + 30;
                                    }

                                    else if(preguntas.get(i).getRespuesta().equals("Idea")){
                                        calificacion = calificacion + 25;
                                    }
                                    else if(preguntas.get(i).getRespuesta().equals("Proyecto")){
                                        calificacion = calificacion + 50;
                                    }
                                    else if(preguntas.get(i).getRespuesta().equals("Empresa")){
                                        calificacion = calificacion + 100;
                                    }



                                    if(preguntas.get(i).getTitulo().length() < 2){
                                    }
                                    else{
                                        respuestas.add(new String[]{preguntas.get(i).getPregunta(), preguntas.get(i).getTitulo()});
                                    }
                                }

                                calificacion = calificacion / 19;

                                respuestasRepresentante.add(new String[]{"Nombre", misDatos.getString("general_nombre", "")});
                                respuestasRepresentante.add(new String[]{"Edad", misDatos.getString("general_edad", "")});
                                respuestasRepresentante.add(new String[]{"Género", misDatos.getString("general_genero", "")});
                                respuestasRepresentante.add(new String[]{"Ocupación", misDatos.getString("general_ocupacion", "")});
                                respuestasRepresentante.add(new String[]{"Teléfono", misDatos.getString("general_telefono", "")});
                                respuestasRepresentante.add(new String[]{"Correo", misDatos.getString("general_correo", "")});
                                respuestasRepresentante.add(new String[]{"Grado académico", misDatos.getString("general_grado_académico", "")});
                                respuestasRepresentante.add(new String[]{"Preocupación 1", misDatos.getString("general_preocupacion_uno", "")});
                                respuestasRepresentante.add(new String[]{"Preocupación 2", misDatos.getString("general_preocupacion_dos", "")});
                                respuestasRepresentante.add(new String[]{"Preocupación 3", misDatos.getString("general_preocupacion_tres", "")});
                                respuestasRepresentante.add(new String[]{"# Integrantes hombres", misDatos.getString("general_hombres", "")});
                                respuestasRepresentante.add(new String[]{"# Integrantes mujeres", misDatos.getString("general_mujeres", "")});
                                respuestasRepresentante.add(new String[]{"# Integrantes hombres IMSS", misDatos.getString("general_hombresIMSS", "")});
                                respuestasRepresentante.add(new String[]{"# Integrantes mujeres IMSS", misDatos.getString("general_mujeresIMSS", "")});
                                respuestasRepresentante.add(new String[]{"# Integrantes hombres Discapacidad", misDatos.getString("general_mujeresDisc", "")});
                                respuestasRepresentante.add(new String[]{"# Integrantes hombres Discapacidad", misDatos.getString("general_mujeresDisc", "")});

                                templatePDF.addSectionsCenter("REPRESENTANTE LEGAL");

                                if(respuestasRepresentante.size() > 0){
                                    // templatePDF.addSectionsCenter("Datos de la empresa");
                                    // templatePDF.addParagraph("Las siguientes preguntas son para determinar un diagnóstico más personalizado sobre los requerimientos que considera que pueda tener en su empresa y las actividades que debe realizar para mejorar la solidez de su startup y encontrar áreas de oportunidad. Esto será analizado por consultores expertos también si ellos lo requieren. Toda la información proporcionada será únicamente para fines de análisis empresarial y no será publicado o reproducido por algún otro medio. A partir de este momento nos comprometemos a su confidencialidad.");
                                    templatePDF.createTableWith2SameLengthcell(headerPreguntas, respuestasRepresentante);
                                }

                                templatePDF.addSectionsCenter("EMPRESA");

                                if(respuestas.size() > 0){
                                   // templatePDF.addSectionsCenter("Datos de la empresa");
                                   // templatePDF.addParagraph("Las siguientes preguntas son para determinar un diagnóstico más personalizado sobre los requerimientos que considera que pueda tener en su empresa y las actividades que debe realizar para mejorar la solidez de su startup y encontrar áreas de oportunidad. Esto será analizado por consultores expertos también si ellos lo requieren. Toda la información proporcionada será únicamente para fines de análisis empresarial y no será publicado o reproducido por algún otro medio. A partir de este momento nos comprometemos a su confidencialidad.");
                                    templatePDF.createTableWith2SameLengthcell(headerPreguntas, respuestas);
                                }

                                templatePDF.addSectionsCenter("RESULTADO FINAL: " + String.valueOf(calificacion) + " puntos.");

                                Toast.makeText(getApplicationContext(), "Diagnóstico completado con éxito", Toast.LENGTH_LONG).show();
                                templatePDF.closeDocument();

                                final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //FirebaseAuth.getInstance().getCurrentUser;
                                UUIDUser = user.getUid();

                                editor.putString(UUIDUser + "diagnosticoDatosGenerales", date);
                                editor.commit();

                                templatePDF.viewPDF2("DiagnosticoGeneral");

                                finish();
                            }
                            else{
                                if(contador == 3){
                                    if(preguntas.get(contador).getRespuesta().equals("En proceso") || preguntas.get(contador).getRespuesta().equals("No")){
                                        contador = contador + 2;
                                    }
                                    else{
                                        contador++;
                                    }
                                }
                                else if(contador == 5){
                                    if(preguntas.get(contador).getRespuesta().equals("En proceso") || preguntas.get(contador).getRespuesta().equals("No")){
                                        contador = 11;
                                    }
                                    else{
                                        contador++;
                                    }
                                }
                                else if(contador == 11){
                                    if(preguntas.get(contador).getRespuesta().equals("En proceso") || preguntas.get(contador).getRespuesta().equals("No")){
                                        contador = contador + 2;
                                    }
                                    else{
                                        contador++;
                                    }
                                }

                                else{
                                    if(contador == 7){
                                        contador = 11;
                                    }
                                    else{
                                        contador++;
                                    }
                                }
                                setPreguntas();
                                btnSi.setBackgroundColor(Color.WHITE);
                                btnNo.setBackgroundColor(Color.WHITE);
                                btnNoSe.setBackgroundColor(Color.WHITE);
                                btnUno.setBackgroundColor(Color.WHITE);
                                btnDos.setBackgroundColor(Color.WHITE);
                                respondida = false;
                            }
                }
                else{
                    Toast.makeText(getApplicationContext(), "Debes responder la pregunta antes de continuar", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void setPreguntas(){
        if(contador<25){

            if(contador == 25){

            }
            else if(contador == 0){
                linearLayoutDos.setVisibility(View.INVISIBLE);
                linearLayoutTres.setVisibility(View.VISIBLE);

                btnSi.setText("Comercio");
                btnNo.setText("Servicio");
                btnNoSe.setText("Producto");

            }
            else if(contador == 1){
                linearLayoutDos.setVisibility(View.INVISIBLE);
                linearLayoutTres.setVisibility(View.VISIBLE);

                btnSi.setText("Idea");
                btnNo.setText("Proyecto");
                btnNoSe.setText("Empresa");
            }
            else if(contador == 2){
                linearLayoutDos.setVisibility(View.INVISIBLE);
                linearLayoutTres.setVisibility(View.VISIBLE);

                btnSi.setText("Menor a 1 año");
                btnNo.setText("1 a 2 años");
                btnNoSe.setText("Más de 2 años");
            }
            else if(contador == 3){
                linearLayoutDos.setVisibility(View.VISIBLE);
                linearLayoutTres.setVisibility(View.INVISIBLE);

                btnUno.setText("Si");
                btnDos.setText("No");
            }
            else if(contador == 4){
                linearLayoutDos.setVisibility(View.INVISIBLE);
                linearLayoutTres.setVisibility(View.VISIBLE);

                btnSi.setText("RIF");
                btnNo.setText("Persona física con actividad empresarial");
                btnNoSe.setText("Empresa moral o asociación civil");
            }
            else if(contador > 4 && contador < 8 || contador > 10 && contador < 25){
                linearLayoutDos.setVisibility(View.INVISIBLE);
                linearLayoutTres.setVisibility(View.VISIBLE);

                btnSi.setText("Si");
                btnNo.setText("En proceso");
                btnNoSe.setText("No");

            }
            else if(contador > 7 && contador < 10){
                linearLayoutDos.setVisibility(View.VISIBLE);
                linearLayoutTres.setVisibility(View.INVISIBLE);

                btnUno.setText("M");
                btnDos.setText("F");

                respondida = true;
            }
            txtArea.setText(preguntas.get(contador).getTitulo());
            txtPregunta.setText(preguntas.get(contador).getPregunta());

            if(contador == 24){
                btnSiguiente.setText("Finalizar");
            }
        }

    }


}

