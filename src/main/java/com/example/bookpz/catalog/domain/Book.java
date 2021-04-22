package com.example.bookpz.catalog.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@AllArgsConstructor
@ToString
@Setter
@Getter
public class Book {

    Long id;
    String title;
    String author;
    Integer year;
    BigDecimal price;

    public Book(String title, String author, Integer year, BigDecimal price) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.price = price;
    }
}
