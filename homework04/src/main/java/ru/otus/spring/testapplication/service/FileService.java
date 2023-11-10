package ru.otus.spring.testapplication.service;

import java.io.Reader;

public interface FileService {
    Reader getReader(String filename);
}
