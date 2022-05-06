package com.uni.dev.msproduct.service;

import com.uni.dev.msproduct.model.Payment;


import java.util.List;

public interface PaymentService {

    List<Payment> findAll();

    Payment create(Payment payment);

    Payment update(Payment payment);

    void delete(String id);

}
