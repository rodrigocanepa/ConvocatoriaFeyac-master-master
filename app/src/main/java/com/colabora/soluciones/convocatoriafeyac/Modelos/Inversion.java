package com.colabora.soluciones.convocatoriafeyac.Modelos;

public class Inversion {

    private int id;
    private String unidad;
    private String descripcion;
    private double importe;
    private int cantidad;
    private double monto;
    private int vida;
    private String categoria;
    private String tipo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getImporte() {
        return importe;
    }

    public void setImporte(double importe) {
        this.importe = importe;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Inversion() {
    }

    public Inversion(int id, String unidad, String descripcion, double importe, int cantidad, double monto, int vida, String categoria, String tipo) {
        this.id = id;
        this.unidad = unidad;
        this.descripcion = descripcion;
        this.importe = importe;
        this.cantidad = cantidad;
        this.monto = monto;
        this.vida = vida;
        this.categoria = categoria;
        this.tipo = tipo;
    }
}
