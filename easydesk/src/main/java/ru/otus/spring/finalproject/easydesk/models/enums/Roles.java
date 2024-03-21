package ru.otus.spring.finalproject.easydesk.models.enums;

public enum Roles {

    ADMINISTRATOR("Has full access to the system, including user management, configuration settings, and all ticket management functionalities."),
    SUPPORT_AGENT("Handles incoming tickets, communicates with users, and resolves issues or escalates them as needed."),
    TEAM_LEAD("Oversees a team of support agents, assigns tickets, monitors performance, and provides guidance and support."),
    DEVELOPER("Works on resolving technical issues reported in tickets, implementing new features, or fixing bugs."),
    QA_ANALYST("Tests software changes before deployment, verifies bug fixes, and ensures software quality."),
    PROJECT_MANAGER("Manages projects and initiatives within the organization, oversees ticket prioritization, and ensures project deadlines are met."),
    PRODUCT_OWNER("Represents the interests of stakeholders, sets product priorities, and defines the roadmap for product development."),
    CUSTOMER("Submits tickets to report issues, request assistance, or provide feedback on products or services."),
    REVIEWER("Reviews and approves changes proposed by developers or other team members before they are implemented."),
    SYSTEM_INTEGRATOR("Responsible for integrating the ticket management system with other systems or tools used in the organization.");

    private final String description;

    Roles(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

