package com.colabora.soluciones.convocatoriafeyac.web.moda;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.Modelos.ItemFoto;
import com.colabora.soluciones.convocatoriafeyac.Modelos.caracteristicas_web;
import com.colabora.soluciones.convocatoriafeyac.Modelos.itemSimple;
import com.colabora.soluciones.convocatoriafeyac.R;
import com.colabora.soluciones.convocatoriafeyac.web.servicios.WebsServiciosSeccion4;
import com.colabora.soluciones.convocatoriafeyac.web.servicios.WebsServiciosSeccion5;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.ArrayList;
import java.util.List;

public class WebsModaSeccion4 extends AppCompatActivity {

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
                    adapter = new WebsModaSeccion4.DataConfigAdapter(itemSimpleList, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            });

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();

                    final AlertDialog.Builder builder = new AlertDialog.Builder(WebsModaSeccion4.this);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View formElementsView = inflater.inflate(R.layout.dialog_item_simple,
                            null, false);

                    itemTitulo = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleTitulo);
                    itemDescripcion = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleDescripcion);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    itemTitulo.setText(itemSimpleList.get(position).getTitulo());
                    itemDescripcion.setText(itemSimpleList.get(position).getDescripcion());

                    builder.setTitle("Servicios");
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
                                adapter = new WebsModaSeccion4.DataConfigAdapter(itemSimpleList, getApplicationContext());
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


    private class DataConfigAdapter extends RecyclerView.Adapter<WebsModaSeccion4.DataConfigHolder> {

        private List<itemSimple> itemSimples;
        Context ctx;

        public DataConfigAdapter(List<itemSimple> itemSimples, Context ctx ){
            this.itemSimples = itemSimples;
            this.ctx = ctx;
        }

        @Override
        public WebsModaSeccion4.DataConfigHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_simple, parent, false);
            return new WebsModaSeccion4.DataConfigHolder(view, ctx, itemSimples);
        }

        @Override
        public void onBindViewHolder(final WebsModaSeccion4.DataConfigHolder holder, final int position) {
            holder.bindConfig(itemSimples.get(position));

        }

        @Override
        public int getItemCount() {
            return itemSimples.size();
        }

    }

    private WebsModaSeccion4.DataConfigAdapter adapter;
    private List<itemSimple> itemSimpleList = new ArrayList<itemSimple>();

    // ******************************************************************


    private Button btnSiguiente;
    private Button btnAddServicio;
    private Button btnSubirFoto;
    private ImageView img;
    private RecyclerView recyclerView;
    private SharedPreferences sharedPreferences;
    private String nombre_web = "";
    private TextInputEditText itemTitulo;
    private TextInputEditText itemDescripcion;
    private boolean imgUpoloaded = false;

    FirebaseStorage storage = FirebaseStorage.getInstance();
    private ProgressDialog progressDialog;

    private String img_ = "";
    private String titulo = "";
    private String descripcion = "";

    private List<caracteristicas_web> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_moda_seccion4);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerModaSeccion4);
        btnSiguiente = (Button)findViewById(R.id.btnModaSeccion4Siguiente);
        btnAddServicio = (Button)findViewById(R.id.btnModaSeccion4AddCaracteristica);
        btnSubirFoto = (Button)findViewById(R.id.btnModaSeccion4);
        img = (ImageView)findViewById(R.id.imgModaSeccion4);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        sharedPreferences = getSharedPreferences("misDatos", 0);
        nombre_web = sharedPreferences.getString("nombrePagWeb","");

        progressDialog = new ProgressDialog(WebsModaSeccion4.this);

        progressDialog.setTitle("Subiendo Información");
        progressDialog.setMessage("Espere un momento mientras el sistema sube su información a la base de datos");

        if (sharedPreferences.getString("web_moda_img_seccion_4","").length() > 1){
            imgUpoloaded = true;
            Picasso.get().load(sharedPreferences.getString("web_moda_img_seccion_4","")).into(img);
        }

        if(sharedPreferences.getString("web_servicios_seccion_4_recycler", "").equals("1")){
            img_ = "";
            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            for (int i = 0; i < list.size(); i++){
                itemSimpleList.add(new itemSimple(list.get(i).getTitulo(), list.get(i).getDescripcion()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsModaSeccion4.DataConfigAdapter(itemSimpleList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
        else if(sharedPreferences.getString("web_servicios_seccion_4_recycler", "").equals("2")){
            img_ = "";
            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            for (int i = 0; i < list.size(); i++){
                itemSimpleList.add(new itemSimple(list.get(i).getTitulo(), list.get(i).getDescripcion()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsModaSeccion4.DataConfigAdapter(itemSimpleList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
        else if(sharedPreferences.getString("web_servicios_seccion_4_recycler", "").equals("3")){
            img_ = "";
            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            for (int i = 0; i < list.size(); i++){
                itemSimpleList.add(new itemSimple(list.get(i).getTitulo(), list.get(i).getDescripcion()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsModaSeccion4.DataConfigAdapter(itemSimpleList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
        else if(sharedPreferences.getString("web_servicios_seccion_4_recycler", "").equals("4")){
            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

            list.add(new caracteristicas_web(img_, titulo, descripcion));

            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            for (int i = 0; i < list.size(); i++){
                itemSimpleList.add(new itemSimple(list.get(i).getTitulo(), list.get(i).getDescripcion()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsModaSeccion4.DataConfigAdapter(itemSimpleList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }

        else if(sharedPreferences.getString("web_servicios_seccion_4_recycler", "").equals("5")){
            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica5_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica5_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            for (int i = 0; i < list.size(); i++){
                itemSimpleList.add(new itemSimple(list.get(i).getTitulo(), list.get(i).getDescripcion()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsModaSeccion4.DataConfigAdapter(itemSimpleList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }

        else if(sharedPreferences.getString("web_servicios_seccion_4_recycler", "").equals("6")){
            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica1_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica2_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica3_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica4_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica5_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica5_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            titulo = sharedPreferences.getString("web_servicios_seccion_4_caracteristica6_titulo","");
            descripcion = sharedPreferences.getString("web_servicios_seccion_4_caracteristica6_descripcion","");

            list.add(new caracteristicas_web("", titulo, descripcion));

            for (int i = 0; i < list.size(); i++){
                itemSimpleList.add(new itemSimple(list.get(i).getTitulo(), list.get(i).getDescripcion()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsModaSeccion4.DataConfigAdapter(itemSimpleList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }

        btnSubirFoto.setOnClickListener(new View.OnClickListener() {
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

                                StorageReference refSubirImagen = storage.getReferenceFromUrl("gs://pyme-assistant.appspot.com/web/userid").child(nombre_web + "imagen6");

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
                                        editor.putString("web_moda_img_seccion_4", url.toString());
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


        btnAddServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemSimpleList.size() < 6){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(WebsModaSeccion4.this);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View formElementsView = inflater.inflate(R.layout.dialog_item_simple,
                            null, false);

                    itemTitulo = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleTitulo);
                    itemDescripcion = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleDescripcion);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    builder.setTitle("Nuevo Servicio");
                    builder.setMessage("Por favor, introduce un título y descripción de tus servicios de forma breve");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String titulo = itemTitulo.getText().toString();
                            String descripcion = itemDescripcion.getText().toString();

                            if(titulo.length() > 0 && descripcion.length() > 0){
                                itemSimple itemSimple_ = new itemSimple(titulo, descripcion);
                                itemSimpleList.add(itemSimple_);

                                // *********** LLENAMOS EL RECYCLER VIEW *****************************
                                adapter = new WebsModaSeccion4.DataConfigAdapter(itemSimpleList, getApplicationContext());
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
                    Toast.makeText(getApplicationContext(), "Sólo se permiten máximo 6 servicios", Toast.LENGTH_LONG).show();
                }

            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!imgUpoloaded){
                    Toast.makeText(getApplicationContext(), "Debes subir la imagen que se mostrará en esta sección de servicios", Toast.LENGTH_LONG).show();
                    return;
                }
                if(itemSimpleList.size() < 3){
                    Toast.makeText(getApplicationContext(), "Debes introducir por lo menos tres de tus servicios", Toast.LENGTH_LONG).show();
                    return;
                }

                // *********** Guardamos los principales datos de los nuevos usuarios *************
                sharedPreferences = getSharedPreferences("misDatos", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(itemSimpleList.size() == 1){
                    editor.putString("web_servicios_seccion_4_recycler", "1");
                    editor.putString("web_servicios_seccion_4_caracteristica1_titulo", itemSimpleList.get(0).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", itemSimpleList.get(0).getDescripcion());
                }
                else if(itemSimpleList.size() == 2){
                    editor.putString("web_servicios_seccion_4_recycler", "2");
                    editor.putString("web_servicios_seccion_4_caracteristica1_titulo", itemSimpleList.get(0).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", itemSimpleList.get(0).getDescripcion());
                    editor.putString("web_servicios_seccion_4_caracteristica2_titulo", itemSimpleList.get(1).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica2_descripcion", itemSimpleList.get(1).getDescripcion());
                }
                else if(itemSimpleList.size() == 3){
                    editor.putString("web_servicios_seccion_4_recycler", "3");
                    editor.putString("web_servicios_seccion_4_caracteristica1_titulo", itemSimpleList.get(0).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", itemSimpleList.get(0).getDescripcion());
                    editor.putString("web_servicios_seccion_4_caracteristica2_titulo", itemSimpleList.get(1).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica2_descripcion", itemSimpleList.get(1).getDescripcion());
                    editor.putString("web_servicios_seccion_4_caracteristica3_titulo", itemSimpleList.get(2).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica3_descripcion", itemSimpleList.get(2).getDescripcion());
                }
                else if(itemSimpleList.size() == 4){
                    editor.putString("web_servicios_seccion_4_recycler", "4");
                    editor.putString("web_servicios_seccion_4_caracteristica1_titulo", itemSimpleList.get(0).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", itemSimpleList.get(0).getDescripcion());
                    editor.putString("web_servicios_seccion_4_caracteristica2_titulo", itemSimpleList.get(1).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica2_descripcion", itemSimpleList.get(1).getDescripcion());
                    editor.putString("web_servicios_seccion_4_caracteristica3_titulo", itemSimpleList.get(2).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica3_descripcion", itemSimpleList.get(2).getDescripcion());
                    editor.putString("web_servicios_seccion_4_caracteristica4_titulo", itemSimpleList.get(3).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica4_descripcion", itemSimpleList.get(3).getDescripcion());
                }
                else if(itemSimpleList.size() == 5){
                    editor.putString("web_servicios_seccion_4_recycler", "5");
                    editor.putString("web_servicios_seccion_4_caracteristica1_titulo", itemSimpleList.get(0).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", itemSimpleList.get(0).getDescripcion());
                    editor.putString("web_servicios_seccion_4_caracteristica2_titulo", itemSimpleList.get(1).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica2_descripcion", itemSimpleList.get(1).getDescripcion());
                    editor.putString("web_servicios_seccion_4_caracteristica3_titulo", itemSimpleList.get(2).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica3_descripcion", itemSimpleList.get(2).getDescripcion());
                    editor.putString("web_servicios_seccion_4_caracteristica4_titulo", itemSimpleList.get(3).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica4_descripcion", itemSimpleList.get(3).getDescripcion());
                    editor.putString("web_servicios_seccion_4_caracteristica5_titulo", itemSimpleList.get(4).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica5_descripcion", itemSimpleList.get(4).getDescripcion());
                }
                else if(itemSimpleList.size() == 6){
                    editor.putString("web_servicios_seccion_4_recycler", "6");
                    editor.putString("web_servicios_seccion_4_caracteristica1_titulo", itemSimpleList.get(0).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica1_descripcion", itemSimpleList.get(0).getDescripcion());
                    editor.putString("web_servicios_seccion_4_caracteristica2_titulo", itemSimpleList.get(1).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica2_descripcion", itemSimpleList.get(1).getDescripcion());
                    editor.putString("web_servicios_seccion_4_caracteristica3_titulo", itemSimpleList.get(2).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica3_descripcion", itemSimpleList.get(2).getDescripcion());
                    editor.putString("web_servicios_seccion_4_caracteristica4_titulo", itemSimpleList.get(3).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica4_descripcion", itemSimpleList.get(3).getDescripcion());
                    editor.putString("web_servicios_seccion_4_caracteristica5_titulo", itemSimpleList.get(4).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica5_descripcion", itemSimpleList.get(4).getDescripcion());
                    editor.putString("web_servicios_seccion_4_caracteristica6_titulo", itemSimpleList.get(5).getTitulo());
                    editor.putString("web_servicios_seccion_4_caracteristica6_descripcion", itemSimpleList.get(5).getDescripcion());
                }
                editor.commit();
                // ******************************************************************************

                Intent i = new Intent(WebsModaSeccion4.this, WebsModaSeccion5.class);
                startActivity(i);
            }
        });

    }
}
