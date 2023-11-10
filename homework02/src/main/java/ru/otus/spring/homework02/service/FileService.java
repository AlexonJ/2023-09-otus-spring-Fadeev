package ru.otus.spring.homework02.service;

import java.io.Reader;

public interface FileService {
    Reader getReader(String filename);
}
