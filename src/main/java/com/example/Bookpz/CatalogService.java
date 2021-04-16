package com.example.Bookpz;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class CatalogService {
    private final Map<Long, Book> storage = new ConcurrentHashMap<>();

    CatalogService(){
        storage.put(1L, new Book(1L, "Pan Tadeusz","Adam Mickiewicz",1883));
        storage.put(2L, new Book(2L, "Lalka","Bolesław Prus",1884));
        storage.put(3L, new Book(3L, "Ogniem i Mieczem","Henryk Sienkiewicz",1885));
        storage.put(4L, new Book(4L, "Chłopi","Władysława Reymonta",1886));
    }

    List<Book> findByTitle(String title){
        return storage.values().stream().filter(book -> book.title.startsWith(title)).collect(Collectors.toList());
    }
}
