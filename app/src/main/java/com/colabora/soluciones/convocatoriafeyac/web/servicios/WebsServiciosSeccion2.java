package com.colabora.soluciones.convocatoriafeyac.web.servicios;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.CotizacionesActivity;
import com.colabora.soluciones.convocatoriafeyac.LoginActivity;
import com.colabora.soluciones.convocatoriafeyac.MainActivity;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Cotizacion;
import com.colabora.soluciones.convocatoriafeyac.Modelos.caracteristicas_web;
import com.colabora.soluciones.convocatoriafeyac.Modelos.itemSimple;
import com.colabora.soluciones.convocatoriafeyac.NuevaCotizacionActivity;
import com.colabora.soluciones.convocatoriafeyac.R;
import com.colabora.soluciones.convocatoriafeyac.VerPDFActivity;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WebsServiciosSeccion2 extends AppCompatActivity {


    // *************************** RECYCLER VIEW ************************

    private class DataConfigHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtTitulo;
        private TextView txtDescripcion;
        private Button btnEditar;
        private Button btnEliminar;

        private List<itemSimple> itemSimples = new ArrayList<itemSimple>();
        private Context ctx;

        public DataConfigHolder(View itemView, Context ctx, final List<itemSimple> itemSimples) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.itemSimples = itemSimples;
            this.ctx = ctx;

            txtTitulo = (TextView) itemView.findViewById(R.id.itemSimpleTitulo);
            txtDescripcion = (TextView) itemView.findViewById(R.id.itemSimpleDescripcion);
            btnEditar = (Button)itemView.findViewById(R.id.dialogItemSimpleEditar);
            btnEliminar = (Button)itemView.findViewById(R.id.dialogItemSimpleEliminar);

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    itemSimpleList.remove(getAdapterPosition());
                    // *********** LLENAMOS EL RECYCLER VIEW *****************************
                    adapter = new WebsServiciosSeccion2.DataConfigAdapter(itemSimpleList, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            });

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();

                    final AlertDialog.Builder builder = new AlertDialog.Builder(WebsServiciosSeccion2.this);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View formElementsView = inflater.inflate(R.layout.dialog_item_simple,
                            null, false);

                    itemTitulo = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleTitulo);
                    itemDescripcion = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleDescripcion);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    itemTitulo.setText(itemSimpleList.get(position).getTitulo());
                    itemDescripcion.setText(itemSimpleList.get(position).getDescripcion());

                    builder.setTitle("Característica");
                    builder.setMessage("Por favor, introduce un título y descripción de las características distintivas de tus servicios");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String titulo = itemTitulo.getText().toString();
                            String descripcion = itemDescripcion.getText().toString();

                            if(titulo.length() > 0 && descripcion.length() > 0){
                                itemSimpleList.remove(position);
                                itemSimple itemSimple_ = new itemSimple(titulo, descripcion);
                                itemSimpleList.add(itemSimple_);

                                // *********** LLENAMOS EL RECYCLER VIEW *****************************
                                adapter = new WebsServiciosSeccion2.DataConfigAdapter(itemSimpleList, getApplicationContext());
                                recyclerView.setAdapter(adapter);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Debes introducir datos válidos", Toast.LENGTH_LONG).show();
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
            });

        }

        public void bindConfig(final itemSimple itemSimple) {
            txtTitulo.setText(itemSimple.getTitulo());
            txtDescripcion.setText(itemSimple.getDescripcion());
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();

        }

    }


    private class DataConfigAdapter extends RecyclerView.Adapter<WebsServiciosSeccion2.DataConfigHolder> {

        private List<itemSimple> itemSimples;
        Context ctx;

        public DataConfigAdapter(List<itemSimple> itemSimples, Context ctx ){
            this.itemSimples = itemSimples;
            this.ctx = ctx;
        }

        @Override
        public WebsServiciosSeccion2.DataConfigHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_simple, parent, false);
            return new WebsServiciosSeccion2.DataConfigHolder(view, ctx, itemSimples);
        }

        @Override
        public void onBindViewHolder(final WebsServiciosSeccion2.DataConfigHolder holder, final int position) {
            holder.bindConfig(itemSimples.get(position));

        }

        @Override
        public int getItemCount() {
            return itemSimples.size();
        }

    }

    private WebsServiciosSeccion2.DataConfigAdapter adapter;

    // ******************************************************************


    private TextInputEditText txtTitulo;
    private TextInputEditText txtDescripcion;
    private TextInputEditText txtDescripcion2;
    private TextInputEditText itemTitulo;
    private TextInputEditText itemDescripcion;
    private Button btnAddImagen;
    private RecyclerView recyclerView;
    private Button addCaracteristica;
    private String nombre_web;
    private ImageView img;
    private Button btnSiguiente;
    private boolean imgUpoloaded = false;
    private List<itemSimple> itemSimpleList = new ArrayList<>();
    private List<caracteristicas_web> caracteristicas = new ArrayList<>();


    FirebaseStorage storage = FirebaseStorage.getInstance();
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;

    private String caracteristicas_titulo = "";
    private String caracteristicas_descripcion = "";
    private String caracteristicas_imagen = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_servicios_seccion2);

        txtTitulo = (TextInputEditText)findViewById(R.id.txtServiciosSeccion2Titulo);
        txtDescripcion = (TextInputEditText)findViewById(R.id.txtServiciosSeccion2Descripcion);
        txtDescripcion2 = (TextInputEditText)findViewById(R.id.txtServiciosSeccion2Descripcion2);
        btnAddImagen = (Button) findViewById(R.id.btnServiciosSeccion2AddImagen);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerServiciosSeccion2);
        addCaracteristica = (Button) findViewById(R.id.btnServiciosSeccion2AddCaracteristica);
        btnSiguiente = (Button) findViewById(R.id.btnServiciosSeccion2Siguiente);
        img = (ImageView)findViewById(R.id.imgServiciosSeccion2);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        sharedPreferences = getSharedPreferences("misDatos", 0);
        nombre_web = sharedPreferences.getString("nombrePagWeb","");

        progressDialog = new ProgressDialog(WebsServiciosSeccion2.this);

        progressDialog.setTitle("Subiendo Información");
        progressDialog.setMessage("Espere un momento mientras el sistema sube su información a la base de datos");

        txtTitulo.setText(sharedPreferences.getString("web_servicios_seccion_2_titulo", ""));
        txtDescripcion.setText(sharedPreferences.getString("web_servicios_seccion_2_descripcion", ""));
        txtDescripcion2.setText(sharedPreferences.getString("web_servicios_seccion_2_descripcion_2", ""));
        if (sharedPreferences.getString("web_servicios_img_seccion_2","").length() > 1){
            imgUpoloaded = true;
            Picasso.get().load(sharedPreferences.getString("web_servicios_img_seccion_2","")).into(img);
        }



        if(sharedPreferences.getString("web_servicios_seccion_2_recycler","").equals("1")){
            caracteristicas_titulo = sharedPreferences.getString("web_servicios_seccion_2_caracteristica1_titulo","");
            caracteristicas_descripcion = sharedPreferences.getString("web_servicios_seccion_2_caracteristica1_descripcion","");
            caracteristicas_imagen = "";

            caracteristicas.add(new caracteristicas_web(caracteristicas_imagen, caracteristicas_titulo, caracteristicas_descripcion));

            for (int i = 0; i < caracteristicas.size(); i++){
                itemSimpleList.add(new itemSimple(caracteristicas.get(i).getTitulo(), caracteristicas.get(i).getDescripcion()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsServiciosSeccion2.DataConfigAdapter(itemSimpleList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
        else if(sharedPreferences.getString("web_servicios_seccion_2_recycler","").equals("2")){
            caracteristicas_titulo = sharedPreferences.getString("web_servicios_seccion_2_caracteristica1_titulo","");
            caracteristicas_descripcion = sharedPreferences.getString("web_servicios_seccion_2_caracteristica1_descripcion","");
            caracteristicas_imagen = "";

            caracteristicas.add(new caracteristicas_web(caracteristicas_imagen, caracteristicas_titulo, caracteristicas_descripcion));

            caracteristicas_titulo = sharedPreferences.getString("web_servicios_seccion_2_caracteristica2_titulo","");
            caracteristicas_descripcion = sharedPreferences.getString("web_servicios_seccion_2_caracteristica2_descripcion","");
            caracteristicas_imagen = "";

            caracteristicas.add(new caracteristicas_web(caracteristicas_imagen, caracteristicas_titulo, caracteristicas_descripcion));

            for (int i = 0; i < caracteristicas.size(); i++){
                itemSimpleList.add(new itemSimple(caracteristicas.get(i).getTitulo(), caracteristicas.get(i).getDescripcion()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsServiciosSeccion2.DataConfigAdapter(itemSimpleList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
        else if(sharedPreferences.getString("web_servicios_seccion_2_recycler","").equals("3")){
            caracteristicas_titulo = sharedPreferences.getString("web_servicios_seccion_2_caracteristica1_titulo","");
            caracteristicas_descripcion = sharedPreferences.getString("web_servicios_seccion_2_caracteristica1_descripcion","");
            caracteristicas_imagen = "";

            caracteristicas.add(new caracteristicas_web(caracteristicas_imagen, caracteristicas_titulo, caracteristicas_descripcion));

            caracteristicas_titulo = sharedPreferences.getString("web_servicios_seccion_2_caracteristica2_titulo","");
            caracteristicas_descripcion = sharedPreferences.getString("web_servicios_seccion_2_caracteristica2_descripcion","");
            caracteristicas_imagen = "";

            caracteristicas.add(new caracteristicas_web(caracteristicas_imagen, caracteristicas_titulo, caracteristicas_descripcion));

            caracteristicas_titulo = sharedPreferences.getString("web_servicios_seccion_2_caracteristica3_titulo","");
            caracteristicas_descripcion = sharedPreferences.getString("web_servicios_seccion_2_caracteristica3_descripcion","");
            caracteristicas_imagen = "";

            caracteristicas.add(new caracteristicas_web(caracteristicas_imagen, caracteristicas_titulo, caracteristicas_descripcion));

            for (int i = 0; i < caracteristicas.size(); i++){
                itemSimpleList.add(new itemSimple(caracteristicas.get(i).getTitulo(), caracteristicas.get(i).getDescripcion()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsServiciosSeccion2.DataConfigAdapter(itemSimpleList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }


        addCaracteristica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemSimpleList.size() < 3){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(WebsServiciosSeccion2.this);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View formElementsView = inflater.inflate(R.layout.dialog_item_simple,
                            null, false);

                    itemTitulo = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleTitulo);
                    itemDescripcion = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleDescripcion);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    builder.setTitle("Nueva Característica");
                    builder.setMessage("Por favor, introduce un título y descripción de las características distintivas de tus servicios");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String titulo = itemTitulo.getText().toString();
                            String descripcion = itemDescripcion.getText().toString();

                            if(titulo.length() > 0 && descripcion.length() > 0){
                                itemSimple itemSimple_ = new itemSimple(titulo, descripcion);
                                itemSimpleList.add(itemSimple_);

                                // *********** LLENAMOS EL RECYCLER VIEW *****************************
                                adapter = new WebsServiciosSeccion2.DataConfigAdapter(itemSimpleList, getApplicationContext());
                                recyclerView.setAdapter(adapter);
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Debes introducir datos válidos", Toast.LENGTH_LONG).show();
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
                else{
                    Toast.makeText(getApplicationContext(), "Sólo se permiten máximo 3 características", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnAddImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickImageDialog.build(new PickSetup().setTitle("Escoge una opción")
                        .setCameraButtonText("Cámara")
                        .setGalleryButtonText("Galería"))
                        .setOnPickResult(new IPickResult() {
                            @Override
                            public void onPickResult(PickResult r) {
                                //TODO: do what you have to...
                                img.setImageBitmap(r.getBitmap());
                                progressDialog.show();

                                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                r.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                byte[] data = outputStream.toByteArray();

                                StorageReference refSubirImagen = storage.getReferenceFromUrl("gs://pyme-assistant.appspot.com/web/userid").child(nombre_web + "imagen2");

                                UploadTask uploadTask = refSubirImagen.putBytes(data);
                                uploadTask.addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        if (progressDialog.isShowing()){
                                            progressDialog.dismiss();
                                        }
                                        Toast.makeText(getApplicationContext(), "Ha ocurrido un error, por favor vuelva a intentarlo", Toast.LENGTH_LONG).show();
                                    }
                                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        if (progressDialog.isShowing()){
                                            progressDialog.dismiss();
                                        }
                                        Toast.makeText(getApplicationContext(), "Imagen subida exitosamente", Toast.LENGTH_LONG).show();
                                        Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                        while(!uri.isComplete());
                                        Uri url = uri.getResult();
                                        // *********** Guardamos los principales datos de los nuevos usuarios *************
                                        sharedPreferences = getSharedPreferences("misDatos", 0);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("web_servicios_img_seccion_2", url.toString());
                                        editor.commit();
                                        imgUpoloaded = true;
                                        // ******************************************************************************
                                    }
                                });

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


        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = txtTitulo.getText().toString();
                String descripcion = txtDescripcion.getText().toString();
                String descripcion2 = txtDescripcion2.getText().toString();

                if(titulo.length() == 0){
                    Toast.makeText(getApplicationContext(), "Debes introducir el título de esta sección", Toast.LENGTH_LONG).show();
                    return;
                }

                if(descripcion.length() == 0){
                    Toast.makeText(getApplicationContext(), "Debes introducir la descripción de esta sección", Toast.LENGTH_LONG).show();
                    return;
                }

                if(descripcion2.length() == 0){
                    Toast.makeText(getApplicationContext(), "Debes introducir la descripción complementaria de esta sección", Toast.LENGTH_LONG).show();
                    return;
                }

                if(itemSimpleList.size() < 1){
                    Toast.makeText(getApplicationContext(), "Debes introducir por lo menos una característica de tus servicios", Toast.LENGTH_LONG).show();
                    return;
                }
                if(!imgUpoloaded){
                    Toast.makeText(getApplicationContext(), "Debes subir la imagen correspondiente a esta sección", Toast.LENGTH_LONG).show();
                    return;
                }

                // *********** Guardamos los principales datos de los nuevos usuarios *************
                sharedPreferences = getSharedPreferences("misDatos", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("web_servicios_seccion_2_titulo", titulo);
                editor.putString("web_servicios_seccion_2_descripcion", descripcion);
                editor.putString("web_servicios_seccion_2_descripcion_2", descripcion2);

                if(itemSimpleList.size() == 1){
                    editor.putString("web_servicios_seccion_2_recycler", "1");
                    editor.putString("web_servicios_seccion_2_caracteristica1_titulo", itemSimpleList.get(0).getTitulo());
                    editor.putString("web_servicios_seccion_2_caracteristica1_descripcion", itemSimpleList.get(0).getDescripcion());
                }
                else if(itemSimpleList.size() == 2){
                    editor.putString("web_servicios_seccion_2_recycler", "2");
                    editor.putString("web_servicios_seccion_2_caracteristica1_titulo", itemSimpleList.get(0).getTitulo());
                    editor.putString("web_servicios_seccion_2_caracteristica1_descripcion", itemSimpleList.get(0).getDescripcion());
                    editor.putString("web_servicios_seccion_2_caracteristica2_titulo", itemSimpleList.get(1).getTitulo());
                    editor.putString("web_servicios_seccion_2_caracteristica2_descripcion", itemSimpleList.get(1).getDescripcion());
                }
                else if(itemSimpleList.size() == 3){
                    editor.putString("web_servicios_seccion_2_recycler", "3");
                    editor.putString("web_servicios_seccion_2_caracteristica1_titulo", itemSimpleList.get(0).getTitulo());
                    editor.putString("web_servicios_seccion_2_caracteristica1_descripcion", itemSimpleList.get(0).getDescripcion());
                    editor.putString("web_servicios_seccion_2_caracteristica2_titulo", itemSimpleList.get(1).getTitulo());
                    editor.putString("web_servicios_seccion_2_caracteristica2_descripcion", itemSimpleList.get(1).getDescripcion());
                    editor.putString("web_servicios_seccion_2_caracteristica3_titulo", itemSimpleList.get(2).getTitulo());
                    editor.putString("web_servicios_seccion_2_caracteristica3_descripcion", itemSimpleList.get(2).getDescripcion());
                }
                editor.commit();
                // ******************************************************************************

                Intent i = new Intent(WebsServiciosSeccion2.this, WebsServiciosSeccion3.class);
                startActivity(i);
            }
        });


    }
}
