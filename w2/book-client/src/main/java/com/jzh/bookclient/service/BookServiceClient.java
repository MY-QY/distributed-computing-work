package com.jzh.bookclient.service;

import com.jzh.bookclient.entity.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@FeignClient("BOOK-SERVICE")
@Service
public interface BookServiceClient {
    @PostMapping("/books")
    Boolean addBook(@RequestBody Book book);


    @DeleteMapping("/books/{id}")
    Boolean deleteBook(@PathVariable Long id);

    @PutMapping("/books/{id}")
    Boolean updateBook(@PathVariable Long id, @RequestBody Book book);

    @PatchMapping("/books/{id}")
    Boolean updateBookPartly(@PathVariable Long id, @RequestBody Book book);

    @GetMapping("/books")
    List<Book> getBookByName(@RequestParam String name);

    @GetMapping("/books/{id}")
    Book getBookById(@PathVariable Long id);
}
