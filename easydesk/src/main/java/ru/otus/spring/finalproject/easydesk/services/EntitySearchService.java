package ru.otus.spring.finalproject.easydesk.services;

import java.util.stream.Stream;

import ru.otus.spring.finalproject.easydesk.dtos.SearchRequest;
import ru.otus.spring.finalproject.easydesk.models.search.Searchable;

public interface EntitySearchService {

    <T> Stream<T> findEntities(SearchRequest<? extends Searchable> searchRequest, Class<T> entityClass);
    <T> Stream<T> findEntities(SearchRequest<? extends Searchable> searchRequest, Class<T> entityClass, String additionalEntitiesString);
}
