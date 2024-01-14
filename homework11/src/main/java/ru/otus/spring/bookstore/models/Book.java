package ru.otus.spring.bookstore.models;

//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.JoinTable;
//import jakarta.persistence.ManyToMany;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.NamedAttributeNode;
//import jakarta.persistence.NamedEntityGraph;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class Book {

    @NonNull
    @Id
    private long id;

    @NonNull
    @Column("title")
    private String title;

    @NonNull
    @Transient
    private Author author;

    @NonNull
    @Transient
    private List<Genre> genres;

    @NonNull
    @Transient
    private List<Comment> comments;


}
