package com.example.bookpz.order.application.port;

import com.example.bookpz.order.domain.Order;

import java.util.List;
import java.util.Optional;

public interface QueryOrderUseCase {

    List<Order> findAll();

    Optional<Order> findById(Long id);
}
