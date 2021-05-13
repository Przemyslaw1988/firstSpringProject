package com.example.bookpz.order.application;

import com.example.bookpz.order.application.port.PlaceOrderUseCase;
import com.example.bookpz.order.db.OrderJpaRepository;
import com.example.bookpz.order.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class PlaceOrderService implements PlaceOrderUseCase {

    private final OrderJpaRepository repository;

    @Override
    public PlaceOrderResponse placeOrder(PlaceOrderCommand command) {
        Order order = Order
                .builder()
                .recipient(command.getRecipient()).items(command.getItems())
                .build();
        Order save = repository.save(order);
        return PlaceOrderResponse.success(save.getId());
    }


    @Override
    public UpdateOrderResponse updateOrder(UpdateOrderCommand command) {

        return repository.findById(command.getId()).map(order -> {
            Order updateFields = command.updateFields(order);
            repository.save(updateFields);
            return UpdateOrderResponse.SUCCESS;
        }).orElseGet(()-> new UpdateOrderResponse(false, Arrays.asList("Status not found with this id" + command.getId())));
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }


}
