package com.colabora.soluciones.convocatoriafeyac;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TarjetaDatosActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private TextInputEditText editNombre;
    private TextInputEditText editCargo;
    private TextInputEditText editNumero;
    private TextInputEditText editCorreo;
    private TextInputEditText editPagina;
    private TextInputEditText editDireccion;
    private TextInputEditText editFacebook;
    private TextInputEditText editInstagram;

    private Button btnVisualizar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta_datos);

        editNombre = (TextInputEditText)findViewById(R.id.txtTarjetaDatosNombre);
        editCargo = (TextInputEditText)findViewById(R.id.txtTarjetaDatosPuesto);
        editNumero = (TextInputEditText)findViewById(R.id.txtTarjetaDatosNumero);
        editCorreo = (TextInputEditText)findViewById(R.id.txtTarjetaDatosCorreo);
        editPagina = (TextInputEditText)findViewById(R.id.txtTarjetasDatosWeb);
        editDireccion = (TextInputEditText)findViewById(R.id.txtTarjetasDatosDireccion);
        editFacebook = (TextInputEditText)findViewById(R.id.txtTarjetasDatosFacebook);
        editInstagram = (TextInputEditText)findViewById(R.id.txtDatosInstagram);

        btnVisualizar = (Button)findViewById(R.id.btnVisualizarTarjeta);

        btnVisualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editNombre.getText().toString().length() == 0){
                    editNombre.setError("Ingresa un valor");
                    return;
                }
                if(editCargo.getText().toString().length() == 0){
                    editCargo.setError("Ingresa un valor");
                    return;
                }
                if(editCorreo.getText().toString().length() == 0){
                    editCorreo.setError("Ingresa un valor");
                    return;
                }
                // Leemos la memoria para ver que tarjetas se han creado
                sharedPreferences = getSharedPreferences("misDatos", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("tarjetaNombre", editNombre.getText().toString());
                editor.putString("tarjetaCargo", editCargo.getText().toString());
                editor.putString("tarjetaNumero", editNumero.getText().toString());
                editor.putString("tarjetaCorreo", editCorreo.getText().toString());
                editor.putString("tarjetaPagina", editPagina.getText().toString());
                editor.putString("tarjetaDireccion", editDireccion.getText().toString());
                editor.putString("tarjetaFacebook", editFacebook.getText().toString());
                editor.putString("tarjetaInstagram", editInstagram.getText().toString());
                editor.commit();

                Intent i = new Intent(TarjetaDatosActivity.this, TarjetasSeleccionarLetraActivity.class);
                startActivity(i);
            }
        });


    }
}
