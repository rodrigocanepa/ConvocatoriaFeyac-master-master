package com.colabora.soluciones.convocatoriafeyac.Db;

import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.CornerPathEffect;

import com.colabora.soluciones.convocatoriafeyac.Modelos.Categorias;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Cliente;
import com.colabora.soluciones.convocatoriafeyac.Modelos.ComprasProveedores;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Concepto;
import com.colabora.soluciones.convocatoriafeyac.Modelos.ConceptoCotizacion;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Cotizacion;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Inventarios;
import com.colabora.soluciones.convocatoriafeyac.Modelos.PagosClientes;
import com.colabora.soluciones.convocatoriafeyac.Modelos.Recordatorios;
import com.colabora.soluciones.convocatoriafeyac.Modelos.VentasDB;

import java.util.ArrayList;
import java.util.List;

class CotizacionCursor extends CursorWrapper {
    public CotizacionCursor(Cursor cursor) {
        super(cursor);
    }

    public Cotizacion getCotizacion(){
        Cursor cursor = getWrappedCursor();
        return new Cotizacion(cursor.getInt(cursor.getColumnIndex(DBSchema.CotizacionesTable.Columns.ID)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesTable.Columns.FOLIO)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesTable.Columns.FECHA)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesTable.Columns.SUBTOTAL)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesTable.Columns.IVA)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesTable.Columns.ENVIO)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesTable.Columns.DESCUENTO)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesTable.Columns.TOTAL)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesTable.Columns.NOTAS_DESTINATARIO)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesTable.Columns.TERMINOS)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesTable.Columns.NOMBRE_ENCARGADO)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesTable.Columns.CARGO_ENCARGADO)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesTable.Columns.NUMERO_ENCARGADO)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesTable.Columns.VENCIMIENTO)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesTable.Columns.NOTAS_ADMIN)));
    }
}

class ClientesCursor extends CursorWrapper {
    public ClientesCursor(Cursor cursor) {
        super(cursor);
    }

    public Cliente getCliente(){
        Cursor cursor = getWrappedCursor();
        return new Cliente(cursor.getInt(cursor.getColumnIndex(DBSchema.ClientesTable.Columns.ID)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ClientesTable.Columns.NOMBRE)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ClientesTable.Columns.DESCRIPCION)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ClientesTable.Columns.CORREO)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ClientesTable.Columns.DIRECCION)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ClientesTable.Columns.TELEFONO)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ClientesTable.Columns.HORARIO)));
    }
}

class ProveedorCursor extends CursorWrapper {
    public ProveedorCursor(Cursor cursor) {
        super(cursor);
    }

    public Cliente getCliente(){
        Cursor cursor = getWrappedCursor();
        return new Cliente(cursor.getInt(cursor.getColumnIndex(DBSchema.ClientesTable.Columns.ID)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ClientesTable.Columns.NOMBRE)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ClientesTable.Columns.DESCRIPCION)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ClientesTable.Columns.CORREO)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ClientesTable.Columns.DIRECCION)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ClientesTable.Columns.TELEFONO)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ClientesTable.Columns.HORARIO)));
    }
}

class ConceptopsCursor extends CursorWrapper {
    public ConceptopsCursor(Cursor cursor) {
        super(cursor);
    }

    public Concepto getConcepto(){
        Cursor cursor = getWrappedCursor();
        return new Concepto(cursor.getInt(cursor.getColumnIndex(DBSchema.ConceptopsTable.Columns.ID)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ConceptopsTable.Columns.NOMBRE)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ConceptopsTable.Columns.PRECIO)));
    }
}

class ConceptosCotizacionCursor extends CursorWrapper {
    public ConceptosCotizacionCursor(Cursor cursor) {
        super(cursor);
    }

