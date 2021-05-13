package com.example.bookpz.catalog.db;

import com.example.bookpz.catalog.domain.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorJpaRepository extends JpaRepository<Author, Long> {
}
