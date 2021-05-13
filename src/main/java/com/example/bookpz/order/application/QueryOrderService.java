package com.example.bookpz.order.application;

import com.example.bookpz.order.application.port.QueryOrderUseCase;
import com.example.bookpz.order.db.OrderJpaRepository;
import com.example.bookpz.order.domain.Order;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class QueryOrderService implements QueryOrderUseCase {

    private final OrderJpaRepository repository;
    @Override
    public List<Order> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Order> findById(Long id) {
        return repository.findById(id);
    }
}
