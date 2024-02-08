package ru.otus.spring.bookstore.config.batchconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SharedBatchConfig {

    public static final String AUTHORS_COLLECTION_NAME = "authors";

    public static final String BOOKS_COLLECTION_NAME = "books";

    @Bean
    public Map<Long, String> authorRelations() {
        return new HashMap<>();
    }

    @Bean
    public Map<Long, String> bookRelations() {
        return new HashMap<>();
    }
}
