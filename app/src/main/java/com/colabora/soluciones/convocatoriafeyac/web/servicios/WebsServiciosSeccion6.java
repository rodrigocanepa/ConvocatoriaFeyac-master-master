package com.colabora.soluciones.convocatoriafeyac.web.servicios;

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

public class WebsServiciosSeccion6 extends AppCompatActivity {


    // *************************** RECYCLER VIEW ************************

    private class DataConfigHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtTitulo;
        private TextView txtDescripcion;
        private ImageView img;
        private Button btnEditar;
        private Button btnEliminar;

        private List<ItemFoto> itemFotos = new ArrayList<ItemFoto>();
        private Context ctx;

        public DataConfigHolder(View itemView, Context ctx, final List<ItemFoto> itemFotos) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.itemFotos = itemFotos;
            this.ctx = ctx;

            txtTitulo = (TextView) itemView.findViewById(R.id.itemSimpleTitulo);
            txtDescripcion = (TextView) itemView.findViewById(R.id.itemSimpleDescripcion);
            btnEditar = (Button)itemView.findViewById(R.id.dialogItemSimpleEditar);
            btnEliminar = (Button)itemView.findViewById(R.id.dialogItemSimpleEliminar);
            //img = (ImageView)itemView.findViewById(R.id.itemFoto);

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    itemFotos.remove(getAdapterPosition());
                    // *********** LLENAMOS EL RECYCLER VIEW *****************************
                    adapter = new WebsServiciosSeccion6.DataConfigAdapter(itemFotos, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            });

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(WebsServiciosSeccion6.this);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View formElementsView = inflater.inflate(R.layout.dialog_item_con_foto,
                            null, false);

                    itemTitulo = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleTitulo);
                    itemDescripcion = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleDescripcion);
                    btnAddFoto = (Button)formElementsView.findViewById(R.id.editItemSimpleAddImagen);
                    imgPortafolio = (ImageView)formElementsView.findViewById(R.id.imgItemSimple);

                    itemTitulo.setText(itemFotos.get(position).getTitulo());
                    itemDescripcion.setText(itemFotos.get(position).getDescripcion());
                    Picasso.get().load(itemFotos.get(position).getUrl()).into(imgPortafolio);


                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    btnAddFoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PickImageDialog.build(new PickSetup().setTitle("Escoge una opción")
                                    .setCameraButtonText("Cámara")
                                    .setGalleryButtonText("Galería"))
                                    .setOnPickResult(new IPickResult() {
                                        @Override
                                        public void onPickResult(PickResult r) {
                                            //TODO: do what you have to...
                                            imgPortafolio.setImageBitmap(r.getBitmap());

                                            progressDialog.show();
                                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                            r.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                            byte[] data = outputStream.toByteArray();

                                            StorageReference refSubirImagen = storage.getReferenceFromUrl("gs://pyme-assistant.appspot.com/web/userid").child(nombre_web + itemFotoList.size() + 5);

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
                                                    Toast.makeText(getApplicationContext(), "¡Foto subida exitosamente!", Toast.LENGTH_LONG).show();

                                                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                                    while(!uri.isComplete());
                                                    Uri url = uri.getResult();
                                                    url_ = url.toString();
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

                    builder.setTitle("Portafolios");
                    builder.setMessage("Por favor, introduce un título y descripción muy breves de las imagenes que conformarán tu portafolios");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String titulo = itemTitulo.getText().toString();
                            String descripcion = itemDescripcion.getText().toString();

                            if(titulo.length() > 0 && descripcion.length() > 0 && url_.length() > 0){
                                itemFotoList.remove(position);
                                ItemFoto itemFoto = new ItemFoto(titulo, descripcion, url_);
                                itemFotoList.add(itemFoto);

                                // *********** LLENAMOS EL RECYCLER VIEW *****************************
                                adapter = new WebsServiciosSeccion6.DataConfigAdapter(itemFotoList, getApplicationContext());
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

        public void bindConfig(final ItemFoto itemFoto) {
            txtTitulo.setText(itemFoto.getTitulo());
            txtDescripcion.setText(itemFoto.getDescripcion());

           // Picasso.get().load(itemFoto.getUrl()).into(img);
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();

        }

    }


    private class DataConfigAdapter extends RecyclerView.Adapter<WebsServiciosSeccion6.DataConfigHolder> {

        private List<ItemFoto> itemFotos;
        Context ctx;

        public DataConfigAdapter(List<ItemFoto> itemFotos, Context ctx ){
            this.itemFotos = itemFotos;
            this.ctx = ctx;
        }

        @Override
        public WebsServiciosSeccion6.DataConfigHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_con_foto, parent, false);
            return new WebsServiciosSeccion6.DataConfigHolder(view, ctx, itemFotos);
        }

        @Override
        public void onBindViewHolder(final WebsServiciosSeccion6.DataConfigHolder holder, final int position) {
            holder.bindConfig(itemFotos.get(position));

        }

        @Override
        public int getItemCount() {
            return itemFotos.size();
        }

    }

    private WebsServiciosSeccion6.DataConfigAdapter adapter;

    // ******************************************************************

    private RecyclerView recyclerView;
    private Button addCaracteristica;
    private String nombre_web;
    private Button btnSiguiente;
    private SharedPreferences sharedPreferences;
    private List<ItemFoto> itemFotoList = new ArrayList<>();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private TextInputEditText itemTitulo;
    private TextInputEditText itemDescripcion;
    private Button btnAddFoto;
    private ImageView imgPortafolio;

    private ProgressDialog progressDialog;
    private String url_ = "";
    private List<caracteristicas_web> imagenes = new ArrayList<>();

    private String servicio_imagen = "";
    private String servicio_titulo = "";
    private String servicio_descripcion = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_servicios_seccion6);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerServiciosSeccion6);
        btnSiguiente = (Button)findViewById(R.id.btnServiciosSeccion6Siguiente);
        addCaracteristica = (Button)findViewById(R.id.btnServiciosSeccion6AddPortafolio);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        sharedPreferences = getSharedPreferences("misDatos", 0);
        nombre_web = sharedPreferences.getString("nombrePagWeb","");

        progressDialog = new ProgressDialog(WebsServiciosSeccion6.this);

        progressDialog.setTitle("Subiendo Información");
        progressDialog.setMessage("Espere un momento mientras el sistema sube su información a la base de datos");


        if(sharedPreferences.getString("web_servicios_seccion_6_recycler", "").equals("1")){
            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            for (int i = 0; i < imagenes.size(); i++){
                itemFotoList.add(new ItemFoto(imagenes.get(i).getTitulo(), imagenes.get(i).getDescripcion(), imagenes.get(i).getImagen()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsServiciosSeccion6.DataConfigAdapter(itemFotoList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
        else if(sharedPreferences.getString("web_servicios_seccion_6_recycler", "").equals("2")){
            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            for (int i = 0; i < imagenes.size(); i++){
                itemFotoList.add(new ItemFoto(imagenes.get(i).getTitulo(), imagenes.get(i).getDescripcion(), imagenes.get(i).getImagen()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsServiciosSeccion6.DataConfigAdapter(itemFotoList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
        else if(sharedPreferences.getString("web_servicios_seccion_6_recycler", "").equals("3")){
            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            for (int i = 0; i < imagenes.size(); i++){
                itemFotoList.add(new ItemFoto(imagenes.get(i).getTitulo(), imagenes.get(i).getDescripcion(), imagenes.get(i).getImagen()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsServiciosSeccion6.DataConfigAdapter(itemFotoList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
        else if(sharedPreferences.getString("web_servicios_seccion_6_recycler", "").equals("4")){
            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            for (int i = 0; i < imagenes.size(); i++){
                itemFotoList.add(new ItemFoto(imagenes.get(i).getTitulo(), imagenes.get(i).getDescripcion(), imagenes.get(i).getImagen()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsServiciosSeccion6.DataConfigAdapter(itemFotoList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
        else if(sharedPreferences.getString("web_servicios_seccion_6_recycler", "").equals("5")){
            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica5_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica5_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica5_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            for (int i = 0; i < imagenes.size(); i++){
                itemFotoList.add(new ItemFoto(imagenes.get(i).getTitulo(), imagenes.get(i).getDescripcion(), imagenes.get(i).getImagen()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsServiciosSeccion6.DataConfigAdapter(itemFotoList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
        else if(sharedPreferences.getString("web_servicios_seccion_6_recycler", "").equals("6")){
            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica1_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica2_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica3_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica4_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica5_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica5_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica5_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            servicio_imagen = sharedPreferences.getString("web_servicios_seccion_6_caracteristica6_url", "");
            servicio_titulo = sharedPreferences.getString("web_servicios_seccion_6_caracteristica6_titulo","");
            servicio_descripcion = sharedPreferences.getString("web_servicios_seccion_6_caracteristica6_descripcion","");

            imagenes.add(new caracteristicas_web(servicio_imagen, servicio_titulo, servicio_descripcion));

            for (int i = 0; i < imagenes.size(); i++){
                itemFotoList.add(new ItemFoto(imagenes.get(i).getTitulo(), imagenes.get(i).getDescripcion(), imagenes.get(i).getImagen()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsServiciosSeccion6.DataConfigAdapter(itemFotoList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }

        addCaracteristica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemFotoList.size() < 6){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(WebsServiciosSeccion6.this);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View formElementsView = inflater.inflate(R.layout.dialog_item_con_foto,
                            null, false);

                    itemTitulo = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleTitulo);
                    itemDescripcion = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleDescripcion);
                    btnAddFoto = (Button)formElementsView.findViewById(R.id.editItemSimpleAddImagen);
                    imgPortafolio = (ImageView)formElementsView.findViewById(R.id.imgItemSimple);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    btnAddFoto.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            PickImageDialog.build(new PickSetup().setTitle("Escoge una opción")
                                    .setCameraButtonText("Cámara")
                                    .setGalleryButtonText("Galería"))
                                    .setOnPickResult(new IPickResult() {
                                        @Override
                                        public void onPickResult(PickResult r) {
                                            //TODO: do what you have to...
                                            imgPortafolio.setImageBitmap(r.getBitmap());
                                            progressDialog.show();

                                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                            r.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                            byte[] data = outputStream.toByteArray();

                                            StorageReference refSubirImagen = storage.getReferenceFromUrl("gs://pyme-assistant.appspot.com/web/userid").child(nombre_web + itemFotoList.size() + 5);

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
                                                    Toast.makeText(getApplicationContext(), "¡Foto subida exitosamente!", Toast.LENGTH_LONG).show();

                                                    Task<Uri> uri = taskSnapshot.getStorage().getDownloadUrl();
                                                    while(!uri.isComplete());
                                                    Uri url = uri.getResult();
                                                    url_ = url.toString();
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

                    builder.setTitle("Portafolios");
                    builder.setMessage("Por favor, introduce un título y descripción muy breves de las imagenes que conformarán tu portafolios");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String titulo = itemTitulo.getText().toString();
                            String descripcion = itemDescripcion.getText().toString();

                            if(titulo.length() > 0 && descripcion.length() > 0 && url_.length() > 0){
                                ItemFoto itemFoto = new ItemFoto(titulo, descripcion, url_);
                                itemFotoList.add(itemFoto);

                                // *********** LLENAMOS EL RECYCLER VIEW *****************************
                                adapter = new WebsServiciosSeccion6.DataConfigAdapter(itemFotoList, getApplicationContext());
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

                if(itemFotoList.size() < 2){
                    Toast.makeText(getApplicationContext(), "Debes introducir por lo menos dos imágenes de tus servicios", Toast.LENGTH_LONG).show();
                    return;
                }

                // *********** Guardamos los principales datos de los nuevos usuarios *************
                sharedPreferences = getSharedPreferences("misDatos", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(itemFotoList.size() == 1){
                    editor.putString("web_servicios_seccion_6_recycler", "1");
                    editor.putString("web_servicios_seccion_6_caracteristica1_titulo", itemFotoList.get(0).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica1_descripcion", itemFotoList.get(0).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica1_url", itemFotoList.get(0).getUrl());
                }
                else if(itemFotoList.size() == 2){
                    editor.putString("web_servicios_seccion_6_recycler", "2");
                    editor.putString("web_servicios_seccion_6_caracteristica1_titulo", itemFotoList.get(0).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica1_descripcion", itemFotoList.get(0).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica1_url", itemFotoList.get(0).getUrl());
                    editor.putString("web_servicios_seccion_6_caracteristica2_titulo", itemFotoList.get(1).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica2_descripcion", itemFotoList.get(1).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica2_url", itemFotoList.get(1).getUrl());
                }
                else if(itemFotoList.size() == 3){
                    editor.putString("web_servicios_seccion_6_recycler", "3");
                    editor.putString("web_servicios_seccion_6_caracteristica1_titulo", itemFotoList.get(0).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica1_descripcion", itemFotoList.get(0).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica1_url", itemFotoList.get(0).getUrl());
                    editor.putString("web_servicios_seccion_6_caracteristica2_titulo", itemFotoList.get(1).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica2_descripcion", itemFotoList.get(1).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica2_url", itemFotoList.get(1).getUrl());
                    editor.putString("web_servicios_seccion_6_caracteristica3_titulo", itemFotoList.get(2).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica3_descripcion", itemFotoList.get(2).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica3_url", itemFotoList.get(2).getUrl());
                }
                else if(itemFotoList.size() == 4){
                    editor.putString("web_servicios_seccion_6_recycler", "4");
                    editor.putString("web_servicios_seccion_6_caracteristica1_titulo", itemFotoList.get(0).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica1_descripcion", itemFotoList.get(0).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica1_url", itemFotoList.get(0).getUrl());
                    editor.putString("web_servicios_seccion_6_caracteristica2_titulo", itemFotoList.get(1).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica2_descripcion", itemFotoList.get(1).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica2_url", itemFotoList.get(1).getUrl());
                    editor.putString("web_servicios_seccion_6_caracteristica3_titulo", itemFotoList.get(2).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica3_descripcion", itemFotoList.get(2).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica3_url", itemFotoList.get(2).getUrl());
                    editor.putString("web_servicios_seccion_6_caracteristica4_titulo", itemFotoList.get(3).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica4_descripcion", itemFotoList.get(3).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica4_url", itemFotoList.get(3).getUrl());
                }
                else if(itemFotoList.size() == 5){
                    editor.putString("web_servicios_seccion_6_recycler", "5");
                    editor.putString("web_servicios_seccion_6_caracteristica1_titulo", itemFotoList.get(0).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica1_descripcion", itemFotoList.get(0).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica1_url", itemFotoList.get(0).getUrl());
                    editor.putString("web_servicios_seccion_6_caracteristica2_titulo", itemFotoList.get(1).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica2_descripcion", itemFotoList.get(1).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica2_url", itemFotoList.get(1).getUrl());
                    editor.putString("web_servicios_seccion_6_caracteristica3_titulo", itemFotoList.get(2).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica3_descripcion", itemFotoList.get(2).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica3_url", itemFotoList.get(2).getUrl());
                    editor.putString("web_servicios_seccion_6_caracteristica4_titulo", itemFotoList.get(2).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica4_descripcion", itemFotoList.get(3).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica4_url", itemFotoList.get(3).getUrl());
                    editor.putString("web_servicios_seccion_6_caracteristica5_titulo", itemFotoList.get(4).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica5_descripcion", itemFotoList.get(4).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica5_url", itemFotoList.get(4).getUrl());
                }
                else if(itemFotoList.size() == 6){
                    editor.putString("web_servicios_seccion_6_recycler", "6");
                    editor.putString("web_servicios_seccion_6_caracteristica1_titulo", itemFotoList.get(0).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica1_descripcion", itemFotoList.get(0).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica1_url", itemFotoList.get(0).getUrl());
                    editor.putString("web_servicios_seccion_6_caracteristica2_titulo", itemFotoList.get(1).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica2_descripcion", itemFotoList.get(1).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica2_url", itemFotoList.get(1).getUrl());
                    editor.putString("web_servicios_seccion_6_caracteristica3_titulo", itemFotoList.get(2).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica3_descripcion", itemFotoList.get(2).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica3_url", itemFotoList.get(2).getUrl());
                    editor.putString("web_servicios_seccion_6_caracteristica4_titulo", itemFotoList.get(2).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica4_descripcion", itemFotoList.get(3).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica4_url", itemFotoList.get(3).getUrl());
                    editor.putString("web_servicios_seccion_6_caracteristica5_titulo", itemFotoList.get(4).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica5_descripcion", itemFotoList.get(4).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica5_url", itemFotoList.get(4).getUrl());
                    editor.putString("web_servicios_seccion_6_caracteristica6_titulo", itemFotoList.get(5).getTitulo());
                    editor.putString("web_servicios_seccion_6_caracteristica6_descripcion", itemFotoList.get(5).getDescripcion());
                    editor.putString("web_servicios_seccion_6_caracteristica6_url", itemFotoList.get(5).getUrl());
                }
                editor.commit();
                // ******************************************************************************

                Intent i = new Intent(WebsServiciosSeccion6.this, WebsServiciosSeccion7.class);
                startActivity(i);
            }
        });

    }
}
