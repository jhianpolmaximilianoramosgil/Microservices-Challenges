package com.example.certidigital.infraestructure;

import com.example.certidigital.domain.Payment;
import com.example.certidigital.domain.PaymentRepository;

public class PaymentMysql implements PaymentRepository {
   //db connection
   @Override
   public Payment save(Payment payment){
      Payment paymentResult = new Payment ();
      paymentResult.setId("1");
      paymentResult.setTipo(payment.getTipo());
      paymentResult.setMoneda(payment.getMoneda());
      paymentResult.setCuenta(payment.getCuenta());
      paymentResult.setTarjeta(payment.getTarjeta());
      paymentResult.setVencimiento(payment.getVencimiento());
      paymentResult.setCvv(payment.getCvv());
      paymentResult.setDescripcion(payment.getDescripcion());
      return paymentResult;
   }

}
