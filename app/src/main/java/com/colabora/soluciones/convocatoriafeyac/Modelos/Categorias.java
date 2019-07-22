package com.colabora.soluciones.convocatoriafeyac.Modelos;

import android.content.ContentValues;

import com.colabora.soluciones.convocatoriafeyac.Db.DBSchema;

public class Categorias {

    private int id;
    private String nombre;
    private String createdOn;

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

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public Categorias(int id, String nombre, String createdOn) {
        this.id = id;
        this.nombre = nombre;
        this.createdOn = createdOn;
    }

    public Categorias() {
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        //values.put(DBSchema.CotizacionesTable.Columns.ID, id);
        values.put(DBSchema.CategoriasTable.Columns.NOMBRE, nombre);
        values.put(DBSchema.CategoriasTable.Columns.CREATEDON, createdOn);

        return values;
    }
}
