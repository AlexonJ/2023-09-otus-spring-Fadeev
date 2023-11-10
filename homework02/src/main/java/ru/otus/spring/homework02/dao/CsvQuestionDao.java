package ru.otus.spring.homework02.dao;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.spring.homework02.config.TestFileNameProvider;
import ru.otus.spring.homework02.dao.dto.Mapper;
import ru.otus.spring.homework02.dao.dto.QuestionDto;
import ru.otus.spring.homework02.domain.Question;
import ru.otus.spring.homework02.exceptions.QuestionReadException;
import ru.otus.spring.homework02.service.FileService;

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
