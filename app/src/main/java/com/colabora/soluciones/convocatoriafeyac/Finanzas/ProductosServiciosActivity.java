package com.colabora.soluciones.convocatoriafeyac.Finanzas;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.Modelos.Conceptos;
import com.colabora.soluciones.convocatoriafeyac.Modelos.ProductoServicio;
import com.colabora.soluciones.convocatoriafeyac.NuevaCotizacionActivity;
import com.colabora.soluciones.convocatoriafeyac.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ProductosServiciosActivity extends AppCompatActivity {


    // *************************** RECYCLER VIEW ************************

    private class DataConfigHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtNombre;
        private TextView txtDescripcion;
        private TextView txtUnidad;
        private TextView txtPromedio;
        private Button btnEditar;
        private Button btnEliminar;

        private List<ProductoServicio> productoServicios = new ArrayList<ProductoServicio>();
        private Context ctx;

        public DataConfigHolder(View itemView, Context ctx, final List<ProductoServicio> productoServicios) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.productoServicios = productoServicios;
            this.ctx = ctx;

            txtNombre = (TextView) itemView.findViewById(R.id.item_producto_servicio_nombre);
            txtDescripcion = (TextView) itemView.findViewById(R.id.item_producto_servicio_descripcion);
            txtUnidad = (TextView) itemView.findViewById(R.id.item_producto_servicio_servicio);
            txtPromedio = (TextView) itemView.findViewById(R.id.item_producto_servicio_promedio);
            btnEditar = (Button) itemView.findViewById(R.id.item_producto_servicio_editar);
            btnEliminar = (Button) itemView.findViewById(R.id.item_producto_servicio_eliminar);

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

        public void bindConfig(final ProductoServicio productoServicio) {
            txtNombre.setText(productoServicio.getNombre());
            txtDescripcion.setText(productoServicio.getDescripcion());
            txtUnidad.setText(productoServicio.getUnidad());
            txtPromedio.setText(String.valueOf(productoServicio.getPromedio()));
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();

        }

    }


    private class DataConfigAdapter extends RecyclerView.Adapter<ProductosServiciosActivity.DataConfigHolder> {

        private List<ProductoServicio> productoServicios;
        Context ctx;

        public DataConfigAdapter(List<ProductoServicio> productoServicios, Context ctx ){
            this.productoServicios = productoServicios;
            this.ctx = ctx;
        }

        @Override
        public ProductosServiciosActivity.DataConfigHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_productos_servicios, parent, false);
            return new ProductosServiciosActivity.DataConfigHolder(view, ctx, productoServicios);
        }

        @Override
        public void onBindViewHolder(final ProductosServiciosActivity.DataConfigHolder holder, final int position) {
            holder.bindConfig(productoServicios.get(position));

        }

        @Override
        public int getItemCount() {
            return productoServicios.size();
        }

    }

    private ProductosServiciosActivity.DataConfigAdapter adapter;

    // ******************************************************************


    private TextInputEditText txtNombre;
    private TextInputEditText txtUnidad;
    private TextInputEditText txtDescripcion;
    private TextInputEditText txtEnero;
    private TextInputEditText txtFebrero;
    private TextInputEditText txtMarzo;
    private TextInputEditText txtAbril;
    private TextInputEditText txtMayo;
    private TextInputEditText txtJunio;
    private TextInputEditText txtJulio;
    private TextInputEditText txtAgosto;
    private TextInputEditText txtSeptiembre;
    private TextInputEditText txtOctubre;
    private TextInputEditText txtNoviembre;
    private TextInputEditText txtDiciembre;

    private Button btnAceptar;
    private Button btnCancelar;

    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private List<ProductoServicio> productoServicios = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productos_servicios);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerProductoServicio);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fabAddProductoServicio);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        // *********** LLENAMOS EL RECYCLER VIEW *****************************
        adapter = new ProductosServiciosActivity.DataConfigAdapter(productoServicios, getApplicationContext());
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog builder = new Dialog(ProductosServiciosActivity.this);

                // Get the layout inflater
                //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //final View formElementsView = inflater.inflate(R.layout.dialog_nuevo_concepto,
                //      null, false);

                builder.setContentView(R.layout.dialog_productos_servicios);

                txtNombre = (TextInputEditText)builder.findViewById(R.id.productoServicioNombre);
                txtUnidad = (TextInputEditText)builder.findViewById(R.id.productoServicioUnidadMedida);
                txtDescripcion = (TextInputEditText)builder.findViewById(R.id.productoServicioDescripcion);
                txtEnero = (TextInputEditText)builder.findViewById(R.id.productoServicioEnero);
                txtFebrero = (TextInputEditText)builder.findViewById(R.id.productoServicioFebrero);
                txtMarzo = (TextInputEditText)builder.findViewById(R.id.productoServicioMarzo);
                txtAbril = (TextInputEditText)builder.findViewById(R.id.productoServicioAbril);
                txtMayo = (TextInputEditText)builder.findViewById(R.id.productoServicioMayo);
                txtJunio = (TextInputEditText)builder.findViewById(R.id.productoServicioJunio);
                txtJulio = (TextInputEditText)builder.findViewById(R.id.productoServicioJulio);
                txtAgosto = (TextInputEditText)builder.findViewById(R.id.productoServicioAgosto);
                txtSeptiembre = (TextInputEditText)builder.findViewById(R.id.productoServicioSeptiembre);
                txtOctubre = (TextInputEditText)builder.findViewById(R.id.productoServicioOctubre);
                txtNoviembre = (TextInputEditText)builder.findViewById(R.id.productoServicioNoviembre);
                txtDiciembre = (TextInputEditText)builder.findViewById(R.id.productoServicioDiciembre);
                btnAceptar = (Button)builder.findViewById(R.id.productoServicioAceptar);
                btnCancelar = (Button)builder.findViewById(R.id.productoServicioCancelar);

                builder.setTitle("Producto o servicio");
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
                        String nombre = txtNombre.getText().toString();
                        String unidad = txtUnidad.getText().toString();
                        String descripcion = txtDescripcion.getText().toString();
                        String enero = txtEnero.getText().toString();
                        String febrero = txtFebrero.getText().toString();
                        String marzo = txtMarzo.getText().toString();
                        String abril = txtAbril.getText().toString();
                        String mayo = txtMayo.getText().toString();
                        String junio = txtJunio.getText().toString();
                        String julio = txtJulio.getText().toString();
                        String agosto = txtAgosto.getText().toString();
                        String septiembre = txtSeptiembre.getText().toString();
                        String octubre = txtOctubre.getText().toString();
                        String noviembre = txtNombre.getText().toString();
                        String diciembre = txtDiciembre.getText().toString();

                        if(nombre.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(unidad.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(descripcion.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(enero.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(febrero.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(marzo.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(abril.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(mayo.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(junio.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(julio.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(agosto.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(septiembre.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(octubre.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(noviembre.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(diciembre.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }

                        int enero_ = Integer.valueOf(txtEnero.getText().toString());
                        int febrero_ = Integer.valueOf(txtFebrero.getText().toString());
                        int marzo_ = Integer.valueOf(txtMarzo.getText().toString());
                        int abril_ = Integer.valueOf(txtAbril.getText().toString());
                        int mayo_ = Integer.valueOf(txtMayo.getText().toString());
                        int junio_ = Integer.valueOf(txtJunio.getText().toString());
                        int julio_ = Integer.valueOf(txtJulio.getText().toString());
                        int agosto_ = Integer.valueOf(txtAgosto.getText().toString());
                        int septiembre_ = Integer.valueOf(txtSeptiembre.getText().toString());
                        int octubre_ = Integer.valueOf(txtOctubre.getText().toString());
                        int noviembre_ = Integer.valueOf(txtNoviembre.getText().toString());
                        int diciembre_ = Integer.valueOf(txtDiciembre.getText().toString());

                        int promedio_ = (enero_ + febrero_ + marzo_ + abril_ + mayo_ + junio_ + julio_ + agosto_ + septiembre_ + octubre_ + noviembre_ + diciembre_)/12;
                        ProductoServicio productoServicio = new ProductoServicio(1, nombre, unidad, descripcion, promedio_, enero_, febrero_, marzo_, abril_, mayo_, junio_, julio_, agosto_, septiembre_, octubre_, noviembre_, diciembre_);

                        productoServicios.add(productoServicio);

                        // *********** LLENAMOS EL RECYCLER VIEW *****************************
                        adapter = new ProductosServiciosActivity.DataConfigAdapter(productoServicios, getApplicationContext());
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
