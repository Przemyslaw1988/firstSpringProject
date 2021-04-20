package com.example.bookpz;


import com.example.bookpz.catalog.application.port.CatalogUseCase;
import com.example.bookpz.catalog.domain.Book;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.example.bookpz.catalog.application.port.CatalogUseCase.CreateBookCommand;

@Component
public class BookAppStartup implements CommandLineRunner {

    private final CatalogUseCase catalog;
    private final String title;
    private final String author;


    public BookAppStartup(
            CatalogUseCase catalog,
            @Value("${bookpz.catalog.query.title}") String title,
            @Value("${bookpz.catalog.query.author}") String author
    ) {
        this.catalog = catalog;
        this.title = title;
        this.author = author;
    }

    @Override
    public void run(String... args) {
        //CatalogService service = new CatalogService();
        initData();
        getBookByTitle();
        getBookByAuthor();


    }

    private void initData() {
        catalog.addBook(new CreateBookCommand(
                "Pan Tadeusz", "Adam Mickiewicz", 1883));
        catalog.addBook(new CreateBookCommand(
                "Lalka", "Bolesław Prus", 1884));
        catalog.addBook(new CreateBookCommand(
                "Ogniem i Mieczem", "Henryk Sienkiewicz", 1885));
        catalog.addBook(new CreateBookCommand(
                "Chłopi", "Władysława Reymonta", 1886));
        catalog.addBook(new CreateBookCommand(
                "Pan Wołodyjowski", "Henryk Sienkiewicz", 1899));
    }

    private void getBookByAuthor() {
        List<Book> bookByAuthor = catalog.findByAuthor(author);
        bookByAuthor.forEach(System.out::println);
    }

    private void getBookByTitle() {
        List<Book> bookByTitle = catalog.findByTitle(title);
        bookByTitle.forEach(System.out::println);
    }
}
