package com.colabora.soluciones.convocatoriafeyac.Modelos;

public class caracteristicas_web_dos {

    private String img;
    private String texto;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public caracteristicas_web_dos() {
    }

    public caracteristicas_web_dos(String img, String texto) {
        this.img = img;
        this.texto = texto;
    }
}
