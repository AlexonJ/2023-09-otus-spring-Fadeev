package ru.otus.spring.finalproject.easydesk.models.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SearchCondition <T extends Searchable> {

    private T field;

    private ComparisonConditions condition;

    private List<String> values;

}
