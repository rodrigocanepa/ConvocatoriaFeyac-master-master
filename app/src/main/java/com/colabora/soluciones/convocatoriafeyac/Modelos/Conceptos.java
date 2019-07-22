package com.colabora.soluciones.convocatoriafeyac.Modelos;

public class Conceptos {

    private String conceptos;
    private String cantidad;
    private String precio;
    private String importe;
    private String impuestos;
    private String impuestos_pesos;

    public String getConceptos() {
        return conceptos;
    }

    public void setConceptos(String conceptos) {
        this.conceptos = conceptos;
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

    public String getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(String impuestos) {
        this.impuestos = impuestos;
    }

    public String getImpuestos_pesos() {
        return impuestos_pesos;
    }

    public void setImpuestos_pesos(String impuestos_pesos) {
        this.impuestos_pesos = impuestos_pesos;
    }

    public Conceptos() {
    }

    public Conceptos(String conceptos, String cantidad, String precio, String importe, String impuestos, String impuestos_pesos) {
        this.conceptos = conceptos;
        this.cantidad = cantidad;
        this.precio = precio;
        this.importe = importe;
        this.impuestos = impuestos;
        this.impuestos_pesos = impuestos_pesos;
    }
}