    public ConceptoCotizacion getConceptoCotizacion(){
        Cursor cursor = getWrappedCursor();
        return new ConceptoCotizacion(cursor.getInt(cursor.getColumnIndex(DBSchema.CotizacionesConceptosTable.Columns.ID)),
                cursor.getInt(cursor.getColumnIndex(DBSchema.CotizacionesConceptosTable.Columns.ID_COTIZACION)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesConceptosTable.Columns.NOMBRE)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesConceptosTable.Columns.CANTIDAD)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesConceptosTable.Columns.PRECIO)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesConceptosTable.Columns.IMPORTE)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesConceptosTable.Columns.IVA)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CotizacionesConceptosTable.Columns.IVA_PRECIO)));
    }
}

class PagosClientesCursor extends CursorWrapper {
    public PagosClientesCursor(Cursor cursor) {
        super(cursor);
    }

    public PagosClientes getPagosClientes(){
        Cursor cursor = getWrappedCursor();
        return new PagosClientes(cursor.getInt(cursor.getColumnIndex(DBSchema.PagosClientesTable.Columns.ID)),
                cursor.getString(cursor.getColumnIndex(DBSchema.PagosClientesTable.Columns.CONCEPTO)),
                cursor.getString(cursor.getColumnIndex(DBSchema.PagosClientesTable.Columns.CREATEDON)),
                cursor.getString(cursor.getColumnIndex(DBSchema.PagosClientesTable.Columns.UPDATEDON)),
                cursor.getString(cursor.getColumnIndex(DBSchema.PagosClientesTable.Columns.ESTATUS)),
                cursor.getInt(cursor.getColumnIndex(DBSchema.PagosClientesTable.Columns.IDCLIENTE)),
                cursor.getString(cursor.getColumnIndex(DBSchema.PagosClientesTable.Columns.MONTO)));
    }
}

class ComprasProveedoresCursor extends CursorWrapper {
    public ComprasProveedoresCursor(Cursor cursor) {
        super(cursor);
    }

    public ComprasProveedores getCompraProveedor(){
        Cursor cursor = getWrappedCursor();
        return new ComprasProveedores(cursor.getInt(cursor.getColumnIndex(DBSchema.ComprasProveedoresTable.Columns.ID)),
                cursor.getInt(cursor.getColumnIndex(DBSchema.ComprasProveedoresTable.Columns.ID_PROVEEDOR)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ComprasProveedoresTable.Columns.CREATEDON)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ComprasProveedoresTable.Columns.UPDATEDON)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ComprasProveedoresTable.Columns.CONCEPTO)),
                cursor.getString(cursor.getColumnIndex(DBSchema.ComprasProveedoresTable.Columns.MONTO)));
    }
}

class RecordatoriosCursor extends CursorWrapper {
    public RecordatoriosCursor(Cursor cursor) {
        super(cursor);
    }

    public Recordatorios getRecordatorio(){
        Cursor cursor = getWrappedCursor();
        return new Recordatorios(cursor.getInt(cursor.getColumnIndex(DBSchema.RecordatoriosTable.Columns.ID)),
                cursor.getString(cursor.getColumnIndex(DBSchema.RecordatoriosTable.Columns.DATE)),
                cursor.getString(cursor.getColumnIndex(DBSchema.RecordatoriosTable.Columns.DESCRIPCION)),
                cursor.getString(cursor.getColumnIndex(DBSchema.RecordatoriosTable.Columns.UBICACION)));
    }
}

class CategoriasCursor extends CursorWrapper {
    public CategoriasCursor(Cursor cursor) {
        super(cursor);
    }

    public Categorias getCategorias(){
        Cursor cursor = getWrappedCursor();
        return new Categorias(cursor.getInt(cursor.getColumnIndex(DBSchema.CategoriasTable.Columns.ID)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CategoriasTable.Columns.NOMBRE)),
                cursor.getString(cursor.getColumnIndex(DBSchema.CategoriasTable.Columns.CREATEDON)));
    }
}

class InventariosCursor extends CursorWrapper {
    public InventariosCursor(Cursor cursor) {
        super(cursor);
    }

