package com.colabora.soluciones.convocatoriafeyac.web.comida;

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

import com.colabora.soluciones.convocatoriafeyac.Modelos.EspecialidadesComida;
import com.colabora.soluciones.convocatoriafeyac.Modelos.ItemFoto;
import com.colabora.soluciones.convocatoriafeyac.Modelos.itemSimple;
import com.colabora.soluciones.convocatoriafeyac.R;
import com.colabora.soluciones.convocatoriafeyac.web.moda.WebsModaSeccion4;
import com.colabora.soluciones.convocatoriafeyac.web.productos.WebsProductosSeccion5;
import com.colabora.soluciones.convocatoriafeyac.web.productos.WebsProductosSeccion6;
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

public class WebsComidaSeccion3 extends AppCompatActivity {

    // *************************** RECYCLER VIEW ************************

    private class DataConfigHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtTitulo;
        private TextView txtDescripcion;
        private ImageView img;
        private Button btnEditar;
        private Button btnEliminar;

        private List<EspecialidadesComida> especialidadesComidas = new ArrayList<EspecialidadesComida>();
        private Context ctx;

        public DataConfigHolder(View itemView, Context ctx, final List<EspecialidadesComida> especialidadesComidas) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.especialidadesComidas = especialidadesComidas;
            this.ctx = ctx;

