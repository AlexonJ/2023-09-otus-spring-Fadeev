package ru.otus.spring.finalproject.easydesk.services;

import ru.otus.spring.finalproject.easydesk.models.db.User;

public interface SecurityService {
    User getCurrentUser();
}
