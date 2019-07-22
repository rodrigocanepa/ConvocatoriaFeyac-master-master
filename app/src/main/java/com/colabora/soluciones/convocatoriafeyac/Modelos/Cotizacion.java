package com.colabora.soluciones.convocatoriafeyac.Modelos;

import android.content.ContentValues;

import com.colabora.soluciones.convocatoriafeyac.Db.DBSchema;

public class Cotizacion {

    private int id;
    private String folio;
    private String fecha;
    private String subtotal;
    private String iva;
    private String envio;
    private String descuento;
    private String total;
    private String notasDestinatario;
    private String terminos;
    private String nombre_enc;
    private String cargo_enc;
    private String numero_enc;
    private String vencimiento;
    private String notasAdmin;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(String subtotal) {
        this.subtotal = subtotal;
    }

    public String getIva() {
        return iva;
    }

    public void setIva(String iva) {
        this.iva = iva;
    }

    public String getEnvio() {
        return envio;
    }

    public void setEnvio(String envio) {
        this.envio = envio;
    }

    public String getDescuento() {
        return descuento;
    }

    public void setDescuento(String descuento) {
        this.descuento = descuento;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getNotasDestinatario() {
        return notasDestinatario;
    }

    public void setNotasDestinatario(String notasDestinatario) {
        this.notasDestinatario = notasDestinatario;
    }

    public String getTerminos() {
        return terminos;
    }

    public void setTerminos(String terminos) {
        this.terminos = terminos;
    }

    public String getNombre_enc() {
        return nombre_enc;
    }

    public void setNombre_enc(String nombre_enc) {
        this.nombre_enc = nombre_enc;
    }

    public String getCargo_enc() {
        return cargo_enc;
    }

    public void setCargo_enc(String cargo_enc) {
        this.cargo_enc = cargo_enc;
    }

    public String getNumero_enc() {
        return numero_enc;
    }

    public void setNumero_enc(String numero_enc) {
        this.numero_enc = numero_enc;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getNotasAdmin() {
        return notasAdmin;
    }

    public void setNotasAdmin(String notasAdmin) {
        this.notasAdmin = notasAdmin;
    }

    public Cotizacion() {
    }

    public Cotizacion(int id, String folio, String fecha, String subtotal, String iva, String envio, String descuento, String total, String notasDestinatario, String terminos, String nombre_enc, String cargo_enc, String numero_enc, String vencimiento, String notasAdmin) {
        this.id = id;
        this.folio = folio;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.iva = iva;
        this.envio = envio;
        this.descuento = descuento;
        this.total = total;
        this.notasDestinatario = notasDestinatario;
        this.terminos = terminos;
        this.nombre_enc = nombre_enc;
        this.cargo_enc = cargo_enc;
        this.numero_enc = numero_enc;
        this.vencimiento = vencimiento;
        this.notasAdmin = notasAdmin;
    }

    public Cotizacion(String folio, String fecha, String subtotal, String iva, String envio, String descuento, String total, String notasDestinatario, String terminos, String nombre_enc, String cargo_enc, String numero_enc, String vencimiento, String notasAdmin) {
        this.folio = folio;
        this.fecha = fecha;
        this.subtotal = subtotal;
        this.iva = iva;
        this.envio = envio;
        this.descuento = descuento;
        this.total = total;
        this.notasDestinatario = notasDestinatario;
        this.terminos = terminos;
        this.nombre_enc = nombre_enc;
        this.cargo_enc = cargo_enc;
        this.numero_enc = numero_enc;
        this.vencimiento = vencimiento;
        this.notasAdmin = notasAdmin;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        //values.put(DBSchema.CotizacionesTable.Columns.ID, id);
        values.put(DBSchema.CotizacionesTable.Columns.FOLIO, folio);
        values.put(DBSchema.CotizacionesTable.Columns.FECHA, fecha);
        values.put(DBSchema.CotizacionesTable.Columns.SUBTOTAL, subtotal);
        values.put(DBSchema.CotizacionesTable.Columns.IVA, iva);
        values.put(DBSchema.CotizacionesTable.Columns.ENVIO, envio);
        values.put(DBSchema.CotizacionesTable.Columns.DESCUENTO, descuento);
        values.put(DBSchema.CotizacionesTable.Columns.TOTAL, total);
        values.put(DBSchema.CotizacionesTable.Columns.NOTAS_DESTINATARIO, notasDestinatario);
        values.put(DBSchema.CotizacionesTable.Columns.TERMINOS, terminos);
        values.put(DBSchema.CotizacionesTable.Columns.NOMBRE_ENCARGADO, nombre_enc);
        values.put(DBSchema.CotizacionesTable.Columns.CARGO_ENCARGADO, cargo_enc);
        values.put(DBSchema.CotizacionesTable.Columns.NUMERO_ENCARGADO, numero_enc);
        values.put(DBSchema.CotizacionesTable.Columns.VENCIMIENTO, vencimiento);
        values.put(DBSchema.CotizacionesTable.Columns.NOTAS_ADMIN, notasAdmin);
        return values;
    }
}
