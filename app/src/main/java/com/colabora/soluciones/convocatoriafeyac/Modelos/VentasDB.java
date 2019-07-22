package com.colabora.soluciones.convocatoriafeyac.Modelos;

import android.content.ContentValues;

import com.colabora.soluciones.convocatoriafeyac.Db.DBSchema;

public class VentasDB {

    private int id;
    private int id_inventario;
    private int id_categoria;
    private int cantidad;
    private String createdOn;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_inventario() {
        return id_inventario;
    }

    public void setId_inventario(int id_inventario) {
        this.id_inventario = id_inventario;
    }

    public int getId_categoria() {
        return id_categoria;
    }

    public void setId_categoria(int id_categoria) {
        this.id_categoria = id_categoria;
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

    public VentasDB(int id, int id_inventario, int id_categoria, int cantidad, String createdOn) {
        this.id = id;
        this.id_inventario = id_inventario;
        this.id_categoria = id_categoria;
        this.cantidad = cantidad;
        this.createdOn = createdOn;
    }

    public VentasDB() {
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        //values.put(DBSchema.CotizacionesTable.Columns.ID, id);
        values.put(DBSchema.VentasTable.Columns.ID_INVENTARIO, id_categoria);
        values.put(DBSchema.VentasTable.Columns.ID_CATEGORIA, id_categoria);
        values.put(DBSchema.VentasTable.Columns.CANTIDAD, cantidad);
        values.put(DBSchema.VentasTable.Columns.CREATEDON, createdOn);

        return values;
    }
}
