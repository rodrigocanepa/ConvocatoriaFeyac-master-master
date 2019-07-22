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
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.Modelos.EspecialidadesComida;
import com.colabora.soluciones.convocatoriafeyac.Modelos.MenuComida;
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

public class WebsComidaSeccion5 extends AppCompatActivity {

    // *************************** RECYCLER VIEW ************************

    private class DataConfigHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtTitulo;
        private TextView txtDescripcion;
        private ImageView img;
        private Button btnEditar;
        private Button btnEliminar;

        private List<MenuComida> menuComidaList = new ArrayList<MenuComida>();
        private Context ctx;

        public DataConfigHolder(View itemView, Context ctx, final List<MenuComida> menuComidaList) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.menuComidaList = menuComidaList;
            this.ctx = ctx;

            txtTitulo = (TextView) itemView.findViewById(R.id.itemSimpleTitulo);
            txtDescripcion = (TextView) itemView.findViewById(R.id.itemSimpleDescripcion);
            btnEditar = (Button)itemView.findViewById(R.id.dialogItemSimpleEditar);
            btnEliminar = (Button)itemView.findViewById(R.id.dialogItemSimpleEliminar);
            //img = (ImageView)itemView.findViewById(R.id.itemFoto);

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    menuComidaList.remove(getAdapterPosition());
                    // *********** LLENAMOS EL RECYCLER VIEW *****************************
                    adapter = new WebsComidaSeccion5.DataConfigAdapter(menuComidaList, getApplicationContext());
                    recyclerView.setAdapter(adapter);
                }
            });

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int position = getAdapterPosition();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(WebsComidaSeccion5.this);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View formElementsView = inflater.inflate(R.layout.dialog_menu_comida,
                            null, false);

                    itemTitulo = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleTitulo);
                    itemDescripcion = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleDescripcion);
                    itemPrecio = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimplePrecio);

                    itemTitulo.setText(menuComidaList.get(position).getTitulo());
                    itemDescripcion.setText(menuComidaList.get(position).getDescripcion());
                    itemPrecio.setText(String.valueOf(menuComidaList.get(position).getPrecio()));

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    builder.setTitle("Menú");
                    builder.setMessage("Por favor, introduce un título, descripción y precio de los principales alimentos de tu menú");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String titulo = itemTitulo.getText().toString();
                            String descripcion = itemDescripcion.getText().toString();
                            int precio = Integer.valueOf(itemPrecio.getText().toString());

                            if(titulo.length() > 0 && descripcion.length() > 0){
                                menuComidaList.remove(position);
                                MenuComida menuComida = new MenuComida(titulo, precio, descripcion);
                                menuComidaList.add(menuComida);

                                // *********** LLENAMOS EL RECYCLER VIEW *****************************
                                adapter = new WebsComidaSeccion5.DataConfigAdapter(menuComidaList, getApplicationContext());
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

        public void bindConfig(final MenuComida menuComida) {
            txtTitulo.setText(menuComida.getTitulo());
            txtDescripcion.setText(menuComida.getDescripcion() + "\n" + String.valueOf(menuComida.getPrecio()));

            // Picasso.get().load(itemFoto.getUrl()).into(img);
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();

        }

    }


    private class DataConfigAdapter extends RecyclerView.Adapter<WebsComidaSeccion5.DataConfigHolder> {

        private List<MenuComida> menuComidaList;
        Context ctx;

        public DataConfigAdapter(List<MenuComida> menuComidaList, Context ctx ){
            this.menuComidaList = menuComidaList;
            this.ctx = ctx;
        }

        @Override
        public WebsComidaSeccion5.DataConfigHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_con_foto, parent, false);
            return new WebsComidaSeccion5.DataConfigHolder(view, ctx, menuComidaList);
        }

        @Override
        public void onBindViewHolder(final WebsComidaSeccion5.DataConfigHolder holder, final int position) {
            holder.bindConfig(menuComidaList.get(position));

        }

        @Override
        public int getItemCount() {
            return menuComidaList.size();
        }

    }

    private WebsComidaSeccion5.DataConfigAdapter adapter;

    // ******************************************************************

    private RecyclerView recyclerView;
    private Button addCaracteristica;
    private Button btnSiguiente;
    private SharedPreferences sharedPreferences;
    private List<MenuComida> menuComidaList = new ArrayList<>();
    private TextInputEditText itemTitulo;
    private TextInputEditText itemDescripcion;
    private TextInputEditText itemPrecio;

    private String specials_img = "";
    private String specials_titulo = "";
    private String specials_descripcion = "";
    private int specials_precio = 0;
    private List<MenuComida> items_menu = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webs_comida_seccion5);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerComidaSeccion5);
        btnSiguiente = (Button)findViewById(R.id.btnComidaSeccion5Siguiente);
        addCaracteristica = (Button)findViewById(R.id.btnComidaSeccion5AddCaracteristica);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        sharedPreferences = getSharedPreferences("misDatos", 0);

        if(sharedPreferences.getString("web_comida_seccion_5_recycler", "").equals("1")){
            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica1_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            menuComidaList = items_menu;
            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsComidaSeccion5.DataConfigAdapter(menuComidaList, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }

        else if(sharedPreferences.getString("web_comida_seccion_5_recycler", "").equals("2")){
            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica1_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica2_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            menuComidaList = items_menu;
            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsComidaSeccion5.DataConfigAdapter(menuComidaList, getApplicationContext());
            recyclerView.setAdapter(adapter);

        }
        else if(sharedPreferences.getString("web_comida_seccion_5_recycler", "").equals("3")){
            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica1_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica2_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica3_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica3_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica3_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            menuComidaList = items_menu;
            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsComidaSeccion5.DataConfigAdapter(menuComidaList, getApplicationContext());
            recyclerView.setAdapter(adapter);

        }

        else if(sharedPreferences.getString("web_comida_seccion_5_recycler", "").equals("4")){
            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica1_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica2_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica3_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica3_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica3_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica4_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica4_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica4_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            menuComidaList = items_menu;
            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsComidaSeccion5.DataConfigAdapter(menuComidaList, getApplicationContext());
            recyclerView.setAdapter(adapter);

        }

        else if(sharedPreferences.getString("web_comida_seccion_5_recycler", "").equals("5")){
            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica1_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica2_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica3_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica3_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica3_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica4_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica4_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica4_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica5_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica5_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica5_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            menuComidaList = items_menu;
            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsComidaSeccion5.DataConfigAdapter(menuComidaList, getApplicationContext());
            recyclerView.setAdapter(adapter);

        }

        else if(sharedPreferences.getString("web_comida_seccion_5_recycler", "").equals("6")){
            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica1_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica1_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica2_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica2_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica3_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica3_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica3_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica4_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica4_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica4_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica5_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica5_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica5_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            specials_titulo = sharedPreferences.getString("web_comida_seccion_5_caracteristica6_titulo","");
            specials_descripcion = sharedPreferences.getString("web_comida_seccion_5_caracteristica6_descripcion","");
            specials_precio = sharedPreferences.getInt("web_comida_seccion_5_caracteristica6_precio",0);

            items_menu.add(new MenuComida(specials_titulo, specials_precio, specials_descripcion));

            menuComidaList = items_menu;
            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new WebsComidaSeccion5.DataConfigAdapter(menuComidaList, getApplicationContext());
            recyclerView.setAdapter(adapter);

        }

        addCaracteristica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(menuComidaList.size() < 6){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(WebsComidaSeccion5.this);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View formElementsView = inflater.inflate(R.layout.dialog_menu_comida,
                            null, false);

                    itemTitulo = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleTitulo);
                    itemDescripcion = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimpleDescripcion);
                    itemPrecio = (TextInputEditText) formElementsView.findViewById(R.id.editItemSimplePrecio);

                    recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

                    builder.setTitle("Menu");
                    builder.setMessage("Por favor, introduce un título y descripción muy breves de los platillos que conformarán tu menú");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String titulo = itemTitulo.getText().toString();
                            String descripcion = itemDescripcion.getText().toString();
                            int precio = Integer.valueOf(itemPrecio.getText().toString());

                            if(titulo.length() > 0 && descripcion.length() > 0){
                                MenuComida menuComida = new MenuComida(titulo, precio, descripcion);
                                menuComidaList.add(menuComida);

                                // *********** LLENAMOS EL RECYCLER VIEW *****************************
                                adapter = new WebsComidaSeccion5.DataConfigAdapter(menuComidaList, getApplicationContext());
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
                    Toast.makeText(getApplicationContext(), "Sólo se permiten máximo 3 ventajas competitivas", Toast.LENGTH_LONG).show();
                }

            }
        });



        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(menuComidaList.size() < 2){
                    Toast.makeText(getApplicationContext(), "Debes introducir por lo menos dos de los alimentos que conforman tu menú", Toast.LENGTH_LONG).show();
                    return;
                }

                // *********** Guardamos los principales datos de los nuevos usuarios *************
                sharedPreferences = getSharedPreferences("misDatos", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                if(menuComidaList.size() == 1){
                    editor.putString("web_comida_seccion_5_recycler", "1");
                    editor.putString("web_comida_seccion_5_caracteristica1_titulo", menuComidaList.get(0).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica1_descripcion", menuComidaList.get(0).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica1_precio", menuComidaList.get(0).getPrecio());
                }
                else if(menuComidaList.size() == 2){
                    editor.putString("web_comida_seccion_5_recycler", "2");
                    editor.putString("web_comida_seccion_5_caracteristica1_titulo", menuComidaList.get(0).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica1_descripcion", menuComidaList.get(0).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica1_precio", menuComidaList.get(0).getPrecio());

                    editor.putString("web_comida_seccion_5_caracteristica2_titulo", menuComidaList.get(1).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica2_descripcion", menuComidaList.get(1).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica2_precio", menuComidaList.get(1).getPrecio());
                }
                else if(menuComidaList.size() == 3){
                    editor.putString("web_comida_seccion_5_recycler", "3");
                    editor.putString("web_comida_seccion_5_caracteristica1_titulo", menuComidaList.get(0).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica1_descripcion", menuComidaList.get(0).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica1_precio", menuComidaList.get(0).getPrecio());

                    editor.putString("web_comida_seccion_5_caracteristica2_titulo", menuComidaList.get(1).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica2_descripcion", menuComidaList.get(1).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica2_precio", menuComidaList.get(1).getPrecio());

                    editor.putString("web_comida_seccion_5_caracteristica3_titulo", menuComidaList.get(2).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica3_descripcion", menuComidaList.get(2).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica3_precio", menuComidaList.get(2).getPrecio());
                }
                else if(menuComidaList.size() == 4){
                    editor.putString("web_comida_seccion_5_recycler", "4");
                    editor.putString("web_comida_seccion_5_caracteristica1_titulo", menuComidaList.get(0).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica1_descripcion", menuComidaList.get(0).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica1_precio", menuComidaList.get(0).getPrecio());

                    editor.putString("web_comida_seccion_5_caracteristica2_titulo", menuComidaList.get(1).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica2_descripcion", menuComidaList.get(1).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica2_precio", menuComidaList.get(1).getPrecio());

                    editor.putString("web_comida_seccion_5_caracteristica3_titulo", menuComidaList.get(2).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica3_descripcion", menuComidaList.get(2).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica3_precio", menuComidaList.get(2).getPrecio());

                    editor.putString("web_comida_seccion_5_caracteristica4_titulo", menuComidaList.get(2).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica4_descripcion", menuComidaList.get(2).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica4_precio", menuComidaList.get(2).getPrecio());
                }
                else if(menuComidaList.size() == 5){
                    editor.putString("web_comida_seccion_5_recycler", "5");
                    editor.putString("web_comida_seccion_5_caracteristica1_titulo", menuComidaList.get(0).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica1_descripcion", menuComidaList.get(0).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica1_precio", menuComidaList.get(0).getPrecio());

                    editor.putString("web_comida_seccion_5_caracteristica2_titulo", menuComidaList.get(1).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica2_descripcion", menuComidaList.get(1).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica2_precio", menuComidaList.get(1).getPrecio());

                    editor.putString("web_comida_seccion_5_caracteristica3_titulo", menuComidaList.get(2).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica3_descripcion", menuComidaList.get(2).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica3_precio", menuComidaList.get(2).getPrecio());

                    editor.putString("web_comida_seccion_5_caracteristica4_titulo", menuComidaList.get(2).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica4_descripcion", menuComidaList.get(2).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica4_precio", menuComidaList.get(2).getPrecio());

                    editor.putString("web_comida_seccion_5_caracteristica5_titulo", menuComidaList.get(3).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica5_descripcion", menuComidaList.get(3).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica5_precio", menuComidaList.get(3).getPrecio());
                }
                else if(menuComidaList.size() == 6){
                    editor.putString("web_comida_seccion_5_recycler", "6");
                    editor.putString("web_comida_seccion_5_caracteristica1_titulo", menuComidaList.get(0).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica1_descripcion", menuComidaList.get(0).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica1_precio", menuComidaList.get(0).getPrecio());

                    editor.putString("web_comida_seccion_5_caracteristica2_titulo", menuComidaList.get(1).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica2_descripcion", menuComidaList.get(1).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica2_precio", menuComidaList.get(1).getPrecio());

                    editor.putString("web_comida_seccion_5_caracteristica3_titulo", menuComidaList.get(2).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica3_descripcion", menuComidaList.get(2).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica3_precio", menuComidaList.get(2).getPrecio());

                    editor.putString("web_comida_seccion_5_caracteristica4_titulo", menuComidaList.get(2).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica4_descripcion", menuComidaList.get(2).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica4_precio", menuComidaList.get(2).getPrecio());

                    editor.putString("web_comida_seccion_5_caracteristica5_titulo", menuComidaList.get(3).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica5_descripcion", menuComidaList.get(3).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica5_precio", menuComidaList.get(3).getPrecio());

                    editor.putString("web_comida_seccion_5_caracteristica6_titulo", menuComidaList.get(4).getTitulo());
                    editor.putString("web_comida_seccion_5_caracteristica6_descripcion", menuComidaList.get(4).getDescripcion());
                    editor.putInt("web_comida_seccion_5_caracteristica6_precio", menuComidaList.get(4).getPrecio());
                }
                editor.commit();
                // ******************************************************************************

                Intent i = new Intent(WebsComidaSeccion5.this, WebsComidaSeccion6.class);
                startActivity(i);
            }
        });
    }
}
