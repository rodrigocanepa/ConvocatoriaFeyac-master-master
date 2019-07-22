package com.colabora.soluciones.convocatoriafeyac.Modelos;

public class Usuario {

    private String id;
    private String nombrePagWeb;
    private String tipoPagWeb;
    private String nombreEmpresa;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombrePagWeb() {
        return nombrePagWeb;
    }

    public void setNombrePagWeb(String nombrePagWeb) {
        this.nombrePagWeb = nombrePagWeb;
    }

    public String getTipoPagWeb() {
        return tipoPagWeb;
    }

    public void setTipoPagWeb(String tipoPagWeb) {
        this.tipoPagWeb = tipoPagWeb;
    }

    public String getNombreEmpresa() {
        return nombreEmpresa;
    }

    public void setNombreEmpresa(String nombreEmpresa) {
        this.nombreEmpresa = nombreEmpresa;
    }

    public Usuario(String id, String nombrePagWeb, String tipoPagWeb, String nombreEmpresa) {
        this.id = id;
        this.nombrePagWeb = nombrePagWeb;
        this.tipoPagWeb = tipoPagWeb;
        this.nombreEmpresa = nombreEmpresa;
    }

    public Usuario() {
    }
}
