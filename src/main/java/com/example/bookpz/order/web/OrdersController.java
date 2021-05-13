package com.example.bookpz.order.web;

import com.example.bookpz.order.application.port.PlaceOrderUseCase;
import com.example.bookpz.order.application.port.PlaceOrderUseCase.PlaceOrderCommand;
import com.example.bookpz.order.application.port.PlaceOrderUseCase.PlaceOrderResponse;
import com.example.bookpz.order.application.port.PlaceOrderUseCase.UpdateOrderCommand;
import com.example.bookpz.order.application.port.PlaceOrderUseCase.UpdateOrderResponse;
import com.example.bookpz.order.application.port.QueryOrderUseCase;
import com.example.bookpz.order.domain.Order;
import com.example.bookpz.order.domain.OrderItem;
import com.example.bookpz.order.domain.OrderStatus;
import com.example.bookpz.order.domain.Recipient;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/orders")
public class OrdersController {

    private final PlaceOrderUseCase placeOrderUseCase;
    private final QueryOrderUseCase queryOrder;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAll() {
        return queryOrder.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id) {
        return queryOrder.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> addOrder(@Valid @RequestBody RestOrderCommand command) {
        PlaceOrderResponse response = placeOrderUseCase.placeOrder(command.toCreateOrderCommand());
        return ResponseEntity.created(createOrderUri(response)).build();
    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateOrder(@PathVariable Long id, @RequestBody RestOrderCommand command) {
        UpdateOrderResponse response = placeOrderUseCase.updateOrder(command.toUpdateOrderCommand(id));
        if (!response.isSuccess()) {
            String reason = String.join(", ", response.getErrors());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, reason);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeOrderById(@PathVariable Long id) {
        placeOrderUseCase.removeById(id);
    }

    private URI createOrderUri(PlaceOrderResponse response) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/" + response.getOrderId().toString()).build().toUri();
    }

    @Data
    private static class RestOrderCommand {

        @NotBlank(message = "Insert correct data for items")
        List<OrderItem> items;

        @NotBlank(message = "Insert correct data for recipient")
        Recipient recipient;

        @NotBlank
        OrderStatus status;

        PlaceOrderCommand toCreateOrderCommand() {
            return PlaceOrderCommand
                    .builder()
                    .items(items)
                    .recipient(recipient)
                    .build();
        }
        UpdateOrderCommand toUpdateOrderCommand(Long id) {
            return new UpdateOrderCommand(id, status);
        }
    }
}
