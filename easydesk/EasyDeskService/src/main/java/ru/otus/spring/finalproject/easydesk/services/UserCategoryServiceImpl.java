package ru.otus.spring.finalproject.easydesk.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.finalproject.easydesk.exceptions.CommonValidationException;
import ru.otus.spring.finalproject.easydesk.exceptions.ErrorMessages;
import ru.otus.spring.finalproject.easydesk.models.db.User;
import ru.otus.spring.finalproject.easydesk.models.enums.Category;
import ru.otus.spring.finalproject.easydesk.repositories.UserCategoryRepository;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class UserCategoryServiceImpl implements UserCategoryService {

    private final UserCategoryRepository userCategoryRepository;

    @Transactional(readOnly = true)
    @Override
    public User getLeastBusyUserByCategory(Category category) {
        Stream<User> users = userCategoryRepository.findAllByCategoryOrderByTicketNumbers(category);
        return users.findFirst().orElseThrow(() ->
                new CommonValidationException(
                        ErrorMessages.USERS_NOT_FOUND_BY_CATEGORY.getCode(),
                        ErrorMessages.USERS_NOT_FOUND_BY_CATEGORY.getMessage().formatted(category)));
    }
}
