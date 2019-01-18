package com.lobosistemas.x.model;

public class Empresa {

    private String empresa_razonsocial;
    private String factura_numero;
    private int calc;

    public String getFactura_numero() {
        return factura_numero;
    }

    public void setFactura_numero(String factura_numero) {
        this.factura_numero = factura_numero;
    }

    public int getCalc() {
        return calc;
    }

    public void setCalc(int calc) {
        this.calc = calc;
    }

    public String getEmpresa_razonsocial() {
        return empresa_razonsocial;
    }

    public void setEmpresa_razonsocial(String empresa_razonsocial) {
        this.empresa_razonsocial = empresa_razonsocial;
    }

}
