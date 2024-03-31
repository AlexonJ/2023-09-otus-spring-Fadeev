package ru.otus.spring.finalproject.easydesk.models.search;

public enum ComparisonConditions {

    EQUAL("="),
    GREATER(">"),
    LESS("<"),
    IN("in"),
    LIKE("like");

    private String sqlEquivalent;

    ComparisonConditions(String sqlEquivalent) {
        this.sqlEquivalent = sqlEquivalent;
    }

    public String getSqlEquivalent() {
        return sqlEquivalent;
    }

}