            txtTitulo = (TextView) itemView.findViewById(R.id.itemSimpleTitulo);
            txtDescripcion = (TextView) itemView.findViewById(R.id.itemSimpleDescripcion);
            btnEditar = (Button)itemView.findViewById(R.id.dialogItemSimpleEditar);
            btnEliminar = (Button)itemView.findViewById(R.id.dialogItemSimpleEliminar);
            //img = (ImageView)itemView.findViewById(R.id.itemFoto);

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    especialidadesComidas.remove(getAdapterPosition());
                    // *********** LLENAMOS EL RECYCLER VIEW *****************************
                    adapter = new WebsComidaSeccion3.DataConfigAdapter(especialidadesComidas, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            });

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(WebsComidaSeccion3.this);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View formElementsView = inflater.inflate(R.layout.dialog_especialidad_comida,
                            null, false);

                    itemTitulo = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleTitulo);
                    itemDescripcion = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleDescripcion);
                    itemPrecio = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimplePrecio);
                    btnAddFoto = (Button)formElementsView.findViewById(R.id.editItemSimpleAddImagen);
                    imgPortafolio = (ImageView)formElementsView.findViewById(R.id.imgItemSimple);

                    itemTitulo.setText(especialidadesComidas.get(position).getTitulo());
                    itemDescripcion.setText(especialidadesComidas.get(position).getDescripcion());
                    itemPrecio.setText(String.valueOf(especialidadesComidas.get(position).getPrecio()));

                    Picasso.get().load(especialidadesComidas.get(position).getImg()).into(imgPortafolio);

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

                                            StorageReference refSubirImagen = storage.getReferenceFromUrl("gs://pyme-assistant.appspot.com/web/userid").child(nombre_web + especialidadesComidas.size() + 4);

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

                    builder.setTitle("Especialidades");
                    builder.setMessage("Por favor, introduce un título, descripción y precio de tus principales especialidades");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String titulo = itemTitulo.getText().toString();
                            String descripcion = itemDescripcion.getText().toString();
                            int precio = Integer.valueOf(itemPrecio.getText().toString());

                            if(titulo.length() > 0 && descripcion.length() > 0 && url_.length() > 0){
                                especialidadesComidas.remove(position);
                                EspecialidadesComida especialidadesComida = new EspecialidadesComida(url_, titulo, precio, descripcion);
                                especialidadesComidas.add(especialidadesComida);

                                // *********** LLENAMOS EL RECYCLER VIEW *****************************
                                adapter = new WebsComidaSeccion3.DataConfigAdapter(especialidadesComidas, getApplicationContext());
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

        public void bindConfig(final EspecialidadesComida especialidadesComida) {
            txtTitulo.setText(especialidadesComida.getTitulo());
            txtDescripcion.setText(especialidadesComida.getDescripcion() + "\n" + String.valueOf(especialidadesComida.getPrecio()));

            // Picasso.get().load(itemFoto.getUrl()).into(img);
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();

        }

    }


    private class DataConfigAdapter extends RecyclerView.Adapter<WebsComidaSeccion3.DataConfigHolder> {

        private List<EspecialidadesComida> especialidadesComidas;
        Context ctx;

        public DataConfigAdapter(List<EspecialidadesComida> especialidadesComidas, Context ctx ){
            this.especialidadesComidas = especialidadesComidas;
            this.ctx = ctx;
        }

        @Override
        public WebsComidaSeccion3.DataConfigHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_con_foto, parent, false);
            return new WebsComidaSeccion3.DataConfigHolder(view, ctx, especialidadesComidas);
        }

        @Override
        public void onBindViewHolder(final WebsComidaSeccion3.DataConfigHolder holder, final int position) {
            holder.bindConfig(especialidadesComidas.get(position));

        }

        @Override
        public int getItemCount() {
            return especialidadesComidas.size();
        }

    }

    private WebsComidaSeccion3.DataConfigAdapter adapter;

    // ******************************************************************

    private RecyclerView recyclerView;
    private Button addCaracteristica;
    private String nombre_web;
    private Button btnSiguiente;
    private SharedPreferences sharedPreferences;
    private List<EspecialidadesComida> especialidadesComidas = new ArrayList<>();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    private TextInputEditText itemTitulo;
    private TextInputEditText itemDescripcion;
    private TextInputEditText itemPrecio;
    private Button btnAddFoto;
    private ImageView imgPortafolio;

    private ProgressDialog progressDialog;
    private String url_ = "";
    private String specials_img = "";
    private String specials_titulo = "";
    private String specials_descripcion = "";
    private int specials_precio = 0;
    private List<EspecialidadesComida> items = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_comida_seccion3);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerComidaSeccion3);
        btnSiguiente = (Button)findViewById(R.id.btnComidaSeccion3Siguiente);
        addCaracteristica = (Button)findViewById(R.id.btnComidaSeccion3AddCaracteristica);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        sharedPreferences = getSharedPreferences("misDatos", 0);
        nombre_web = sharedPreferences.getString("nombrePagWeb","");

        progressDialog = new ProgressDialog(WebsComidaSeccion3.this);

        progressDialog.setTitle("Subiendo Información");
        progressDialog.setMessage("Espere un momento mientras el sistema sube su información a la base de datos");

        if(sharedPreferences.getString("web_comida_seccion_3_recycler", "").equals("1")){
            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica1_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            especialidadesComidas = items;
            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsComidaSeccion3.DataConfigAdapter(especialidadesComidas, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
        else if(sharedPreferences.getString("web_comida_seccion_3_recycler", "").equals("2")){
            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica1_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica2_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            especialidadesComidas = items;
            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsComidaSeccion3.DataConfigAdapter(especialidadesComidas, getApplicationContext());
            recyclerView.setAdapter(adapter);

        }

        else if(sharedPreferences.getString("web_comida_seccion_3_recycler", "").equals("3")){
            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica1_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica2_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica3_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            especialidadesComidas = items;
            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsComidaSeccion3.DataConfigAdapter(especialidadesComidas, getApplicationContext());
            recyclerView.setAdapter(adapter);

        }

        else if(sharedPreferences.getString("web_comida_seccion_3_recycler", "").equals("4")){
            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica1_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica2_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica3_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica4_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            especialidadesComidas = items;
            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsComidaSeccion3.DataConfigAdapter(especialidadesComidas, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }


        else if(sharedPreferences.getString("web_comida_seccion_3_recycler", "").equals("5")){
            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica1_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica2_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica3_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica4_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica5_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica5_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica5_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica5_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            especialidadesComidas = items;
            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsComidaSeccion3.DataConfigAdapter(especialidadesComidas, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }

        else if(sharedPreferences.getString("web_comida_seccion_3_recycler", "").equals("6")){
            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica1_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica1_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica2_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica2_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica3_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica3_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica4_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica4_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica5_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica5_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica5_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica5_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            specials_img = sharedPreferences.getString("web_comida_seccion_3_caracteristica6_url","");
            specials_titulo = sharedPreferences.getString("web_comida_seccion_3_caracteristica6_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_3_caracteristica6_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_3_caracteristica6_precio",0);

            items.add(new EspecialidadesComida(specials_img, specials_titulo, specials_precio, specials_descripcion));

            especialidadesComidas = items;
            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsComidaSeccion3.DataConfigAdapter(especialidadesComidas, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }


        addCaracteristica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(especialidadesComidas.size() < 6){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(WebsComidaSeccion3.this);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View formElementsView = inflater.inflate(R.layout.dialog_especialidad_comida,
                            null, false);

                    itemTitulo = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleTitulo);
                    itemDescripcion = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleDescripcion);
                    itemPrecio = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimplePrecio);
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

                                            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                                            r.getBitmap().compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                                            byte[] data = outputStream.toByteArray();

                                            StorageReference refSubirImagen = storage.getReferenceFromUrl("gs://pyme-assistant.appspot.com/web/userid").child(nombre_web + especialidadesComidas.size() + 12);

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

                    builder.setTitle("Especialidades");
                    builder.setMessage("Por favor, introduce un título, descripción y precio de tus principales especialidades");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String titulo = itemTitulo.getText().toString();
                            String descripcion = itemDescripcion.getText().toString();
                            int precio = Integer.valueOf(itemPrecio.getText().toString());

                            if(titulo.length() > 0 && descripcion.length() > 0 && url_.length() > 0){
                                EspecialidadesComida especialidadesComida = new EspecialidadesComida(url_, titulo, precio, descripcion);
                                especialidadesComidas.add(especialidadesComida);

                                // *********** LLENAMOS EL RECYCLER VIEW *****************************
                                adapter = new WebsComidaSeccion3.DataConfigAdapter(especialidadesComidas, getApplicationContext());
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
                    Toast.makeText(getApplicationContext(), "Sólo se permiten máximo 6 especialidades", Toast.LENGTH_LONG).show();
                }

            }
        });



        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(especialidadesComidas.size() < 3){
                    Toast.makeText(getApplicationContext(), "Debes introducir por lo menos tres de tus especialidades", Toast.LENGTH_LONG).show();
                    return;
                }

                // *********** Guardamos los principales datos de los nuevos usuarios *************
                sharedPreferences = getSharedPreferences("misDatos", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(especialidadesComidas.size() == 1){
                    editor.putString("web_comida_seccion_3_recycler", "1");
                    editor.putString("web_comida_seccion_3_caracteristica1_titulo", especialidadesComidas.get(0).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica1_descripcion", especialidadesComidas.get(0).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica1_url", especialidadesComidas.get(0).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica1_precio", especialidadesComidas.get(0).getPrecio());
                }
                else if(especialidadesComidas.size() == 2){
                    editor.putString("web_comida_seccion_3_recycler", "2");
                    editor.putString("web_comida_seccion_3_caracteristica1_titulo", especialidadesComidas.get(0).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica1_descripcion", especialidadesComidas.get(0).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica1_url", especialidadesComidas.get(0).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica1_precio", especialidadesComidas.get(0).getPrecio());

                    editor.putString("web_comida_seccion_3_caracteristica2_titulo", especialidadesComidas.get(1).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica2_descripcion", especialidadesComidas.get(1).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica2_url", especialidadesComidas.get(1).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica2_precio", especialidadesComidas.get(1).getPrecio());
                }
                else if(especialidadesComidas.size() == 3){
                    editor.putString("web_comida_seccion_3_recycler", "3");
                    editor.putString("web_comida_seccion_3_caracteristica1_titulo", especialidadesComidas.get(0).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica1_descripcion", especialidadesComidas.get(0).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica1_url", especialidadesComidas.get(0).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica1_precio", especialidadesComidas.get(0).getPrecio());

                    editor.putString("web_comida_seccion_3_caracteristica2_titulo", especialidadesComidas.get(1).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica2_descripcion", especialidadesComidas.get(1).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica2_url", especialidadesComidas.get(1).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica2_precio", especialidadesComidas.get(1).getPrecio());

                    editor.putString("web_comida_seccion_3_caracteristica3_titulo", especialidadesComidas.get(2).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica3_descripcion", especialidadesComidas.get(2).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica3_url", especialidadesComidas.get(2).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica3_precio", especialidadesComidas.get(2).getPrecio());
                }
                else if(especialidadesComidas.size() == 4){
                    editor.putString("web_comida_seccion_3_recycler", "4");
                    editor.putString("web_comida_seccion_3_caracteristica1_titulo", especialidadesComidas.get(0).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica1_descripcion", especialidadesComidas.get(0).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica1_url", especialidadesComidas.get(0).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica1_precio", especialidadesComidas.get(0).getPrecio());

                    editor.putString("web_comida_seccion_3_caracteristica2_titulo", especialidadesComidas.get(1).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica2_descripcion", especialidadesComidas.get(1).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica2_url", especialidadesComidas.get(1).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica2_precio", especialidadesComidas.get(1).getPrecio());

                    editor.putString("web_comida_seccion_3_caracteristica3_titulo", especialidadesComidas.get(2).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica3_descripcion", especialidadesComidas.get(2).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica3_url", especialidadesComidas.get(2).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica3_precio", especialidadesComidas.get(2).getPrecio());

                    editor.putString("web_comida_seccion_3_caracteristica4_titulo", especialidadesComidas.get(3).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica4_descripcion", especialidadesComidas.get(3).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica4_url", especialidadesComidas.get(3).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica4_precio", especialidadesComidas.get(3).getPrecio());
                }
                else if(especialidadesComidas.size() == 5){
                    editor.putString("web_comida_seccion_3_recycler", "5");
                    editor.putString("web_comida_seccion_3_caracteristica1_titulo", especialidadesComidas.get(0).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica1_descripcion", especialidadesComidas.get(0).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica1_url", especialidadesComidas.get(0).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica1_precio", especialidadesComidas.get(0).getPrecio());

                    editor.putString("web_comida_seccion_3_caracteristica2_titulo", especialidadesComidas.get(1).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica2_descripcion", especialidadesComidas.get(1).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica2_url", especialidadesComidas.get(1).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica2_precio", especialidadesComidas.get(1).getPrecio());

                    editor.putString("web_comida_seccion_3_caracteristica3_titulo", especialidadesComidas.get(2).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica3_descripcion", especialidadesComidas.get(2).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica3_url", especialidadesComidas.get(2).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica3_precio", especialidadesComidas.get(2).getPrecio());

                    editor.putString("web_comida_seccion_3_caracteristica4_titulo", especialidadesComidas.get(3).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica4_descripcion", especialidadesComidas.get(3).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica4_url", especialidadesComidas.get(3).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica4_precio", especialidadesComidas.get(3).getPrecio());

                    editor.putString("web_comida_seccion_3_caracteristica5_titulo", especialidadesComidas.get(4).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica5_descripcion", especialidadesComidas.get(4).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica5_url", especialidadesComidas.get(4).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica5_precio", especialidadesComidas.get(4).getPrecio());
                }
                else if(especialidadesComidas.size() == 6){
                    editor.putString("web_comida_seccion_3_recycler", "6");
                    editor.putString("web_comida_seccion_3_caracteristica1_titulo", especialidadesComidas.get(0).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica1_descripcion", especialidadesComidas.get(0).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica1_url", especialidadesComidas.get(0).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica1_precio", especialidadesComidas.get(0).getPrecio());

                    editor.putString("web_comida_seccion_3_caracteristica2_titulo", especialidadesComidas.get(1).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica2_descripcion", especialidadesComidas.get(1).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica2_url", especialidadesComidas.get(1).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica2_precio", especialidadesComidas.get(1).getPrecio());

                    editor.putString("web_comida_seccion_3_caracteristica3_titulo", especialidadesComidas.get(2).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica3_descripcion", especialidadesComidas.get(2).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica3_url", especialidadesComidas.get(2).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica3_precio", especialidadesComidas.get(2).getPrecio());

                    editor.putString("web_comida_seccion_3_caracteristica4_titulo", especialidadesComidas.get(3).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica4_descripcion", especialidadesComidas.get(3).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica4_url", especialidadesComidas.get(3).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica4_precio", especialidadesComidas.get(3).getPrecio());

                    editor.putString("web_comida_seccion_3_caracteristica5_titulo", especialidadesComidas.get(4).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica5_descripcion", especialidadesComidas.get(4).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica5_url", especialidadesComidas.get(4).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica5_precio", especialidadesComidas.get(4).getPrecio());

                    editor.putString("web_comida_seccion_3_caracteristica6_titulo", especialidadesComidas.get(5).getTitulo());
                    editor.putString("web_comida_seccion_3_caracteristica6_descripcion", especialidadesComidas.get(5).getDescripcion());
                    editor.putString("web_comida_seccion_3_caracteristica6_url", especialidadesComidas.get(5).getImg());
                    editor.putInt("web_comida_seccion_3_caracteristica6_precio", especialidadesComidas.get(5).getPrecio());
                }
                editor.commit();
                // ******************************************************************************

                Intent i = new Intent(WebsComidaSeccion3.this, WebsComidaSeccion4.class);
                startActivity(i);
            }
        });
    }
}
