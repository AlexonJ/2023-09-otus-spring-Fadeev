package ru.otus.spring.finalproject.easydesk.models.search;


import ru.otus.spring.finalproject.easydesk.models.db.User;
import ru.otus.spring.finalproject.easydesk.models.enums.TicketPriority;

import java.time.LocalDate;
import java.time.LocalDateTime;

public enum TicketSearchFields implements Searchable{

    TICKET_CODE("ticket.code", String.class),
    PRIORITY("ticket.priority", TicketPriority.class),
    DUE_DATE("ticket.dueDate", LocalDateTime.class),
    ASSIGNED_TO_ID("ticket.assignedTo", User.class),
    CREATED_BY_ID("ticket.createdBy", User.class);

    private final String relativePath;

    private final Class fieldClass;

    TicketSearchFields(String path, Class fieldClass) {
        this.relativePath = path;
        this.fieldClass = fieldClass;
    }

    @Override
    public String getPath() {
        return this.relativePath;
    }

    @Override
    public Class getFieldClass() {
        return this.fieldClass;
    }
}
