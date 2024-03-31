package ru.otus.spring.finalproject.easydesk.services;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.finalproject.easydesk.models.db.User;
import ru.otus.spring.finalproject.easydesk.models.enums.Category;

public interface UserCategoryService {
    @Transactional(readOnly = true)
    User getLeastBusyUserByCategory(Category category);
}
