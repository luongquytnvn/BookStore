package com.codegym.services;

import com.codegym.models.Payment;

public interface IPaymentService {
    Iterable<Payment> findAll();

    Payment findById(Long id);

    void save(Payment payment);

    void remove(Long id);
}
