package ru.otus.spring.finalproject.easydesk.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.otus.spring.finalproject.easydesk.models.db.User;
import ru.otus.spring.finalproject.easydesk.models.db.UserCategory;
import ru.otus.spring.finalproject.easydesk.models.enums.Category;

import java.util.stream.Stream;

public interface UserCategoryRepository extends JpaRepository<UserCategory, Long> {

    @Query("SELECT uc.user FROM UserCategory uc LEFT JOIN Ticket t ON t.assignedTo = uc.user WHERE uc.category = :category\n" +
            "GROUP BY uc.user ORDER BY COUNT(t) ASC"
    )
    Stream<User> findAllByCategoryOrderByTicketNumbers(Category category);

}
