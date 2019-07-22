package com.colabora.soluciones.convocatoriafeyac.Db;

public final class DBSchema {

    public static final class CotizacionesTable{
        public static final String NAME = "cotizaciones";

        public static final class Columns {
            public static final String ID = "id";
            public static final String FOLIO = "folio";
            public static final String FECHA = "fecha";
            public static final String SUBTOTAL = "subtotal";
            public static final String IVA = "iva";
            public static final String ENVIO = "envio";
            public static final String DESCUENTO = "descuento";
            public static final String TOTAL = "total";
            public static final String NOTAS_DESTINATARIO = "notasDestinatario";
            public static final String TERMINOS = "terminos";
            public static final String NOMBRE_ENCARGADO = "nombre_enc";
            public static final String CARGO_ENCARGADO = "cargo_enc";
            public static final String NUMERO_ENCARGADO = "numero_enc";
            public static final String VENCIMIENTO = "vencimiento";
            public static final String NOTAS_ADMIN = "notasAdmin";

        }
    }

    public static final class ClientesTable{
        public static final String NAME = "clientes";

        public static final class Columns {
            public static final String ID = "id";
            public static final String NOMBRE = "nombre";
            public static final String DESCRIPCION = "descripcion";
            public static final String CORREO = "correo";
            public static final String DIRECCION = "direccion";
            public static final String TELEFONO = "telefono";
            public static final String HORARIO = "horario";
        }
    }

    public static final class ProveedoresTable{
        public static final String NAME = "proveedores";

        public static final class Columns {
            public static final String ID = "id";
            public static final String NOMBRE = "nombre";
            public static final String DESCRIPCION = "descripcion";
            public static final String CORREO = "correo";
            public static final String DIRECCION = "direccion";
            public static final String TELEFONO = "telefono";
            public static final String HORARIO = "horario";
        }
    }

    public static final class ConceptopsTable{
        public static final String NAME = "conceptos";

        public static final class Columns {
            public static final String ID = "id";
            public static final String NOMBRE = "nombre";
            public static final String PRECIO = "precio";
        }
    }

    public static final class CotizacionesConceptosTable{
        public static final String NAME = "CotizacionesConceptos";

        public static final class Columns {
            public static final String ID = "id";
            public static final String ID_COTIZACION = "idCotizacion";
            public static final String NOMBRE = "nombre";
            public static final String CANTIDAD = "cantidad";
            public static final String PRECIO = "precio";
            public static final String IMPORTE = "importe";
            public static final String IVA = "iva";
            public static final String IVA_PRECIO = "iva_precio";
        }
    }

    public static final class PagosClientesTable{
        public static final String NAME = "pagos_clientes";

        public static final class Columns {
            public static final String ID = "id";
            public static final String CONCEPTO = "concepto";
            public static final String CREATEDON = "createdOn";
            public static final String UPDATEDON = "updatedOn";
            public static final String ESTATUS = "estatus";
            public static final String IDCLIENTE = "id_cliente";
            public static final String MONTO = "monto";
        }
    }

    public static final class ComprasProveedoresTable{
        public static final String NAME = "compras_proveedores";

        public static final class Columns {
            public static final String ID = "id";
            public static final String ID_PROVEEDOR = "id_proveedor";
            public static final String CREATEDON = "createdOn";
            public static final String UPDATEDON = "updatedOn";
            public static final String CONCEPTO = "concepto";
            public static final String MONTO = "monto";
        }
    }

    public static final class RecordatoriosTable{
        public static final String NAME = "recordatorios";

        public static final class Columns {
            public static final String ID = "id";
            public static final String DATE = "date";
            public static final String DESCRIPCION = "descripcion";
            public static final String UBICACION = "ubicacion";
        }
    }

    public static final class CategoriasTable{
        public static final String NAME = "categorias";

        public static final class Columns {
            public static final String ID = "id";
            public static final String NOMBRE = "nombre";
            public static final String CREATEDON = "createdOn";
        }
    }

    public static final class InventariosTable{
        public static final String NAME = "inventarios";

        public static final class Columns {
            public static final String ID = "id";
            public static final String ID_CATEGORIA = "nombre";
            public static final String TITULO = "titulo";
            public static final String DESCRIPCION = "descripcion";
            public static final String CANTIDAD = "cantidad";
            public static final String CREATEDON = "createdOn";
            public static final String UPDATEDON = "updatedOn";

        }
    }

    public static final class VentasTable{
        public static final String NAME = "ventas";

        public static final class Columns {
            public static final String ID = "id";
            public static final String ID_INVENTARIO = "id_inventario";
            public static final String ID_CATEGORIA = "id_categoria";
            public static final String CANTIDAD = "cantidad";
            public static final String CREATEDON = "createdOn";

        }
    }
}
