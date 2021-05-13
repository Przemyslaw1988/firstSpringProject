package com.example.bookpz.catalog.application;

import com.example.bookpz.catalog.application.port.CatalogUseCase;
import com.example.bookpz.catalog.db.AuthorJpaRepository;
import com.example.bookpz.catalog.db.BookJpaRepository;
import com.example.bookpz.catalog.domain.Author;
import com.example.bookpz.catalog.domain.Book;
import com.example.bookpz.uploads.application.ports.UploadUseCase;
import com.example.bookpz.uploads.application.ports.UploadUseCase.SaveUploadCommand;
import com.example.bookpz.uploads.domain.Upload;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
class CatalogService implements CatalogUseCase {

    private final BookJpaRepository repository;
    private final AuthorJpaRepository authorRepository;
    private final UploadUseCase upload;

    @Override
    public List<Book> findAll() {
        return repository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return repository.findById(id);

    }

    @Override
    public List<Book> findByTitle(String title) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase()
                        .contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return repository.findAll()
                .stream()
//                .filter(book -> book.getAuthor().toLowerCase()
//                        .contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findOneByTitle(String title) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().contains(title))
                .findFirst();
    }

    @Override
    public Optional<Book> findOneByAuthor(String author) {
        return repository.findAll()
                .stream()
//                .filter(book -> book.getAuthor().toLowerCase()
//                        .startsWith(author.toLowerCase()))
                .findFirst();
    }

    @Override
    public List<Book> findByTitleAndAuthor(String title, String author) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase()
                        .contains(title.toLowerCase()))
//                .filter(book -> book.getAuthor().toLowerCase()
//                        .contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Book> findOneByTitleAndAuthor(String title, String author) {
        return repository.findAll()
                .stream()
                .filter(book -> book.getTitle().toLowerCase()
                        .startsWith(title.toLowerCase()))
//                .filter(book -> book.getAuthor().toLowerCase()
//                        .startsWith(author.toLowerCase()))
                .findFirst();
    }

    @Override
    public Book addBook(CreateBookCommand command) {
        Book book = toBook(command);
        return repository.save(book);
    }

    private Book toBook(CreateBookCommand command) {
        Book book = new Book(command.getTitle(), command.getYear(), command.getPrice());
                Set<Author> authors = command.getAuthors()
                        .stream()
                        .map(authorsId ->
                                authorRepository
                                        .findById(authorsId)
                                        .orElseThrow(() ->
                                                new IllegalArgumentException("Cannot find author with id " + authorsId))

                        ).collect(Collectors.toSet());
        book.setAuthors(authors);
        return book;
    }

    @Override
    public UpdateBookResponse updateBook(UpdateBookCommand command) {
        return repository
                .findById(command.getId())
                .map(book -> {
                    Book updatedBook = command.updateFields(book);
                    repository.save(updatedBook);
                    return UpdateBookResponse.SUCCESS;
                })
                .orElseGet(() -> new UpdateBookResponse(false, Collections.singletonList("Book not found with id: " + command.getId())));
    }

    @Override
    public void removeById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public void updateBookCover(UpdateBookCoverCommand command) {
        repository.findById(command.getId()).ifPresent(book ->
        {
            Upload saveToUpload = upload.save(
                    new SaveUploadCommand(
                            command.getFile(),
                            command.getContentType(),
                            command.getFilename()));
            book.setCoverId(saveToUpload.getId());
            repository.save(book);
        });
    }

    @Override
    public void removeBookCover(Long id) {
        repository.findById(id).ifPresent(book -> {
                    upload.removeById(book.getCoverId());
                    book.setCoverId(null);
                    repository.save(book);
                }
        );

    }


}