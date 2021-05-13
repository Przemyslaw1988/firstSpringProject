package com.example.bookpz.order.application.port;

import com.example.bookpz.order.domain.Order;
import com.example.bookpz.order.domain.OrderItem;
import com.example.bookpz.order.domain.OrderStatus;
import com.example.bookpz.order.domain.Recipient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

public interface PlaceOrderUseCase {

    PlaceOrderResponse placeOrder(PlaceOrderCommand command);

    UpdateOrderResponse updateOrder(UpdateOrderCommand command);

    void removeById(Long id);

    @Builder
    @Value
    class PlaceOrderCommand {

        @Singular
        List<OrderItem> items;
        Recipient recipient;
    }

    @Value
    @Builder
    @AllArgsConstructor
    class UpdateOrderCommand {
        Long id;
        OrderStatus status;

        public Order updateFields(Order order) {
            if (status != null) {
                order.setStatus(status);
            }return order;
        }
    }
    @Value
    class UpdateOrderResponse{
        public static UpdateOrderResponse SUCCESS = new UpdateOrderResponse(true, Collections.emptyList());
        boolean success;
        List<String> errors;
    }

    @Value
    class PlaceOrderResponse {
        boolean success;
        Long OrderId;
        List<String> errors;

        public static PlaceOrderResponse success(Long OrderId) {
            return new PlaceOrderResponse(true, OrderId, emptyList());
        }

        public static PlaceOrderResponse failure(String... errors) {
            return new PlaceOrderResponse(false, null, Arrays.asList(errors));
        }
    }
}
