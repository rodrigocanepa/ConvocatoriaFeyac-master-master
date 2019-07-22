package com.colabora.soluciones.convocatoriafeyac;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class TarjetasActivity extends AppCompatActivity {

    private ImageView tarjeta1Back;
    private ImageView tarjeta1Front;
    private TextView txtTitutulo1;
    private TextView txtMensaje1;

    private ImageView tarjeta2Back;
    private ImageView tarjeta2Front;
    private TextView txtTitutulo2;
    private TextView txtMensaje2;

    private ImageView tarjeta3Back;
    private ImageView tarjeta3Front;
    private TextView txtTitutulo3;
    private TextView txtMensaje3;

    private ImageView tarjeta4Back;
    private ImageView tarjeta4Front;
    private TextView txtTitutulo4;
    private TextView txtMensaje4;

    private RelativeLayout relativeLayout1;
    private RelativeLayout relativeLayout2;
    private RelativeLayout relativeLayout3;
    private RelativeLayout relativeLayout4;

    private SharedPreferences sharedPreferences;
    private boolean tarjeta1 = false;
    private boolean tarjeta2 = false;
    private boolean tarjeta3 = false;
    private boolean tarjeta4 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjetas);

        tarjeta1Back = (ImageView)findViewById(R.id.imgTarjeta1Back);
        tarjeta1Front = (ImageView)findViewById(R.id.imgTarjeta1Front);
        txtMensaje1 = (TextView)findViewById(R.id.txtTarjetasNoCreada1);
        txtTitutulo1 = (TextView)findViewById(R.id.txtTarjetasTitutlo1);

        tarjeta2Back = (ImageView)findViewById(R.id.imgTarjeta2Back);
        tarjeta2Front = (ImageView)findViewById(R.id.imgTarjeta2Front);
        txtMensaje2 = (TextView)findViewById(R.id.txtTarjetasNoCreada2);
        txtTitutulo2 = (TextView)findViewById(R.id.txtTarjetasTitutlo2);

        tarjeta3Back = (ImageView)findViewById(R.id.imgTarjeta3Back);
        tarjeta3Front = (ImageView)findViewById(R.id.imgTarjeta3Front);
        txtMensaje3 = (TextView)findViewById(R.id.txtTarjetasNoCreada3);
        txtTitutulo3 = (TextView)findViewById(R.id.txtTarjetasTitutlo3);

        tarjeta4Back = (ImageView)findViewById(R.id.imgTarjeta4Back);
        tarjeta4Front = (ImageView)findViewById(R.id.imgTarjeta4Front);
        txtMensaje4 = (TextView)findViewById(R.id.txtTarjetasNoCreada4);
        txtTitutulo4 = (TextView)findViewById(R.id.txtTarjetasTitutlo4);

        relativeLayout1 = (RelativeLayout)findViewById(R.id.relativeTarjetas1);
        relativeLayout2 = (RelativeLayout)findViewById(R.id.relativeTarjetas2);
        relativeLayout3 = (RelativeLayout)findViewById(R.id.relativeTarjetas3);
        relativeLayout4 = (RelativeLayout)findViewById(R.id.relativeTarjetas4);

        // Leemos la memoria para ver que tarjetas se han creado
        sharedPreferences = getSharedPreferences("misDatos", 0);
        tarjeta1 = sharedPreferences.getBoolean("tarjeta1",false);
        tarjeta2 = sharedPreferences.getBoolean("tarjeta2",false);
        tarjeta3 = sharedPreferences.getBoolean("tarjeta3",false);
        tarjeta4 = sharedPreferences.getBoolean("tarjeta4",false);

        if(tarjeta1){
            txtMensaje1.setVisibility(View.INVISIBLE);

            File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
            if(!folder.exists())
                folder.mkdirs();
            File file = new File(folder, "Tarjeta1_detras.png");

            if(file.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                tarjeta1Back.setImageBitmap(myBitmap);
            }

            File file2 = new File(folder, "Tarjeta1_frente.png");

            if(file2.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(file2.getAbsolutePath());
                tarjeta1Front.setImageBitmap(myBitmap);
            }
        }
        else{
            txtTitutulo1.setVisibility(View.INVISIBLE);
            tarjeta1Front.setVisibility(View.INVISIBLE);
            tarjeta1Back.setVisibility(View.INVISIBLE);

        }

        if(tarjeta2){
            txtMensaje2.setVisibility(View.INVISIBLE);

            File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
            if(!folder.exists())
                folder.mkdirs();
            File file = new File(folder, "Tarjeta2_detras.png");

            if(file.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                tarjeta2Back.setImageBitmap(myBitmap);
            }

            File file2 = new File(folder, "Tarjeta2_frente.png");

            if(file2.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(file2.getAbsolutePath());
                tarjeta2Front.setImageBitmap(myBitmap);
            }
        }
        else{
            txtTitutulo2.setVisibility(View.INVISIBLE);
            tarjeta2Front.setVisibility(View.INVISIBLE);
            tarjeta2Back.setVisibility(View.INVISIBLE);

        }

        if(tarjeta3){
            txtMensaje3.setVisibility(View.INVISIBLE);

            File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
            if(!folder.exists())
                folder.mkdirs();
            File file = new File(folder, "Tarjeta3_detras.png");

            if(file.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                tarjeta3Back.setImageBitmap(myBitmap);
            }

            File file2 = new File(folder, "Tarjeta3_frente.png");

            if(file2.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(file2.getAbsolutePath());
                tarjeta3Front.setImageBitmap(myBitmap);
            }
        }
        else{
            txtTitutulo3.setVisibility(View.INVISIBLE);
            tarjeta3Front.setVisibility(View.INVISIBLE);
            tarjeta3Back.setVisibility(View.INVISIBLE);


        }

        if(tarjeta4){
            txtMensaje4.setVisibility(View.INVISIBLE);

            File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
            if(!folder.exists())
                folder.mkdirs();
            File file = new File(folder, "Tarjeta4_detras.png");

            if(file.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                tarjeta4Back.setImageBitmap(myBitmap);
            }

            File file2 = new File(folder, "Tarjeta4_frente.png");

            if(file2.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(file2.getAbsolutePath());
                tarjeta4Front.setImageBitmap(myBitmap);
            }
        }
        else{
            txtTitutulo4.setVisibility(View.INVISIBLE);
            tarjeta4Front.setVisibility(View.INVISIBLE);
            tarjeta4Back.setVisibility(View.INVISIBLE);


        }

        relativeLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tarjeta1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(TarjetasActivity.this);
                    builder.setTitle("Tarjetas de presentación");
                    builder.setMessage("Seleccione la acción que desee realizar")
                            .setPositiveButton("Compartir", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                                    if(!folder.exists())
                                        folder.mkdirs();
                                    File file = new File(folder, "Tarjeta1_frente.png");
                                    File file2 = new File(folder, "Tarjeta1_detras.png");

                                    ArrayList<Uri> uris = new ArrayList<>();
                                    //new way
                                    Uri pd = FileProvider.getUriForFile(TarjetasActivity.this, "com.colabora.soluciones.convocatoriafeyac.provider", file);
                                    Uri pd2 = FileProvider.getUriForFile(TarjetasActivity.this, "com.colabora.soluciones.convocatoriafeyac.provider", file2);

                                    uris.add(pd);
                                    uris.add(pd2);

                                    Intent intent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);

                                    //intent.setDataAndType(pd,"application/pdf");
                                    intent.setType("image/*");
                                    //String shareBodyText = "Para la mejora continua de mi empresa/negocio he realizado un diagnóstico el cual se comparto a continuación por medio de la aplicación Pyme Assistant";
                                    //intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Diágnostico Pyme Assitant");
                                    intent.putParcelableArrayListExtra(android.content.Intent.EXTRA_STREAM, uris);

                                    startActivity(Intent.createChooser(intent, "Escoge un método para compartir"));
                                }
                            })
                            .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                                    if(!folder.exists())
                                        folder.mkdirs();
                                    File file = new File(folder, "Tarjeta1_frente.png");
                                    File file2 = new File(folder, "Tarjeta1_detras.png");
                                    if (file.exists ()) file.delete ();
                                    if (file2.exists ()) file2.delete ();

                                    Toast.makeText(getApplicationContext(), "Tarjeta de presentación eliminada", Toast.LENGTH_SHORT).show();
                                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                                    sharedPreferences = getSharedPreferences("misDatos", 0);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("tarjeta1", false);
                                    editor.commit();
                                    // ******************************************************************************

                                    Intent t= new Intent(TarjetasActivity.this,TarjetasActivity.class);
                                    startActivity(t);
                                    finish();
                                }
                            });// Create the AlertDialog object and return it
                    builder.create();
                    builder.show();
                }
                else{
                    // Leemos la memoria para ver que tarjetas se han creado
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("numeroTarjeta", "1");
                    editor.commit();

                    Intent i = new Intent(TarjetasActivity.this, TarjetaDatosActivity.class);
                    startActivity(i);
                }
            }
        });

        relativeLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tarjeta2){
                    AlertDialog.Builder builder = new AlertDialog.Builder(TarjetasActivity.this);
                    builder.setTitle("Tarjetas de presentación");
                    builder.setMessage("Seleccione la acción que desee realizar")
                            .setPositiveButton("Compartir", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                                    if(!folder.exists())
                                        folder.mkdirs();
                                    File file = new File(folder, "Tarjeta2_frente.png");
                                    File file2 = new File(folder, "Tarjeta2_detras.png");

                                    ArrayList<Uri> uris = new ArrayList<>();
                                    //new way
                                    Uri pd = FileProvider.getUriForFile(TarjetasActivity.this, "com.colabora.soluciones.convocatoriafeyac.provider", file);
                                    Uri pd2 = FileProvider.getUriForFile(TarjetasActivity.this, "com.colabora.soluciones.convocatoriafeyac.provider", file2);

                                    uris.add(pd);
                                    uris.add(pd2);

                                    Intent intent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);

                                    //intent.setDataAndType(pd,"application/pdf");
                                    intent.setType("image/*");
                                    //String shareBodyText = "Para la mejora continua de mi empresa/negocio he realizado un diagnóstico el cual se comparto a continuación por medio de la aplicación Pyme Assistant";
                                    //intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Diágnostico Pyme Assitant");
                                    intent.putParcelableArrayListExtra(android.content.Intent.EXTRA_STREAM, uris);

                                    startActivity(Intent.createChooser(intent, "Escoge un método para compartir"));
                                }
                            })
                            .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                                    if(!folder.exists())
                                        folder.mkdirs();
                                    File file = new File(folder, "Tarjeta2_frente.png");
                                    File file2 = new File(folder, "Tarjeta2_detras.png");
                                    if (file.exists ()) file.delete ();
                                    if (file2.exists ()) file2.delete ();

                                    Toast.makeText(getApplicationContext(), "Tarjeta de presentación eliminada", Toast.LENGTH_SHORT).show();
                                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                                    sharedPreferences = getSharedPreferences("misDatos", 0);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("tarjeta2", false);
                                    editor.commit();
                                    // ******************************************************************************

                                    Intent t= new Intent(TarjetasActivity.this,TarjetasActivity.class);
                                    startActivity(t);
                                    finish();
                                }
                            });// Create the AlertDialog object and return it
                    builder.create();
                    builder.show();
                }
                else{
                    // Leemos la memoria para ver que tarjetas se han creado
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("numeroTarjeta", "2");
                    editor.commit();

                    Intent i = new Intent(TarjetasActivity.this, TarjetaDatosActivity.class);
                    startActivity(i);
                }
            }
        });

        relativeLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tarjeta3){
                    AlertDialog.Builder builder = new AlertDialog.Builder(TarjetasActivity.this);
                    builder.setTitle("Tarjetas de presentación");
                    builder.setMessage("Seleccione la acción que desee realizar")
                            .setPositiveButton("Compartir", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                                    if(!folder.exists())
                                        folder.mkdirs();
                                    File file = new File(folder, "Tarjeta3_frente.png");
                                    File file2 = new File(folder, "Tarjeta3_detras.png");

                                    ArrayList<Uri> uris = new ArrayList<>();
                                    //new way
                                    Uri pd = FileProvider.getUriForFile(TarjetasActivity.this, "com.colabora.soluciones.convocatoriafeyac.provider", file);
                                    Uri pd2 = FileProvider.getUriForFile(TarjetasActivity.this, "com.colabora.soluciones.convocatoriafeyac.provider", file2);

                                    uris.add(pd);
                                    uris.add(pd2);

                                    Intent intent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);

                                    //intent.setDataAndType(pd,"application/pdf");
                                    intent.setType("image/*");
                                    //String shareBodyText = "Para la mejora continua de mi empresa/negocio he realizado un diagnóstico el cual se comparto a continuación por medio de la aplicación Pyme Assistant";
                                    //intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Diágnostico Pyme Assitant");
                                    intent.putParcelableArrayListExtra(android.content.Intent.EXTRA_STREAM, uris);

                                    startActivity(Intent.createChooser(intent, "Escoge un método para compartir"));
                                }
                            })
                            .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                                    if(!folder.exists())
                                        folder.mkdirs();
                                    File file = new File(folder, "Tarjeta3_frente.png");
                                    File file2 = new File(folder, "Tarjeta3_detras.png");
                                    if (file.exists ()) file.delete ();
                                    if (file2.exists ()) file2.delete ();

                                    Toast.makeText(getApplicationContext(), "Tarjeta de presentación eliminada", Toast.LENGTH_SHORT).show();
                                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                                    sharedPreferences = getSharedPreferences("misDatos", 0);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("tarjeta3", false);
                                    editor.commit();
                                    // ******************************************************************************

                                    Intent t= new Intent(TarjetasActivity.this,TarjetasActivity.class);
                                    startActivity(t);
                                    finish();
                                }
                            });// Create the AlertDialog object and return it
                    builder.create();
                    builder.show();
                }
                else{
                    // Leemos la memoria para ver que tarjetas se han creado
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("numeroTarjeta", "3");
                    editor.commit();

                    Intent i = new Intent(TarjetasActivity.this, TarjetaDatosActivity.class);
                    startActivity(i);
                }
            }
        });

        relativeLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tarjeta4){
                    AlertDialog.Builder builder = new AlertDialog.Builder(TarjetasActivity.this);
                    builder.setTitle("Tarjetas de presentación");
                    builder.setMessage("Seleccione la acción que desee realizar")
                            .setPositiveButton("Compartir", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                                    if(!folder.exists())
                                        folder.mkdirs();
                                    File file = new File(folder, "Tarjeta4_frente.png");
                                    File file2 = new File(folder, "Tarjeta4_detras.png");

                                    ArrayList<Uri> uris = new ArrayList<>();
                                    //new way
                                    Uri pd = FileProvider.getUriForFile(TarjetasActivity.this, "com.colabora.soluciones.convocatoriafeyac.provider", file);
                                    Uri pd2 = FileProvider.getUriForFile(TarjetasActivity.this, "com.colabora.soluciones.convocatoriafeyac.provider", file2);

                                    uris.add(pd);
                                    uris.add(pd2);

                                    Intent intent = new Intent(android.content.Intent.ACTION_SEND_MULTIPLE);

                                    //intent.setDataAndType(pd,"application/pdf");
                                    intent.setType("image/*");
                                    //String shareBodyText = "Para la mejora continua de mi empresa/negocio he realizado un diagnóstico el cual se comparto a continuación por medio de la aplicación Pyme Assistant";
                                    //intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Diágnostico Pyme Assitant");
                                    intent.putParcelableArrayListExtra(android.content.Intent.EXTRA_STREAM, uris);

                                    startActivity(Intent.createChooser(intent, "Escoge un método para compartir"));
                                }
                            })
                            .setNegativeButton("Eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                                    if(!folder.exists())
                                        folder.mkdirs();
                                    File file = new File(folder, "Tarjeta4_frente.png");
                                    File file2 = new File(folder, "Tarjeta4_detras.png");
                                    if (file.exists ()) file.delete ();
                                    if (file2.exists ()) file2.delete ();

                                    Toast.makeText(getApplicationContext(), "Tarjeta de presentación eliminada", Toast.LENGTH_SHORT).show();
                                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                                    sharedPreferences = getSharedPreferences("misDatos", 0);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putBoolean("tarjeta4", false);
                                    editor.commit();
                                    // ******************************************************************************

                                    Intent t= new Intent(TarjetasActivity.this,TarjetasActivity.class);
                                    startActivity(t);
                                    finish();
                                }
                            });// Create the AlertDialog object and return it
                    builder.create();
                    builder.show();
                }
                else{
                    // Leemos la memoria para ver que tarjetas se han creado
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("numeroTarjeta", "4");
                    editor.commit();

                    Intent i = new Intent(TarjetasActivity.this, TarjetaDatosActivity.class);
                    startActivity(i);
                }
            }
        });


    }
}
