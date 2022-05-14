package com.example.certidigital.rest;

import com.example.certidigital.application.PaymentService;
import com.example.certidigital.domain.Certificate;
import com.example.certidigital.domain.Payment;
import com.example.certidigital.domain.PaymentRepository;
import com.example.certidigital.infraestructure.PaymentMysql;
import com.example.certidigital.infraestructure.PaymentOracle;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
public class PaymentController {

   // Oracle
   // PaymentRepository paymentRepository = new PaymentOracle();

   //Mysql
   PaymentRepository paymentRepository = new PaymentMysql();

   PaymentService paymentService = new PaymentService(paymentRepository);

   @PostMapping
   public Payment savePayment(@RequestBody Payment payment){
     //Payment payment = new Payment();
     return paymentService.savePayment(payment);
   }

}
