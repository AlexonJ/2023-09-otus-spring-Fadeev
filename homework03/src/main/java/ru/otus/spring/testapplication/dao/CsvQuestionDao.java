package ru.otus.spring.testapplication.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.testapplication.config.TestFileNameProvider;
import ru.otus.spring.testapplication.dao.dto.Mapper;
import ru.otus.spring.testapplication.dao.dto.QuestionDto;
import ru.otus.spring.testapplication.domain.Question;
import ru.otus.spring.testapplication.exceptions.QuestionReadException;
import ru.otus.spring.testapplication.service.FileService;

import java.util.List;

@RequiredArgsConstructor
@Component
public class CsvQuestionDao implements QuestionDao {
    public static final Character COLUMNS_DELIMITER = ';';

    private final TestFileNameProvider fileNameProvider;

    private final FileService fileService;

    private final Mapper mapper;

    @Override
    public List<Question> findAll() {
        return getCsvToBean().stream().map(mapper::questionDtoToQuestion).toList();
    }

    public CsvToBean<QuestionDto> getCsvToBean() {
        try {
            return new CsvToBeanBuilder<QuestionDto>(fileService.getReader(fileNameProvider.getTestFileName()))
                    .withType(QuestionDto.class).withSkipLines(1).withSeparator(COLUMNS_DELIMITER).build();

        } catch (Exception e) {
            throw new QuestionReadException("An error occurred while reading the file: " + e.getMessage(), e);
        }
    }

}