    public Inventarios getInventarios(){
        Cursor cursor = getWrappedCursor();
        return new Inventarios(cursor.getInt(cursor.getColumnIndex(DBSchema.InventariosTable.Columns.ID)),
                cursor.getInt(cursor.getColumnIndex(DBSchema.InventariosTable.Columns.ID_CATEGORIA)),
                cursor.getString(cursor.getColumnIndex(DBSchema.InventariosTable.Columns.TITULO)),
                cursor.getString(cursor.getColumnIndex(DBSchema.InventariosTable.Columns.DESCRIPCION)),
                cursor.getInt(cursor.getColumnIndex(DBSchema.InventariosTable.Columns.CANTIDAD)),
                cursor.getString(cursor.getColumnIndex(DBSchema.InventariosTable.Columns.CREATEDON)),
                cursor.getString(cursor.getColumnIndex(DBSchema.InventariosTable.Columns.UPDATEDON)));
    }
}

class VentasCursor extends CursorWrapper {
    public VentasCursor(Cursor cursor) {
        super(cursor);
    }

    public VentasDB getVentas(){
        Cursor cursor = getWrappedCursor();
        return new VentasDB(cursor.getInt(cursor.getColumnIndex(DBSchema.VentasTable.Columns.ID)),
                cursor.getInt(cursor.getColumnIndex(DBSchema.VentasTable.Columns.ID_INVENTARIO)),
                cursor.getInt(cursor.getColumnIndex(DBSchema.VentasTable.Columns.ID_CATEGORIA)),
                cursor.getInt(cursor.getColumnIndex(DBSchema.VentasTable.Columns.CANTIDAD)),
                cursor.getString(cursor.getColumnIndex(DBSchema.InventariosTable.Columns.CREATEDON)));
    }
}


public final class Querys {

    private DateBaseHelper dataBaseHelper;
    private SQLiteDatabase db;

    // CREAMOS EL CONSTRUCTOR, .GETWRITABLEDATABASE NOS PERMITIRA ESCRIBIR EN LA MISMA
    public Querys(Context context){
        dataBaseHelper = new DateBaseHelper(context);
        db = dataBaseHelper.getWritableDatabase();
    }

    public Cliente getClientePorFiltro(String idUser){

        String query = "SELECT * FROM " + DBSchema.ClientesTable.NAME + " where nombre like '%" + idUser + "%'";
        Cliente myUser = new Cliente();
        ClientesCursor cursor = new ClientesCursor(db.rawQuery(query, null));
        while (cursor.moveToNext()){
            myUser = cursor.getCliente();
        }
        cursor.close();

        return myUser;
    }

    public Cotizacion getCotizacionPorID(String idUser){

        String query = "SELECT * FROM " + DBSchema.CotizacionesTable.NAME + " where id = " + idUser;
        Cotizacion myUser = new Cotizacion();
        CotizacionCursor cursor = new CotizacionCursor(db.rawQuery(query, null));
        while (cursor.moveToNext()){
            myUser = cursor.getCotizacion();
        }
        cursor.close();

        return myUser;
    }

    public Cliente getProveedorPorFiltro(String idUser){

        String query = "SELECT * FROM " + DBSchema.ProveedoresTable.NAME + " where nombre like '%" + idUser + "%'";
        Cliente myUser = new Cliente();
        ClientesCursor cursor = new ClientesCursor(db.rawQuery(query, null));
        while (cursor.moveToNext()){
            myUser = cursor.getCliente();
        }
        cursor.close();

        return myUser;
    }


    public List<Cotizacion> getAllCotizaciones(){
        ArrayList<Cotizacion> list = new ArrayList<Cotizacion>();

        CotizacionCursor cursor = new CotizacionCursor(db.rawQuery("SELECT * FROM " + DBSchema.CotizacionesTable.NAME, null));
        while (cursor.moveToNext()){
            list.add(cursor.getCotizacion());
        }
        cursor.close();

        return list;
    }

    public void insertPagosClientes(PagosClientes pagosClientes) {
        db.insert(
                DBSchema.PagosClientesTable.NAME,
                null,
                pagosClientes.toContentValues());
    }

