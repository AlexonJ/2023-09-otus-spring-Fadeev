package ru.otus.spring.homework2.dao.dto;

import com.opencsv.bean.AbstractCsvConverter;
import ru.otus.spring.homework2.domain.Answer;

public class AnswerCsvConverter extends AbstractCsvConverter {

    @Override
    public Object convertToRead(String value) {
        var valueArr = value.split("%");
        return new Answer(valueArr[0], Boolean.parseBoolean(valueArr[1]));
    }
}
