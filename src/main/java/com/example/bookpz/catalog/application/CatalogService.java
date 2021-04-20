package com.example.bookpz.catalog.application;

import com.example.bookpz.catalog.application.port.CatalogUseCase;
import com.example.bookpz.catalog.domain.Book;
import com.example.bookpz.catalog.domain.CatalogRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class CatalogService implements CatalogUseCase {

    private final CatalogRepository repository;

    public List<Book> findByTitle(String title) {
        return repository.findAll()
                .stream()
                .filter(book -> book
                        .getTitle()
                        .startsWith(title))
                .collect(Collectors.toList());
    }

    public List<Book> findByAuthor(String author) {
        return repository.findAll()
                .stream()
                .filter(book -> book
                        .getAuthor()
                        .startsWith(author))
                .collect(Collectors.toList());
    }

    public List<Book> findAll() {
        return null;
    }

    public Optional<Book> findOneByTitleAndAuthor(String title, String author) {
        return Optional.empty();
    }

    public void addBook(CreateBookCommand command) {
        Book book = new Book(command.getTitle(), command.getAuthor(), command.getYear());
        repository.saveBook(book);
    }

    public void removeBookById(Long id) {

    }

    public void updateBook() {

    }


}
