package com.colabora.soluciones.convocatoriafeyac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DiagnosticosMenuActivity extends AppCompatActivity {

    private ImageView imgDatosGenerales;
    private ImageView imgEstatusActual;
    private ImageView imgMarketing;
    private ImageView imgProcesos;
    private TextView txtDatosGenerales;
    private TextView txtEstatusActual;
    private TextView txtMarketing;
    private TextView txtProcesos;

    private SharedPreferences sharedPreferences;
    private String UUIDUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosticos_menu);

        imgDatosGenerales = (ImageView)findViewById(R.id.imgStartupDiagnosticoNecesidad);
        imgEstatusActual = (ImageView)findViewById(R.id.imgStartupDiagnosticoEstatus);
        imgMarketing = (ImageView)findViewById(R.id.imgStartupDiagnosticoMarketing);
        imgProcesos = (ImageView)findViewById(R.id.imgStartupDiagnosticoProcesos);
        txtDatosGenerales = (TextView)findViewById(R.id.txtStartupDiagnosticoMainDatosGenerales);
        txtEstatusActual = (TextView)findViewById(R.id.txtStartupDiagnosticoMainEstatus);
        txtMarketing = (TextView)findViewById(R.id.txtStartupDiagnosticoMainMarketing);
        txtProcesos = (TextView)findViewById(R.id.txtStartupDiagnosticoMainProcesos);

        // btnFinalizar = (Button)view.findViewById(R.id.btnStartupMainDiagnosticoFinalizar);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //FirebaseAuth.getInstance().getCurrentUser;
        UUIDUser = user.getUid();

        imgDatosGenerales.setColorFilter(Color.argb(110,10,10,10), PorterDuff.Mode.DARKEN);
        imgEstatusActual.setColorFilter(Color.argb(110,10,10,10), PorterDuff.Mode.DARKEN);
        imgMarketing.setColorFilter(Color.argb(110,10,10,10), PorterDuff.Mode.DARKEN);
        imgProcesos.setColorFilter(Color.argb(110,10,10,10), PorterDuff.Mode.DARKEN);

        sharedPreferences = getSharedPreferences("misDatos", 0);
        final String diagnosticoEstatusActual = sharedPreferences.getString(UUIDUser + "diagnosticoEstatusActual","");
        final String diagnosticoDatosGenerales = sharedPreferences.getString(UUIDUser + "diagnosticoDatosGenerales","");
        final String diagnosticoMarketing = sharedPreferences.getString(UUIDUser + "diagnosticoMarketing","");
        final String diagnosticoProcesos = sharedPreferences.getString(UUIDUser + "diagnosticoProcesos","");

        if(diagnosticoDatosGenerales.length() > 0){
            txtDatosGenerales.setText("Estatus: Respondido (" + diagnosticoDatosGenerales + ")");
        }
        else{
            txtDatosGenerales.setText("Estatus: No respondido");
        }
        if(diagnosticoEstatusActual.length() > 0){
            txtEstatusActual.setText("Estatus: Respondido (" + diagnosticoEstatusActual + ")");
        }
        else{
            txtEstatusActual.setText("Estatus: No respondido");
        }
        if(diagnosticoMarketing.length() > 0){
            txtMarketing.setText("Estatus: Respondido (" + diagnosticoMarketing + ")");
        }
        else{
            txtMarketing.setText("Estatus: No respondido");
        }
        if(diagnosticoProcesos.length() > 0){
            txtProcesos.setText("Estatus: Respondido (" + diagnosticoProcesos + ")");
        }
        else{
            txtProcesos.setText("Estatus: No respondido");
        }

        imgDatosGenerales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DiagnosticosMenuActivity.this, DiagnosticoGeneralActivity.class);
                startActivity(i);
            }
        });

        imgEstatusActual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DiagnosticosMenuActivity.this, RealizarDiagnosticoActivity.class);
                startActivity(i);
            }
        });

        imgMarketing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DiagnosticosMenuActivity.this, ServiciosDiagnosticoActivity.class);
                startActivity(i);
            }
        });

        imgProcesos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DiagnosticosMenuActivity.this, ProductosDiagnosticoActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();

        sharedPreferences = getSharedPreferences("misDatos", 0);
        final String diagnosticoEstatusActual = sharedPreferences.getString(UUIDUser + "diagnosticoEstatusActual","");
        final String diagnosticoDatosGenerales = sharedPreferences.getString(UUIDUser + "diagnosticoDatosGenerales","");
        final String diagnosticoMarketing = sharedPreferences.getString(UUIDUser + "diagnosticoMarketing","");
        final String diagnosticoProcesos = sharedPreferences.getString(UUIDUser + "diagnosticoProcesos","");

        if(diagnosticoDatosGenerales.length() > 0){
            txtDatosGenerales.setText("Estatus: Respondido (" + diagnosticoDatosGenerales + ")");
        }
        else{
            txtDatosGenerales.setText("Estatus: No respondido");
        }
        if(diagnosticoEstatusActual.length() > 0){
            txtEstatusActual.setText("Estatus: Respondido (" + diagnosticoEstatusActual + ")");
        }
        else{
            txtEstatusActual.setText("Estatus: No respondido");
        }
        if(diagnosticoMarketing.length() > 0){
            txtMarketing.setText("Estatus: Respondido (" + diagnosticoMarketing + ")");
        }
        else{
            txtMarketing.setText("Estatus: No respondido");
        }
        if(diagnosticoProcesos.length() > 0){
            txtProcesos.setText("Estatus: Respondido (" + diagnosticoProcesos + ")");
        }
        else{
            txtProcesos.setText("Estatus: No respondido");
        }
    }
}
