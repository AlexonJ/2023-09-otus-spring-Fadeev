package ru.otus.spring.finalproject.easydesk.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.otus.spring.finalproject.easydesk.dtos.attachments.AttachmentDto;
import ru.otus.spring.finalproject.easydesk.dtos.comments.CommentDto;
import ru.otus.spring.finalproject.easydesk.dtos.processes.ProcessDto;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketDto;
import ru.otus.spring.finalproject.easydesk.dtos.users.UserDto;
import ru.otus.spring.finalproject.easydesk.dtos.waypoints.WaypointDto;
import ru.otus.spring.finalproject.easydesk.dtos.waypoints.WaypointDtoWithoutNextWaypoints;
import ru.otus.spring.finalproject.easydesk.models.db.Attachment;
import ru.otus.spring.finalproject.easydesk.models.db.Comment;
import ru.otus.spring.finalproject.easydesk.models.db.Ticket;
import ru.otus.spring.finalproject.easydesk.models.db.User;
import ru.otus.spring.finalproject.easydesk.models.db.Waypoint;

@Mapper(componentModel = "spring")
public interface DtoMapper {

    TicketDto ticketToTicketDto(Ticket ticket);

    UserDto userToUserDto(User user);

    ProcessDto processToProcessDto(Process process);

    WaypointDto waypointToWaypointDto(Waypoint waypoint);

    WaypointDtoWithoutNextWaypoints waypointToWaypointDtoWithoutNextWaypoints(Waypoint waypoint);

    AttachmentDto attachmentToAttachmentDto(Attachment attachment);

    @Mapping(target = "content", ignore = true)
    AttachmentDto attachmentToAttachmentDtoWithoutContent(Attachment attachment);

    CommentDto commentToCommentDto(Comment comment);
}
