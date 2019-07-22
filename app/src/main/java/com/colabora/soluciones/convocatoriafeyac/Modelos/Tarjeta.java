package com.colabora.soluciones.convocatoriafeyac.Modelos;

public class Tarjeta {

    private String id;
    private String fotoBack;
    private String fotoFront;
    private String nombreEmpleado;
    private String puestoEmpleado;
    private String telefono;
    private String correo;
    private String pagWeb;
    private String direccion;
    private String facebook;
    private String instagram;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFotoBack() {
        return fotoBack;
    }

    public void setFotoBack(String fotoBack) {
        this.fotoBack = fotoBack;
    }

    public String getFotoFront() {
        return fotoFront;
    }

    public void setFotoFront(String fotoFront) {
        this.fotoFront = fotoFront;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public String getPuestoEmpleado() {
        return puestoEmpleado;
    }

    public void setPuestoEmpleado(String puestoEmpleado) {
        this.puestoEmpleado = puestoEmpleado;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPagWeb() {
        return pagWeb;
    }

    public void setPagWeb(String pagWeb) {
        this.pagWeb = pagWeb;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public Tarjeta() {
    }

    public Tarjeta(String id, String fotoBack, String fotoFront, String nombreEmpleado, String puestoEmpleado, String telefono, String correo, String pagWeb, String direccion, String facebook, String instagram) {
        this.id = id;
        this.fotoBack = fotoBack;
        this.fotoFront = fotoFront;
        this.nombreEmpleado = nombreEmpleado;
        this.puestoEmpleado = puestoEmpleado;
        this.telefono = telefono;
        this.correo = correo;
        this.pagWeb = pagWeb;
        this.direccion = direccion;
        this.facebook = facebook;
        this.instagram = instagram;
    }
}
