package com.colabora.soluciones.convocatoriafeyac.Modelos;

import android.content.ContentValues;

import com.colabora.soluciones.convocatoriafeyac.Db.DBSchema;

public class Inventarios {

    private int id;
    private int id_categoria;
    private String titulo;
    private String descripcion;
    private int cantidad;
    private String createdOn;
    private String updatedOn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }

    public Inventarios(int id, int id_categoria, String titulo, String descripcion, int cantidad, String createdOn, String updatedOn) {
        this.id = id;
        this.id_categoria = id_categoria;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
    }

    public Inventarios() {
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        //values.put(DBSchema.CotizacionesTable.Columns.ID, id);
        values.put(DBSchema.InventariosTable.Columns.ID_CATEGORIA, id_categoria);
        values.put(DBSchema.InventariosTable.Columns.TITULO, titulo);
        values.put(DBSchema.InventariosTable.Columns.DESCRIPCION, descripcion);
        values.put(DBSchema.InventariosTable.Columns.CANTIDAD, cantidad);
        values.put(DBSchema.InventariosTable.Columns.CREATEDON, createdOn);
        values.put(DBSchema.InventariosTable.Columns.UPDATEDON, updatedOn);

        return values;
    }
}
