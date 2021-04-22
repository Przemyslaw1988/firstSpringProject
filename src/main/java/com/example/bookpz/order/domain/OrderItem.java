package com.example.bookpz.order.domain;

import com.example.bookpz.catalog.domain.Book;
import lombok.Value;

@Value
public class OrderItem {

    Book book;

    int quantity;

}
