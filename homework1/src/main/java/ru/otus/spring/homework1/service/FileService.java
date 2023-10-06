package ru.otus.spring.homework1.service;

import java.io.Reader;

public interface FileService {
    Reader getReader(String filename);
}
