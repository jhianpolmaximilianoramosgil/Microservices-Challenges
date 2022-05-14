package com.example.certidigital.domain;

import lombok.Data;

@Data
public class Payment {

   private String id;
   private String tipo;
   private String  moneda;
   private String cuenta;
   private String tarjeta;
   private String vencimiento;
   private String cvv;
   private String descripcion;

}
