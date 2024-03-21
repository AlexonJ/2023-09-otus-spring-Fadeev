package ru.otus.spring.finalproject.easydesk.dtos;

import lombok.Data;
import ru.otus.spring.finalproject.easydesk.models.search.SearchCondition;
import ru.otus.spring.finalproject.easydesk.models.search.Searchable;

import java.util.List;

@Data
public class SearchRequest<T extends Searchable> {

    List<SearchCondition<T>> searchConditionals;

}
