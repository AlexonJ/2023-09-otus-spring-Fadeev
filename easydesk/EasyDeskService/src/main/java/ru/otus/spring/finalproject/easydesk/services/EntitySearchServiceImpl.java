package ru.otus.spring.finalproject.easydesk.services;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.finalproject.easydesk.dtos.SearchRequest;
import ru.otus.spring.finalproject.easydesk.exceptions.CommonValidationException;
import ru.otus.spring.finalproject.easydesk.exceptions.ErrorMessages;
import ru.otus.spring.finalproject.easydesk.models.db.User;
import ru.otus.spring.finalproject.easydesk.models.enums.TicketPriority;
import ru.otus.spring.finalproject.easydesk.models.search.Searchable;
import ru.otus.spring.finalproject.easydesk.exceptions.FieldValueParseException;
import ru.otus.spring.finalproject.easydesk.repositories.UserRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class EntitySearchServiceImpl implements EntitySearchService {

    private static final String WHERE_CONDITIONAL_TEMPLATE = "%s%s %s ?%s";
    private static final String SELECT_FROM_TEMPLATE = "SELECT %s FROM %s %s";

    private final UserRepository userRepository;

    private final EntityManager entityManager;

    @Override
    public <T> Stream<T> findEntities(SearchRequest<? extends Searchable> searchRequest, Class<T> entityClass) {
        return findEntities(searchRequest, entityClass, null);
    }

    @Override
    public <T> Stream<T> findEntities(SearchRequest<? extends Searchable> searchRequest, Class<T> entityClass, String additionalEntitiesString) {

        var classSimpleName = entityClass.getSimpleName();
        var classSimpleNameLowCamel = upperCamelToLowerCamel(classSimpleName);

        StringBuilder sqlRequest = new StringBuilder(SELECT_FROM_TEMPLATE.formatted(
                classSimpleNameLowCamel, classSimpleName, classSimpleNameLowCamel));

        if (!Objects.isNull(additionalEntitiesString) && !additionalEntitiesString.isEmpty()) {
            sqlRequest.append(additionalEntitiesString);
        }
        StringBuilder whereConditions = new StringBuilder();
        List<Object> parameters = new ArrayList<>();

        int i = 0;
        for (var condition : searchRequest.getSearchConditionals()) {

            if (condition.getValues().size() == 1) {
                var value = condition.getValues().stream().findFirst().get();
                try {
                    parameters.add(convertStringToObject(value, condition.getField().getFieldClass()));
                } catch (NumberFormatException | DateTimeParseException ex) {
                    throw new FieldValueParseException(ex.getMessage());
                }
            }

            whereConditions.append(WHERE_CONDITIONAL_TEMPLATE.formatted(
                    whereConditions.isEmpty() ? "" : " AND ",
                    condition.getField().getPath(),
                    condition.getCondition().getSqlEquivalent(),
                    i + 1));
            i++;
        }

        if (!whereConditions.isEmpty()) {
            sqlRequest.append(" WHERE ").append(whereConditions);
        }

        var query = entityManager.createQuery(sqlRequest.toString(), entityClass);
        for (i = 0; i < parameters.size(); i++) {
            query.setParameter(i + 1, parameters.get(i));
        }
        var result = query.getResultList();
        return result.stream();
    }

    private Object convertStringToObject(String stringValue, Class<?> clazz) {

        if (clazz.equals(Integer.class)) {
            return Integer.parseInt(stringValue);
        } else if (clazz.equals(Double.class)) {
            return Double.parseDouble(stringValue);
        } else if (clazz.equals(Long.class)) {
            return Long.parseLong(stringValue);
        } else if (clazz.equals(LocalDateTime.class)) {
            return LocalDateTime.parse(stringValue);
        } else if (clazz.equals(LocalDate.class)) {
            return LocalDate.parse(stringValue);
        } else if (clazz.equals(String.class)) {
            return stringValue;
        } else if (clazz.equals(TicketPriority.class)) {
            return TicketPriority.valueOf(stringValue);
        } else if (clazz.equals(User.class)) {
            return userRepository.findFirstById(Long.parseLong(stringValue)).orElseThrow(
                    () -> new CommonValidationException(
                            ErrorMessages.USER_NOT_FOUND_BY_ID.getCode(),
                            ErrorMessages.USER_NOT_FOUND_BY_ID.getMessage().formatted(stringValue)));
        }

        return null;
    }

    public String upperCamelToLowerCamel(String sourceString) {
        if (sourceString == null || sourceString.isEmpty()) {
            return sourceString;
        }
        char firstChar = Character.toLowerCase(sourceString.charAt(0));
        return firstChar + sourceString.substring(1);
    }
}
