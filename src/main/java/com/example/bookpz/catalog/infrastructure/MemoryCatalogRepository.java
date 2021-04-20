package com.example.bookpz.catalog.infrastructure;

import com.example.bookpz.catalog.domain.Book;
import com.example.bookpz.catalog.domain.CatalogRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
class MemoryCatalogRepository implements CatalogRepository {

    private final Map<Long, Book> storage = new ConcurrentHashMap<>();
    private final AtomicLong ID_NEXT_VALUE = new AtomicLong();

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public void saveBook(Book book) {
        long nextId = getNextId();
        book.setId(nextId);
        storage.put(nextId, book);
    }

    private long getNextId() {
        return ID_NEXT_VALUE.getAndIncrement();
    }
}