    public List<PagosClientes> getAllPagosClientes(){
        ArrayList<PagosClientes> list = new ArrayList<PagosClientes>();

        PagosClientesCursor cursor = new PagosClientesCursor(db.rawQuery("SELECT * FROM " + DBSchema.PagosClientesTable.NAME, null));
        while (cursor.moveToNext()){
            list.add(cursor.getPagosClientes());
        }
        cursor.close();

        return list;
    }

    public void deleteAllPagosClientes() {

        PagosClientesCursor cursor = new PagosClientesCursor(db.rawQuery("DELETE FROM " + DBSchema.PagosClientesTable.NAME, null));
        while (cursor.moveToNext()) {
        }
        cursor.close();
    }

    public void updatePagosCliente(PagosClientes pagosClientes, String ID) {
        db.update(
                DBSchema.PagosClientesTable.NAME,
                pagosClientes.toContentValues(),
                DBSchema.PagosClientesTable.Columns.ID + " LIKE ?",
                new String[]{ID});
    }

    public void insertComprasProveedores(ComprasProveedores comprasProveedores) {
        db.insert(
                DBSchema.ComprasProveedoresTable.NAME,
                null,
                comprasProveedores.toContentValues());
    }

    public List<ComprasProveedores> getAllComprasProveedores(){
        ArrayList<ComprasProveedores> list = new ArrayList<ComprasProveedores>();

        ComprasProveedoresCursor cursor = new ComprasProveedoresCursor(db.rawQuery("SELECT * FROM " + DBSchema.ComprasProveedoresTable.NAME, null));
        while (cursor.moveToNext()){
            list.add(cursor.getCompraProveedor());
        }
        cursor.close();

        return list;
    }

    public void deleteAllComprasProveedores() {

        ComprasProveedoresCursor cursor = new ComprasProveedoresCursor(db.rawQuery("DELETE FROM " + DBSchema.ComprasProveedoresTable.NAME, null));
        while (cursor.moveToNext()) {
        }
        cursor.close();
    }

    public void updateComprasProveedores(ComprasProveedores comprasProveedores, String ID) {
        db.update(
                DBSchema.ComprasProveedoresTable.NAME,
                comprasProveedores.toContentValues(),
                DBSchema.ComprasProveedoresTable.Columns.ID + " LIKE ?",
                new String[]{ID});
    }

    public void insertRecordatorios(Recordatorios recordatorios) {
        db.insert(
                DBSchema.RecordatoriosTable.NAME,
                null,
                recordatorios.toContentValues());
    }

    public List<Recordatorios> getAllRecordatorios(){
        ArrayList<Recordatorios> list = new ArrayList<Recordatorios>();

        RecordatoriosCursor cursor = new RecordatoriosCursor(db.rawQuery("SELECT * FROM " + DBSchema.RecordatoriosTable.NAME, null));
        while (cursor.moveToNext()){
            list.add(cursor.getRecordatorio());
        }
        cursor.close();

        return list;
    }

    public void deleteAllRecordatorio() {

        RecordatoriosCursor cursor = new RecordatoriosCursor(db.rawQuery("DELETE FROM " + DBSchema.RecordatoriosTable.NAME, null));
        while (cursor.moveToNext()) {
        }
        cursor.close();
    }

    public void updateRecordatorios(Recordatorios recordatorios, String ID) {
        db.update(
                DBSchema.RecordatoriosTable.NAME,
                recordatorios.toContentValues(),
                DBSchema.RecordatoriosTable.Columns.ID + " LIKE ?",
                new String[]{ID});
    }

    public void insertCategorias(Categorias categorias) {
        db.insert(
                DBSchema.CategoriasTable.NAME,
                null,
                categorias.toContentValues());
    }

    public List<Categorias> getAllCategorias(){
        ArrayList<Categorias> list = new ArrayList<Categorias>();

        CategoriasCursor cursor = new CategoriasCursor(db.rawQuery("SELECT * FROM " + DBSchema.CategoriasTable.NAME, null));
        while (cursor.moveToNext()){
            list.add(cursor.getCategorias());
        }
        cursor.close();

        return list;
    }

    public void deleteAllCategorias() {

        CategoriasCursor cursor = new CategoriasCursor(db.rawQuery("DELETE FROM " + DBSchema.CategoriasTable.NAME, null));
        while (cursor.moveToNext()) {
        }
        cursor.close();
    }

