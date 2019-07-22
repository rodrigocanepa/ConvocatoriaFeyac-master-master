package com.colabora.soluciones.convocatoriafeyac;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.Db.Querys;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Concepto;
import com.colabora.soluciones.convocatoriafeyac.Modelos.ConceptoCotizacion;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Conceptos;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Cotizacion;
import com.colabora.soluciones.convocatoriafeyac.Modelos.TemplatePDF;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NuevaCotizacionActivity extends AppCompatActivity {

    // *************************** RECYCLER VIEW ************************

    private class DataConfigHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtConcepto;
        private TextView txtCantidad;
        private TextView txtPrecio;
        private TextView txtDescuento;
        private TextView txtImpuesto;
        private Button btnEditar;
        private Button btnEliminar;

        private List<Conceptos> conceptos = new ArrayList<Conceptos>();
        private Context ctx;

        public DataConfigHolder(View itemView, Context ctx, final List<Conceptos> conceptos) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.conceptos = conceptos;
            this.ctx = ctx;

            txtConcepto = (TextView) itemView.findViewById(R.id.txtItemCotizacionConcepto);
            txtCantidad = (TextView) itemView.findViewById(R.id.txtItemCotizacionCantidad);
            txtPrecio = (TextView) itemView.findViewById(R.id.txtItemCotizacionPrecio);
            txtDescuento = (TextView) itemView.findViewById(R.id.txtItemCotizacionDescuento);
            txtImpuesto = (TextView) itemView.findViewById(R.id.txtItemCotizacionImpuesto);
            btnEditar = (Button) itemView.findViewById(R.id.btnItemsEditar);
            btnEliminar = (Button) itemView.findViewById(R.id.btnItemsEliminar);

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog builder = new Dialog(NuevaCotizacionActivity.this);

                    // Get the layout inflater
                    //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    //final View formElementsView = inflater.inflate(R.layout.dialog_nuevo_concepto,
                    //      null, false);

                    builder.setContentView(R.layout.dialog_nuevo_concepto);

                    txtDialogConcepto = (TextInputEditText) builder.findViewById(R.id.txtDialogConcepto);
                    txtDialogCantidad = (TextInputEditText) builder.findViewById(R.id.txtDialogCantidad);
                    txtDialogPrecio = (TextInputEditText) builder.findViewById(R.id.txtDialogPrecio);
                    swDialogImpuestos = (Switch) builder.findViewById(R.id.swDialogImpuestos);
                    btnDialogAceptar = (Button) builder.findViewById(R.id.btnDialogAceptar);
                    btnDialogCancelar = (Button) builder.findViewById(R.id.btnDialogCancelar);
                    TextInputLayout textInputLayoutPrecio = (TextInputLayout)builder.findViewById(R.id.txtInputLayoutPrecio);
                    TextInputLayout textInputLayoutCantidad = (TextInputLayout)builder.findViewById(R.id.txtInputLayoutCantidad);

                    txtDialogPrecio.setText(conceptos.get(getAdapterPosition()).getPrecio());
                    txtDialogCantidad.setText(conceptos.get(getAdapterPosition()).getCantidad());
                    txtDialogConcepto.setText(conceptos.get(getAdapterPosition()).getConceptos());

                    swDialogImpuestos.setChecked(true);

                    if(radioButtonCantidad.isChecked()){
                        textInputLayoutCantidad.setHint("Cantidad (obligatorio)");
                        textInputLayoutPrecio.setHint("Precio (obligatorio)");
                    }
                    else{
                        textInputLayoutCantidad.setHint("Horas (obligatorio)");
                        textInputLayoutPrecio.setHint("Tarifa (obligatorio)");
                    }
                    builder.setTitle("Nuevo concepto");
                    // builder.setMessage("Ingrese los datos del nuevo concepto. Si desea ingresar un importe fraccionario utilice el punto decimal '.' para especificarlo, por ejemplo 50.50");

                    btnDialogCancelar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            builder.dismiss();
                        }
                    });

                    btnDialogAceptar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String concepto_ = txtDialogConcepto.getText().toString();
                            String cantidad_ = txtDialogCantidad.getText().toString();
                            String precio_ = txtDialogPrecio.getText().toString();
                            String descuento_ = "No aplica";
                            String descuento_pesos = "0";
                            String impuestos_ = "No aplica";
                            String impuestos_pesos = "0";

                            if(concepto_.isEmpty()){
                                Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(cantidad_.isEmpty()){
                                Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                                return;
                            }
                            if(precio_.isEmpty()){
                                Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                                return;
                            }

                            double cantidad_double = Double.valueOf(cantidad_);
                            double precio_double = Double.valueOf(precio_);
                            double importe_ = cantidad_double * precio_double;
                            String importe = String.valueOf(importe_);

                            if(swDialogImpuestos.isChecked()){
                                impuestos_ = "IVA: 16%";
                                double descuento_double = (cantidad_double * precio_double) * 1.16 - (cantidad_double * precio_double);
                                impuestos_pesos = String.valueOf(descuento_double);
                            }

                            conceptos.remove(getAdapterPosition());
                            Conceptos concepto = new Conceptos(concepto_,cantidad_,precio_,importe,impuestos_,impuestos_pesos);
                            conceptos.add(concepto);

                            // *********** LLENAMOS EL RECYCLER VIEW *****************************
                            adapter = new DataConfigAdapter(conceptos, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                            // ****************** ACTUALIZAMOS EL SUBTOTAL ********************
                            double subtotal = 0;
                            double iva = 0;
                            double descuento = 0;

                            for(int j = 0; j < conceptos.size(); j++){
                                subtotal += Double.valueOf(conceptos.get(j).getImporte());
                                iva += Double.valueOf(conceptos.get(j).getImpuestos_pesos());
                            }

                            if(swDescuento.isChecked()){
                                if(spinnerDescuento.getSelectedItem().equals("$")){
                                    descuento = Double.valueOf(editDescuento.getText().toString());
                                    txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                                }
                                else{
                                    double desc = Double.valueOf(editDescuento.getText().toString());
                                    double descReal = desc/100;
                                    descuento = subtotal * descReal;
                                    txtDescuentos.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                                }
                            }

                            double gastosEnvio = 0;

                            if(swGastosEnvio.isChecked()){
                                if (editGastosEnvio.getText().toString().length() > 0) {
                                    gastosEnvio = Double.valueOf(editGastosEnvio.getText().toString());
                                }
                            }

                            double total = subtotal - descuento + iva + gastosEnvio;
                            txtTotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(total));
                            txtSubtotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(subtotal));
                            txtGastosEnvio.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(gastosEnvio));
                            txtIVA.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(iva));

                            // ****************************************************************
                            builder.dismiss();
                        }
                    });
                /*builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {



                    }
                });

                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });*/

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

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final int position = getAdapterPosition();
                    AlertDialog.Builder builder = new AlertDialog.Builder(NuevaCotizacionActivity.this);
                    builder.setTitle("Eliminar concepto");
                    builder.setMessage("¿Estas seguro de querer elimnar este concepto?")
                            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    conceptos.remove(position);
                                    // *********** LLENAMOS EL RECYCLER VIEW *****************************
                                    adapter = new NuevaCotizacionActivity.DataConfigAdapter(conceptos, getApplicationContext());
                                    recyclerView.setAdapter(adapter);

                                    // ****************** ACTUALIZAMOS EL SUBTOTAL ********************
                                    double subtotal = 0;
                                    double iva = 0;
                                    double descuento = 0;

                                    for(int j = 0; j < conceptos.size(); j++){
                                        subtotal += Double.valueOf(conceptos.get(j).getImporte());
                                        iva += Double.valueOf(conceptos.get(j).getImpuestos_pesos());
                                    }

                                    if(swDescuento.isChecked()){
                                        if(spinnerDescuento.getSelectedItem().equals("$")){
                                            descuento = Double.valueOf(editDescuento.getText().toString());
                                            txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                                        }
                                        else{
                                            double desc = Double.valueOf(editDescuento.getText().toString());
                                            double descReal = desc/100;
                                            descuento = subtotal * descReal;
                                            txtDescuentos.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                                        }
                                    }

                                    double gastosEnvio = 0;

                                    if(swGastosEnvio.isChecked()){
                                        if (editGastosEnvio.getText().toString().length() > 0) {
                                            gastosEnvio = Double.valueOf(editGastosEnvio.getText().toString());
                                        }
                                    }

                                    double total = subtotal - descuento + iva + gastosEnvio;
                                    txtTotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(total));
                                    txtSubtotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(subtotal));
                                    txtGastosEnvio.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(gastosEnvio));
                                    txtIVA.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(iva));

                                    // ****************************************************************


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
            });

        }

        public void bindConfig(final Conceptos conceptos) {
            txtConcepto.setText(conceptos.getConceptos());
            txtCantidad.setText(conceptos.getCantidad());
            txtPrecio.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(Double.valueOf(conceptos.getPrecio())));
            txtImpuesto.setText(conceptos.getImpuestos());
            txtDescuento.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(Double.valueOf(conceptos.getImporte())));

        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();

        }

    }


    private class DataConfigAdapter extends RecyclerView.Adapter<NuevaCotizacionActivity.DataConfigHolder> {

        private List<Conceptos> conceptos;
        Context ctx;

        public DataConfigAdapter(List<Conceptos> conceptos, Context ctx ){
            this.conceptos = conceptos;
            this.ctx = ctx;
        }

        @Override
        public NuevaCotizacionActivity.DataConfigHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.items_conceptos, parent, false);
            return new NuevaCotizacionActivity.DataConfigHolder(view, ctx, conceptos);
        }

        @Override
        public void onBindViewHolder(final NuevaCotizacionActivity.DataConfigHolder holder, final int position) {
            holder.bindConfig(conceptos.get(position));

        }

        @Override
        public int getItemCount() {
            return conceptos.size();
        }

    }

    private NuevaCotizacionActivity.DataConfigAdapter adapter;
    private List<Conceptos> conceptos = new ArrayList<Conceptos>();
    private RecyclerView recyclerView;

    // ******************************************************************

    private EditText editNoFormato;
    private EditText editFecha;
    private RadioButton radioButtonCantidad;
    private RadioButton radioButtonHoras;
    private ImageView imgLogo;
    private Button btnAgregarConcepto;
    private Button btnVisualizar;
    private Switch swDescuento;
    private Switch swGastosEnvio;
    private EditText editDescuento;
    private EditText editGastosEnvio;
    private Spinner spinnerDescuento;
    private TextInputEditText txtNotasDestinatario;
    private TextInputEditText txtNotasAdmin;
    private TextInputEditText txtTerminos;
    private TextInputEditText txtNombreEncargado;
    private TextInputEditText txtNumeroEncargado;
    private TextInputEditText txtCargoEncargado;
    private EditText editVencimiento;

    private TextView txtGastosEnvio;
    private TextView txtCantidad;
    private TextView txtPrecio;

    private TextView txtSubtotal;
    private TextView txtDescuentos;
    private TextView txtIVA;
    private TextView txtTotal;

    // *******************************************************************

    private TextInputEditText txtDialogConcepto;
    private TextInputEditText txtDialogCantidad;
    private TextInputEditText txtDialogPrecio;
    private Button btnDialogAceptar;
    private Button btnDialogCancelar;
    private Switch swDialogImpuestos;

    private List<String> descuentos = new ArrayList<>();
    private static final String CERO = "0";
    private static final String BARRA = "/";

    // GENERACION DE PDF
    private TemplatePDF templatePDF;
    private String[]headerConceptos = {"Concepto", "Cantidad", "Precio", "Importe"};
    private String[]headerConceptos2 = {"Concepto", "Horas", "Tarifa", "Importe"};

    private AlertDialog alert;
    private Querys querys;

    public static final String EXTRA_FOLIO = "extra_folio";
    public static final String EXTRA_FOLIO_EDITAR = "extra_folio_editar";

    private String ex_folio = "";
    private String ex_folio_editar = "";

    private ImageView imgSubtotal;
    private ImageView imgConceptos;

    private SharedPreferences sharedPreferences;
    private String nombreEmpresa = "";
    private String cargoAdmin;
    private String nombreAdmin;
    private String telefonoAdmin;
    private String terminosCondiciones;

    private ListView listViewConceptos;
    private Button buscarConceptos;

    private List<Concepto> conceptosList;

    private NumberFormat formatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_cotizacion);
        setTitle("Nueva Cotización");
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        recyclerView = (RecyclerView)findViewById(R.id.recyclerConceptosCotizacion);
        editNoFormato = (EditText)findViewById(R.id.editCotizacionFolio);
        editFecha = (EditText)findViewById(R.id.editCotizacionFecha);
        radioButtonCantidad = (RadioButton)findViewById(R.id.radioCotizacionCantidad);
        radioButtonHoras = (RadioButton)findViewById(R.id.radioCotizacionHoras);
        imgLogo = (ImageView)findViewById(R.id.imgCotizacionLogo);
        btnAgregarConcepto = (Button)findViewById(R.id.btnCotizacionAddConcepto);
        btnVisualizar = (Button)findViewById(R.id.btnVisualizarCotizacion);
        swDescuento = (Switch)findViewById(R.id.swCotizacionDescuento);
        swGastosEnvio = (Switch)findViewById(R.id.swCotizacionGastosEnvio);
        editDescuento = (EditText)findViewById(R.id.editCotizacionDescuento);
        editGastosEnvio = (EditText)findViewById(R.id.editCotizacionGastosEnvio);
        spinnerDescuento = (Spinner)findViewById(R.id.spinnerCotizacionDescuento);
        txtNotasAdmin = (TextInputEditText)findViewById(R.id.txtCotizacionesNotasAdmin);
        txtNotasDestinatario = (TextInputEditText)findViewById(R.id.txtCotizacionNotas);
        txtTerminos = (TextInputEditText)findViewById(R.id.txtCotizacionesTerminos);
        txtGastosEnvio = (TextView)findViewById(R.id.txtCotizacionGastosEnvio);
        txtCantidad = (TextView)findViewById(R.id.txtContizacionCantidad);
        txtPrecio = (TextView)findViewById(R.id.txtCotizacionPrecio);
        editVencimiento = (EditText)findViewById(R.id.editCotizacionVence);
        txtNombreEncargado = (TextInputEditText)findViewById(R.id.txtCotizacionesNombreEncargado);
        txtNumeroEncargado = (TextInputEditText)findViewById(R.id.txtCotizacionesNumero);
        txtCargoEncargado = (TextInputEditText)findViewById(R.id.txtCotizacionesCargo);
        querys = new Querys(getApplicationContext());

        txtSubtotal = (TextView)findViewById(R.id.txtCotizacionSubtotal);
        txtDescuentos = (TextView)findViewById(R.id.txtCotizacionDescuento);
        txtIVA = (TextView)findViewById(R.id.txtCotizacionIVA);
        txtTotal = (TextView)findViewById(R.id.txtCotizacionTotal);
        imgConceptos = (ImageView)findViewById(R.id.info_Conceptos);
        imgSubtotal = (ImageView)findViewById(R.id.info_Subtotal);

        editDescuento.setEnabled(false);
        editGastosEnvio.setEnabled(false);
        radioButtonCantidad.setChecked(true);
        radioButtonHoras.setChecked(false);

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        txtNotasDestinatario.setText("En caso de requerir factura se necesitarán los datos fiscales de persona física o moral según sea el caso");

        formatter = new DecimalFormat("#,###");

        Intent i = getIntent();
        ex_folio = i.getStringExtra(EXTRA_FOLIO);
        ex_folio_editar = i.getStringExtra(EXTRA_FOLIO_EDITAR);


        // Leemos la memoria para ver que tarjetas se han creado
        sharedPreferences = getSharedPreferences("misDatos", 0);
        nombreEmpresa = sharedPreferences.getString("nombreEmpresa","");
        nombreAdmin = sharedPreferences.getString("nombreAdmin","");
        cargoAdmin = sharedPreferences.getString("cargoAdmin","");
        telefonoAdmin = sharedPreferences.getString("telefonoAdmin","");
        terminosCondiciones = sharedPreferences.getString("terminosCondiciones","");

        txtNombreEncargado.setText(nombreAdmin);
        txtNumeroEncargado.setText(telefonoAdmin);
        txtCargoEncargado.setText(cargoAdmin);
        txtTerminos.setText(terminosCondiciones);

        if(ex_folio_editar != null){
            Cotizacion cotizacion = querys.getCotizacionPorID(ex_folio_editar);
            editNoFormato.setText(cotizacion.getFolio());
            editFecha.setText(cotizacion.getFecha());
            editVencimiento.setText(cotizacion.getVencimiento());
            txtNombreEncargado.setText(cotizacion.getNombre_enc());
            txtNumeroEncargado.setText(cotizacion.getNumero_enc());
            txtCargoEncargado.setText(cotizacion.getCargo_enc());
            txtTerminos.setText(cotizacion.getTerminos());
            txtNotasDestinatario.setText(cotizacion.getNotasDestinatario());
            txtNotasAdmin.setText(cotizacion.getNotasAdmin());


            List<ConceptoCotizacion> conceptoCotizacions = new ArrayList<>();
            conceptoCotizacions = querys.getAllConceptosCotizacionesPorID(ex_folio_editar);

            for(int j = 0; j < conceptoCotizacions.size(); j++){
                conceptos.add(new Conceptos(conceptoCotizacions.get(j).getNombre(), conceptoCotizacions.get(j).getCantidad(), conceptoCotizacions.get(j).getPrecio(), conceptoCotizacions.get(j).getImporte(), conceptoCotizacions.get(j).getIva(), conceptoCotizacions.get(j).getIva_precio()));
            }

            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new NuevaCotizacionActivity.DataConfigAdapter(conceptos, getApplicationContext());
            recyclerView.setAdapter(adapter);

            txtTotal.setText(cotizacion.getTotal());
            txtSubtotal.setText(cotizacion.getSubtotal());
            txtGastosEnvio.setText(cotizacion.getEnvio());
            txtIVA.setText(cotizacion.getIva());
        }
        else{
            editNoFormato.setText(ex_folio);
        }


        File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
        if(!folder.exists())
            folder.mkdirs();
        File file = new File(folder, "logoEmpresa.png");

        if(file.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imgLogo.setImageBitmap(myBitmap);
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

        radioButtonCantidad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    radioButtonHoras.setChecked(false);
                    txtCantidad.setText("Cantidad");
                    txtPrecio.setText("Precio");
                }
            }
        });

        radioButtonHoras.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    radioButtonCantidad.setChecked(false);
                    txtCantidad.setText("Horas");
                    txtPrecio.setText("Tarifa");
                }
            }
        });

        imgConceptos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(NuevaCotizacionActivity.this);
                builder.setTitle("Información");
                builder.setMessage("Los conceptos pueden dividirse en dos grupos principales: Cantidad y horas, el primero hace referencia a aquellas cotizaciones por objetos o servicios tangibles, y el segundo es ideal para cotizaciones de cursos o mentorías que se cobren por una tarifa fija por hora.")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                            }
                        });// Create the AlertDialog object and return it
                builder.create();
                builder.show();
            }
        });

        imgSubtotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(NuevaCotizacionActivity.this);
                builder.setTitle("Información");
                builder.setMessage("El descuento de su cotización y los gastos de envío se aplican sobre el monto subtotal + IVA (si fuese el caso)")
                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {


                            }
                        });// Create the AlertDialog object and return it
                builder.create();
                builder.show();
            }
        });


        descuentos.add("%");
        descuentos.add("$");


        final ArrayAdapter<String> adapterCategoria = new ArrayAdapter<String>(
                NuevaCotizacionActivity.this, R.layout.support_simple_spinner_dropdown_item, descuentos);
        spinnerDescuento.setAdapter(adapterCategoria);

        Date date = Calendar.getInstance().getTime();
        final DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String today = formatter.format(date);

        swGastosEnvio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    editGastosEnvio.setEnabled(true);

                    // ****************** ACTUALIZAMOS EL SUBTOTAL ********************
                    if (editDescuento.getText().toString().length() > 0) {
                        double subtotal = 0;
                        double iva = 0;
                        double descuento = 0;

                        for (int j = 0; j < conceptos.size(); j++) {
                            subtotal += Double.valueOf(conceptos.get(j).getImporte());
                            iva += Double.valueOf(conceptos.get(j).getImpuestos_pesos());
                        }

                        if (swDescuento.isChecked()) {
                            if (spinnerDescuento.getSelectedItem().equals("$")) {
                                descuento = Double.valueOf(editDescuento.getText().toString());
                                txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                            } else {
                                double desc = Double.valueOf(editDescuento.getText().toString());
                                double descReal = desc / 100;
                                descuento = subtotal * descReal;
                                txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                            }
                        }

                        double gastosEnvio = 0;

                        if (editGastosEnvio.getText().toString().length() > 0) {
                            gastosEnvio = Double.valueOf(editGastosEnvio.getText().toString());
                        }

                        double total = subtotal - descuento + iva + gastosEnvio;
                        txtTotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(total));
                        txtSubtotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(subtotal));
                        txtGastosEnvio.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(gastosEnvio));
                        txtIVA.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(iva));

                        // ****************************************************************
                    }
                } else {
                    editGastosEnvio.setEnabled(false);
                    txtGastosEnvio.setText("$0.0");
                    // ****************** ACTUALIZAMOS EL SUBTOTAL ********************
                    if (editDescuento.getText().toString().length() > 0) {
                        double subtotal = 0;
                        double iva = 0;
                        double descuento = 0;

                        for (int j = 0; j < conceptos.size(); j++) {
                            subtotal += Double.valueOf(conceptos.get(j).getImporte());
                            iva += Double.valueOf(conceptos.get(j).getImpuestos_pesos());
                        }

                        if (swDescuento.isChecked()) {
                            if (spinnerDescuento.getSelectedItem().equals("$")) {
                                descuento = Double.valueOf(editDescuento.getText().toString());
                                txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                            } else {
                                double desc = Double.valueOf(editDescuento.getText().toString());
                                double descReal = desc / 100;
                                descuento = subtotal * descReal;
                                txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                            }
                        }

                        double gastosEnvio = 0;

                        double total = subtotal - descuento + iva + gastosEnvio;
                        txtTotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(total));
                        txtSubtotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(subtotal));
                        txtGastosEnvio.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(gastosEnvio));
                        txtIVA.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(iva));

                        // ****************************************************************
                    }
                }
            }
        });

        swDescuento.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    editDescuento.setEnabled(true);
                    // ****************** ACTUALIZAMOS EL SUBTOTAL ********************
                    if(editDescuento.getText().toString().length() > 0){
                        double subtotal = 0;
                        double iva = 0;
                        double descuento = 0;

                        for(int j = 0; j < conceptos.size(); j++){
                            subtotal += Double.valueOf(conceptos.get(j).getImporte());
                            iva += Double.valueOf(conceptos.get(j).getImpuestos_pesos());
                        }

                        if(swDescuento.isChecked()){
                            if(spinnerDescuento.getSelectedItem().equals("$")){
                                descuento = Double.valueOf(editDescuento.getText().toString());
                                txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                            }
                            else{
                                double desc = Double.valueOf(editDescuento.getText().toString());
                                double descReal = desc/100;
                                descuento = subtotal * descReal;
                                txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                            }
                        }

                        double gastosEnvio = 0;

                        if(swGastosEnvio.isChecked()){
                            if (editGastosEnvio.getText().toString().length() > 0) {
                                gastosEnvio = Double.valueOf(editGastosEnvio.getText().toString());
                            }
                        }


                        double total = subtotal - descuento + iva + gastosEnvio;
                        txtTotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(total));
                        txtSubtotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(subtotal));
                        txtGastosEnvio.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(gastosEnvio));
                        txtIVA.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(iva));

                        // ****************************************************************
                    }
                    else{
                        editDescuento.setText("0");
                    }
                }
                else{
                    editDescuento.setEnabled(false);
                    txtDescuentos.setText("-$0.00");

                    double subtotal = 0;
                    double iva = 0;
                    double descuento = 0;

                    for(int j = 0; j < conceptos.size(); j++){
                        subtotal += Double.valueOf(conceptos.get(j).getImporte());
                        iva += Double.valueOf(conceptos.get(j).getImpuestos_pesos());
                    }

                    if(swDescuento.isChecked()){
                        if(spinnerDescuento.getSelectedItem().equals("$")){
                            descuento = Double.valueOf(editDescuento.getText().toString());
                            txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                        }
                        else{
                            double desc = Double.valueOf(editDescuento.getText().toString());
                            double descReal = desc/100;
                            descuento = subtotal * descReal;
                            txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                        }
                    }

                    double gastosEnvio = 0;

                    if(swGastosEnvio.isChecked()){
                        if (editGastosEnvio.getText().toString().length() > 0) {
                            gastosEnvio = Double.valueOf(editGastosEnvio.getText().toString());
                        }
                    }

                    double total = subtotal - descuento + iva + gastosEnvio;
                    txtTotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(total));
                    txtSubtotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(subtotal));
                    txtGastosEnvio.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(gastosEnvio));
                    txtIVA.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(iva));
                }
            }
        });

        if(!swDescuento.isChecked()){
            txtDescuentos.setText("-$0.00");
        }
        if(!swGastosEnvio.isChecked()){
            txtGastosEnvio.setText("$0.00");
        }


        spinnerDescuento.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // ****************** ACTUALIZAMOS EL SUBTOTAL ********************
                if(editDescuento.getText().toString().length() > 0){
                    double subtotal = 0;
                    double iva = 0;
                    double descuento = 0;

                    for(int j = 0; j < conceptos.size(); j++){
                        subtotal += Double.valueOf(conceptos.get(j).getImporte());
                        iva += Double.valueOf(conceptos.get(j).getImpuestos_pesos());
                    }

                    if(swDescuento.isChecked()){
                        if(spinnerDescuento.getSelectedItem().equals("$")){
                            descuento = Double.valueOf(editDescuento.getText().toString());
                            txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                        }
                        else{
                            double desc = Double.valueOf(editDescuento.getText().toString());
                            double descReal = desc/100;
                            descuento = subtotal * descReal;
                            txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                        }
                    }

                    double gastosEnvio = 0;

                    if(swGastosEnvio.isChecked()){
                        if (editGastosEnvio.getText().toString().length() > 0) {
                            gastosEnvio = Double.valueOf(editGastosEnvio.getText().toString());
                        }
                    }

                    double total = subtotal - descuento + iva + gastosEnvio;
                    txtTotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(total));
                    txtSubtotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(subtotal));
                    txtGastosEnvio.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(gastosEnvio));
                    txtIVA.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(iva));

                    // ****************************************************************
                }
                else{
                    editDescuento.setText("0");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        editFecha.setText(today);

        editFecha.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.onTouchEvent(motionEvent);
                InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return true;
            }

        });

        editVencimiento.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.onTouchEvent(motionEvent);
                InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
                return true;
            }

        });

        editFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Calendario para obtener fecha & hora
                final Calendar c = Calendar.getInstance();

                //Variables para obtener la fecha
                final int mes = c.get(Calendar.MONTH);
                final int dia = c.get(Calendar.DAY_OF_MONTH);
                final int anio = c.get(Calendar.YEAR);

                DatePickerDialog recogerFecha = new DatePickerDialog(NuevaCotizacionActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                        final int mesActual = month + 1;
                        //Formateo el día obtenido: antepone el 0 si son menores de 10
                        String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                        //Formateo el mes obtenido: antepone el 0 si son menores de 10
                        String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                        //Muestro la fecha con el formato deseado
                        editFecha.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);


                    }
                    //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
                    /**
                     *También puede cargar los valores que usted desee
                     */
                },anio, mes, dia);
                //Muestro el widget
                recogerFecha.show();
            }
        });

        editVencimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editFecha.getText().toString().length() > 0){

                    //Calendario para obtener fecha & hora
                    final Calendar c = Calendar.getInstance();

                    //Variables para obtener la fecha
                    final int mes = c.get(Calendar.MONTH);
                    final int dia = c.get(Calendar.DAY_OF_MONTH);
                    final int anio = c.get(Calendar.YEAR);

                    DatePickerDialog recogerFecha = new DatePickerDialog(NuevaCotizacionActivity.this, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, final int year, int month, int dayOfMonth) {
                            //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                            final int mesActual = month + 1;
                            //Formateo el día obtenido: antepone el 0 si son menores de 10
                            final String diaFormateado = (dayOfMonth < 10)? CERO + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                            //Formateo el mes obtenido: antepone el 0 si son menores de 10
                            final String mesFormateado = (mesActual < 10)? CERO + String.valueOf(mesActual):String.valueOf(mesActual);
                            //Muestro la fecha con el formato deseado
                            editVencimiento.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);



                        }
                        //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
                        /**
                         *También puede cargar los valores que usted desee
                         */
                    },anio, mes, dia);
                    //Muestro el widget

                    Date date;
                    long startDate = 0;
                    try {
                        String dateString = editFecha.getText().toString();
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        date = sdf.parse(dateString);

                        startDate = date.getTime();

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    recogerFecha.getDatePicker().setMinDate(startDate);
                    recogerFecha.show();

                }
                else{
                    Toast.makeText(getApplicationContext(), "Debes seleccionar primero la fecha de la cotización", Toast.LENGTH_LONG).show();
                }
            }
        });

        editDescuento.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ****************** ACTUALIZAMOS EL SUBTOTAL ********************
                if(editDescuento.getText().toString().length() > 0){
                    double subtotal = 0;
                    double iva = 0;
                    double descuento = 0;

                    for(int j = 0; j < conceptos.size(); j++){
                        subtotal += Double.valueOf(conceptos.get(j).getImporte());
                        iva += Double.valueOf(conceptos.get(j).getImpuestos_pesos());
                    }

                    if(swDescuento.isChecked()){
                        if(spinnerDescuento.getSelectedItem().equals("$")){
                            descuento = Double.valueOf(editDescuento.getText().toString());
                            txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                        }
                        else{
                            double desc = Double.valueOf(editDescuento.getText().toString());
                            double descReal = desc/100;
                            descuento = subtotal * descReal;
                            txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                        }
                    }

                    double gastosEnvio = 0;

                    if(swGastosEnvio.isChecked()){
                        if (editGastosEnvio.getText().toString().length() > 0) {
                            gastosEnvio = Double.valueOf(editGastosEnvio.getText().toString());
                        }
                    }


                    double total = subtotal - descuento + iva + gastosEnvio;
                    txtTotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(total));
                    txtSubtotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(subtotal));
                    txtGastosEnvio.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(gastosEnvio));
                    txtIVA.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(iva));

                    // ****************************************************************
                }
                else {
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        editGastosEnvio.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ****************** ACTUALIZAMOS EL SUBTOTAL ********************
                if(editGastosEnvio.getText().toString().length() > 0){
                    double subtotal = 0;
                    double iva = 0;
                    double descuento = 0;

                    for(int j = 0; j < conceptos.size(); j++){
                        subtotal += Double.valueOf(conceptos.get(j).getImporte());
                        iva += Double.valueOf(conceptos.get(j).getImpuestos_pesos());
                    }

                    if(swDescuento.isChecked()){
                        if(spinnerDescuento.getSelectedItem().equals("$")){
                            descuento = Double.valueOf(editDescuento.getText().toString());
                            txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                        }
                        else{
                            double desc = Double.valueOf(editDescuento.getText().toString());
                            double descReal = desc/100;
                            descuento = subtotal * descReal;
                            txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                        }
                    }

                    double gastosEnvio = 0;

                    if(swGastosEnvio.isChecked()){
                        if (editGastosEnvio.getText().toString().length() > 0) {
                            gastosEnvio = Double.valueOf(editGastosEnvio.getText().toString());
                        }
                    }


                    double total = subtotal - descuento + iva + gastosEnvio;
                    txtTotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(total));
                    txtSubtotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(subtotal));
                    txtGastosEnvio.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(gastosEnvio));
                    txtIVA.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(iva));

                    // ****************************************************************
                }
                else{
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        btnAgregarConcepto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog builder = new Dialog(NuevaCotizacionActivity.this);

                // Get the layout inflater
                //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //final View formElementsView = inflater.inflate(R.layout.dialog_nuevo_concepto,
                  //      null, false);

                builder.setContentView(R.layout.dialog_nuevo_concepto);

                txtDialogConcepto = (TextInputEditText) builder.findViewById(R.id.txtDialogConcepto);
                txtDialogCantidad = (TextInputEditText) builder.findViewById(R.id.txtDialogCantidad);
                txtDialogPrecio = (TextInputEditText) builder.findViewById(R.id.txtDialogPrecio);
                swDialogImpuestos = (Switch) builder.findViewById(R.id.swDialogImpuestos);
                btnDialogAceptar = (Button) builder.findViewById(R.id.btnDialogAceptar);
                btnDialogCancelar = (Button) builder.findViewById(R.id.btnDialogCancelar);
                buscarConceptos = (Button) builder.findViewById(R.id.buscarConceptops);
                TextInputLayout textInputLayoutPrecio = (TextInputLayout)builder.findViewById(R.id.txtInputLayoutPrecio);
                TextInputLayout textInputLayoutCantidad = (TextInputLayout)builder.findViewById(R.id.txtInputLayoutCantidad);

                swDialogImpuestos.setChecked(true);

                if(radioButtonCantidad.isChecked()){
                    textInputLayoutCantidad.setHint("Cantidad (obligatorio)");
                    textInputLayoutPrecio.setHint("Precio (obligatorio)");
                }
                else{
                    textInputLayoutCantidad.setHint("Horas (obligatorio)");
                    textInputLayoutPrecio.setHint("Tarifa (obligatorio)");
                }
                builder.setTitle("Nuevo concepto");
               // builder.setMessage("Ingrese los datos del nuevo concepto. Si desea ingresar un importe fraccionario utilice el punto decimal '.' para especificarlo, por ejemplo 50.50");

                btnDialogCancelar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder.dismiss();
                    }
                });
                buscarConceptos.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onClick(View v) {
                        //builder.dismiss();

                        conceptosList = querys.getAllConceptos();
                        if(conceptosList.size() > 0 ){
                            final Dialog builder = new Dialog(NuevaCotizacionActivity.this);

                            // Get the layout inflater
                            //LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            //final View formElementsView = inflater.inflate(R.layout.dialog_nuevo_concepto,
                            //      null, false);

                            builder.setContentView(R.layout.dialog_lista_conceptos);

                            listViewConceptos = (ListView) builder.findViewById(R.id.listViewConceptos);


                            MyListAdapter adaptador = new MyListAdapter(NuevaCotizacionActivity.this, R.layout.list_conceptos, conceptosList);
                            listViewConceptos.setAdapter(adaptador);

                            listViewConceptos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    txtDialogConcepto.setText(conceptosList.get(position).getNombre());
                                    txtDialogPrecio.setText(conceptosList.get(position).getPrecio());
                                    builder.dismiss();
                                }
                            });

                            builder.setTitle("Conceptos");
                            // Inflate and set the layout for the dialog
                            // Pass null as the parent view because its going in the dialog layout
                            DisplayMetrics metrics = getResources().getDisplayMetrics();
                            int width = metrics.widthPixels;
                            int height = metrics.heightPixels;

                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                builder.create();
                            }
                            builder.show();
                            builder.getWindow().setLayout((7 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Aún no tiene conceptos almacenados en su base de datos", Toast.LENGTH_LONG).show();
                        }


                    }
                });

                btnDialogAceptar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String concepto_ = txtDialogConcepto.getText().toString();
                        String cantidad_ = txtDialogCantidad.getText().toString();
                        String precio_ = txtDialogPrecio.getText().toString();
                        String descuento_ = "No aplica";
                        String descuento_pesos = "0";
                        String impuestos_ = "No aplica";
                        String impuestos_pesos = "0";

                        if(concepto_.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(cantidad_.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }
                        if(precio_.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Debes llenar los campos obligatorios", Toast.LENGTH_LONG).show();
                            return;
                        }

                        double cantidad_double = Double.valueOf(cantidad_);
                        double precio_double = Double.valueOf(precio_);
                        double importe_ = cantidad_double * precio_double;
                        String importe = String.valueOf(importe_);

                        if(swDialogImpuestos.isChecked()){
                            impuestos_ = "IVA: 16%";
                            double descuento_double = (cantidad_double * precio_double) * 1.16 - (cantidad_double * precio_double);
                            impuestos_pesos = String.valueOf(descuento_double);
                        }

                        Conceptos concepto = new Conceptos(concepto_,cantidad_,precio_,importe,impuestos_,impuestos_pesos);
                        conceptos.add(concepto);

                        // *********** LLENAMOS EL RECYCLER VIEW *****************************
                        adapter = new DataConfigAdapter(conceptos, getApplicationContext());
                        recyclerView.setAdapter(adapter);

                        // ****************** ACTUALIZAMOS EL SUBTOTAL ********************
                        double subtotal = 0;
                        double iva = 0;
                        double descuento = 0;

                        for(int j = 0; j < conceptos.size(); j++){
                            subtotal += Double.valueOf(conceptos.get(j).getImporte());
                            iva += Double.valueOf(conceptos.get(j).getImpuestos_pesos());
                        }

                        if(swDescuento.isChecked()){
                            if(spinnerDescuento.getSelectedItem().equals("$")){
                                descuento = Double.valueOf(editDescuento.getText().toString());
                                txtDescuentos.setText("-$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                            }
                            else{
                                double desc = Double.valueOf(editDescuento.getText().toString());
                                double descReal = desc/100;
                                descuento = subtotal * descReal;
                                txtDescuentos.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(descuento));
                            }
                        }

                        double gastosEnvio = 0;

                        if(swGastosEnvio.isChecked()){
                            if (editGastosEnvio.getText().toString().length() > 0) {
                                gastosEnvio = Double.valueOf(editGastosEnvio.getText().toString());
                            }
                        }
                        double total = subtotal - descuento + iva + gastosEnvio;
                      //  String formattedNumber = formatter.format(total);
                        txtTotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(total));
                        txtSubtotal.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(subtotal));
                        txtGastosEnvio.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(gastosEnvio));
                        txtIVA.setText("$" + NumberFormat.getNumberInstance(Locale.US).format(iva));

                        // ****************************************************************
                        builder.dismiss();
                    }
                });
                DisplayMetrics metrics = getResources().getDisplayMetrics();
                int width = metrics.widthPixels;
                int height = metrics.heightPixels;

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder.create();
                }
                builder.show();
                builder.getWindow().setLayout((7 * width)/7, ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });


        btnVisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String folio = editNoFormato.getText().toString();
                String fecha = editFecha.getText().toString();
                String notasDestinatario = txtNotasDestinatario.getText().toString();
                String notasAdmin = txtNotasAdmin.getText().toString();
                String terminos = txtTerminos.getText().toString();

                String subtotal = txtSubtotal.getText().toString();
                String iva = txtIVA.getText().toString();
                String envio = txtGastosEnvio.getText().toString();
                String descuento = txtDescuentos.getText().toString();
                String total = txtTotal.getText().toString();

                String nombre_enc = txtNombreEncargado.getText().toString();
                String numero_enc = txtNumeroEncargado.getText().toString();
                String cargo_enc = txtCargoEncargado.getText().toString();
                String vencimiento = editVencimiento.getText().toString();

                if(folio.isEmpty()){
                    editNoFormato.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes llenar los campos requeridos", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(fecha.isEmpty()){
                    editFecha.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes llenar los campos requeridos", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(notasDestinatario.isEmpty()){
                    txtNotasDestinatario.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes llenar los campos requeridos", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(notasAdmin.isEmpty()){
                    txtNotasAdmin.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes llenar los campos requeridos", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(terminos.isEmpty()){
                    txtTerminos.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes llenar los campos requeridos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(vencimiento.isEmpty()){
                    editVencimiento.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes llenar los campos requeridos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(nombre_enc.isEmpty()){
                    txtNombreEncargado.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes llenar los campos requeridos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(numero_enc.isEmpty()){
                    txtNumeroEncargado.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes llenar los campos requeridos", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(cargo_enc.isEmpty()){
                    txtCargoEncargado.setError("Ingresa un valor");
                    Toast.makeText(getApplicationContext(), "Debes llenar los campos requeridos", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(conceptos.size() == 0){
                    Toast.makeText(getApplicationContext(), "Debes ingresar por lo menos un concepto para visualizar la cotización", Toast.LENGTH_LONG).show();
                    return;
                }

                Cotizacion cotizacion = new Cotizacion(folio, fecha, subtotal, iva, envio, descuento, total, notasDestinatario, terminos, nombre_enc, cargo_enc, numero_enc, vencimiento, notasAdmin);


                if(ex_folio_editar != null){
                    querys.updateCotizacion(cotizacion, ex_folio_editar);

                    List<ConceptoCotizacion> list = querys.getAllConceptosCotizacionesPorID(ex_folio_editar);
                    for (int i = 0; i < list.size(); i++){
                        querys.deleteConceptoCotizacion(String.valueOf(list.get(i).getId()));
                    }
                    for (int i = 0; i < conceptos.size(); i++){
                        ConceptoCotizacion conceptoCotizacion = new ConceptoCotizacion(Integer.valueOf(folio), conceptos.get(i).getConceptos(), conceptos.get(i).getCantidad(), conceptos.get(i).getPrecio(), conceptos.get(i).getImporte(),"",conceptos.get(i).getImpuestos_pesos());
                        querys.insertConceptoCotizacion(conceptoCotizacion);
                    }
                }
                else{
                    querys.insertCotizacion(cotizacion);
                    for (int i = 0; i < conceptos.size(); i++){
                        ConceptoCotizacion conceptoCotizacion = new ConceptoCotizacion(Integer.valueOf(folio), conceptos.get(i).getConceptos(), conceptos.get(i).getCantidad(), conceptos.get(i).getPrecio(), conceptos.get(i).getImporte(),"",conceptos.get(i).getImpuestos_pesos());
                        querys.insertConceptoCotizacion(conceptoCotizacion);
                    }
                    ex_folio_editar = String.valueOf(folio);
                }


                String tipoDescuento = "";
                if(!descuento.equals("-$0.00")) {
                    if (spinnerDescuento.getSelectedItem().toString().equals("%")) {
                        tipoDescuento = "(" + String.valueOf(editDescuento.getText().toString()) + "%)";
                    } else {
                        tipoDescuento = ("($)");
                    }
                }

                // *********** Guardamos los principales datos de los nuevos usuarios *************
                sharedPreferences = getSharedPreferences("misDatos", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("terminosCondiciones", terminos);
                editor.commit();
                generacionCotizacionPDF(folio, fecha, subtotal, iva, envio, descuento, total, notasDestinatario, terminos, nombre_enc, cargo_enc, numero_enc, vencimiento, tipoDescuento);
            }
        });
    }

    private void generacionCotizacionPDF(String folio, String fecha, String subtotal, String iva, String envio, String descuento, String total, String notas, String terminos, String atte, String cargo, String telefono, String vencimiento, String tipoDescuento){

        imgLogo.setDrawingCacheEnabled(true);
        imgLogo.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        //imgToUpload.layout(0, 0, imgToUpload.getMeasuredWidth(), imgToUpload.getMeasuredHeight());
        imgLogo.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(imgLogo.getDrawingCache());

        templatePDF = new TemplatePDF(getApplicationContext());
        templatePDF.openDocument("Cotizacion_" + folio);
        templatePDF.addMetaData("Cotizacion", "Pyme Assistant", "Soluciones Colabora");
        //templatePDF.addImage(getApplicationContext());
        templatePDF.createTableWithFoto(getApplicationContext(), bitmap, "COTIZACIÓN", folio, fecha, vencimiento, nombreEmpresa);
        templatePDF.addLine();

        templatePDF.addSectionsCenter("Estimado Cliente");
        templatePDF.addParagraphCenter("Por este medio le presentamos la siguiente propuesta económica");

        ArrayList<String[]> rowsConceptops = new ArrayList<>();
        for(int i = 0; i < conceptos.size(); i++){
            double precio = Double.valueOf(conceptos.get(i).getPrecio());
            double importe = Double.valueOf(conceptos.get(i).getImporte());
            String precio_ = NumberFormat.getNumberInstance(Locale.US).format(precio);
            String importe_ = NumberFormat.getNumberInstance(Locale.US).format(importe);
            rowsConceptops.add(new String[] {conceptos.get(i).getConceptos(), conceptos.get(i).getCantidad(),  "$" + precio_, "$" + importe_});
        }

        if(radioButtonCantidad.isChecked()){
            templatePDF.creaTableCotizacion(headerConceptos, rowsConceptops);
        }
        else{
            templatePDF.creaTableCotizacion(headerConceptos2, rowsConceptops);
        }

        templatePDF.createTableTotal(subtotal, iva, envio, descuento, total, tipoDescuento);
        templatePDF.addLine();
        templatePDF.addSections("Notas");
        templatePDF.addParagraph(notas);
        templatePDF.addSections("Términos y condiciones");
        templatePDF.addParagraph(terminos);
        templatePDF.addLine();
        templatePDF.addParagraph("De antemano agradecemos sus atenciones y quedamos en espera de su pronta respuesta");
        templatePDF.addParagraphCenter("Atte." + atte);
        templatePDF.addParagraphCenter(cargo);
        templatePDF.addParagraphCenter(telefono);
        // templatePDF.addParagraph(nombreConsultor);
       // templatePDF.addSections("Experiencia Académica");
       // templatePDF.createTableWithTheSameLength(headerExpAcaemica, rowsAcedemica);
       // templatePDF.addSections("Experiencia Profesional");
       // templatePDF.createTableWithTheSameLength(headerExpProfesional, rowsProfesional);
        templatePDF.closeDocument();

        templatePDF.viewPDF(folio);
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();

       android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(NuevaCotizacionActivity.this);
        builder.setMessage("¿Desea salir de esta cotización? Si lo hace antes de finalizar sus avances se perderán")
                .setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent i = new Intent(NuevaCotizacionActivity.this, MainActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });// Create the AlertDialog object and return it
        builder.create();
        builder.show();

    }
}
