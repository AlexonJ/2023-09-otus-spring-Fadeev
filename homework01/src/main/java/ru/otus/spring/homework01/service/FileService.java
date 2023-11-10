package ru.otus.spring.homework01.service;

import java.io.Reader;

public interface FileService {
    Reader getReader(String filename);
}
