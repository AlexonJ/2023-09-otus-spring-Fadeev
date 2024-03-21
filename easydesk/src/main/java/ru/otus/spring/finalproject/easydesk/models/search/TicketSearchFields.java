package ru.otus.spring.finalproject.easydesk.models.search;


public enum TicketSearchFields implements Searchable{

    TICKET_CODE("ticket.code", String.class),
    USERNAME_CREATOR("", String.class);

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
