package ru.otus.spring.finalproject.easydesk.models.search;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchCondition <T extends Searchable> {

    private T field;

    private ComparisonConditions condition;

    private List<String> values;

}
