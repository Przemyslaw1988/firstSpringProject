package com.example.bookpz.catalog.domain;

import java.util.List;

public interface CatalogRepository {

    List<Book> findAll();

    void saveBook(Book book);
}