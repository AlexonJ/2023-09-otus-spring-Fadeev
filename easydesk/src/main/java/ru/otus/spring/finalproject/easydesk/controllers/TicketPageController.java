package ru.otus.spring.finalproject.easydesk.controllers;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;
import ru.otus.spring.finalproject.easydesk.dtos.SearchRequest;
import ru.otus.spring.finalproject.easydesk.models.enums.TicketPriority;
import ru.otus.spring.finalproject.easydesk.models.search.ComparisonConditions;
import ru.otus.spring.finalproject.easydesk.models.search.SearchCondition;
import ru.otus.spring.finalproject.easydesk.models.search.TicketSearchFields;
import ru.otus.spring.finalproject.easydesk.services.SecurityService;
import ru.otus.spring.finalproject.easydesk.services.TicketService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Controller
public class TicketPageController {

    private final TicketService ticketService;

    private final SecurityService securityService;

    @GetMapping(path = {"/tickets/list", "/tickets"})
    public String ticketList(@RequestParam(required = false) String priority,
                             @RequestParam(required = false) LocalDate dueDateBegin,
                             @RequestParam(required = false) LocalDate dueDateEnd,
                             Model model) {

        SearchRequest<TicketSearchFields> ticketSearchRequest = new SearchRequest<>();
        List<SearchCondition<TicketSearchFields>> searchConditionals = new ArrayList<>();
        ticketSearchRequest.setSearchConditionals(searchConditionals);

        if (!StringUtils.isEmptyOrWhitespace(priority)) {
            SearchCondition<TicketSearchFields> prioritySearchCondition = new SearchCondition<>();
            prioritySearchCondition.setField(TicketSearchFields.PRIORITY);
            prioritySearchCondition.setCondition(ComparisonConditions.EQUAL);
            prioritySearchCondition.setValues(List.of(priority));
            searchConditionals.add(prioritySearchCondition);
        }

        if (!Objects.isNull(dueDateBegin)) {
            SearchCondition<TicketSearchFields> prioritySearchCondition = new SearchCondition<>();
            prioritySearchCondition.setField(TicketSearchFields.DUE_DATE);
            prioritySearchCondition.setCondition(ComparisonConditions.GREATER);
            prioritySearchCondition.setValues(List.of(dueDateBegin.atStartOfDay().toString()));
            searchConditionals.add(prioritySearchCondition);
        }

        if (!Objects.isNull(dueDateEnd)) {
            SearchCondition<TicketSearchFields> prioritySearchCondition = new SearchCondition<>();
            prioritySearchCondition.setField(TicketSearchFields.DUE_DATE);
            prioritySearchCondition.setCondition(ComparisonConditions.LESS);
            prioritySearchCondition.setValues(List.of(LocalDateTime.of(dueDateEnd, LocalTime.MAX).toString()));
            searchConditionals.add(prioritySearchCondition);
        }

        SearchCondition<TicketSearchFields> prioritySearchCondition = new SearchCondition<>();
        prioritySearchCondition.setField(TicketSearchFields.ASSIGNED_TO_ID);
        prioritySearchCondition.setCondition(ComparisonConditions.EQUAL);
        prioritySearchCondition.setValues(List.of(Long.toString(securityService.getCurrentUser().getId())));
        searchConditionals.add(prioritySearchCondition);

        var tickets = ticketService.findTickets(ticketSearchRequest);

        model.addAttribute("tickets", tickets);
        model.addAttribute("priority", priority);
        model.addAttribute("dueDateBegin", dueDateBegin);
        model.addAttribute("dueDateEnd", dueDateEnd);
        model.addAttribute("priorities", TicketPriority.values());

        return "tickets-list";
    }
}
