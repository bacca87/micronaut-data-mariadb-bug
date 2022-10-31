package com.example;

import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import jakarta.inject.Inject;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

@Controller("/")
public class BookController {

    @Inject
    private BookService bookService;

    @Get("/setup")
    Mono<Void> setupData() {
        return bookService.setupData();
    }

    @Get("/all")
    Publisher<Book> all() {
        return bookService.getAll();
    }

    @Get("/add")
    Publisher<Book> add(String title) {
        return bookService.add(title);
    }
}