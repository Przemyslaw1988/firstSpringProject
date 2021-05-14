package com.example.bookpz;


import com.example.bookpz.catalog.application.port.CatalogUseCase;
import com.example.bookpz.catalog.db.AuthorJpaRepository;
import com.example.bookpz.catalog.domain.Author;
import com.example.bookpz.catalog.domain.Book;
import com.example.bookpz.order.application.port.ManipulateOrderUseCase;
import com.example.bookpz.order.application.port.QueryOrderUseCase;
import com.example.bookpz.order.domain.OrderItem;
import com.example.bookpz.order.domain.Recipient;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Set;

import static com.example.bookpz.catalog.application.port.CatalogUseCase.CreateBookCommand;
import static com.example.bookpz.order.application.port.ManipulateOrderUseCase.PlaceOrderCommand;
import static com.example.bookpz.order.application.port.ManipulateOrderUseCase.PlaceOrderResponse;

@Component
@AllArgsConstructor
public class BookAppStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final ManipulateOrderUseCase placeOrder;
    private final QueryOrderUseCase queryOrder;
    private final AuthorJpaRepository repository;

    @Override
    public void run(String... args) {

        initData();
        placeOrder();
    }

    public void placeOrder() {
        Book effectiveJava = catalog.findOneByTitle("Effective Java")
                .orElseThrow(() -> new IllegalStateException("Cannot find a book"));
        Book javaPuzzlers = catalog.findOneByTitle("Java Puzzlers")
                .orElseThrow(() -> new IllegalStateException("Cannot find a book"));

        // create recipient
        Recipient recipient = Recipient
                .builder()
                .name("Jan Kowalski")
                .phone("123-456-789")
                .street("Armii Krajowej 31")
                .city("Krakow")
                .zipCode("30-150")
                .email("jan@example.org")
                .build();

        PlaceOrderCommand command = PlaceOrderCommand
                .builder()
                .recipient(recipient)
                .item(new OrderItem(effectiveJava.getId(), 16))
                .item(new OrderItem(javaPuzzlers.getId(), 7))
                .build();

        PlaceOrderResponse response = placeOrder.placeOrder(command);
        String result = response.handle(
                orderId -> "Created ORDER with id: " + orderId,
                error -> "Failed to created order: " + error
        );
        System.out.println(result);

        // list all orders
        queryOrder.findAll()
                .forEach(order -> System.out.println("GOT ORDER WITH TOTAL PRICE: " + order.totalPrice() + " DETAILS: " + order));
    }


    private void initData() {

        Author joshua = new Author("Joshua", "Bloch");
        Author neal = new Author("Neal", "Gafter");
        repository.save(joshua);
        repository.save(neal);

        CreateBookCommand effectiveJava = new CreateBookCommand(
                "Effective Java",
                Set.of(joshua.getId()),
                2005,
                new BigDecimal("79.00")
        );
        CreateBookCommand javaPuzzlers = new CreateBookCommand(
                "Java Puzzlers",
                Set.of(joshua.getId(), neal.getId()),
                2018,
                new BigDecimal("99.00")
        );
        catalog.addBook(effectiveJava);
        catalog.addBook(javaPuzzlers);
    }
}