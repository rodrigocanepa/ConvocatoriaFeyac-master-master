package com.colabora.soluciones.convocatoriafeyac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.Modelos.VerPDFDiagActivity;

import java.io.File;

public class DiagnosticoActivity extends AppCompatActivity {

    private LinearLayout linearLayout;
    private TextView txtActividades;
    private SharedPreferences sharedPreferences;
    private Button btnDiagnostico;
    private Button btnVerDiagnostico;

    private boolean diagnostico = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnostico);

        txtActividades = (TextView)findViewById(R.id.txtDiagnosticoActividades);
        linearLayout = (LinearLayout)findViewById(R.id.linearDiagnostico);
        btnDiagnostico = (Button)findViewById(R.id.btnRealizarDiagnostico);
        btnVerDiagnostico = (Button)findViewById(R.id.btnVerActividades);

        // Leemos la memoria para ver que tarjetas se han creado
        sharedPreferences = getSharedPreferences("misDatos", 0);
        diagnostico = sharedPreferences.getBoolean("diagnostico",false);

        if(diagnostico){
            btnDiagnostico.setVisibility(View.INVISIBLE);
        }
        else{
            linearLayout.setVisibility(View.INVISIBLE);
        }

        btnDiagnostico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DiagnosticoActivity.this, RealizarDiagnosticoActivity.class);
                startActivity(i);
            }
        });

        btnVerDiagnostico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                if(!folder.exists())
                    folder.mkdirs();
                File file = new File(folder, "Diagnostico.pdf");                    //old wayç
                if(file.exists()){
                    Intent intent = new Intent(getApplicationContext(), VerPDFDiagActivity.class);
                    intent.putExtra("path", file.getAbsolutePath());
                    intent.putExtra("tipo", "Diagnostico");
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Aún no has respondido el diagnóstico", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}
