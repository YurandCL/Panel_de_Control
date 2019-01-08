package com.lobosistemas.gestiondeventas.model;

public class Empresa {

    private String empresa_razonsocial;
    private int empresa_cod;
    private String calc;

    public int getEmpresa_cod() {
        return empresa_cod;
    }

    public void setEmpresa_cod(int empresa_cod) {
        this.empresa_cod = empresa_cod;
    }

    public String getCalc() {
        return calc;
    }

    public void setCalc(String calc) {
        this.calc = calc;
    }

    public String getEmpresa_razonsocial() {
        return empresa_razonsocial;
    }

    public void setEmpresa_razonsocial(String empresa_razonsocial) {
        this.empresa_razonsocial = empresa_razonsocial;
    }

}