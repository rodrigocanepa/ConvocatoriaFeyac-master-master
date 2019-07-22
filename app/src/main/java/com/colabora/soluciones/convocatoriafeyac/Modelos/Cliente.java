package com.colabora.soluciones.convocatoriafeyac.Modelos;

import android.content.ContentValues;

import com.colabora.soluciones.convocatoriafeyac.Db.DBSchema;

public class Cliente {

    private int id;
    private String nombre;
    private String descripcion;
    private String correo;
    private String direccion;
    private String telefono;
    private String horario;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public Cliente() {
    }

    public Cliente(int id, String nombre, String descripcion, String correo, String direccion, String telefono, String horario) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.horario = horario;
    }

    public Cliente(String nombre, String descripcion, String correo, String direccion, String telefono, String horario) {
        //this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.correo = correo;
        this.direccion = direccion;
        this.telefono = telefono;
        this.horario = horario;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        //values.put(DBSchema.CotizacionesTable.Columns.ID, id);
        values.put(DBSchema.ClientesTable.Columns.NOMBRE, nombre);
        values.put(DBSchema.ClientesTable.Columns.DESCRIPCION, descripcion);
        values.put(DBSchema.ClientesTable.Columns.CORREO, correo);
        values.put(DBSchema.ClientesTable.Columns.DIRECCION, direccion);
        values.put(DBSchema.ClientesTable.Columns.TELEFONO, telefono);
        values.put(DBSchema.ClientesTable.Columns.HORARIO, horario);

        return values;
    }
}
