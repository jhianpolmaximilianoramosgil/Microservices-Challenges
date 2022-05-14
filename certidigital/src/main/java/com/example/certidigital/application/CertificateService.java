package com.example.certidigital.application;

import com.example.certidigital.domain.Certificate;
import com.example.certidigital.domain.CertificateRepository;


public class CertificateService {

   CertificateRepository certificateRepository;

   public CertificateService(CertificateRepository certificateRepository) {
      this.certificateRepository = certificateRepository;
   }

   public Certificate saveCertificate(Certificate certificate){
      return certificateRepository.save(certificate);
   }

}
