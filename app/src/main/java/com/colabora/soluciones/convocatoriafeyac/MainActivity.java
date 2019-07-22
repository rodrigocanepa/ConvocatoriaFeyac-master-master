package com.colabora.soluciones.convocatoriafeyac;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.Finanzas.MenuFinanzasActivity;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Cliente;
import com.colabora.soluciones.convocatoriafeyac.Modelos.VerPDFDiagActivity;
import com.colabora.soluciones.convocatoriafeyac.PlanDeNegocios.AdmonPlanActivity;
import com.colabora.soluciones.convocatoriafeyac.PlanDeNegocios.PlanNegociosActivity;
import com.colabora.soluciones.convocatoriafeyac.web.DisenoWebActivity;
import com.colabora.soluciones.convocatoriafeyac.web.MenuPagWebActivity;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.github.barteksc.pdfviewer.util.FileUtils;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView imgTarjetas;
    private ImageView imgCotizaciones;
    private ImageView imgFinanciero;
    private ImageView imgClientes;
    private ImageView imgProveedores;
    private ImageView imgRH;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;

    private String nombreEmpresaMain = "";

    private TextInputEditText editNombreEmpresa;
    private TextInputEditText editNombreAdmin;
    private TextInputEditText editCargoAdmin;
    private TextInputEditText editTelefonoAdmin;
    private ImageView imgLogo;
    private TextView txtDialogFoto;
    private EditText editAdmon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Pyme Assistant");

        imgClientes = (ImageView)findViewById(R.id.imgMainClientes);
        imgCotizaciones = (ImageView)findViewById(R.id.imgMainCotizaciones);
        imgFinanciero = (ImageView)findViewById(R.id.imgMainFinanciero);
        imgProveedores = (ImageView)findViewById(R.id.imgMainProveedor);
        imgRH = (ImageView)findViewById(R.id.imgMainRH);
        imgTarjetas = (ImageView)findViewById(R.id.imgMainTarjetas);

        imgTarjetas.setColorFilter(Color.argb(150,20,20,20), PorterDuff.Mode.DARKEN);
        imgCotizaciones.setColorFilter(Color.argb(150,20,20,20), PorterDuff.Mode.DARKEN);
        imgFinanciero.setColorFilter(Color.argb(150,20,20,20), PorterDuff.Mode.DARKEN);
        imgProveedores.setColorFilter(Color.argb(150,20,20,20), PorterDuff.Mode.DARKEN);
        imgRH.setColorFilter(Color.argb(150,20,20,20), PorterDuff.Mode.DARKEN);
        imgClientes.setColorFilter(Color.argb(150,20,20,20), PorterDuff.Mode.DARKEN);

        progressDialog = new ProgressDialog(MainActivity.this);

        progressDialog.setTitle("Descargando Archivo");
        progressDialog.setMessage("Espere un momento mientras la aplicación descarga el formato de proyección financiera base");


        // Leemos la memoria para ver que tarjetas se han creado
        sharedPreferences = getSharedPreferences("misDatos", 0);
        nombreEmpresaMain = sharedPreferences.getString("nombreEmpresa","");

        if(nombreEmpresaMain.length() == 0){
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            // Get the layout inflater
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View formElementsView = inflater.inflate(R.layout.dialog_perfil,
                    null, false);

            editNombreAdmin = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogPerfilNombreAdmin);
            editCargoAdmin = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogPerfilCargoAdmin);
            editNombreEmpresa = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogPerfilNombreEmpresa);
            editTelefonoAdmin = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogPerfilNumeroAdmin);
            imgLogo = (ImageView) formElementsView.findViewById(R.id.imgDialogLogotipo);
            txtDialogFoto = (TextView) formElementsView.findViewById(R.id.txtDialogLogotipo);

            imgLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PickImageDialog.build(new PickSetup().setTitle("Escoge una opción")
                            .setCameraButtonText("Cámara")
                            .setGalleryButtonText("Galería"))
                            .setOnPickResult(new IPickResult() {
                                @Override
                                public void onPickResult(PickResult r) {
                                    //TODO: do what you have to...
                                    imgLogo.setImageBitmap(r.getBitmap());
                                    txtDialogFoto.setText("Logotipo seleccionado con exito");
                                    txtDialogFoto.setTextColor(Color.rgb(0,100,0));
                                }
                            })
                            .setOnPickCancel(new IPickCancel() {
                                @Override
                                public void onCancelClick() {
                                    //TODO: do what you have to if user clicked cancel
                                }
                            }).show(getSupportFragmentManager());
                }
            });

            builder.setTitle("Mi perfil");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    String nombreEmpresa = editNombreEmpresa.getText().toString();
                    String cargoAdmin = editCargoAdmin.getText().toString();
                    String nombreAdmin = editNombreAdmin.getText().toString();
                    String telefonoAdmin = editTelefonoAdmin.getText().toString();

                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("nombreEmpresa", nombreEmpresa);
                    editor.putString("cargoAdmin", cargoAdmin);
                    editor.putString("nombreAdmin", nombreAdmin);
                    editor.putString("telefonoAdmin", telefonoAdmin);
                    editor.commit();
                    nombreEmpresaMain = sharedPreferences.getString("nombreEmpresa","");
                    // ******************************************************************************

                    File folder = new File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");

                    if(!folder.exists())
                        folder.mkdirs();
                    File file = new File(folder, "logoEmpresa.png");

                    imgLogo.setDrawingCacheEnabled(true);
                    imgLogo.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    //imgToUpload.layout(0, 0, imgToUpload.getMeasuredWidth(), imgToUpload.getMeasuredHeight());
                    imgLogo.buildDrawingCache();
                    Bitmap bitmap = Bitmap.createBitmap(imgLogo.getDrawingCache());

                    if (file.exists ()) file.delete ();
                    try {
                        FileOutputStream out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), "Logo guardado con exito", Toast.LENGTH_SHORT).show();

                }
            });

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(formElementsView);
            // Add action buttons
            builder.create();
            builder.show();
        }

        imgCotizaciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombreEmpresaMain.length() == 0){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Información");
                    builder.setMessage("Debes completar los datos de tu empresa antes de avanzar")
                            .setPositiveButton("Ir a mi perfil", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                                    // Get the layout inflater
                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View formElementsView = inflater.inflate(R.layout.dialog_perfil,
                                            null, false);

                                    editNombreAdmin = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogPerfilNombreAdmin);
                                    editCargoAdmin = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogPerfilCargoAdmin);
                                    editNombreEmpresa = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogPerfilNombreEmpresa);
                                    editTelefonoAdmin = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogPerfilNumeroAdmin);
                                    imgLogo = (ImageView) formElementsView.findViewById(R.id.imgDialogLogotipo);
                                    txtDialogFoto = (TextView) formElementsView.findViewById(R.id.txtDialogLogotipo);

                                    // Leemos la memoria para ver que tarjetas se han creado
                                    sharedPreferences = getSharedPreferences("misDatos", 0);
                                    editNombreAdmin.setText(sharedPreferences.getString("nombreAdmin",""));
                                    editCargoAdmin.setText(sharedPreferences.getString("cargoAdmin",""));
                                    editNombreEmpresa.setText(sharedPreferences.getString("nombreEmpresa",""));
                                    editTelefonoAdmin.setText(sharedPreferences.getString("telefonoAdmin",""));

                                    File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                                    if(!folder.exists())
                                        folder.mkdirs();
                                    File file = new File(folder, "logoEmpresa.png");

                                    if(file.exists()){

                                        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                        imgLogo.setImageBitmap(myBitmap);
                                        txtDialogFoto.setText("Logotipo seleccionado con exito");
                                        txtDialogFoto.setTextColor(Color.rgb(0,100,0));
                                    }


                                    imgLogo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            PickImageDialog.build(new PickSetup().setTitle("Escoge una opción")
                                                    .setCameraButtonText("Cámara")
                                                    .setGalleryButtonText("Galería"))
                                                    .setOnPickResult(new IPickResult() {
                                                        @Override
                                                        public void onPickResult(PickResult r) {
                                                            //TODO: do what you have to...
                                                            imgLogo.setImageBitmap(r.getBitmap());
                                                            txtDialogFoto.setText("Logotipo seleccionado con exito");
                                                            txtDialogFoto.setTextColor(Color.rgb(0,100,0));
                                                        }
                                                    })
                                                    .setOnPickCancel(new IPickCancel() {
                                                        @Override
                                                        public void onCancelClick() {
                                                            //TODO: do what you have to if user clicked cancel
                                                        }
                                                    }).show(getSupportFragmentManager());
                                        }
                                    });

                                    builder.setTitle("Mi perfil");
                                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            String nombreEmpresa = editNombreEmpresa.getText().toString();
                                            String cargoAdmin = editCargoAdmin.getText().toString();
                                            String nombreAdmin = editNombreAdmin.getText().toString();
                                            String telefonoAdmin = editTelefonoAdmin.getText().toString();

                                            // *********** Guardamos los principales datos de los nuevos usuarios *************
                                            sharedPreferences = getSharedPreferences("misDatos", 0);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("nombreEmpresa", nombreEmpresa);
                                            editor.putString("cargoAdmin", cargoAdmin);
                                            editor.putString("nombreAdmin", nombreAdmin);
                                            editor.putString("telefonoAdmin", telefonoAdmin);
                                            editor.commit();
                                            nombreEmpresaMain = sharedPreferences.getString("nombreEmpresa","");
                                            // ******************************************************************************

                                            File folder = new File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");

                                            if(!folder.exists())
                                                folder.mkdirs();
                                            File file = new File(folder, "logoEmpresa.png");

                                            imgLogo.setDrawingCacheEnabled(true);
                                            imgLogo.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                                            //imgToUpload.layout(0, 0, imgToUpload.getMeasuredWidth(), imgToUpload.getMeasuredHeight());
                                            imgLogo.buildDrawingCache();
                                            Bitmap bitmap = Bitmap.createBitmap(imgLogo.getDrawingCache());

                                            if (file.exists ()) file.delete ();
                                            try {
                                                FileOutputStream out = new FileOutputStream(file);
                                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                                                out.flush();
                                                out.close();

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            Toast.makeText(getApplicationContext(), "Logo guardado con exito", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });

                                    // Inflate and set the layout for the dialog
                                    // Pass null as the parent view because its going in the dialog layout
                                    builder.setView(formElementsView);
                                    // Add action buttons
                                    builder.create();
                                    builder.show();

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();
                }
                else{
                    Intent i = new Intent(MainActivity.this, CotizacionesActivity.class);
                    startActivity(i);
                }

            }
        });

        imgTarjetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nombreEmpresaMain.length() == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Información");
                    builder.setMessage("Debes completar los datos de tu empresa antes de avanzar")
                            .setPositiveButton("Ir a mi perfil", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                                    // Get the layout inflater
                                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                    final View formElementsView = inflater.inflate(R.layout.dialog_perfil,
                                            null, false);

                                    editNombreAdmin = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogPerfilNombreAdmin);
                                    editCargoAdmin = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogPerfilCargoAdmin);
                                    editNombreEmpresa = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogPerfilNombreEmpresa);
                                    editTelefonoAdmin = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogPerfilNumeroAdmin);
                                    imgLogo = (ImageView) formElementsView.findViewById(R.id.imgDialogLogotipo);
                                    txtDialogFoto = (TextView) formElementsView.findViewById(R.id.txtDialogLogotipo);

                                    // Leemos la memoria para ver que tarjetas se han creado
                                    sharedPreferences = getSharedPreferences("misDatos", 0);
                                    editNombreAdmin.setText(sharedPreferences.getString("nombreAdmin", ""));
                                    editCargoAdmin.setText(sharedPreferences.getString("cargoAdmin", ""));
                                    editNombreEmpresa.setText(sharedPreferences.getString("nombreEmpresa", ""));
                                    editTelefonoAdmin.setText(sharedPreferences.getString("telefonoAdmin", ""));

                                    File folder = new File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                                    if (!folder.exists())
                                        folder.mkdirs();
                                    File file = new File(folder, "logoEmpresa.png");

                                    if (file.exists()) {

                                        Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                                        imgLogo.setImageBitmap(myBitmap);
                                        txtDialogFoto.setText("Logotipo seleccionado con exito");
                                        txtDialogFoto.setTextColor(Color.rgb(0, 100, 0));
                                    }


                                    imgLogo.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            PickImageDialog.build(new PickSetup().setTitle("Escoge una opción")
                                                    .setCameraButtonText("Cámara")
                                                    .setGalleryButtonText("Galería"))
                                                    .setOnPickResult(new IPickResult() {
                                                        @Override
                                                        public void onPickResult(PickResult r) {
                                                            //TODO: do what you have to...
                                                            imgLogo.setImageBitmap(r.getBitmap());
                                                            txtDialogFoto.setText("Logotipo seleccionado con exito");
                                                            txtDialogFoto.setTextColor(Color.rgb(0, 100, 0));
                                                        }
                                                    })
                                                    .setOnPickCancel(new IPickCancel() {
                                                        @Override
                                                        public void onCancelClick() {
                                                            //TODO: do what you have to if user clicked cancel
                                                        }
                                                    }).show(getSupportFragmentManager());
                                        }
                                    });

                                    builder.setTitle("Mi perfil");
                                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                            String nombreEmpresa = editNombreEmpresa.getText().toString();
                                            String cargoAdmin = editCargoAdmin.getText().toString();
                                            String nombreAdmin = editNombreAdmin.getText().toString();
                                            String telefonoAdmin = editTelefonoAdmin.getText().toString();

                                            // *********** Guardamos los principales datos de los nuevos usuarios *************
                                            sharedPreferences = getSharedPreferences("misDatos", 0);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("nombreEmpresa", nombreEmpresa);
                                            editor.putString("cargoAdmin", cargoAdmin);
                                            editor.putString("nombreAdmin", nombreAdmin);
                                            editor.putString("telefonoAdmin", telefonoAdmin);
                                            editor.commit();
                                            nombreEmpresaMain = sharedPreferences.getString("nombreEmpresa","");
                                            // ******************************************************************************

                                            File folder = new File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");

                                            if (!folder.exists())
                                                folder.mkdirs();
                                            File file = new File(folder, "logoEmpresa.png");

                                            imgLogo.setDrawingCacheEnabled(true);
                                            imgLogo.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                                            //imgToUpload.layout(0, 0, imgToUpload.getMeasuredWidth(), imgToUpload.getMeasuredHeight());
                                            imgLogo.buildDrawingCache();
                                            Bitmap bitmap = Bitmap.createBitmap(imgLogo.getDrawingCache());

                                            if (file.exists()) file.delete();
                                            try {
                                                FileOutputStream out = new FileOutputStream(file);
                                                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                                                out.flush();
                                                out.close();

                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                            Toast.makeText(getApplicationContext(), "Logo guardado con exito", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });

                                    // Inflate and set the layout for the dialog
                                    // Pass null as the parent view because its going in the dialog layout
                                    builder.setView(formElementsView);
                                    // Add action buttons
                                    builder.create();
                                    builder.show();

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();

                }
                else{
                    Intent i = new Intent(MainActivity.this, TarjetasActivity.class);
                    startActivity(i);
                }

            }
        });

        imgFinanciero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*
                Intent i = new Intent(MainActivity.this, MenuFinanzasActivity.class);
                startActivity(i);
                */



                // ***************************** GUARDAMOS LA IMAGEN ***********************
                File folder = new File(Environment.getExternalStorageDirectory().toString(), "Documents");
                if(!folder.exists())
                    folder.mkdirs();
                File file = new File(folder, "finanzas_pyme.xlsx");

                if (!file.exists ()){
                    progressDialog.show();
                    StorageReference storageRef = storage.getReferenceFromUrl("gs://pyme-assistant.appspot.com").child("proyeccion_financiera.xlsx");
                    final long ONE_MEGABYTE = 1024 * 1024;
                    storageRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                            // ***************************** GUARDAMOS LA IMAGEN ***********************
                            File folder = new File(Environment.getExternalStorageDirectory().toString(), "Documents");

                            if(!folder.exists())
                                folder.mkdirs();
                            File file = new File(folder, "finanzas_pyme.xlsx");

                            if (file.exists ()) file.delete ();
                            try{
                                FileOutputStream out = new FileOutputStream(file);
                                out.write(bytes);
                                out.flush();
                                out.close();

                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }
                            }
                            catch (Exception e){
                                e.printStackTrace();
                                if(progressDialog.isShowing()){
                                    progressDialog.dismiss();
                                }

                            }
                            Toast.makeText(getApplicationContext(), "Descargado con exito", Toast.LENGTH_SHORT).show();
                            Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.microsoft.office.excel");
                            if (launchIntent != null) {
                                startActivity(launchIntent);//null pointer check in case package name was not found
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Debes descargar Microsoft Excel en la Playstore para avanzar", Toast.LENGTH_LONG).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    });

                }
                else{
                    Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.microsoft.office.excel");
                    if (launchIntent != null) {
                        startActivity(launchIntent);//null pointer check in case package name was not found
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Debes descargar Microsoft Excel en la Playstore para avanzar", Toast.LENGTH_LONG).show();
                    }
                }


            }
        });

        imgClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, ClientesActivity.class);
                startActivity(i);
            }
        });

        imgProveedores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NominasActivity.class);
                startActivity(i);
            }
        });

        imgRH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "Disponible en próxima actualizaición", Toast.LENGTH_LONG).show();

                //Intent i = new Intent(MainActivity.this, MenuPagWebActivity.class);
                //startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Perfil) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            // Get the layout inflater
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View formElementsView = inflater.inflate(R.layout.dialog_perfil,
                    null, false);

            editNombreAdmin = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogPerfilNombreAdmin);
            editCargoAdmin = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogPerfilCargoAdmin);
            editNombreEmpresa = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogPerfilNombreEmpresa);
            editTelefonoAdmin = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogPerfilNumeroAdmin);
            imgLogo = (ImageView) formElementsView.findViewById(R.id.imgDialogLogotipo);
            txtDialogFoto = (TextView) formElementsView.findViewById(R.id.txtDialogLogotipo);

            // Leemos la memoria para ver que tarjetas se han creado
            sharedPreferences = getSharedPreferences("misDatos", 0);
            editNombreAdmin.setText(sharedPreferences.getString("nombreAdmin",""));
            editCargoAdmin.setText(sharedPreferences.getString("cargoAdmin",""));
            editNombreEmpresa.setText(sharedPreferences.getString("nombreEmpresa",""));
            editTelefonoAdmin.setText(sharedPreferences.getString("telefonoAdmin",""));

            File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
            if(!folder.exists())
                folder.mkdirs();
            File file = new File(folder, "logoEmpresa.png");

            if(file.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
                imgLogo.setImageBitmap(myBitmap);
                txtDialogFoto.setText("Logotipo seleccionado con exito");
                txtDialogFoto.setTextColor(Color.rgb(0,100,0));
            }


            imgLogo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PickImageDialog.build(new PickSetup().setTitle("Escoge una opción")
                            .setCameraButtonText("Cámara")
                            .setGalleryButtonText("Galería"))
                            .setOnPickResult(new IPickResult() {
                                @Override
                                public void onPickResult(PickResult r) {
                                    //TODO: do what you have to...
                                    imgLogo.setImageBitmap(r.getBitmap());
                                    txtDialogFoto.setText("Logotipo seleccionado con exito");
                                    txtDialogFoto.setTextColor(Color.rgb(0,100,0));
                                }
                            })
                            .setOnPickCancel(new IPickCancel() {
                                @Override
                                public void onCancelClick() {
                                    //TODO: do what you have to if user clicked cancel
                                }
                            }).show(getSupportFragmentManager());
                }
            });

            builder.setTitle("Mi perfil");
            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    String nombreEmpresa = editNombreEmpresa.getText().toString();
                    String cargoAdmin = editCargoAdmin.getText().toString();
                    String nombreAdmin = editNombreAdmin.getText().toString();
                    String telefonoAdmin = editTelefonoAdmin.getText().toString();

                    // *********** Guardamos los principales datos de los nuevos usuarios *************
                    sharedPreferences = getSharedPreferences("misDatos", 0);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("nombreEmpresa", nombreEmpresa);
                    editor.putString("cargoAdmin", cargoAdmin);
                    editor.putString("nombreAdmin", nombreAdmin);
                    editor.putString("telefonoAdmin", telefonoAdmin);
                    editor.commit();
                    nombreEmpresaMain = sharedPreferences.getString("nombreEmpresa","");
                    // ******************************************************************************

                    File folder = new File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");

                    if(!folder.exists())
                        folder.mkdirs();
                    File file = new File(folder, "logoEmpresa.png");

                    imgLogo.setDrawingCacheEnabled(true);
                    imgLogo.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                    //imgToUpload.layout(0, 0, imgToUpload.getMeasuredWidth(), imgToUpload.getMeasuredHeight());
                    imgLogo.buildDrawingCache();
                    Bitmap bitmap = Bitmap.createBitmap(imgLogo.getDrawingCache());

                    if (file.exists ()) file.delete ();
                    try {
                        FileOutputStream out = new FileOutputStream(file);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
                        out.flush();
                        out.close();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(getApplicationContext(), "Logo guardado con exito", Toast.LENGTH_SHORT).show();

                }
            });

            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(formElementsView);
            // Add action buttons
            builder.create();
            builder.show();
        }
        else if(id == R.id.action_Cerrar){
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Cerrar sesión");
            builder.setMessage("¿Estas seguro de querer cerrar sesión?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            FirebaseAuth.getInstance().signOut();
                            LoginManager.getInstance().logOut();
                            Intent i = new Intent(MainActivity.this, LoginActivity.class);
                            startActivity(i);
                            finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                        }
                    });
            // Create the AlertDialog object and return it
            builder.create();
            builder.show();
        }

        else if(id == R.id.action_diagnostico){
            Intent i = new Intent(MainActivity.this, DiagnosticosMenuActivity.class);
            startActivity(i);
        }
        else if(id == R.id.action_plan){
            Intent i = new Intent(MainActivity.this, PlanNegociosActivity.class);
            startActivity(i);
        }
        else if(id == R.id.action_administrador){

            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

            // Get the layout inflater
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View formElementsView = inflater.inflate(R.layout.dialog_administrador,
                    null, false);

            editAdmon = (EditText) formElementsView.findViewById(R.id.admonCodigo);

            builder.setTitle("Administrador");

            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    String nombreEmpresa = editAdmon.getText().toString();
                    if(nombreEmpresa.equals("AdmonColaboraTrabajo")){
                        Intent j = new Intent(MainActivity.this, AdmonPlanActivity.class);
                        startActivity(j);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Contraseña incorrecta", Toast.LENGTH_LONG).show();
                    }

                }
            });

            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(formElementsView);
            // Add action buttons
            builder.create();
            builder.show();
        }

        return super.onOptionsItemSelected(item);
    }
}
