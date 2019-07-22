package com.colabora.soluciones.convocatoriafeyac.Modelos;

import android.content.ContentValues;

import com.colabora.soluciones.convocatoriafeyac.Db.DBSchema;

public class Recordatorios {

    private int id;
    private String date;
    private String descripcion;
    private String ubicacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public Recordatorios(int id, String date, String descripcion, String ubicacion) {
        this.id = id;
        this.date = date;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
    }

    public Recordatorios() {
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        //values.put(DBSchema.CotizacionesTable.Columns.ID, id);
        values.put(DBSchema.RecordatoriosTable.Columns.DATE, date);
        values.put(DBSchema.RecordatoriosTable.Columns.DESCRIPCION, descripcion);
        values.put(DBSchema.RecordatoriosTable.Columns.UBICACION, ubicacion);

        return values;
    }
}
