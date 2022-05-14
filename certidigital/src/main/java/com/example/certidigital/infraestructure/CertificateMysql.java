package com.example.certidigital.infraestructure;

import com.example.certidigital.domain.Certificate;
import com.example.certidigital.domain.CertificateRepository;

public class CertificateMysql implements CertificateRepository {
   //db connection
   @Override
   public Certificate save(Certificate certificate){
      Certificate certificateResult = new Certificate ();
      certificateResult.setId("1");
      certificateResult.setTipo(certificate.getTipo());
      certificateResult.setFecha(certificate.getFecha());
      certificateResult.setMotivo(certificate.getMotivo());
      certificateResult.setSolicitante(certificate.getSolicitante());
      return certificateResult;
   }

}
