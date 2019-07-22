package com.colabora.soluciones.convocatoriafeyac;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.Db.Querys;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Concepto;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Conceptos;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Cotizacion;
import com.colabora.soluciones.convocatoriafeyac.Modelos.TemplatePDF;
import com.colabora.soluciones.convocatoriafeyac.Modelos.VerPDFDiagActivity;
import com.vansuita.pickimage.bean.PickResult;
import com.vansuita.pickimage.bundle.PickSetup;
import com.vansuita.pickimage.dialog.PickImageDialog;
import com.vansuita.pickimage.listeners.IPickCancel;
import com.vansuita.pickimage.listeners.IPickResult;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CotizacionesActivity extends AppCompatActivity {

    // *************************** RECYCLER VIEW ************************

    private class DataConfigHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtNotas;
        private TextView txtMonto;
        private TextView txtFecha;
        private LinearLayout linearLayout;

        private List<Cotizacion> cotizaciones = new ArrayList<Cotizacion>();
        private Context ctx;

        public DataConfigHolder(View itemView, Context ctx, final List<Cotizacion> cotizaciones) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.cotizaciones = cotizaciones;
            this.ctx = ctx;

            txtNotas = (TextView) itemView.findViewById(R.id.item_cotizacion_notas);
            txtFecha = (TextView) itemView.findViewById(R.id.item_cotizacion_fecha);
            txtMonto = (TextView) itemView.findViewById(R.id.item_cotizacion_monto);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.item_layoutCotizaciones);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    PopupMenu popup = new PopupMenu(CotizacionesActivity.this, linearLayout);
                    //Inflating the Popup using xml file
                    popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                    //registering popup with OnMenuItemClickListener
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        public boolean onMenuItemClick(MenuItem item) {
                            final int pos = getAdapterPosition();
                            if(item.getTitle().equals("Ver/Compartir")){
                                File folder = new  File(Environment.getExternalStorageDirectory().toString(), "PymeAssitant");
                                if(!folder.exists())
                                    folder.mkdirs();
                                File file = new File(folder, "Cotizacion_"+ cotizaciones.get(pos).getFolio() + ".pdf");                    //old way

                                Intent intent = new Intent(getApplicationContext(), VerPDFActivity.class);
                                intent.putExtra("path", file.getAbsolutePath());
                                intent.putExtra("tipo", cotizaciones.get(pos).getFolio());
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                getApplicationContext().startActivity(intent);
                            }
                            else if(item.getTitle().equals("Editar")){
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CotizacionesActivity.this);
                                builder.setMessage("¿Desea editar esta cotización?")
                                        .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent i = new Intent(CotizacionesActivity.this, NuevaCotizacionActivity.class);
                                                i.putExtra(NuevaCotizacionActivity.EXTRA_FOLIO_EDITAR, cotizaciones.get(pos).getFolio());
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
                            else if(item.getTitle().equals("Eliminar")){
                                android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(CotizacionesActivity.this);
                                builder.setMessage("¿Desea eliminar esta cotización?")
                                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                querys.deleteCotizacion(String.valueOf(cotizaciones.get(pos).getId()));
                                                actualizar();
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
                           // Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                            return true;
                        }
                    });

                    popup.show();//showing popup menu
                }
            });

        }

        public void bindConfig(final Cotizacion cotizacion) {
            txtNotas.setText(cotizacion.getNotasAdmin());
            txtFecha.setText(cotizacion.getFecha());
            txtMonto.setText(cotizacion.getTotal());
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();

        }

    }


    private class DataConfigAdapter extends RecyclerView.Adapter<CotizacionesActivity.DataConfigHolder> {

        private List<Cotizacion> cotizaciones;
        Context ctx;

        public DataConfigAdapter(List<Cotizacion> cotizaciones, Context ctx ){
            this.cotizaciones = cotizaciones;
            this.ctx = ctx;
        }

        @Override
        public CotizacionesActivity.DataConfigHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_cotizacion, parent, false);
            return new CotizacionesActivity.DataConfigHolder(view, ctx, cotizaciones);
        }

        @Override
        public void onBindViewHolder(final CotizacionesActivity.DataConfigHolder holder, final int position) {
            holder.bindConfig(cotizaciones.get(position));

        }

        @Override
        public int getItemCount() {
            return cotizaciones.size();
        }

    }

    private CotizacionesActivity.DataConfigAdapter adapter;
    private List<Cotizacion> cotizaciones = new ArrayList<Cotizacion>();
    private RecyclerView recyclerView;

    // ******************************************************************



    private FloatingActionButton floatingActionButton;
    private Querys querys;
    // GENERACION DE PDF
    private TemplatePDF templatePDF;
    private String[]headerConceptos = {"Concepto", "Cantidad", "Precio", "Impuestos", "Importe"};
    private String[]headerConceptos2 = {"Concepto", "Horas", "Tarifa", "Impuestos", "Importe"};

    private List<String> meses = new ArrayList<>();
    private List<String> anios = new ArrayList<>();
    private Spinner spinnerMes;
    private Spinner spinnerAnio;
    private List<Cotizacion> cotizacionesFiltradas = new ArrayList<Cotizacion>();
    private Button btnVerMisConceptos;

    private TextInputEditText editNuevoConcepto;
    private ListView listViewConceptos;
    private List<Concepto> conceptos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cotizaciones);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.fabAddCotizacion);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerCotizaciones);
        spinnerAnio = (Spinner)findViewById(R.id.spinnerCotizacionAnio);
        spinnerMes = (Spinner)findViewById(R.id.spinnerCotizacionMes);
        btnVerMisConceptos = (Button)findViewById(R.id.btnVerMisConceptos);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        querys = new Querys(getApplicationContext());

        meses.add("Mostrar todos");
        meses.add("Enero");
        meses.add("Febrero");
        meses.add("Marzo");
        meses.add("Abril");
        meses.add("Mayo");
        meses.add("Junio");
        meses.add("Julio");
        meses.add("Agosto");
        meses.add("Septiembre");
        meses.add("Octubre");
        meses.add("Noviembre");
        meses.add("Diciembre");

        anios.add("Mostrar todos");
        anios.add("2018");
        anios.add("2019");
        anios.add("2020");
        anios.add("2021");

        ArrayAdapter<String> adapterCategoria = new ArrayAdapter<String>(
                CotizacionesActivity.this, R.layout.support_simple_spinner_dropdown_item, meses);
        spinnerMes.setAdapter(adapterCategoria);

        ArrayAdapter<String> adapterCategoria2 = new ArrayAdapter<String>(
                CotizacionesActivity.this, R.layout.support_simple_spinner_dropdown_item, anios);
        spinnerAnio.setAdapter(adapterCategoria2);

        spinnerMes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mes = spinnerMes.getSelectedItem().toString();
                String anio = spinnerAnio.getSelectedItem().toString();

                if(mes.equals("Enero")){
                    if(!anio.equals("Mostrar todos")){
                        actualizarListaPorFiltro("01", anio);
                    }
                    else{
                        actualizarListaPorFiltroMes("01");
                    }
                }
                else if(mes.equals("Febrero")){
                    if(!anio.equals("Mostrar todos")){
                        actualizarListaPorFiltro("02", anio);
                    }
                    else{
                        actualizarListaPorFiltroMes("02");
                    }
                }
                else if(mes.equals("Marzo")){
                    if(!anio.equals("Mostrar todos")){
                        actualizarListaPorFiltro("03", anio);
                    }
                    else{
                        actualizarListaPorFiltroMes("03");
                    }
                }
                else if(mes.equals("Abril")){
                    if(!anio.equals("Mostrar todos")){
                        actualizarListaPorFiltro("04", anio);
                    }
                    else{
                        actualizarListaPorFiltroMes("04");
                    }
                }
                else if(mes.equals("Mayo")){
                    if(!anio.equals("Mostrar todos")){
                        actualizarListaPorFiltro("05", anio);
                    }
                    else{
                        actualizarListaPorFiltroMes("05");
                    }
                }
                else if(mes.equals("Junio")){
                    if(!anio.equals("Mostrar todos")){
                        actualizarListaPorFiltro("06", anio);
                    }
                    else{
                        actualizarListaPorFiltroMes("06");
                    }
                }
                else if(mes.equals("Julio")){
                    if(!anio.equals("Mostrar todos")){
                        actualizarListaPorFiltro("07", anio);
                    }
                    else{
                        actualizarListaPorFiltroMes("07");
                    }
                }
                else if(mes.equals("Agosto")){
                    if(!anio.equals("Mostrar todos")){
                        actualizarListaPorFiltro("08", anio);
                    }
                    else{
                        actualizarListaPorFiltroMes("08");
                    }
                }
                else if(mes.equals("Septiembre")){
                    if(!anio.equals("Mostrar todos")){
                        actualizarListaPorFiltro("09", anio);
                    }
                    else{
                        actualizarListaPorFiltroMes("09");
                    }
                }
                else if(mes.equals("Octubre")){
                    if(!anio.equals("Mostrar todos")){
                        actualizarListaPorFiltro("10", anio);
                    }
                    else{
                        actualizarListaPorFiltroMes("10");
                    }
                }
                else if(mes.equals("Noviembre")){
                    if(!anio.equals("Mostrar todos")){
                        actualizarListaPorFiltro("11", anio);
                    }
                    else{
                        actualizarListaPorFiltroMes("11");
                    }
                }
                else if(mes.equals("Diciembre")){
                    if(!anio.equals("Mostrar todos")){
                        actualizarListaPorFiltro("12", anio);
                    }
                    else{
                        actualizarListaPorFiltroMes("12");
                    }
                }
                else if(mes.equals("Mostrar todos")){
                    if(!anio.equals("Mostrar todos")){
                        actualizarListaPorFiltroAnio(anio);
                    }
                    else{
                        cotizaciones = querys.getAllCotizaciones();

                        // *********** LLENAMOS EL RECYCLER VIEW *****************************
                        adapter = new CotizacionesActivity.DataConfigAdapter(cotizaciones, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerAnio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String mes2 = spinnerMes.getSelectedItem().toString();
                String anio = spinnerAnio.getSelectedItem().toString();
                String mes = "";

                if(mes2.equals("Enero")){
                    mes = "01";
                }
                else if(mes2.equals("Febrero")){
                    mes = "02";
                }
                else if(mes2.equals("Marzo")){
                    mes = "03";
                }
                else if(mes2.equals("Abril")){
                    mes = "04";
                }
                else if(mes2.equals("Mayo")){
                    mes = "05";
                }
                else if(mes2.equals("Junio")){
                    mes = "06";
                }
                else if(mes2.equals("Julio")){
                    mes = "07";
                }
                else if(mes2.equals("Agosto")){
                    mes = "08";
                }
                else if(mes2.equals("Septiembre")){
                    mes = "09";
                }
                else if(mes2.equals("Octubre")){
                    mes = "10";
                }
                else if(mes2.equals("Noviembre")){
                    mes = "11";
                }
                else if(mes2.equals("Diciembre")){
                    mes = "12";
                }
                else if(mes2.equals("Mostrar todos")){
                    mes = "Mostrar todos";
                }

                if(anio.equals("2018")){
                    if(!mes.equals("Mostrar todos")){
                        actualizarListaPorFiltro(mes, anio);
                    }
                    else{
                        actualizarListaPorFiltroAnio(anio);
                    }
                }
                else if(anio.equals("2019")){
                    if(!mes.equals("Mostrar todos")){
                        actualizarListaPorFiltro(mes, anio);
                    }
                    else{
                        actualizarListaPorFiltroAnio(anio);
                    }
                }
                else if(anio.equals("2020")){
                    if(!mes.equals("Mostrar todos")){
                        actualizarListaPorFiltro(mes, anio);
                    }
                    else{
                        actualizarListaPorFiltroAnio(anio);
                    }
                }
                else if(anio.equals("2021")){
                    if(!mes.equals("Mostrar todos")){
                        actualizarListaPorFiltro(mes, anio);
                    }
                    else{
                        actualizarListaPorFiltroAnio(anio);
                    }
                }
                else if(anio.equals("Mostrar todos")){
                    if(!anio.equals("Mostrar todos")){
                        actualizarListaPorFiltroMes(mes);
                    }
                    else{
                        cotizaciones = querys.getAllCotizaciones();

                        // *********** LLENAMOS EL RECYCLER VIEW *****************************
                        adapter = new CotizacionesActivity.DataConfigAdapter(cotizaciones, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CotizacionesActivity.this, NuevaCotizacionActivity.class);
                i.putExtra(NuevaCotizacionActivity.EXTRA_FOLIO, generacionFolio());
                startActivity(i);
            }
        });

        cotizaciones = querys.getAllCotizaciones();

        // *********** LLENAMOS EL RECYCLER VIEW *****************************
        adapter = new CotizacionesActivity.DataConfigAdapter(cotizaciones, getApplicationContext());
        recyclerView.setAdapter(adapter);

        if(cotizaciones.size() == 0){
            Toast.makeText(getApplicationContext(), "Aún no tienes cotizaciones en tu historial", Toast.LENGTH_LONG).show();
        }



        btnVerMisConceptos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CotizacionesActivity.this, ConceptosActivity.class);
                startActivity(i);
            }
        });

    }

    private void actualizar(){
        cotizaciones = querys.getAllCotizaciones();

        // *********** LLENAMOS EL RECYCLER VIEW *****************************
        adapter = new CotizacionesActivity.DataConfigAdapter(cotizaciones, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    private String generacionFolio(){

        int size = cotizaciones.size();
        int folio;
        if(size == 0){
            folio = 0;
        }
        else{
            folio = cotizaciones.get(size - 1).getId();
        }
        String folio_completo = "";

        if(folio < 9){
            folio_completo = "00" + String.valueOf(folio + 1);
        }
        else if(folio < 99){
            folio_completo = "0" + String.valueOf(folio + 1);
        }
        else{
            folio_completo = String.valueOf(folio + 1);
        }

        return folio_completo;
    }

    private void actualizarListaPorFiltro(String mes, String anio){

        cotizacionesFiltradas.clear();

        for(int i = 0; i<cotizaciones.size(); i++){
            String fecha[] = cotizaciones.get(i).getFecha().split("/");
            String dia_ = fecha[0];
            String mes_ = fecha[1];
            String anio_ = fecha[2];

            if(mes_.equals(mes) && anio_.equals(anio)){
                cotizacionesFiltradas.add(cotizaciones.get(i));
            }
        }
        // *********** LLENAMOS EL RECYCLER VIEW *****************************
        adapter = new CotizacionesActivity.DataConfigAdapter(cotizacionesFiltradas, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    private void actualizarListaPorFiltroMes(String mes){

        cotizacionesFiltradas.clear();

        for(int i = 0; i<cotizaciones.size(); i++){
            String fecha[] = cotizaciones.get(i).getFecha().split("/");
            String dia_ = fecha[0];
            String mes_ = fecha[1];
            String anio_ = fecha[2];

            if(mes_.equals(mes)){
                cotizacionesFiltradas.add(cotizaciones.get(i));
            }
        }
        // *********** LLENAMOS EL RECYCLER VIEW *****************************
        adapter = new CotizacionesActivity.DataConfigAdapter(cotizacionesFiltradas, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }

    private void actualizarListaPorFiltroAnio(String anio){

        cotizacionesFiltradas.clear();

        for(int i = 0; i<cotizaciones.size(); i++){
            String fecha[] = cotizaciones.get(i).getFecha().split("/");
            String dia_ = fecha[0];
            String mes_ = fecha[1];
            String anio_ = fecha[2];

            if(anio_.equals(anio)){
                cotizacionesFiltradas.add(cotizaciones.get(i));
            }
        }

        // *********** LLENAMOS EL RECYCLER VIEW *****************************
        adapter = new CotizacionesActivity.DataConfigAdapter(cotizacionesFiltradas, getApplicationContext());
        recyclerView.setAdapter(adapter);
    }
}
