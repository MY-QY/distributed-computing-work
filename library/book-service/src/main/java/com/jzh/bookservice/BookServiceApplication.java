package com.jzh.bookservice;

import entity.Book;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@SpringBootApplication
@RestController
public class BookServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BookServiceApplication.class, args);
    }

    @PostMapping("/books")
    public Boolean addBook(@RequestBody Book book) {
        System.out.println("添加书籍：" + book);
        return true;
    }

    @DeleteMapping("/books/{id}")
    public Boolean deleteBook(@PathVariable Long id) {
        System.out.println("成功删除 id 为 " + id + " 的书籍");
        return true;
    }

    @PutMapping("/books/{id}")
    public Boolean updateBook(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);
        System.out.println("更新书为 " + book);
        return true;
    }

    @PatchMapping("/books/{id}")
    public Boolean updateBookPartly(@PathVariable Long id, @RequestBody Book book) {
        book.setId(id);
        System.out.println("部分更新书为：" + book);
        return true;
    }

    @GetMapping("/books")
    public List<Book> getBookByName(@RequestParam String name) {
        ArrayList<Book> arr = new ArrayList<>();
        Random random = new Random();
        arr.add(new Book(random.nextLong(), name + UUID.randomUUID()));
        arr.add(new Book(random.nextLong(), name + UUID.randomUUID()));
        arr.add(new Book(random.nextLong(), name + UUID.randomUUID()));
        System.out.println("获取到 " + name + " 相关的书籍如下：");
        System.out.println(arr);
        return arr;
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable Long id) {
        Book book = new Book(id, "book-" + id);
        System.out.println("获取到书籍：" + book);
        return book;
    }


}
