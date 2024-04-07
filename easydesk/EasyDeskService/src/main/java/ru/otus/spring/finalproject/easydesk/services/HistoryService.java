package ru.otus.spring.finalproject.easydesk.services;

import ru.otus.spring.finalproject.easydesk.dtos.history.Externable;

public interface HistoryService {
    <T extends Externable> void sendHistoryData(T original, T updated);
}
