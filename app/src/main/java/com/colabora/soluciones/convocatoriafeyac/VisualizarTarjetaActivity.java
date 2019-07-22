package com.colabora.soluciones.convocatoriafeyac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class VisualizarTarjetaActivity extends AppCompatActivity {

    private TextView txtNombre1;
    private TextView txtCargo1;
    private TextView txtTelefono1;
    private TextView txtCorreo1;
    private TextView txtPagina1;
    private TextView txtDireccion1;
    private TextView txtFacebook1;
    private TextView txtInstagram1;

    private TextView txtNombre2;
    private TextView txtCargo2;
    private TextView txtTelefono2;
    private TextView txtCorreo2;
    private TextView txtPagina2;
    private TextView txtDireccion2;
    private TextView txtFacebook2;
    private TextView txtInstagram2;

    private TextView txtNombre3;
    private TextView txtCargo3;
    private TextView txtTelefono3;
    private TextView txtCorreo3;
    private TextView txtPagina3;
    private TextView txtDireccion3;
    private TextView txtFacebook3;
    private TextView txtInstagram3;

    private LinearLayout linearLayout1;
    private LinearLayout linearLayout2;
    private LinearLayout linearLayout3;

    private ImageView imgView1;
    private ImageView imgView2;

    private RelativeLayout relativeLayout1;
    private RelativeLayout relativeLayout2;

    private SharedPreferences sharedPreferences;

    private int seleccionEstiloTarjeta;
    private int seleccionTipoLetra;
    private String nombre;
    private String puesto;
    private String numero;
    private String correo;
    private String pagina;
    private String direccion;
    private String facebook;
    private String instagram;

    private Button btnGuardar;
    private Button btnCompartir;


    protected Typeface typeface1;
    protected Typeface typeface2;
    protected Typeface typeface3;
    protected Typeface typeface4;
    protected Typeface typeface5;
    protected Typeface typeface6;

    private LinearLayout linearPagWeb1;
    private LinearLayout linearPagWeb2;
    private LinearLayout linearPagWeb3;

    private LinearLayout linearDireccion1;
    private LinearLayout linearDireccion2;
    private LinearLayout linearDireccion3;

    private LinearLayout linearFacebook1;
    private LinearLayout linearFacebook2;
    private LinearLayout linearFacebook3;

    private LinearLayout linearInstagram1;
    private LinearLayout linearInstagram2;
    private LinearLayout linearInstagram3;

    private ImageView imgLogo;
    private ImageView qr1;
    private ImageView qr2;

    private boolean resume = false;
    public final static int QRcodeWidth = 500 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_tarjeta);

        txtNombre1 = (TextView)findViewById(R.id.txtVisualizarTarjetaNombre1);
        txtNombre2 = (TextView)findViewById(R.id.txtVisualizarTarjetaNombre2);
        txtNombre3 = (TextView)findViewById(R.id.txtVisualizarTarjetaNombre3);

        txtCargo1 = (TextView)findViewById(R.id.txtVisualizarTarjetaCargo1);
        txtCargo2 = (TextView)findViewById(R.id.txtVisualizarTarjetaCargo2);
        txtCargo3 = (TextView)findViewById(R.id.txtVisualizarTarjetaCargo3);

        txtTelefono1 = (TextView)findViewById(R.id.txtVisualizarTarjetaTelefono1);
        txtTelefono2 = (TextView)findViewById(R.id.txtVisualizarTarjetaTelefono2);
        txtTelefono3 = (TextView)findViewById(R.id.txtVisualizarTarjetaTelefono3);

        txtCorreo1 = (TextView)findViewById(R.id.txtVisualizarTarjetaCorreo1);
        txtCorreo2 = (TextView)findViewById(R.id.txtVisualizarTarjetaCorreo2);
        txtCorreo3 = (TextView)findViewById(R.id.txtVisualizarTarjetaCorreo3);

        txtPagina1 = (TextView)findViewById(R.id.txtVisualizarTarjetaPagina1);
        txtPagina2 = (TextView)findViewById(R.id.txtVisualizarTarjetaPagina2);
        txtPagina3 = (TextView)findViewById(R.id.txtVisualizarTarjetaPagina3);

        txtDireccion1 = (TextView)findViewById(R.id.txtVisualizarTarjetaDireccion1);
        txtDireccion2 = (TextView)findViewById(R.id.txtVisualizarTarjetaDireccion2);
        txtDireccion3 = (TextView)findViewById(R.id.txtVisualizarTarjetaDireccion3);

        txtFacebook1 = (TextView)findViewById(R.id.txtVisualizarTarjetaFacebook1);
        txtFacebook2 = (TextView)findViewById(R.id.txtVisualizarTarjetaFacebook2);
        txtFacebook3 = (TextView)findViewById(R.id.txtVisualizarTarjetaFacebook3);

        txtInstagram1 = (TextView)findViewById(R.id.txtVisualizarTarjetaInstragram1);
        txtInstagram2 = (TextView)findViewById(R.id.txtVisualizarTarjetaInstragram2);
        txtInstagram3 = (TextView)findViewById(R.id.txtVisualizarTarjetaInstragram3);

        linearLayout1 = (LinearLayout) findViewById(R.id.linearVisualizacion1);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearVisualizacion2);
        linearLayout3 = (LinearLayout) findViewById(R.id.linearVisualizacion3);

        linearPagWeb1 = (LinearLayout) findViewById(R.id.linearPagWeb1);
        linearPagWeb2 = (LinearLayout) findViewById(R.id.linearPagWeb2);
        linearPagWeb3 = (LinearLayout) findViewById(R.id.linearPagWeb3);

        linearDireccion1 = (LinearLayout) findViewById(R.id.linearDireccion1);
        linearDireccion2 = (LinearLayout) findViewById(R.id.linearDireccion2);
        linearDireccion3 = (LinearLayout) findViewById(R.id.linearDireccion3);

        linearInstagram1 = (LinearLayout) findViewById(R.id.linearInstagram1);
        linearInstagram2 = (LinearLayout) findViewById(R.id.linearInstagram2);
        linearInstagram3 = (LinearLayout) findViewById(R.id.linearInstagram3);

        linearFacebook1 = (LinearLayout) findViewById(R.id.linearFacebook1);
        linearFacebook2 = (LinearLayout) findViewById(R.id.linearFacebook2);
        linearFacebook3 = (LinearLayout) findViewById(R.id.linearFacebook3);

        imgView1 = (ImageView) findViewById(R.id.imgVisualizarTajetaDatos);
        imgView2 = (ImageView) findViewById(R.id.imgVisualizarTarjetaDatos2);

        relativeLayout1 = (RelativeLayout) findViewById(R.id.relativeTarjetaFinalizada1);
        relativeLayout2 =(RelativeLayout) findViewById(R.id.relativeTarjetaFinalizada2);

        btnCompartir = (Button) findViewById(R.id.btnCompartirTarjeta);
        btnGuardar = (Button) findViewById(R.id.btnGuardarTarjeta);
        qr1 = (ImageView) findViewById(R.id.qrTipo1);
        qr2 = (ImageView) findViewById(R.id.qrTipo2);

        qr1.setVisibility(View.INVISIBLE);
        qr2.setVisibility(View.INVISIBLE);

        imgLogo = (ImageView) findViewById(R.id.imgTarjetaLogo);

        File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
        if(!folder.exists())
            folder.mkdirs();
        File file = new File(folder, "logoEmpresa.png");

        if(file.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imgLogo.setImageBitmap(myBitmap);
        }

        typeface1 = Typeface.createFromAsset(getAssets(), "Anton-Regular.ttf");
        typeface2 = Typeface.createFromAsset(getAssets(), "Boogaloo-Regular.ttf");
        typeface3 = Typeface.createFromAsset(getAssets(), "OldStandard-Regular.ttf");
        typeface4 = Typeface.createFromAsset(getAssets(), "PoiretOne-Regular.ttf");
        typeface5 = Typeface.createFromAsset(getAssets(), "Roboto-Regular.ttf");
        typeface6 = Typeface.createFromAsset(getAssets(), "Sacramento-Regular.ttf");

        // Leemos la memoria para ver que tarjetas se han creado
        sharedPreferences = getSharedPreferences("misDatos", 0);
        seleccionEstiloTarjeta = sharedPreferences.getInt("tarjetaEstilo",0);
        seleccionTipoLetra = sharedPreferences.getInt("tarjetaLetra",0);
        nombre = sharedPreferences.getString("tarjetaNombre","");
        puesto = sharedPreferences.getString("tarjetaCargo","");
        numero = sharedPreferences.getString("tarjetaNumero","");
        correo = sharedPreferences.getString("tarjetaCorreo","");
        pagina = sharedPreferences.getString("tarjetaPagina","");
        direccion = sharedPreferences.getString("tarjetaDireccion","");
        facebook = sharedPreferences.getString("tarjetaFacebook","");
        instagram = sharedPreferences.getString("tarjetaInstagram","");

        linearLayout1.setVisibility(View.INVISIBLE);
        linearLayout2.setVisibility(View.INVISIBLE);
        linearLayout3.setVisibility(View.INVISIBLE);

        if(direccion.length() == 0){
            linearDireccion1.setVisibility(View.INVISIBLE);
            linearDireccion2.setVisibility(View.INVISIBLE);
            linearDireccion3.setVisibility(View.INVISIBLE);
        }

        if(facebook.length() == 0){
            linearFacebook1.setVisibility(View.INVISIBLE);
            linearFacebook2.setVisibility(View.INVISIBLE);
            linearFacebook3.setVisibility(View.INVISIBLE);
        }
        if(instagram.length() == 0){
            linearInstagram1.setVisibility(View.INVISIBLE);
            linearInstagram2.setVisibility(View.INVISIBLE);
            linearInstagram3.setVisibility(View.INVISIBLE);
        }
        if(pagina.length() == 0){
            linearPagWeb1.setVisibility(View.INVISIBLE);
            linearPagWeb2.setVisibility(View.INVISIBLE);
            linearPagWeb3.setVisibility(View.INVISIBLE);
        }
        // *************************** Seleccionamos el estilo de letra ***********************************

        if(seleccionTipoLetra == 1){
            txtNombre1.setTypeface(typeface1);
            txtCargo1.setTypeface(typeface1);
            txtTelefono1.setTypeface(typeface1);
            txtCorreo1.setTypeface(typeface1);
            txtPagina1.setTypeface(typeface1);
            txtDireccion1.setTypeface(typeface1);
            txtFacebook1.setTypeface(typeface1);
            txtInstagram1.setTypeface(typeface1);

            txtNombre2.setTypeface(typeface1);
            txtCargo2.setTypeface(typeface1);
            txtTelefono2.setTypeface(typeface1);
            txtCorreo2.setTypeface(typeface1);
            txtPagina2.setTypeface(typeface1);
            txtDireccion2.setTypeface(typeface1);
            txtFacebook2.setTypeface(typeface1);
            txtInstagram2.setTypeface(typeface1);

            txtNombre3.setTypeface(typeface1);
            txtCargo3.setTypeface(typeface1);
            txtTelefono3.setTypeface(typeface1);
            txtCorreo3.setTypeface(typeface1);
            txtPagina3.setTypeface(typeface1);
            txtDireccion3.setTypeface(typeface1);
            txtFacebook3.setTypeface(typeface1);
            txtInstagram3.setTypeface(typeface1);

            // **************************
            txtNombre1.setTextSize(19);
            txtCargo1.setTextSize(15);
            txtTelefono1.setTextSize(15);
            txtCorreo1.setTextSize(15);
            txtPagina1.setTextSize(15);
            txtDireccion1.setTextSize(15);
            txtFacebook1.setTextSize(15);
            txtInstagram1.setTextSize(15);

            txtNombre2.setTextSize(19);
            txtCargo2.setTextSize(15);
            txtTelefono2.setTextSize(15);
            txtCorreo2.setTextSize(15);
            txtPagina2.setTextSize(15);
            txtDireccion2.setTextSize(15);
            txtFacebook2.setTextSize(15);
            txtInstagram2.setTextSize(15);

            txtNombre3.setTextSize(19);
            txtCargo3.setTextSize(15);
            txtTelefono3.setTextSize(15);
            txtCorreo3.setTextSize(15);
            txtPagina3.setTextSize(15);
            txtDireccion3.setTextSize(15);
            txtFacebook3.setTextSize(15);
            txtInstagram3.setTextSize(15);
             // ****************************

        }
        else if (seleccionTipoLetra == 2){
            txtNombre1.setTypeface(typeface2);
            txtCargo1.setTypeface(typeface2);
            txtTelefono1.setTypeface(typeface2);
            txtCorreo1.setTypeface(typeface2);
            txtPagina1.setTypeface(typeface2);
            txtDireccion1.setTypeface(typeface2);
            txtFacebook1.setTypeface(typeface2);
            txtInstagram1.setTypeface(typeface2);

            txtNombre2.setTypeface(typeface2);
            txtCargo2.setTypeface(typeface2);
            txtTelefono2.setTypeface(typeface2);
            txtCorreo2.setTypeface(typeface2);
            txtPagina2.setTypeface(typeface2);
            txtDireccion2.setTypeface(typeface2);
            txtFacebook2.setTypeface(typeface2);
            txtInstagram2.setTypeface(typeface2);

            txtNombre3.setTypeface(typeface2);
            txtCargo3.setTypeface(typeface2);
            txtTelefono3.setTypeface(typeface2);
            txtCorreo3.setTypeface(typeface2);
            txtPagina3.setTypeface(typeface2);
            txtDireccion3.setTypeface(typeface2);
            txtFacebook3.setTypeface(typeface2);
            txtInstagram3.setTypeface(typeface2);
        }
        else if (seleccionTipoLetra == 3){
            txtNombre1.setTypeface(typeface3);
            txtCargo1.setTypeface(typeface3);
            txtTelefono1.setTypeface(typeface3);
            txtCorreo1.setTypeface(typeface3);
            txtPagina1.setTypeface(typeface3);
            txtDireccion1.setTypeface(typeface3);
            txtFacebook1.setTypeface(typeface3);
            txtInstagram1.setTypeface(typeface3);

            txtNombre2.setTypeface(typeface3);
            txtCargo2.setTypeface(typeface3);
            txtTelefono2.setTypeface(typeface3);
            txtCorreo2.setTypeface(typeface3);
            txtPagina2.setTypeface(typeface3);
            txtDireccion2.setTypeface(typeface3);
            txtFacebook2.setTypeface(typeface3);
            txtInstagram2.setTypeface(typeface3);

            txtNombre3.setTypeface(typeface3);
            txtCargo3.setTypeface(typeface3);
            txtTelefono3.setTypeface(typeface3);
            txtCorreo3.setTypeface(typeface3);
            txtPagina3.setTypeface(typeface3);
            txtDireccion3.setTypeface(typeface3);
            txtFacebook3.setTypeface(typeface3);
            txtInstagram3.setTypeface(typeface3);
        }
        else if (seleccionTipoLetra == 4){
            txtNombre1.setTypeface(typeface4);
            txtCargo1.setTypeface(typeface4);
            txtTelefono1.setTypeface(typeface4);
            txtCorreo1.setTypeface(typeface4);
            txtPagina1.setTypeface(typeface4);
            txtDireccion1.setTypeface(typeface4);
            txtFacebook1.setTypeface(typeface4);
            txtInstagram1.setTypeface(typeface4);

            txtNombre2.setTypeface(typeface4);
            txtCargo2.setTypeface(typeface4);
            txtTelefono2.setTypeface(typeface4);
            txtCorreo2.setTypeface(typeface4);
            txtPagina2.setTypeface(typeface4);
            txtDireccion2.setTypeface(typeface4);
            txtFacebook2.setTypeface(typeface4);
            txtInstagram2.setTypeface(typeface4);

            txtNombre3.setTypeface(typeface4);
            txtCargo3.setTypeface(typeface4);
            txtTelefono3.setTypeface(typeface4);
            txtCorreo3.setTypeface(typeface4);
            txtPagina3.setTypeface(typeface4);
            txtDireccion3.setTypeface(typeface4);
            txtFacebook3.setTypeface(typeface4);
            txtInstagram3.setTypeface(typeface4);
        }
        else if (seleccionTipoLetra == 5){
            txtNombre1.setTypeface(typeface5);
            txtCargo1.setTypeface(typeface5);
            txtTelefono1.setTypeface(typeface5);
            txtCorreo1.setTypeface(typeface5);
            txtPagina1.setTypeface(typeface5);
            txtDireccion1.setTypeface(typeface5);
            txtFacebook1.setTypeface(typeface5);
            txtInstagram1.setTypeface(typeface5);

            txtNombre2.setTypeface(typeface5);
            txtCargo2.setTypeface(typeface5);
            txtTelefono2.setTypeface(typeface5);
            txtCorreo2.setTypeface(typeface5);
            txtPagina2.setTypeface(typeface5);
            txtDireccion2.setTypeface(typeface5);
            txtFacebook2.setTypeface(typeface5);
            txtInstagram2.setTypeface(typeface5);

            txtNombre3.setTypeface(typeface5);
            txtCargo3.setTypeface(typeface5);
            txtTelefono3.setTypeface(typeface5);
            txtCorreo3.setTypeface(typeface5);
            txtPagina3.setTypeface(typeface5);
            txtDireccion3.setTypeface(typeface5);
            txtFacebook3.setTypeface(typeface5);
            txtInstagram3.setTypeface(typeface5);
        }
        else if (seleccionTipoLetra == 6){
            txtNombre1.setTypeface(typeface6);
            txtCargo1.setTypeface(typeface6);
            txtTelefono1.setTypeface(typeface6);
            txtCorreo1.setTypeface(typeface6);
            txtPagina1.setTypeface(typeface6);
            txtDireccion1.setTypeface(typeface6);
            txtFacebook1.setTypeface(typeface6);
            txtInstagram1.setTypeface(typeface6);

            txtNombre2.setTypeface(typeface6);
            txtCargo2.setTypeface(typeface6);
            txtTelefono2.setTypeface(typeface6);
            txtCorreo2.setTypeface(typeface6);
            txtPagina2.setTypeface(typeface6);
            txtDireccion2.setTypeface(typeface6);
            txtFacebook2.setTypeface(typeface6);
            txtInstagram2.setTypeface(typeface6);

            txtNombre3.setTypeface(typeface6);
            txtCargo3.setTypeface(typeface6);
            txtTelefono3.setTypeface(typeface6);
            txtCorreo3.setTypeface(typeface6);
            txtPagina3.setTypeface(typeface6);
            txtDireccion3.setTypeface(typeface6);
            txtFacebook3.setTypeface(typeface6);
            txtInstagram3.setTypeface(typeface6);
        }

        // ************************************************************************************************
        if(seleccionEstiloTarjeta == 1){
            linearLayout1.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.detras_1).into(imgView1);
            Picasso.get().load(R.drawable.frente__1).into(imgView2);

            if(pagina.length() > 0){
                try {
                    qr1.setImageBitmap(TextToImageEncode(pagina));
                    qr1.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
            txtNombre1.setText(nombre);
            txtCargo1.setText(puesto);
            txtTelefono1.setText(numero);
            txtCorreo1.setText(correo);
            txtPagina1.setText(pagina);
            txtDireccion1.setText(direccion);
            txtFacebook1.setText(facebook);
            txtInstagram1.setText(instagram);
        }

        if(seleccionEstiloTarjeta == 2){
            linearLayout1.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.detras2).into(imgView1);
            Picasso.get().load(R.drawable.frente2).into(imgView2);

            if(pagina.length() > 0){
                try {
                    qr1.setImageBitmap(TextToImageEncode(pagina));
                    qr1.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

            txtNombre1.setText(nombre);
            txtCargo1.setText(puesto);
            txtTelefono1.setText(numero);
            txtCorreo1.setText(correo);
            txtPagina1.setText(pagina);
            txtDireccion1.setText(direccion);
            txtFacebook1.setText(facebook);
            txtInstagram1.setText(instagram);
        }

        if(seleccionEstiloTarjeta == 3){
            linearLayout2.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.detras3).into(imgView1);
            Picasso.get().load(R.drawable.frente3).into(imgView2);

            if(pagina.length() > 0){
                try {
                    qr1.setImageBitmap(TextToImageEncode(pagina));
                    qr1.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

            txtNombre2.setText(nombre);
            txtCargo2.setText(puesto);
            txtTelefono2.setText(numero);
            txtCorreo2.setText(correo);
            txtPagina2.setText(pagina);
            txtDireccion2.setText(direccion);
            txtFacebook2.setText(facebook);
            txtInstagram2.setText(instagram);
        }

        if(seleccionEstiloTarjeta == 4){
            linearLayout3.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.detras4).into(imgView1);
            Picasso.get().load(R.drawable.frente4).into(imgView2);

            if(pagina.length() > 0){
                try {
                    qr2.setImageBitmap(TextToImageEncode(pagina));
                    qr2.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

            txtNombre3.setText(nombre);
            txtCargo3.setText(puesto);
            txtTelefono3.setText(numero);
            txtCorreo3.setText(correo);
            txtPagina3.setText(pagina);
            txtDireccion3.setText(direccion);
            txtFacebook3.setText(facebook);
            txtInstagram3.setText(instagram);
        }

        if(seleccionEstiloTarjeta == 5){
            linearLayout1.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.detras_5).into(imgView1);
            Picasso.get().load(R.drawable.frente5).into(imgView2);

            if(pagina.length() > 0){
                try {
                    qr1.setImageBitmap(TextToImageEncode(pagina));
                    qr1.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

            txtNombre1.setText(nombre);
            txtCargo1.setText(puesto);
            txtTelefono1.setText(numero);
            txtCorreo1.setText(correo);
            txtPagina1.setText(pagina);
            txtDireccion1.setText(direccion);
            txtFacebook1.setText(facebook);
            txtInstagram1.setText(instagram);
        }

        if(seleccionEstiloTarjeta == 6){
            linearLayout2.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.detras6).into(imgView1);
            Picasso.get().load(R.drawable.frente6).into(imgView2);

            if(pagina.length() > 0){
                try {
                    qr1.setImageBitmap(TextToImageEncode(pagina));
                    qr1.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

            txtNombre2.setText(nombre);
            txtCargo2.setText(puesto);
            txtTelefono2.setText(numero);
            txtCorreo2.setText(correo);
            txtPagina2.setText(pagina);
            txtDireccion2.setText(direccion);
            txtFacebook2.setText(facebook);
            txtInstagram2.setText(instagram);
        }

        if(seleccionEstiloTarjeta == 7){
            linearLayout1.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.detras7).into(imgView1);
            Picasso.get().load(R.drawable.frente__7).into(imgView2);

            if(pagina.length() > 0){
                try {
                    qr1.setImageBitmap(TextToImageEncode(pagina));
                    qr1.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

            txtNombre1.setText(nombre);
            txtCargo1.setText(puesto);
            txtTelefono1.setText(numero);
            txtCorreo1.setText(correo);
            txtPagina1.setText(pagina);
            txtDireccion1.setText(direccion);
            txtFacebook1.setText(facebook);
            txtInstagram1.setText(instagram);
        }

        if(seleccionEstiloTarjeta == 8){
            linearLayout1.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.detras8).into(imgView1);
            Picasso.get().load(R.drawable.frente8).into(imgView2);

            if(pagina.length() > 0){
                try {
                    qr1.setImageBitmap(TextToImageEncode(pagina));
                    qr1.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

            txtNombre1.setText(nombre);
            txtCargo1.setText(puesto);
            txtTelefono1.setText(numero);
            txtCorreo1.setText(correo);
            txtPagina1.setText(pagina);
            txtDireccion1.setText(direccion);
            txtFacebook1.setText(facebook);
            txtInstagram1.setText(instagram);
        }

        if(seleccionEstiloTarjeta == 9){
            linearLayout2.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.detras9).into(imgView1);
            Picasso.get().load(R.drawable.frente9).into(imgView2);

            if(pagina.length() > 0){
                try {
                    qr1.setImageBitmap(TextToImageEncode(pagina));
                    qr1.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

            txtNombre2.setText(nombre);
            txtCargo2.setText(puesto);
            txtTelefono2.setText(numero);
            txtCorreo2.setText(correo);
            txtPagina2.setText(pagina);
            txtDireccion2.setText(direccion);
            txtFacebook2.setText(facebook);
            txtInstagram2.setText(instagram);
        }

        if(seleccionEstiloTarjeta == 10){
            linearLayout3.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.detras10).into(imgView1);
            Picasso.get().load(R.drawable.frente10).into(imgView2);

            if(pagina.length() > 0){
                try {
                    qr2.setImageBitmap(TextToImageEncode(pagina));
                    qr2.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

            txtNombre3.setText(nombre);
            txtCargo3.setText(puesto);
            txtTelefono3.setText(numero);
            txtCorreo3.setText(correo);
            txtPagina3.setText(pagina);
            txtDireccion3.setText(direccion);
            txtFacebook3.setText(facebook);
            txtInstagram3.setText(instagram);
        }

        if(seleccionEstiloTarjeta == 11){
            linearLayout1.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.detras11).into(imgView1);
            Picasso.get().load(R.drawable.frente11).into(imgView2);

            if(pagina.length() > 0){
                try {
                    qr1.setImageBitmap(TextToImageEncode(pagina));
                    qr1.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

            txtNombre1.setText(nombre);
            txtCargo1.setText(puesto);
            txtTelefono1.setText(numero);
            txtCorreo1.setText(correo);
            txtPagina1.setText(pagina);
            txtDireccion1.setText(direccion);
            txtFacebook1.setText(facebook);
            txtInstagram1.setText(instagram);
        }

        if(seleccionEstiloTarjeta == 12){
            linearLayout2.setVisibility(View.VISIBLE);
            Picasso.get().load(R.drawable.detras12).into(imgView1);
            Picasso.get().load(R.drawable.frente12).into(imgView2);

            if(pagina.length() > 0){
                try {
                    qr1.setImageBitmap(TextToImageEncode(pagina));
                    qr1.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }

            txtNombre2.setText(nombre);
            txtCargo2.setText(puesto);
            txtTelefono2.setText(numero);
            txtCorreo2.setText(correo);
            txtPagina2.setText(pagina);
            txtDireccion2.setText(direccion);
            txtFacebook2.setText(facebook);
            txtInstagram2.setText(instagram);
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroTarjeta = sharedPreferences.getString("numeroTarjeta", "");

                File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                if(!folder.exists())
                    folder.mkdirs();
                File file = new File(folder, "Tarjeta" + numeroTarjeta + "_detras.png");                    //old way
                //Uri uri = Uri.fromFile(file);
                if (file.exists ()) file.delete ();
                try {

                    relativeLayout1.setDrawingCacheEnabled(true);
                    relativeLayout1.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    //imgToUpload.layout(0, 0, imgToUpload.getMeasuredWidth(), imgToUpload.getMeasuredHeight());
                    relativeLayout1.buildDrawingCache();
                    Bitmap bitmap = Bitmap.createBitmap(relativeLayout1.getDrawingCache());

                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();

                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("saveTarjetaDetras" + numeroTarjeta, true);
                    editor.putBoolean("tarjeta" + numeroTarjeta, true);
                    editor.commit();
                    // ******************************************************************************

                } catch (Exception e) {
                    e.printStackTrace();
                }


                folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                if(!folder.exists())
                    folder.mkdirs();
                File file2 = new File(folder, "Tarjeta" + numeroTarjeta + "_frente.png");                    //old way
                //Uri uri = Uri.fromFile(file);
                if (file2.exists ()) file2.delete ();
                try {

                    relativeLayout2.setDrawingCacheEnabled(true);
                    relativeLayout2.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    //imgToUpload.layout(0, 0, imgToUpload.getMeasuredWidth(), imgToUpload.getMeasuredHeight());
                    relativeLayout2.buildDrawingCache();
                    Bitmap bitmap = Bitmap.createBitmap(relativeLayout2.getDrawingCache());

                    FileOutputStream out = new FileOutputStream(file2);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();


                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("saveTarjetaFrente" + numeroTarjeta, true);
                    editor.commit();
                    // ******************************************************************************

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(), "Tarjeta guardada con éxito", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(VisualizarTarjetaActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });


        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numeroTarjeta = sharedPreferences.getString("numeroTarjeta", "");

                File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                if(!folder.exists())
                    folder.mkdirs();
                File file = new File(folder, "Tarjeta" + numeroTarjeta + "_detras.png");                    //old way
                //Uri uri = Uri.fromFile(file);
                if (file.exists ()) file.delete ();
                try {

                    relativeLayout1.setDrawingCacheEnabled(true);
                    relativeLayout1.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    //imgToUpload.layout(0, 0, imgToUpload.getMeasuredWidth(), imgToUpload.getMeasuredHeight());
                    relativeLayout1.buildDrawingCache();
                    Bitmap bitmap = Bitmap.createBitmap(relativeLayout1.getDrawingCache());

                    FileOutputStream out = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();

                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("saveTarjetaDetras" + numeroTarjeta, true);
                    editor.putBoolean("tarjeta" + numeroTarjeta, true);
                    editor.commit();
                    // ******************************************************************************

                } catch (Exception e) {
                    e.printStackTrace();
                }


                folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                if(!folder.exists())
                    folder.mkdirs();
                File file2 = new File(folder, "Tarjeta" + numeroTarjeta + "_frente.png");                    //old way
                //Uri uri = Uri.fromFile(file);
                if (file2.exists ()) file2.delete ();
                try {

                    relativeLayout2.setDrawingCacheEnabled(true);
                    relativeLayout2.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    //imgToUpload.layout(0, 0, imgToUpload.getMeasuredWidth(), imgToUpload.getMeasuredHeight());
                    relativeLayout2.buildDrawingCache();
                    Bitmap bitmap = Bitmap.createBitmap(relativeLayout2.getDrawingCache());

                    FileOutputStream out = new FileOutputStream(file2);
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                    out.flush();
                    out.close();


                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("saveTarjetaFrente" + numeroTarjeta, true);
                    editor.commit();
                    // ******************************************************************************

                } catch (Exception e) {
                    e.printStackTrace();
                }

                ArrayList<Uri> uris = new ArrayList<>();
                //new way
                Uri pd = FileProvider.getUriForFile(VisualizarTarjetaActivity.this, "com.colabora.soluciones.convocatoriafeyac.provider", file);
                Uri pd2 = FileProvider.getUriForFile(VisualizarTarjetaActivity.this, "com.colabora.soluciones.convocatoriafeyac.provider", file2);

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
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(resume){
            Intent i = new Intent(VisualizarTarjetaActivity.this, MainActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        resume = true;
    }

    private Bitmap TextToImageEncode(String Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    Value,
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }
}
