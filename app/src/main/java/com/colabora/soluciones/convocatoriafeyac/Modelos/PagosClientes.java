package com.colabora.soluciones.convocatoriafeyac.Modelos;

import android.content.ContentValues;

import com.colabora.soluciones.convocatoriafeyac.Db.DBSchema;

public class PagosClientes {

    private int id;
    private String concepto;
    private String createdOn;
    private String updatedOn;
    private String estatus;
    private int id_cliente;
    private String monto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
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

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public PagosClientes() {
    }

    public PagosClientes(int id, String concepto, String createdOn, String updatedOn, String estatus, int id_cliente, String monto) {
        this.id = id;
        this.concepto = concepto;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.estatus = estatus;
        this.id_cliente = id_cliente;
        this.monto = monto;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        //values.put(DBSchema.CotizacionesTable.Columns.ID, id);
        values.put(DBSchema.PagosClientesTable.Columns.CONCEPTO, concepto);
        values.put(DBSchema.PagosClientesTable.Columns.CREATEDON, createdOn);
        values.put(DBSchema.PagosClientesTable.Columns.UPDATEDON, updatedOn);
        values.put(DBSchema.PagosClientesTable.Columns.ESTATUS, estatus);
        values.put(DBSchema.PagosClientesTable.Columns.IDCLIENTE, id_cliente);
        values.put(DBSchema.PagosClientesTable.Columns.MONTO, monto);

        return values;
    }
}
