package com.example.bookpz.catalog.web;

import com.example.bookpz.catalog.application.port.AuthorUseCase;
import com.example.bookpz.catalog.domain.Author;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/authors")
class AuthorsController {
    private final AuthorUseCase authors;

    @GetMapping
    public List<Author> findAll() {
        return authors.findAll();
    }
}

