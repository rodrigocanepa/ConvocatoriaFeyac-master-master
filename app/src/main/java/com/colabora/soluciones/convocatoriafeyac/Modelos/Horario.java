package com.colabora.soluciones.convocatoriafeyac.Modelos;

public class Horario {

    private String dia;
    private String dehora;
    private String hastahora;

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getDehora() {
        return dehora;
    }

    public void setDehora(String dehora) {
        this.dehora = dehora;
    }

    public String getHastahora() {
        return hastahora;
    }

    public void setHastahora(String hastahora) {
        this.hastahora = hastahora;
    }

    public Horario() {
    }

    public Horario(String dia, String dehora, String hastahora) {
        this.dia = dia;
        this.dehora = dehora;
        this.hastahora = hastahora;
    }
}
