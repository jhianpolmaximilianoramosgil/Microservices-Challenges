package com.example.certidigital.infraestructure;

import com.example.certidigital.domain.Certificate;
import com.example.certidigital.domain.CertificateRepository;



public class CertificateDbTest implements CertificateRepository {
   //Connect H2
   @Override
   public Certificate save(Certificate certificate) {
      Certificate certificateResult = new Certificate ();
      certificateResult.setId("1");
      certificateResult.setTipo("Estudios");
      certificateResult.setFecha("15/05/2022");
      certificateResult.setMotivo("Solicito certificado de estudios para estudiar una carrera profesional");
      certificateResult.setSolicitante("Jhianpol Ramos Gil");
      return certificateResult;
   }
}
