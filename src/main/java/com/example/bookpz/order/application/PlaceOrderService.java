package com.example.bookpz.order.application;

import com.example.bookpz.order.application.port.PlaceOrderUseCase;
import com.example.bookpz.order.domain.Order;
import com.example.bookpz.order.domain.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlaceOrderService implements PlaceOrderUseCase {
    private final OrderRepository repository;

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {
        Order order = Order
                .builder()
                .recipient(command.getRecipient()).items(command.getItems())
                .build();
        Order save = repository.save(order);
        return PlaceOrderResponse.success(save.getId());
    }
}
