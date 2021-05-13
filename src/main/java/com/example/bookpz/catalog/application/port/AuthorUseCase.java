package com.example.bookpz.catalog.application.port;

import com.example.bookpz.catalog.domain.Author;

import java.util.List;

public interface AuthorUseCase {

    List<Author> findAll();
}
