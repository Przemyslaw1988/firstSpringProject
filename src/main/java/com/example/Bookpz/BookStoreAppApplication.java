package com.example.Bookpz;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class BookStoreAppApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BookStoreAppApplication.class, args);
	}
	private final CatalogService catalogService;

	public BookStoreAppApplication(CatalogService catalogService) {
		this.catalogService = catalogService;
	}

	@Override
	public void run(String... args) {
		//CatalogService service = new CatalogService();
		List<Book> book = catalogService.findByTitle("Pan Tadeusz");
		book.forEach(System.out::println);
	}
}
