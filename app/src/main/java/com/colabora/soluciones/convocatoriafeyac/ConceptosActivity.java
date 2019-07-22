package com.colabora.soluciones.convocatoriafeyac;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.Db.Querys;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Concepto;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Conceptos;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConceptosActivity extends AppCompatActivity {

    // *************************** RECYCLER VIEW ************************

    private class DataConfigHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtConcepto;
        private TextView txtCantidad;
        private Button btnEditar;
        private Button btnEliminar;

        private List<Concepto> conceptos = new ArrayList<Concepto>();
        private Context ctx;

        public DataConfigHolder(View itemView, Context ctx, final List<Concepto> conceptos) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.conceptos = conceptos;
            this.ctx = ctx;

            txtConcepto = (TextView) itemView.findViewById(R.id.item_concepto_nombre);
            txtCantidad = (TextView) itemView.findViewById(R.id.item_concepto_precio);
            btnEditar = (Button) itemView.findViewById(R.id.item_editarConcepto);
            btnEliminar = (Button) itemView.findViewById(R.id.item_eliminarConcepto);

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ConceptosActivity.this);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View formElementsView = inflater.inflate(R.layout.dialog_db_concepto,
                            null, false);

                    editNuevoConcepto = (TextInputEditText)formElementsView.findViewById(R.id.txtNuevoConcepto);
                    editNuevoConceptoPrecio = (TextInputEditText)formElementsView.findViewById(R.id.txtNuevoConceptoPrecio);

                    editNuevoConcepto.setText(conceptos.get(getAdapterPosition()).getNombre());
                    editNuevoConceptoPrecio.setText(conceptos.get(getAdapterPosition()).getPrecio());

                    builder.setTitle("Editar concepto");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            if(editNuevoConcepto.getText().toString().length() > 0){
                                Concepto concepto = new Concepto(editNuevoConcepto.getText().toString(), editNuevoConceptoPrecio.getText().toString());
                                querys.updateConcepto(concepto, String.valueOf(conceptos.get(getAdapterPosition()).getId()));
                                Toast.makeText(getApplicationContext(), "Concepto guardado con éxito", Toast.LENGTH_SHORT).show();

                                actualizar();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Introduce un concepto válido", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    builder.setView(formElementsView);
                    // Add action buttons
                    builder.create();
                    builder.show();
                }
            });

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = getAdapterPosition();
                    AlertDialog.Builder builder = new AlertDialog.Builder(ConceptosActivity.this);
                    builder.setTitle("Eliminar concepto");
                    builder.setMessage("¿Estas seguro de querer elimnar este concepto de la base de datos?")
                            .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    querys.deleteConcepto(String.valueOf(conceptos.get(getAdapterPosition()).getId()));
                                    actualizar();

                                }
                            })
                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();
                }
            });

        }

        public void bindConfig(final Concepto conceptos) {
            txtConcepto.setText(conceptos.getNombre());
            if(conceptos.getPrecio().length() == 0){
                txtCantidad.setText("Precio no registrado");
            }
            else{
                txtCantidad.setText("$" +  NumberFormat.getNumberInstance(Locale.US).format(Double.valueOf(conceptos.getPrecio())));
            }
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();
        }
    }


    private class DataConfigAdapter extends RecyclerView.Adapter<ConceptosActivity.DataConfigHolder> {

        private List<Concepto> conceptos;
        Context ctx;

        public DataConfigAdapter(List<Concepto> conceptos, Context ctx ){
            this.conceptos = conceptos;
            this.ctx = ctx;
        }

        @Override
        public ConceptosActivity.DataConfigHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_db_concepto, parent, false);
            return new ConceptosActivity.DataConfigHolder(view, ctx, conceptos);
        }

        @Override
        public void onBindViewHolder(final ConceptosActivity.DataConfigHolder holder, final int position) {
            holder.bindConfig(conceptos.get(position));

        }

        @Override
        public int getItemCount() {
            return conceptos.size();
        }

    }

    private ConceptosActivity.DataConfigAdapter adapter;
    private List<Concepto> conceptos = new ArrayList<Concepto>();
    private RecyclerView recyclerView;

    // ******************************************************************

    private Querys querys;
    private FloatingActionButton floatingActionButton;
    private TextInputEditText editNuevoConcepto;
    private TextInputEditText editNuevoConceptoPrecio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conceptos);

        querys = new Querys(getApplicationContext());
        recyclerView = (RecyclerView)findViewById(R.id.recycleDBConceptos);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fabAddConcepto);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        conceptos = querys.getAllConceptos();

        // *********** LLENAMOS EL RECYCLER VIEW *****************************
        adapter = new ConceptosActivity.DataConfigAdapter(conceptos, getApplicationContext());
        recyclerView.setAdapter(adapter);

        if(conceptos.size() == 0){
            Toast.makeText(getApplicationContext(), "Aún no tienes conceptos guardados en la base de datos", Toast.LENGTH_LONG).show();
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(ConceptosActivity.this);

                // Get the layout inflater
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View formElementsView = inflater.inflate(R.layout.dialog_db_concepto,
                        null, false);

                editNuevoConcepto = (TextInputEditText)formElementsView.findViewById(R.id.txtNuevoConcepto);
                editNuevoConceptoPrecio = (TextInputEditText)formElementsView.findViewById(R.id.txtNuevoConceptoPrecio);

                builder.setTitle("Nuevo concepto");
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(editNuevoConcepto.getText().toString().length() > 0){
                            Concepto concepto = new Concepto(editNuevoConcepto.getText().toString(), editNuevoConceptoPrecio.getText().toString());
                            querys.insertConcepto(concepto);
                            Toast.makeText(getApplicationContext(), "Concepto guardado con éxito", Toast.LENGTH_SHORT).show();
                            conceptos = querys.getAllConceptos();

                            // *********** LLENAMOS EL RECYCLER VIEW *****************************
                            adapter = new ConceptosActivity.DataConfigAdapter(conceptos, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Introduce un concepto válido", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setView(formElementsView);
                // Add action buttons
                builder.create();
                builder.show();
            }
        });


    }

    private void actualizar(){
        conceptos = querys.getAllConceptos();
        // *********** LLENAMOS EL RECYCLER VIEW *****************************
        adapter = new DataConfigAdapter(conceptos, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}