    public void updateCategorias(Categorias categorias, String ID) {
        db.update(
                DBSchema.CategoriasTable.NAME,
                categorias.toContentValues(),
                DBSchema.CategoriasTable.Columns.ID + " LIKE ?",
                new String[]{ID});
    }

    public void insertInventario(Inventarios inventarios) {
        db.insert(
                DBSchema.InventariosTable.NAME,
                null,
                inventarios.toContentValues());
    }

    public List<Inventarios> getAllInventarios(){
        ArrayList<Inventarios> list = new ArrayList<Inventarios>();

        InventariosCursor cursor = new InventariosCursor(db.rawQuery("SELECT * FROM " + DBSchema.InventariosTable.NAME, null));
        while (cursor.moveToNext()){
            list.add(cursor.getInventarios());
        }
        cursor.close();

        return list;
    }

    public void deleteAllInventarios() {

        InventariosCursor cursor = new InventariosCursor(db.rawQuery("DELETE FROM " + DBSchema.InventariosTable.NAME, null));
        while (cursor.moveToNext()) {
        }
        cursor.close();
    }

    public void updateInventario(Inventarios inventarios, String ID) {
        db.update(
                DBSchema.InventariosTable.NAME,
                inventarios.toContentValues(),
                DBSchema.InventariosTable.Columns.ID + " LIKE ?",
                new String[]{ID});
    }

    public void insertVentas(VentasDB ventasDB) {
        db.insert(
                DBSchema.VentasTable.NAME,
                null,
                ventasDB.toContentValues());
    }

    public List<VentasDB> getAllVentas(){
        ArrayList<VentasDB> list = new ArrayList<VentasDB>();

        VentasCursor cursor = new VentasCursor(db.rawQuery("SELECT * FROM " + DBSchema.VentasTable.NAME, null));
        while (cursor.moveToNext()){
            list.add(cursor.getVentas());
        }
        cursor.close();

        return list;
    }

    public void deleteAllVentas() {

        VentasCursor cursor = new VentasCursor(db.rawQuery("DELETE FROM " + DBSchema.VentasTable.NAME, null));
        while (cursor.moveToNext()) {
        }
        cursor.close();
    }

    public void updateVentas(VentasDB ventasDB, String ID) {
        db.update(
                DBSchema.VentasTable.NAME,
                ventasDB.toContentValues(),
                DBSchema.PagosClientesTable.Columns.ID + " LIKE ?",
                new String[]{ID});
    }

    public List<ConceptoCotizacion> getAllConceptosCotizacionesPorID(String id){
        ArrayList<ConceptoCotizacion> list = new ArrayList<ConceptoCotizacion>();

        ConceptosCotizacionCursor cursor = new ConceptosCotizacionCursor(db.rawQuery("SELECT * FROM " + DBSchema.CotizacionesConceptosTable.NAME + " where idCotizacion = " + id, null));
        while (cursor.moveToNext()){
            list.add(cursor.getConceptoCotizacion());
        }
        cursor.close();

        return list;
    }

    public List<Concepto> getAllConceptos(){
        ArrayList<Concepto> list = new ArrayList<Concepto>();

        ConceptopsCursor cursor = new ConceptopsCursor(db.rawQuery("SELECT * FROM " + DBSchema.ConceptopsTable.NAME, null));
        while (cursor.moveToNext()){
            list.add(cursor.getConcepto());
        }
        cursor.close();

        return list;
    }

    public List<Cliente> getAllClientes(){
        ArrayList<Cliente> list = new ArrayList<Cliente>();

        ClientesCursor cursor = new ClientesCursor(db.rawQuery("SELECT * FROM " + DBSchema.ClientesTable.NAME, null));
        while (cursor.moveToNext()){
            list.add(cursor.getCliente());
        }
        cursor.close();

        return list;
    }

