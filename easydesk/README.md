## Ticket Management System - "EasyDesk"

### Technologies Used:
- Spring Boot
- PostgreSQL
- Liquibase
- Spring DATA JPA
- Spring Security
- WebMVC/WebFlux
- Swagger
- Docker
- UI - Thymeleaf + HTML

#### Data Stored by the System:
1. **Ticket Data:**
    - Ticket ID
    - Title
    - Description
    - User who created the ticket (client)
    - Priority
    - Status (route point) - for tracking the movement of the ticket
    - Creation Date
    - Expected Completion Date
    - Information about the executor
    - Additional attachments/information
    - Comments/messages

2. **User Data:**
    - Username
    - Password
    - Full Name
    - Group
    - Rating
    - Email

3. **Route Data:**
    - Route points and their connections

4. **Message Data:**
    - Ticket
    - Text
    - Creation Date
    - Recipient and sender

5. **Attachment Data:**
    - Name
    - Content
    - Creation Date
    - User creator

#### System Functions:
1. **Ticket Management:**
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