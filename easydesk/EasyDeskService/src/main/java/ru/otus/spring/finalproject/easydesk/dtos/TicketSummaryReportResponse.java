package ru.otus.spring.finalproject.easydesk.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.spring.finalproject.easydesk.models.enums.Category;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Schema(name = "Ticket summary response")
public class TicketSummaryReportResponse {

    private LocalDateTime beginReportDate;
    private LocalDateTime endReportDate;

    private long totalTicketsCount;
    private long createdTicketsCount;
    private long closedTicketsCount;
    private long expiredTicketsCount;
    private long completedInTimeTicketsCount;

    private List<CategorySummary> categories;

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategorySummary {

        private Category category;

        private long totalTicketsCount;
        private long createdTicketsCount;
        private long closedTicketsCount;
        private long expiredTicketsCount;
        private long completedInTimeTicketsCount;

        List<TicketShortInfo> tickets;

        public void increaseTotalTicketsCount() {
            totalTicketsCount++;
        }

        public void increaseCreatedTicketsCount() {
            createdTicketsCount++;
        }
        public void increaseClosedTicketsCount() {
            closedTicketsCount++;
        }

        public void increaseExpiredTicketsCount() {
            expiredTicketsCount++;
        }
        public void increaseCompletedInTimeTicketsCount() {
            completedInTimeTicketsCount++;
        }
    }

    @Builder
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TicketShortInfo {
        private String ticketCode;
        private String description;
    }

    public void increaseTotalTicketsCount() {
        totalTicketsCount++;
    }

    public void increaseCreatedTicketsCount() {
        createdTicketsCount++;
    }

    public void increaseClosedTicketsCount() {
        closedTicketsCount++;
    }

    public void increaseExpiredTicketsCount() {
        expiredTicketsCount++;
    }

    public void increaseCompletedInTimeTicketsCount() {
        completedInTimeTicketsCount++;
    }

}
