package com.jzh.bookclient.controller;

import com.jzh.bookclient.entity.Book;
import com.jzh.bookclient.service.BookServiceClient;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
public class BookController {
    private final BookServiceClient bookServiceClient;

    @Autowired
    public BookController(BookServiceClient bookServiceClient) {
        this.bookServiceClient = bookServiceClient;
    }

    @PostMapping("/books")
    public Boolean addBook(@RequestBody Book book) {
        return bookServiceClient.addBook(book);
    }

    @DeleteMapping("/books/{id}")
    public Boolean deleteBook(@PathVariable Long id) {
        return bookServiceClient.deleteBook(id);
    }

    @PutMapping("/books/{id}")
    public Boolean updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookServiceClient.updateBook(id, book);
    }

    @PatchMapping("/books/{id}")
    public Boolean updateBookPartly(@PathVariable Long id, @RequestBody Book book) {
        return bookServiceClient.updateBookPartly(id, book);
    }

    @GetMapping("/books")
    public List<Book> getBookByName(@RequestParam String name) {
        return bookServiceClient.getBookByName(name);
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable Long id) {
        return bookServiceClient.getBookById(id);
    }
}
