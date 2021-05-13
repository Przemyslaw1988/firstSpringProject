package com.example.bookpz.catalog.application;

import com.example.bookpz.catalog.application.port.AuthorUseCase;
import com.example.bookpz.catalog.db.AuthorJpaRepository;
import com.example.bookpz.catalog.domain.Author;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService implements AuthorUseCase {
    private final AuthorJpaRepository repository;

    @Override
    public List<Author> findAll() {
        return repository.findAll();
    }
}
