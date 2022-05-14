package com.example.certidigital.domain;

import lombok.Data;

@Data
public class Certificate {

   private String id;
   private String tipo;
   private String fecha;
   private String motivo;
   private String solicitante;

}
