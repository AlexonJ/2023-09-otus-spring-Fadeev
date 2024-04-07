## Ticket Management System - "EasyDesk"

### Technologies used:
- Spring Boot
- PostgreSQL
- Liquibase
- Spring DATA JPA
- Spring Security
- WebMVC/WebFlux
- Swagger
- Docker
- UI - Thymeleaf + HTML

#### Data stored by the system:
1. **Ticket data:**
    - Ticket ID
    - Ticket code
    - Title
    - Description
    - User who created the ticket (client)
    - Priority
    - Status (route point) - for tracking the movement of the ticket
    - Creation Date
    - Expected Completion Date
    - Information about the executor
    - Additional attachments
    - Comments (messages)

2. **User data:**
    - Username
    - Password
    - Full Name
    - Group
    - Rating
    - Email

3. **Route data:**
    - Route points and their connections

4. **Message data:**
    - Ticket
    - Text
    - Creation date
    - Recipient and sender

5. **Attachment data:**
    - Name
    - Content
    - Creation Date
    - User creator

#### System functions:
1. **Ticket management:**
    - Creation of tickets with addressing to the least loaded user in the group
    - Access control for users to tickets of individual groups, as well as attachments and messages
    - Editing ticket data
    - Addition/removal of additional attachments
    - Message handling - sending messages to users in the group/initiator
    - Routing of tickets according to the route map with addressing to users in the group
    - Sending email notifications about assigning a new ticket and approaching completion dates
    - Ability to rate the quality of work performed (rating)

2. **Reporting:**
    - Summary data on tickets broken down by groups (total number of incoming tickets for the period, number of tickets in progress, closed tickets for the period, tickets forwarded to another group, average ticket completion time)
    - Group ratings based on feedback
    - Results of the effectiveness of group and individual performer work