package com.colabora.soluciones.convocatoriafeyac.web.salud;

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

import com.colabora.soluciones.convocatoriafeyac.Modelos.Horario;
import com.colabora.soluciones.convocatoriafeyac.Modelos.itemSimple;
import com.colabora.soluciones.convocatoriafeyac.R;
import com.colabora.soluciones.convocatoriafeyac.web.servicios.WebsServiciosSeccion2;
import com.colabora.soluciones.convocatoriafeyac.web.servicios.WebsServiciosSeccion3;
import com.colabora.soluciones.convocatoriafeyac.web.servicios.WebsServiciosSeccion4;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class WebsSaludSeccion3 extends AppCompatActivity {

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
                    adapter = new WebsSaludSeccion3.DataConfigAdapter(itemSimpleList, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            });

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();

                    final AlertDialog.Builder builder = new AlertDialog.Builder(WebsSaludSeccion3.this);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View formElementsView = inflater.inflate(R.layout.dialog_item_simple,
                            null, false);

                    itemTitulo = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleTitulo);
                    itemDescripcion = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleDescripcion);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    itemTitulo.setText(itemSimpleList.get(position).getTitulo());
                    itemDescripcion.setText(itemSimpleList.get(position).getDescripcion());

                    builder.setTitle("Horario");
                    builder.setMessage("Por favor, introduce los días y los horarios de atención a tus clientes");
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
                                adapter = new WebsSaludSeccion3.DataConfigAdapter(itemSimpleList, getApplicationContext());
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

            txtTitulo.setHint("Día");
            txtDescripcion.setHint("Horario");
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();

        }

    }


    private class DataConfigAdapter extends RecyclerView.Adapter<WebsSaludSeccion3.DataConfigHolder> {

        private List<itemSimple> itemSimples;
        Context ctx;

        public DataConfigAdapter(List<itemSimple> itemSimples, Context ctx ){
            this.itemSimples = itemSimples;
            this.ctx = ctx;
        }

        @Override
        public WebsSaludSeccion3.DataConfigHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_simple, parent, false);
            return new WebsSaludSeccion3.DataConfigHolder(view, ctx, itemSimples);
        }

        @Override
        public void onBindViewHolder(final WebsSaludSeccion3.DataConfigHolder holder, final int position) {
            holder.bindConfig(itemSimples.get(position));

        }

        @Override
        public int getItemCount() {
            return itemSimples.size();
        }

    }

    private WebsSaludSeccion3.DataConfigAdapter adapter;

    // ******************************************************************


    private TextInputEditText itemTitulo;
    private TextInputEditText itemDescripcion;
    private RecyclerView recyclerView;
    private Button addCaracteristica;
    private Button btnSiguiente;
    private List<itemSimple> itemSimpleList = new ArrayList<>();


    FirebaseStorage storage = FirebaseStorage.getInstance();
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences;

    private String imagen = "";
    private String titulo = "";
    private List<Horario> hora = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_salud_seccion3);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerSaludSeccion3);
        addCaracteristica = (Button) findViewById(R.id.btnSaludSeccion3AddCaracteristica);
        btnSiguiente = (Button) findViewById(R.id.btnSaludSeccion3Siguiente);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        sharedPreferences = getSharedPreferences("misDatos", 0);

        if(sharedPreferences.getString("web_salud_seccion_3_recycler", "").equals("1")){
            imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica1_titulo","");
            titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica1_descripcion","");

            hora.add(new Horario(imagen, titulo, ""));

            for (int i = 0; i < hora.size(); i++){
                itemSimpleList.add(new itemSimple(hora.get(i).getDia(), hora.get(i).getDehora()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsSaludSeccion3.DataConfigAdapter(itemSimpleList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }

        else if(sharedPreferences.getString("web_salud_seccion_3_recycler", "").equals("2")){
            imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica1_titulo","");
            titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica1_descripcion","");

            hora.add(new Horario(imagen, titulo, ""));

            imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica2_titulo","");
            titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica2_descripcion","");

            hora.add(new Horario(imagen, titulo, ""));

            for (int i = 0; i < hora.size(); i++){
                itemSimpleList.add(new itemSimple(hora.get(i).getDia(), hora.get(i).getDehora()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsSaludSeccion3.DataConfigAdapter(itemSimpleList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }

        else if(sharedPreferences.getString("web_salud_seccion_3_recycler", "").equals("3")){
            imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica1_titulo","");
            titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica1_descripcion","");

            hora.add(new Horario(imagen, titulo, ""));

            imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica2_titulo","");
            titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica2_descripcion","");

            hora.add(new Horario(imagen, titulo, ""));

            imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica3_titulo","");
            titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica3_descripcion","");

            hora.add(new Horario(imagen, titulo, ""));

            for (int i = 0; i < hora.size(); i++){
                itemSimpleList.add(new itemSimple(hora.get(i).getDia(), hora.get(i).getDehora()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsSaludSeccion3.DataConfigAdapter(itemSimpleList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }

        else if(sharedPreferences.getString("web_salud_seccion_3_recycler", "").equals("4")){
            imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica1_titulo","");
            titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica1_descripcion","");

            hora.add(new Horario(imagen, titulo, ""));

            imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica2_titulo","");
            titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica2_descripcion","");

            hora.add(new Horario(imagen, titulo, ""));

            imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica3_titulo","");
            titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica3_descripcion","");

            hora.add(new Horario(imagen, titulo, ""));

            imagen = sharedPreferences.getString("web_salud_seccion_3_caracteristica4_titulo","");
            titulo = sharedPreferences.getString("web_salud_seccion_3_caracteristica4_descripcion","");

            hora.add(new Horario(imagen, titulo, ""));

            for (int i = 0; i < hora.size(); i++){
                itemSimpleList.add(new itemSimple(hora.get(i).getDia(), hora.get(i).getDehora()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsSaludSeccion3.DataConfigAdapter(itemSimpleList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }


        addCaracteristica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(itemSimpleList.size() < 4){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(WebsSaludSeccion3.this);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View formElementsView = inflater.inflate(R.layout.dialog_item_simple,
                            null, false);

                    itemTitulo = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleTitulo);
                    itemDescripcion = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleDescripcion);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    builder.setTitle("Horario");
                    builder.setMessage("Por favor, introduce los días y los horarios de atención a tus clientes");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String titulo = itemTitulo.getText().toString();
                            String descripcion = itemDescripcion.getText().toString();

                            if(titulo.length() > 0 && descripcion.length() > 0){
                                itemSimple itemSimple_ = new itemSimple(titulo, descripcion);
                                itemSimpleList.add(itemSimple_);

                                // *********** LLENAMOS EL RECYCLER VIEW *****************************
                                adapter = new WebsSaludSeccion3.DataConfigAdapter(itemSimpleList, getApplicationContext());
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
                    Toast.makeText(getApplicationContext(), "Sólo se permiten máximo 4 horarios diferentes", Toast.LENGTH_LONG).show();
                }

            }
        });



        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(itemSimpleList.size() < 1){
                    Toast.makeText(getApplicationContext(), "Debes introducir por lo menos una característica de tus servicios", Toast.LENGTH_LONG).show();
                    return;
                }

                // *********** Guardamos los principales datos de los nuevos usuarios *************
                sharedPreferences = getSharedPreferences("misDatos", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(itemSimpleList.size() == 1){
                    editor.putString("web_salud_seccion_3_recycler", "1");
                    editor.putString("web_salud_seccion_3_caracteristica1_titulo", itemSimpleList.get(0).getTitulo());
                    editor.putString("web_salud_seccion_3_caracteristica1_descripcion", itemSimpleList.get(0).getDescripcion());
                }
                else if(itemSimpleList.size() == 2){
                    editor.putString("web_salud_seccion_3_recycler", "2");
                    editor.putString("web_salud_seccion_3_caracteristica1_titulo", itemSimpleList.get(0).getTitulo());
                    editor.putString("web_salud_seccion_3_caracteristica1_descripcion", itemSimpleList.get(0).getDescripcion());
                    editor.putString("web_salud_seccion_3_caracteristica2_titulo", itemSimpleList.get(1).getTitulo());
                    editor.putString("web_salud_seccion_3_caracteristica2_descripcion", itemSimpleList.get(1).getDescripcion());
                }
                else if(itemSimpleList.size() == 3){
                    editor.putString("web_salud_seccion_3_recycler", "3");
                    editor.putString("web_salud_seccion_3_caracteristica1_titulo", itemSimpleList.get(0).getTitulo());
                    editor.putString("web_salud_seccion_3_caracteristica1_descripcion", itemSimpleList.get(0).getDescripcion());
                    editor.putString("web_salud_seccion_3_caracteristica2_titulo", itemSimpleList.get(1).getTitulo());
                    editor.putString("web_salud_seccion_3_caracteristica2_descripcion", itemSimpleList.get(1).getDescripcion());
                    editor.putString("web_salud_seccion_3_caracteristica3_titulo", itemSimpleList.get(2).getTitulo());
                    editor.putString("web_salud_seccion_3_caracteristica3_descripcion", itemSimpleList.get(2).getDescripcion());
                }
                else if(itemSimpleList.size() == 4){
                    editor.putString("web_salud_seccion_3_recycler", "4");
                    editor.putString("web_salud_seccion_3_caracteristica1_titulo", itemSimpleList.get(0).getTitulo());
                    editor.putString("web_salud_seccion_3_caracteristica1_descripcion", itemSimpleList.get(0).getDescripcion());
                    editor.putString("web_salud_seccion_3_caracteristica2_titulo", itemSimpleList.get(1).getTitulo());
                    editor.putString("web_salud_seccion_3_caracteristica2_descripcion", itemSimpleList.get(1).getDescripcion());
                    editor.putString("web_salud_seccion_3_caracteristica3_titulo", itemSimpleList.get(2).getTitulo());
                    editor.putString("web_salud_seccion_3_caracteristica3_descripcion", itemSimpleList.get(2).getDescripcion());
                    editor.putString("web_salud_seccion_3_caracteristica4_titulo", itemSimpleList.get(3).getTitulo());
                    editor.putString("web_salud_seccion_3_caracteristica4_descripcion", itemSimpleList.get(3).getDescripcion());
                }
                editor.commit();
                // ******************************************************************************

                Intent i = new Intent(WebsSaludSeccion3.this, WebsSaludSeccion4.class);
                startActivity(i);
            }
        });

    }
}
