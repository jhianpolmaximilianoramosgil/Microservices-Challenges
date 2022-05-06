package com.uni.dev.msproduct.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
@Document(collection = "product")

public class Payment {


    @Id
    private String id;
    private String tipo;
    private String  moneda;
    private String cuenta;
    private String tarjeta;
    private String vencimiento;
    private String cvv;
    private String descripcion;


    public String getId() { return id;}

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {return tipo;}

    public void setTipo(String tipo) {this.tipo = tipo;}

    public String getMoneda() {return moneda;}

    public void setMoneda(String moneda) {this.tipo = moneda;}

    public String getCuenta() {
        return cuenta;
    }

    public void setCuenta(String cuenta) {
        this.cuenta = cuenta;
    }

    public String getTarjeta() {return tarjeta;}

    public void set(String tarjeta) {this.tarjeta = tarjeta;}

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
