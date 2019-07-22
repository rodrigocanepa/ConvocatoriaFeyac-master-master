package com.colabora.soluciones.convocatoriafeyac.Modelos;

import android.content.ContentValues;

import com.colabora.soluciones.convocatoriafeyac.Db.DBSchema;

public class ConceptoCotizacion {

    private int id;
    private int idCotizacion;
    private String nombre;
    private String cantidad;
    private String precio;
    private String importe;
    private String iva;
    private String iva_precio;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCotizacion() {
        return idCotizacion;
    }

    public void setIdCotizacion(int idCotizacion) {
        this.idCotizacion = idCotizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getImporte() {
        return importe;
    }

    public void setImporte(String importe) {
        this.importe = importe;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getIva_precio() {
        return iva_precio;
    }

    public void setIva_precio(String iva_precio) {
        this.iva_precio = iva_precio;
    }

    public ConceptoCotizacion() {
    }

    public ConceptoCotizacion(int id, int idCotizacion, String nombre, String cantidad, String precio, String importe, String iva, String iva_precio) {
        this.id = id;
        this.idCotizacion = idCotizacion;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.importe = importe;
        this.iva = iva;
        this.iva_precio = iva_precio;
    }

    public ConceptoCotizacion(int idCotizacion, String nombre, String cantidad, String precio, String importe, String iva, String iva_precio) {
        this.idCotizacion = idCotizacion;
        this.nombre = nombre;
        this.cantidad = cantidad;
        this.precio = precio;
        this.importe = importe;
        this.iva = iva;
        this.iva_precio = iva_precio;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        //values.put(DBSchema.CotizacionesTable.Columns.ID, id);
        values.put(DBSchema.CotizacionesConceptosTable.Columns.ID_COTIZACION, idCotizacion);
        values.put(DBSchema.CotizacionesConceptosTable.Columns.NOMBRE, nombre);
        values.put(DBSchema.CotizacionesConceptosTable.Columns.CANTIDAD, cantidad);
        values.put(DBSchema.CotizacionesConceptosTable.Columns.PRECIO, precio);
        values.put(DBSchema.CotizacionesConceptosTable.Columns.IMPORTE, importe);
        values.put(DBSchema.CotizacionesConceptosTable.Columns.IVA, iva);
        values.put(DBSchema.CotizacionesConceptosTable.Columns.IVA_PRECIO, iva_precio);

        return values;
    }
}
