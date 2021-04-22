package com.example.bookpz;


import com.example.bookpz.catalog.application.port.CatalogUseCase;
import com.example.bookpz.catalog.application.port.CatalogUseCase.UpdateBookCommand;
import com.example.bookpz.catalog.domain.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import static com.example.bookpz.catalog.application.port.CatalogUseCase.CreateBookCommand;

@Component
public class BookAppStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final String title;
    private final String author;
    private final Long limit;

    public BookAppStartup(
            CatalogUseCase catalog,
            @Value("${bookpz.catalog.query.title}") String title,
            @Value("${bookpz.catalog.query.author}") String author,
            @Value("${bookpz.catalog.limit}") Long limit
    ) {
        this.catalog = catalog;
        this.title = title;
        this.author = author;
        this.limit = limit;
    }

    @Override
    public void run(String... args) {
        //CatalogService service = new CatalogService();
        initData();
        searchCatalog();

    }

    private void searchCatalog() {
        getBookByTitle();
        findAndUpdate();
        getBookByTitle();
    }


    private void initData() {
        catalog.addBook(new CreateBookCommand(
                "Pan Tadeusz", "Adam Mickiewicz", 1883,new BigDecimal("19.90")));
        catalog.addBook(new CreateBookCommand(
                "Lalka", "Bolesław Prus", 1884, new BigDecimal("29.90")));
        catalog.addBook(new CreateBookCommand(
                "Ogniem i Mieczem", "Henryk Sienkiewicz", 1885, new BigDecimal("14.90")));
        catalog.addBook(new CreateBookCommand(
                "Chłopi", "Władysława Reymonta", 1886, new BigDecimal("21.90")));
        catalog.addBook(new CreateBookCommand(
                "Pan Wołodyjowski", "Henryk Sienkiewicz", 1899, new BigDecimal("18.90")));
    }

    private void getBookByAuthor() {
        List<Book> bookByAuthor = catalog.findByAuthor(author);
        bookByAuthor.forEach(System.out::println);
    }

    private void getBookByTitle() {
        List<Book> bookByTitle = catalog.findByTitle(title);
        bookByTitle.forEach(System.out::println);
    }


    private void findAndUpdate() {
        catalog.findOneByTitleAndAuthor("Pan Tadeusz", "Adam Mickiewicz")
                .ifPresent(book -> {
                    UpdateBookCommand command = UpdateBookCommand
                            .builder()
                            .id(book.getId())
                            .title("Pan Tadeusz, czyli Ostatni Zajazd na Litwie")
                            .build();
                    CatalogUseCase.UpdateBookResponse response = catalog.updateBook(command);
                    System.out.println("Updating book result: " + response.isSuccess());
                });
    }
}