    public List<Cliente> getAllProveedores(){
        ArrayList<Cliente> list = new ArrayList<Cliente>();

        ClientesCursor cursor = new ClientesCursor(db.rawQuery("SELECT * FROM " + DBSchema.ProveedoresTable.NAME, null));
        while (cursor.moveToNext()){
            list.add(cursor.getCliente());
        }
        cursor.close();

        return list;
    }

    public void insertClientes(Cliente cliente) {
        db.insert(
                DBSchema.ClientesTable.NAME,
                null,
                cliente.toContentValues());
    }

    public void insertProveedores(Cliente cliente) {
        db.insert(
                DBSchema.ProveedoresTable.NAME,
                null,
                cliente.toContentValues());
    }

    public void deleteCliente(String cardID) {
        db.delete(
                DBSchema.ClientesTable.NAME,
                DBSchema.ClientesTable.Columns.ID + " LIKE ?",
                new String[]{cardID});
    }

    public void deleteProveedor(String cardID) {
        db.delete(
                DBSchema.ProveedoresTable.NAME,
                DBSchema.ProveedoresTable.Columns.ID + " LIKE ?",
                new String[]{cardID});
    }

    public void insertCotizacion(Cotizacion cotizacion) {
        db.insert(
                DBSchema.CotizacionesTable.NAME,
                null,
                cotizacion.toContentValues());
    }

    public void insertConcepto(Concepto concepto) {
        db.insert(
                DBSchema.ConceptopsTable.NAME,
                null,
                concepto.toContentValues());
    }

    public void insertConceptoCotizacion(ConceptoCotizacion conceptoCotizacion) {
        db.insert(
                DBSchema.CotizacionesConceptosTable.NAME,
                null,
                conceptoCotizacion.toContentValues());
    }

    public void deleteCotizacion(String cardID) {
        db.delete(
                DBSchema.CotizacionesTable.NAME,
                DBSchema.CotizacionesTable.Columns.ID + " LIKE ?",
                new String[]{cardID});
    }

    public void deleteCotizacionConceptos(String cardID) {
        db.delete(
                DBSchema.CotizacionesConceptosTable.NAME,
                DBSchema.CotizacionesTable.Columns.ID + " LIKE ?",
                new String[]{cardID});
    }

    public void deleteConcepto(String cardID) {
        db.delete(
                DBSchema.ConceptopsTable.NAME,
                DBSchema.ConceptopsTable.Columns.ID + " LIKE ?",
                new String[]{cardID});
    }

    public void deleteConceptoCotizacion(String cardID) {
        db.delete(
                DBSchema.CotizacionesConceptosTable.NAME,
                DBSchema.CotizacionesConceptosTable.Columns.ID + " LIKE ?",
                new String[]{cardID});
    }

    public void updateCliente(Cliente cliente, String cardID) {
        db.update(
                DBSchema.ClientesTable.NAME,
                cliente.toContentValues(),
                DBSchema.ClientesTable.Columns.ID + " LIKE ?",
                new String[]{cardID});
    }

    public void updateCotizacion(Cotizacion cotizacion, String cardID) {
        db.update(
                DBSchema.CotizacionesTable.NAME,
                cotizacion.toContentValues(),
                DBSchema.CotizacionesTable.Columns.FOLIO + " LIKE ?",
                new String[]{cardID});
    }

    public void updateConcepto(Concepto concepto, String cardID) {
        db.update(
                DBSchema.ConceptopsTable.NAME,
                concepto.toContentValues(),
                DBSchema.ConceptopsTable.Columns.ID + " LIKE ?",
                new String[]{cardID});
    }

    public void updateConceptoCotizacion(ConceptoCotizacion concepto, String cardID) {
        db.update(
                DBSchema.CotizacionesConceptosTable.NAME,
                concepto.toContentValues(),
                DBSchema.CotizacionesConceptosTable.Columns.ID + " LIKE ?",
                new String[]{cardID});
    }

    public void updateProveedor(Cliente cliente, String cardID) {
        db.update(
                DBSchema.ProveedoresTable.NAME,
                cliente.toContentValues(),
                DBSchema.ProveedoresTable.Columns.ID + " LIKE ?",
                new String[]{cardID});
    }
}
