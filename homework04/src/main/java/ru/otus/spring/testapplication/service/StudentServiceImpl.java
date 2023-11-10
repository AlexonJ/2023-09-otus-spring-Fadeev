package ru.otus.spring.testapplication.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.testapplication.domain.Student;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final IOService ioService;

    private final MessageService messageService;

    @Override
    public Student enterStudentData() {
        String firstName = ioService.readStringWithPrompt(messageService.getMessage("input.first-name"));
        String lastName = ioService.readStringWithPrompt(messageService.getMessage("input.last-name"));
        return new Student(firstName, lastName);
    }
}
