package com.example.certidigital.infraestructure;

import com.example.certidigital.domain.Payment;
import com.example.certidigital.domain.PaymentRepository;


public class PaymentDbTest implements PaymentRepository {
   //Connect H2
   @Override
   public Payment save(Payment payment) {
      Payment paymentResult = new Payment ();
      paymentResult.setId("1");
      paymentResult.setTipo("Interbank");
      paymentResult.setMoneda("soles");
      paymentResult.setCuenta("Ahorros");
      paymentResult.setTarjeta("4213550105729973");
      paymentResult.setVencimiento("03/26");
      paymentResult.setCvv("408");
      paymentResult.setDescripcion("Pago del certificado");

      return paymentResult;
   }
}
