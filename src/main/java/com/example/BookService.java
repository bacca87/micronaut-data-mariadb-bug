package com.example;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Transactional
public class BookService {

    @Inject
    private BookRepository bookRepository;

    public Publisher<Book> getAll() {
        return bookRepository.findAll();
    }

    public Mono<Book> get(int id) {
        return Mono.from(bookRepository.findById(id))
                .switchIfEmpty(Mono.error(new IllegalArgumentException("Invalid book id!")));
    }

    public Publisher<Book> add(@NotNull String title) {
        return Mono.just(new Book())
                .map(entity -> {
                    entity.setTitle(title);
                    return entity;
                })
                .flatMap(entity -> Mono.from(bookRepository.save(entity)));
    }

    public Mono<Void> setupData() {
        List<Book> books = new ArrayList<>();

        for(int i = 1; i<=33; i++) {
            books.add(new Book(i, "book " + i));
        }

        return Flux.fromIterable(books)
                .flatMap(bookRepository::save).then();
    }
}