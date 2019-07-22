package com.colabora.soluciones.convocatoriafeyac.Finanzas;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.Modelos.Inversion;
import com.colabora.soluciones.convocatoriafeyac.Modelos.ProductoServicio;
import com.colabora.soluciones.convocatoriafeyac.NuevaCotizacionActivity;
import com.colabora.soluciones.convocatoriafeyac.R;

import java.util.ArrayList;
import java.util.List;

public class InversionActivity extends AppCompatActivity {

    // *************************** RECYCLER VIEW ************************

    private class DataConfigHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtNombre;
        private TextView txtDescripcion;
        private TextView txtUnidad;
        private TextView txtPromedio;
        private Button btnEditar;
        private Button btnEliminar;

        private List<Inversion> inversions = new ArrayList<Inversion>();
        private Context ctx;

        public DataConfigHolder(View itemView, Context ctx, final List<Inversion> inversions) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.inversions = inversions;
            this.ctx = ctx;

            txtNombre = (TextView) itemView.findViewById(R.id.item_inversion_nombre);
            txtDescripcion = (TextView) itemView.findViewById(R.id.item_inversion_descripcion);
            txtUnidad = (TextView) itemView.findViewById(R.id.item_inversion_unidad);
            txtPromedio = (TextView) itemView.findViewById(R.id.item_inversion_monto);
            btnEditar = (Button) itemView.findViewById(R.id.item_inversion_editar);
            btnEliminar = (Button) itemView.findViewById(R.id.item_inversion_eliminar);

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        public void bindConfig(final Inversion inversion) {
            txtNombre.setText(inversion.getDescripcion());
            txtDescripcion.setText(inversion.getCategoria());
            txtUnidad.setText(inversion.getUnidad());
            txtPromedio.setText(String.valueOf(inversion.getMonto()));
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();

        }

    }


    private class DataConfigAdapter extends RecyclerView.Adapter<InversionActivity.DataConfigHolder> {

        private List<Inversion> inversions;
        Context ctx;

        public DataConfigAdapter(List<Inversion> inversions, Context ctx ){
            this.inversions = inversions;
            this.ctx = ctx;
        }

        @Override
        public InversionActivity.DataConfigHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_inversion, parent, false);
            return new InversionActivity.DataConfigHolder(view, ctx, inversions);
        }

        @Override
        public void onBindViewHolder(final InversionActivity.DataConfigHolder holder, final int position) {
            holder.bindConfig(inversions.get(position));

        }

        @Override
        public int getItemCount() {
            return inversions.size();
        }

    }

    private InversionActivity.DataConfigAdapter adapter;

    // ******************************************************************

    private Button btnCancelar;
    private Button btnAceptar;
    private TextInputEditText txtUnidad;
    private TextInputEditText txtDescripcion;
    private TextInputEditText txtPrecioUnitario;
    private TextInputEditText txtCantidad;
    private TextInputEditText txtVida;
    private Spinner spinner;

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private List<String> descuentos = new ArrayList<>();

    private List<Inversion> inversions = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inversion);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerInversion);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fabAddInversion);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // *********** LLENAMOS EL RECYCLER VIEW *****************************
        adapter = new InversionActivity.DataConfigAdapter(inversions, getApplicationContext());
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog builder = new Dialog(InversionActivity.this);

                // Get the layout inflater
                //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //final View formElementsView = inflater.inflate(R.layout.dialog_nuevo_concepto,
                //      null, false);

                builder.setContentView(R.layout.dialog_inversion);

                btnAceptar = (Button)builder.findViewById(R.id.inversionAceptar);
                btnCancelar = (Button)builder.findViewById(R.id.inversionCancelar);
                txtUnidad = (TextInputEditText)builder.findViewById(R.id.inversionUnidad);
                txtDescripcion = (TextInputEditText)builder.findViewById(R.id.inversionDescripcion);
                txtPrecioUnitario = (TextInputEditText)builder.findViewById(R.id.inversionImporte);
                txtCantidad = (TextInputEditText)builder.findViewById(R.id.inversionCantidad);
                txtVida = (TextInputEditText)builder.findViewById(R.id.inversionVida);
                spinner = (Spinner)builder.findViewById(R.id.spinnerInversion);

                descuentos.add("Seleccione una opción");
                descuentos.add("Mobiliario y equipo en general");
                descuentos.add("Herramientas");
                descuentos.add("Construcciones y adecuaciones");
                descuentos.add("Marcas, patentes, permisos, derechos");
                descuentos.add("Investigación y desarrollo");

                final ArrayAdapter<String> adapterCategoria = new ArrayAdapter<String>(
                        InversionActivity.this, R.layout.support_simple_spinner_dropdown_item, descuentos);
                spinner.setAdapter(adapterCategoria);


                builder.setTitle("Inversión");
                // builder.setMessage("Ingrese los datos del nuevo concepto. Si desea ingresar un importe fraccionario utilice el punto decimal '.' para especificarlo, por ejemplo 50.50");

                btnCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });

                btnAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String unidad = txtUnidad.getText().toString();
                        String descripcion = txtDescripcion.getText().toString();
                        String precioUnitario = txtPrecioUnitario.getText().toString();
                        String vida = txtVida.getText().toString();
                        String cantidad = txtCantidad.getText().toString();


                        if(unidad.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(descripcion.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(precioUnitario.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(vida.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(cantidad.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(spinner.getSelectedItem().toString().equals("Seleccione una opción")){
                            Toast.makeText(getApplicationContext(), "Debes seleccionar el tipo de inversión", Toast.LENGTH_LONG).show();
                            return;
                        }


                        int cantidad_ = Integer.valueOf(txtCantidad.getText().toString());
                        int vida_ = Integer.valueOf(txtVida.getText().toString());
                        double importe_ = Integer.valueOf(txtPrecioUnitario.getText().toString());
                        double monto_ = importe_ * vida_;

                        Inversion inversion = new Inversion(1, unidad, descripcion, importe_, cantidad_, monto_, vida_, "", "");

                        inversions.add(inversion);

                        // *********** LLENAMOS EL RECYCLER VIEW *****************************
                        adapter = new InversionActivity.DataConfigAdapter(inversions, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                        // ****************************************************************
                        builder.dismiss();
                    }
                });

                // Inflate and set the layout for the dialog
                // Pass null as the parent view because its going in the dialog layout
                // Add action buttons
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.create();
                }
                builder.show();
                builder.getWindow().setLayout((6 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

    }
}
