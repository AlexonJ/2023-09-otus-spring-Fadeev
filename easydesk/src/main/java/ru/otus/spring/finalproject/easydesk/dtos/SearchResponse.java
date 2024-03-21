package ru.otus.spring.finalproject.easydesk.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SearchResponse<T> {

    List<T> result;

}
