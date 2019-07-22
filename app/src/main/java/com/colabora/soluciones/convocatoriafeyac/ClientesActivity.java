package com.colabora.soluciones.convocatoriafeyac;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.colabora.soluciones.convocatoriafeyac.Db.Querys;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Cliente;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ClientesActivity extends AppCompatActivity {


    // *************************** RECYCLER VIEW ************************

    private class DataConfigHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtNombre;
        private TextView txtDescripcion;
        private TextView txtCorreo;
        private TextView txtDireccion;
        private TextView txtTelefono;
        private TextView txtHorario;

        private Button btnEditar;
        private Button btnEliminar;

        private List<Cliente> clientes = new ArrayList<Cliente>();
        private Context ctx;

        public DataConfigHolder(View itemView, Context ctx, final List<Cliente> clientes) {
            super(itemView);
            itemView.setOnClickListener(this);

            this.clientes = clientes;
            this.ctx = ctx;

            txtNombre = (TextView) itemView.findViewById(R.id.item_cliente_nombre);
            txtDescripcion = (TextView) itemView.findViewById(R.id.item_cliente_descripcion);
            txtCorreo = (TextView) itemView.findViewById(R.id.item_cliente_correo);
            txtDireccion = (TextView) itemView.findViewById(R.id.item_cliente_direccion);
            txtTelefono = (TextView) itemView.findViewById(R.id.item_cliente_telefono);
            txtHorario = (TextView) itemView.findViewById(R.id.item_cliente_horario);

            btnEditar = (Button)itemView.findViewById(R.id.btnItemClientesEditar);
            btnEliminar = (Button)itemView.findViewById(R.id.btnItemClientesEliminar);

            btnEditar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    if (clientes_) {
                        //Toast.makeText(getApplicationContext(), "clientes", Toast.LENGTH_SHORT).show();
                        final AlertDialog.Builder builder = new AlertDialog.Builder(ClientesActivity.this);

                        // Get the layout inflater
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View formElementsView = inflater.inflate(R.layout.dialog_add_cliente,
                                null, false);

                        editDialogNombre = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteNombre);
                        editDialogDescripcion = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteDescripcion);
                        editDialogCorreo = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteCorreo);
                        editDialogDireccion = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteDireccion);
                        editDialogTelefono = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteTelefono);
                        editDialogHorario = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteHorario);

                        editDialogNombre.setText(clientes.get(pos).getNombre());
                        editDialogDescripcion.setText(clientes.get(pos).getDescripcion());
                        editDialogCorreo.setText(clientes.get(pos).getCorreo());
                        editDialogDireccion.setText(clientes.get(pos).getDireccion());
                        editDialogTelefono.setText(clientes.get(pos).getTelefono());
                        editDialogHorario.setText(clientes.get(pos).getHorario());

                        builder.setTitle("Cliente");
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String nombre = editDialogNombre.getText().toString();
                                String descripcion = editDialogDescripcion.getText().toString();
                                String correo = editDialogCorreo.getText().toString();
                                String direccion = editDialogDireccion.getText().toString();
                                String telefono = editDialogTelefono.getText().toString();
                                String horario = editDialogHorario.getText().toString();

                                if (nombre.length() < 1) {
                                    Toast.makeText(getApplicationContext(), "Debes llenar los datos obligatorios para guardar", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (descripcion.length() < 2) {
                                    Toast.makeText(getApplicationContext(), "Debes llenar los datos obligatorios para guardar", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (correo.length() < 2) {
                                    Toast.makeText(getApplicationContext(), "Debes llenar los datos obligatorios para guardar", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (direccion.length() == 0) {
                                    direccion = "No registrado";
                                }
                                if (telefono.length() == 0) {
                                    telefono = "No registrado";
                                }
                                if (horario.length() == 0) {
                                    horario = "No registrado";
                                }

                                Cliente cliente = new Cliente(nombre, descripcion, correo, direccion, telefono, horario);
                                querys.updateCliente(cliente, String.valueOf(clientes.get(pos).getId()));

                                actualizar();


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

                    if (proveedores_) {
                        //Toast.makeText(getApplicationContext(), "proveedores", Toast.LENGTH_SHORT).show();

                        final AlertDialog.Builder builder = new AlertDialog.Builder(ClientesActivity.this);

                        // Get the layout inflater
                        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View formElementsView = inflater.inflate(R.layout.dialog_add_cliente,
                                null, false);

                        editDialogNombre = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteNombre);
                        editDialogDescripcion = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteDescripcion);
                        editDialogCorreo = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteCorreo);
                        editDialogDireccion = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteDireccion);
                        editDialogTelefono = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteTelefono);
                        editDialogHorario = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteHorario);

                        editDialogNombre.setText(clientes.get(pos).getNombre());
                        editDialogDescripcion.setText(clientes.get(pos).getDescripcion());
                        editDialogCorreo.setText(clientes.get(pos).getCorreo());
                        editDialogDireccion.setText(clientes.get(pos).getDireccion());
                        editDialogTelefono.setText(clientes.get(pos).getTelefono());
                        editDialogHorario.setText(clientes.get(pos).getHorario());

                        builder.setTitle("Proveedor");
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String nombre = editDialogNombre.getText().toString();
                                String descripcion = editDialogDescripcion.getText().toString();
                                String correo = editDialogCorreo.getText().toString();
                                String direccion = editDialogDireccion.getText().toString();
                                String telefono = editDialogTelefono.getText().toString();
                                String horario = editDialogHorario.getText().toString();

                                if (nombre.length() < 1) {
                                    Toast.makeText(getApplicationContext(), "Debes llenar los datos obligatorios para guardar", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (descripcion.length() < 2) {
                                    Toast.makeText(getApplicationContext(), "Debes llenar los datos obligatorios para guardar", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (correo.length() < 2) {
                                    Toast.makeText(getApplicationContext(), "Debes llenar los datos obligatorios para guardar", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if (direccion.length() == 0) {
                                    direccion = "No registrado";
                                }
                                if (telefono.length() == 0) {
                                    telefono = "No registrado";
                                }
                                if (horario.length() == 0) {
                                    horario = "No registrado";
                                }

                                Cliente cliente = new Cliente(nombre, descripcion, correo, direccion, telefono, horario);

                                querys.updateProveedor(cliente, String.valueOf(clientes.get(pos).getId()));
                                actualizar();


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
                }
            });

            btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final int pos = getAdapterPosition();
                    if(proveedores_){
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ClientesActivity.this);
                        builder.setMessage("¿Desea eliminar este proveedor?")
                                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        querys.deleteProveedor(String.valueOf(clientes.get(pos).getId()));
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
                    else if(clientes_){
                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(ClientesActivity.this);
                        builder.setMessage("¿Desea eliminar este cliente?")
                                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        querys.deleteCliente(String.valueOf(clientes.get(pos).getId()));
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
                }
            });
        }

        public void bindConfig(final Cliente cliente) {
            txtNombre.setText(cliente.getNombre());
            txtDescripcion.setText(cliente.getDescripcion());
            txtCorreo.setText(cliente.getCorreo());
            txtDireccion.setText(cliente.getDireccion());
            txtTelefono.setText(cliente.getTelefono());
            txtHorario.setText(cliente.getHorario());
        }

        @Override
        public void onClick(View v) {
            final int position = getAdapterPosition();

        }

    }


    private class DataConfigAdapter extends RecyclerView.Adapter<ClientesActivity.DataConfigHolder> {

        private List<Cliente> clientes;
        Context ctx;

        public DataConfigAdapter(List<Cliente> clientes, Context ctx ){
            this.clientes = clientes;
            this.ctx = ctx;
        }

        @Override
        public ClientesActivity.DataConfigHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_clientes, parent, false);
            return new ClientesActivity.DataConfigHolder(view, ctx, clientes);
        }

        @Override
        public void onBindViewHolder(final ClientesActivity.DataConfigHolder holder, final int position) {
            holder.bindConfig(clientes.get(position));

        }

        @Override
        public int getItemCount() {
            return clientes.size();
        }

    }

    private ClientesActivity.DataConfigAdapter adapter;
    private List<Cliente> clientes = new ArrayList<Cliente>();
    // ******************************************************************


    private Button btnClientes;
    private Button btnProveedores;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private boolean clientes_ = false;
    private boolean proveedores_ = false;

    private TextInputEditText editDialogNombre;
    private TextInputEditText editDialogDescripcion;
    private TextInputEditText editDialogCorreo;
    private TextInputEditText editDialogDireccion;
    private TextInputEditText editDialogTelefono;
    private TextInputEditText editDialogHorario;

    private Querys querys;
    private EditText editFiltroNombre;
    private ImageButton btnFiltro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clientes);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        btnClientes = (Button)findViewById(R.id.btnClientesClientes);
        btnProveedores = (Button) findViewById(R.id.btnClientesProveedores);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerProveedores);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.fabAddClientes);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        editFiltroNombre = (EditText)findViewById(R.id.editFiltroNombre);
        btnFiltro = (ImageButton) findViewById(R.id.btnFiltroNombre);

        querys = new Querys(getApplicationContext());

        proveedores_ = true;
        clientes_ = false;

        clientes = querys.getAllProveedores();
        // *********** LLENAMOS EL RECYCLER VIEW *****************************
        adapter = new ClientesActivity.DataConfigAdapter(clientes, getApplicationContext());
        recyclerView.setAdapter(adapter);

        btnFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filtro = editFiltroNombre.getText().toString();
                hideKeyboard();
                if(filtro.length() == 0){
                    if (proveedores_){
                        clientes = querys.getAllProveedores();
                        // *********** LLENAMOS EL RECYCLER VIEW *****************************
                        adapter = new ClientesActivity.DataConfigAdapter(clientes, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                    else if(clientes_){
                        clientes = querys.getAllClientes();
                        // *********** LLENAMOS EL RECYCLER VIEW *****************************
                        adapter = new ClientesActivity.DataConfigAdapter(clientes, getApplicationContext());
                        recyclerView.setAdapter(adapter);
                    }
                }
                else{
                    if (proveedores_){
                        clientes.clear();
                        Cliente cliente = querys.getProveedorPorFiltro(filtro);
                        if(cliente.getNombre() != null){
                            clientes.add(cliente);
                        }
                        if(clientes.size() == 0){
                            Toast.makeText(getApplicationContext(), "No se encontraron coincidencias", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Se encontraron " + String.valueOf(clientes.size()) + " coincidencia(s)", Toast.LENGTH_SHORT).show();
                            // *********** LLENAMOS EL RECYCLER VIEW *****************************
                            adapter = new ClientesActivity.DataConfigAdapter(clientes, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        }

                    }
                    else if(clientes_){
                        clientes.clear();
                        Cliente cliente = querys.getClientePorFiltro(filtro);
                        if(cliente.getNombre() != null){
                            clientes.add(cliente);
                        }
                        if(clientes.size() == 0){
                            Toast.makeText(getApplicationContext(), "No se encontraron coincidencias", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Se encontraron " + String.valueOf(clientes.size()) + " coincidencia(s)", Toast.LENGTH_SHORT).show();
                            // *********** LLENAMOS EL RECYCLER VIEW *****************************
                            adapter = new ClientesActivity.DataConfigAdapter(clientes, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        }

                    }
                }
            }
        });

        btnClientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnClientes.setBackgroundColor(Color.parseColor("#82a33b"));
                btnProveedores.setBackgroundColor(Color.LTGRAY);
                clientes_ = true;
                proveedores_ = false;
                editFiltroNombre.setText("");

                clientes = querys.getAllClientes();
                // *********** LLENAMOS EL RECYCLER VIEW *****************************
                adapter = new ClientesActivity.DataConfigAdapter(clientes, getApplicationContext());
                recyclerView.setAdapter(adapter);

            }
        });

        btnProveedores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnProveedores.setBackgroundColor(Color.parseColor("#82a33b"));
                btnClientes.setBackgroundColor(Color.LTGRAY);
                proveedores_ = true;
                clientes_ = false;
                editFiltroNombre.setText("");

                clientes = querys.getAllProveedores();
                // *********** LLENAMOS EL RECYCLER VIEW *****************************
                adapter = new ClientesActivity.DataConfigAdapter(clientes, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(clientes_){
                    //Toast.makeText(getApplicationContext(), "clientes", Toast.LENGTH_SHORT).show();
                    final AlertDialog.Builder builder = new AlertDialog.Builder(ClientesActivity.this);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View formElementsView = inflater.inflate(R.layout.dialog_add_cliente,
                            null, false);

                    editDialogNombre = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteNombre);
                    editDialogDescripcion = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteDescripcion);
                    editDialogCorreo = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteCorreo);
                    editDialogDireccion = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteDireccion);
                    editDialogTelefono = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteTelefono);
                    editDialogHorario = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteHorario);


                    builder.setTitle("Cliente");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String nombre = editDialogNombre.getText().toString();
                            String descripcion = editDialogDescripcion.getText().toString();
                            String correo = editDialogCorreo.getText().toString();
                            String direccion = editDialogDireccion.getText().toString();
                            String telefono = editDialogTelefono.getText().toString();
                            String horario = editDialogHorario.getText().toString();

                            if(nombre.length() < 1){
                                Toast.makeText(getApplicationContext(), "Debes llenar los datos obligatorios para guardar", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(descripcion.length() < 2){
                                Toast.makeText(getApplicationContext(), "Debes llenar los datos obligatorios para guardar", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(correo.length() < 2){
                                Toast.makeText(getApplicationContext(), "Debes llenar los datos obligatorios para guardar", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(direccion.length() == 0){
                                direccion = "No registrado";
                            }
                            if(telefono.length() == 0){
                                telefono = "No registrado";
                            }
                            if(horario.length() == 0){
                                horario = "No registrado";
                            }

                            Cliente cliente = new Cliente(nombre,descripcion,correo,direccion,telefono,horario);

                            querys.insertClientes(cliente);

                            clientes = querys.getAllClientes();
                            // *********** LLENAMOS EL RECYCLER VIEW *****************************
                            adapter = new ClientesActivity.DataConfigAdapter(clientes, getApplicationContext());
                            recyclerView.setAdapter(adapter);


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

                if(proveedores_){
                    //Toast.makeText(getApplicationContext(), "proveedores", Toast.LENGTH_SHORT).show();

                    final AlertDialog.Builder builder = new AlertDialog.Builder(ClientesActivity.this);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View formElementsView = inflater.inflate(R.layout.dialog_add_cliente,
                            null, false);

                    editDialogNombre = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteNombre);
                    editDialogDescripcion = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteDescripcion);
                    editDialogCorreo = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteCorreo);
                    editDialogDireccion = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteDireccion);
                    editDialogTelefono = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteTelefono);
                    editDialogHorario = (TextInputEditText) formElementsView.findViewById(R.id.txtDialogClienteHorario);


                    builder.setTitle("Proveedor");
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            String nombre = editDialogNombre.getText().toString();
                            String descripcion = editDialogDescripcion.getText().toString();
                            String correo = editDialogCorreo.getText().toString();
                            String direccion = editDialogDireccion.getText().toString();
                            String telefono = editDialogTelefono.getText().toString();
                            String horario = editDialogHorario.getText().toString();

                            if(nombre.length() < 1){
                                Toast.makeText(getApplicationContext(), "Debes llenar los datos obligatorios para guardar", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(descripcion.length() < 2){
                                Toast.makeText(getApplicationContext(), "Debes llenar los datos obligatorios para guardar", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(correo.length() < 2){
                                Toast.makeText(getApplicationContext(), "Debes llenar los datos obligatorios para guardar", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            if(direccion.length() == 0){
                                direccion = "No registrado";
                            }
                            if(telefono.length() == 0){
                                telefono = "No registrado";
                            }
                            if(horario.length() == 0){
                                horario = "No registrado";
                            }

                            Cliente cliente = new Cliente(nombre,descripcion,correo,direccion,telefono,horario);

                            querys.insertProveedores(cliente);

                            clientes = querys.getAllProveedores();
                            // *********** LLENAMOS EL RECYCLER VIEW *****************************
                            adapter = new ClientesActivity.DataConfigAdapter(clientes, getApplicationContext());
                            recyclerView.setAdapter(adapter);


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
            }
        });
    }

    private void actualizar(){
        if(proveedores_){
            clientes = querys.getAllProveedores();
            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new ClientesActivity.DataConfigAdapter(clientes, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }
        else if(clientes_){
            clientes = querys.getAllClientes();
            // *********** LLENAMOS EL RECYCLER VIEW *****************************
            adapter = new ClientesActivity.DataConfigAdapter(clientes, getApplicationContext());
            recyclerView.setAdapter(adapter);
        }

    }

    public void hideKeyboard() {
        // Check if no view has focus:
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
