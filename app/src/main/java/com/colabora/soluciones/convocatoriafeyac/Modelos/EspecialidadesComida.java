package com.colabora.soluciones.convocatoriafeyac.Modelos;

public class EspecialidadesComida {

    private String img;
    private String titulo;
    private int precio;
    private String descripcion;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EspecialidadesComida() {
    }

    public EspecialidadesComida(String img, String titulo, int precio, String descripcion) {
        this.img = img;
        this.titulo = titulo;
        this.precio = precio;
        this.descripcion = descripcion;
    }
}
