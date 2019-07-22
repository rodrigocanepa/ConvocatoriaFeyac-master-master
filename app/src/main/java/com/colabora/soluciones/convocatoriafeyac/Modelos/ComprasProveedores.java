package com.colabora.soluciones.convocatoriafeyac.Modelos;

import android.content.ContentValues;

import com.colabora.soluciones.convocatoriafeyac.Db.DBSchema;

public class ComprasProveedores {

    private int id;
    private int id_proveedor;
    private String createdOn;
    private String updatedOn;
    private String concepto;
    private String monto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_proveedor() {
        return id_proveedor;
    }

    public void setId_proveedor(int id_proveedor) {
        this.id_proveedor = id_proveedor;
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

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getMonto() {
        return monto;
    }

    public void setMonto(String monto) {
        this.monto = monto;
    }

    public ComprasProveedores() {
    }

    public ComprasProveedores(int id, int id_proveedor, String createdOn, String updatedOn, String concepto, String monto) {
        this.id = id;
        this.id_proveedor = id_proveedor;
        this.createdOn = createdOn;
        this.updatedOn = updatedOn;
        this.concepto = concepto;
        this.monto = monto;
    }

    public ContentValues toContentValues() {
        ContentValues values = new ContentValues();
        //values.put(DBSchema.CotizacionesTable.Columns.ID, id);
        values.put(DBSchema.ComprasProveedoresTable.Columns.ID_PROVEEDOR, id_proveedor);
        values.put(DBSchema.ComprasProveedoresTable.Columns.CREATEDON, createdOn);
        values.put(DBSchema.ComprasProveedoresTable.Columns.UPDATEDON, updatedOn);
        values.put(DBSchema.ComprasProveedoresTable.Columns.CONCEPTO, concepto);
        values.put(DBSchema.ComprasProveedoresTable.Columns.MONTO, monto);

        return values;
    }
}
