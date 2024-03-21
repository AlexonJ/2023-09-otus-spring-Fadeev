package ru.otus.spring.finalproject.easydesk.models.enums;

import lombok.Getter;

@Getter
public enum TicketCategory {
    BUG_REPORT("BUG"),
    FEATURE_REQUEST("FRQ"),
    TECHNICAL_ISSUE("TCH"),
    USER_ASSISTANCE("USR"),
    INFRASTRUCTURE("INF"),
    DOCUMENTATION("DOC"),
    SECURITY_CONCERN("SEC"),
    PERFORMANCE_ISSUE("PER"),
    TRAINING_REQUEST("TRN"),
    FEEDBACK_SUGGESTION("FDB");

    private final String codePrefix;

    TicketCategory(String codePrefix) {
        this.codePrefix = codePrefix;
    }
}
