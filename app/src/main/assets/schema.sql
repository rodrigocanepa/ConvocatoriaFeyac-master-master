CREATE TABLE "cotizaciones" ("id" INTEGER PRIMARY KEY AUTOINCREMENT,
                        "nombre" TEXT,
                        "folio" TEXT,
                        "fecha" TEXT,
                        "subtotal" TEXT,
                        "iva" TEXT,
                        "envio" TEXT,
                        "descuento" TEXT,
                        "total" TEXT,
                        "notasDestinatario" TEXT,
                        "terminos" TEXT,
                        "nombre_enc" TEXT,
                        "cargo_enc" TEXT,
                        "numero_enc" TEXT,
                        "vencimiento" TEXT,
                        "notasAdmin" TEXT);

CREATE TABLE "clientes" ("id" INTEGER PRIMARY KEY AUTOINCREMENT,
                                                 "nombre" TEXT,
                                                 "descripcion" TEXT,
                                                 "correo" TEXT,
                                                 "direccion" TEXT,
                                                 "telefono" TEXT,
                                                 "horario" TEXT);

CREATE TABLE "proveedores" ("id" INTEGER PRIMARY KEY AUTOINCREMENT,
                        "nombre" TEXT,
                        "descripcion" TEXT,
                        "correo" TEXT,
                        "direccion" TEXT,
                        "telefono" TEXT,
                        "horario" TEXT);

CREATE TABLE "conceptos" ("id" INTEGER PRIMARY KEY AUTOINCREMENT,
                        "nombre" TEXT,
                        "precio" TEXT);

CREATE TABLE "CotizacionesConceptos" ("id" INTEGER PRIMARY KEY AUTOINCREMENT,
                        "idCotizacion" TEXT,
                        "nombre" TEXT,
                        "cantidad" TEXT,
                        "precio" TEXT,
                        "importe" TEXT,
                        "iva" TEXT,
                        "iva_precio" TEXT);

CREATE TABLE "pagos_clientes" ("id" INTEGER PRIMARY KEY AUTOINCREMENT,
                        "concepto" TEXT,
                        "createdOn" TEXT,
                        "updatedOn" TEXT,
                        "estatus" TEXT,
                        "id_cliente" INTEGER,
                        "monto" TEXT);

CREATE TABLE "compras_proveedores" ("id" INTEGER PRIMARY KEY AUTOINCREMENT,
                        "id_proveedor" INTEGER,
                        "createdOn" TEXT,
                        "updatedOn" TEXT,
                        "concepto" TEXT,
                        "monto" TEXT);

CREATE TABLE "recordatorios" ("id" INTEGER PRIMARY KEY AUTOINCREMENT,
                        "date" TEXT,
                        "descripcion" TEXT,
                        "ubicacion" TEXT);

CREATE TABLE "categorias" ("id" INTEGER PRIMARY KEY AUTOINCREMENT,
                        "nombre" TEXT,
                        "createdOn" TEXT);

CREATE TABLE "inventarios" ("id" INTEGER PRIMARY KEY AUTOINCREMENT,
                        "id_categoria" INTEGER,
                        "titulo" TEXT,
                        "descripcion" TEXT,
                        "cantidad" INTEGER,
                        "createdOn" TEXT,
                        "updatedOn" TEXT);

CREATE TABLE "ventas" ("id" INTEGER PRIMARY KEY AUTOINCREMENT,
                        "id_inventario" INTEGER,
                        "id_categoria" INTEGER,
                        "cantidad" INTEGER,
                        "createdOn" TEXT);