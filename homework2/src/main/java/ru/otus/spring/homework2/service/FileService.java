package ru.otus.spring.homework2.service;

import java.io.Reader;

public interface FileService {
    Reader getReader(String filename);
}
