package ru.otus.spring.homework1.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import ru.otus.spring.homework1.config.TestFileNameProvider;
import ru.otus.spring.homework1.dao.dto.QuestionDto;
import ru.otus.spring.homework1.domain.Question;
import ru.otus.spring.homework1.exceptions.QuestionReadException;
import ru.otus.spring.homework1.service.FileService;

import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {

    public static final Character COLUMNS_DELIMITER = ';';

    private final TestFileNameProvider fileNameProvider;

    private final FileService fileService;

    @Override
    public List<Question> findAll() {
        try {
            return new CsvToBeanBuilder<QuestionDto>(fileService.getReader(fileNameProvider.getTestFileName()))
                    .withType(QuestionDto.class).withSkipLines(1).withSeparator(COLUMNS_DELIMITER).build()
                    .stream().map(QuestionDto::toDomainObject).toList();

        } catch (Exception e) {
            throw new QuestionReadException("An error occurred while reading the file: " + e.getMessage(), e);
        }
    }
}
