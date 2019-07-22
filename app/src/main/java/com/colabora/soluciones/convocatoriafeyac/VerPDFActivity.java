package com.colabora.soluciones.convocatoriafeyac;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.colabora.soluciones.convocatoriafeyac.Db.Querys;
import com.colabora.soluciones.convocatoriafeyac.Modelos.VerPDFDiagActivity;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.File;

public class VerPDFActivity extends AppCompatActivity {

    private PDFView pdfView;
    private File file;
    private String direccion;
    private Button btnEnviarContizacion;
    private Querys querys;

    private boolean bandera = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_pdf);

        pdfView = (PDFView)findViewById(R.id.pdfViewCotizacionPDF);
        btnEnviarContizacion = (Button)findViewById(R.id.btnEnvierCotizacion);

        final Bundle bundle = getIntent().getExtras();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser(); //FirebaseAuth.getInstance().getCurrentUser;
        final String UUIDUser = user.getUid();

        if(bundle != null){
            file = new File((bundle.getString("path","")));
            direccion = bundle.getString("tipo","");
        }

        pdfView.fromFile(file)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .enableDoubletap(true)
                .enableAntialiasing(true)
                .load();

        btnEnviarContizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!direccion.equals("Nomina")){
                    File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                    if(!folder.exists())
                        folder.mkdirs();
                    File file = new File(folder, "Cotizacion_"+ direccion + ".pdf");                    //old way
                    //Uri uri = Uri.fromFile(file);
                    //new way
                    Uri pd = FileProvider.getUriForFile(VerPDFActivity.this, "com.colabora.soluciones.convocatoriafeyac.provider", file);
                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);

                    //intent.setDataAndType(pd,"application/pdf");
                    intent.setType("application/pdf");
                    //String shareBodyText = "Para la mejora continua de mi empresa/negocio he realizado un diagnóstico el cual se comparto a continuación por medio de la aplicación Pyme Assistant";
                    //intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Diágnostico Pyme Assitant");
                    intent.putExtra(android.content.Intent.EXTRA_STREAM, pd);
                    startActivity(Intent.createChooser(intent, "Escoge un método para compartir"));
                }
                else{
                    File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                    if(!folder.exists())
                        folder.mkdirs();
                    File file = new File(folder, "Nomina.pdf");                    //old way
                    //Uri uri = Uri.fromFile(file);
                    //new way
                    Uri pd = FileProvider.getUriForFile(VerPDFActivity.this, "com.colabora.soluciones.convocatoriafeyac.provider", file);
                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);

                    //intent.setDataAndType(pd,"application/pdf");
                    intent.setType("application/pdf");
                    //String shareBodyText = "Para la mejora continua de mi empresa/negocio he realizado un diagnóstico el cual se comparto a continuación por medio de la aplicación Pyme Assistant";
                    //intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Diágnostico Pyme Assitant");
                    intent.putExtra(android.content.Intent.EXTRA_STREAM, pd);
                    startActivity(Intent.createChooser(intent, "Escoge un método para compartir"));
                }

            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        bandera = true;
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(bandera){
            Intent i = new Intent(VerPDFActivity.this, MainActivity.class);
            finish();
            startActivity(i);
        }
    }
}
