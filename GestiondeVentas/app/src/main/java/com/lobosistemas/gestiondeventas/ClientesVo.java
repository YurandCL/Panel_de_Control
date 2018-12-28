package com.lobosistemas.gestiondeventas;

public class ClientesVo {

    private String nombre;
    private String dias_retraso;

    public ClientesVo(){

    }

    public ClientesVo(String nombre, String dias_retraso) {
        this.nombre = nombre;
        this.dias_retraso = dias_retraso;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDias_retraso() {
        return dias_retraso;
    }

    public void setDias_retraso(String dias_retraso) {
        this.dias_retraso = dias_retraso;
    }
}
