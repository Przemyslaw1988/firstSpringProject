package com.example.bookpz.order.application.port;

import com.example.bookpz.order.domain.Order;

import java.util.List;

public interface QueryOrderUseCase {

    List<Order> findAll();
}
