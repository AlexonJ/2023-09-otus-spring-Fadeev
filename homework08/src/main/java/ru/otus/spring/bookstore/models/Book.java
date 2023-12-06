package ru.otus.spring.bookstore.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@Document(collection = "books")
public class Book {

    @Id
    private String id;

    private String title;

    @DBRef
    private Author author;

    private List<String> genres;

    private List<String> comments;

    public Book(String id, String title, Author author, List<String> genres) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genres = genres;
        this.comments = new ArrayList<>();
    }

}
