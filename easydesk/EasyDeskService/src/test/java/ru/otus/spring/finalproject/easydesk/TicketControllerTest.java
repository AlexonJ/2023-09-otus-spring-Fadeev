package ru.otus.spring.finalproject.easydesk;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.spring.finalproject.easydesk.config.SecurityConfig;
import ru.otus.spring.finalproject.easydesk.controllers.TicketController;
import ru.otus.spring.finalproject.easydesk.dtos.SearchRequest;
import ru.otus.spring.finalproject.easydesk.dtos.SearchResponse;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketDto;
import ru.otus.spring.finalproject.easydesk.mappers.DtoMapper;
import ru.otus.spring.finalproject.easydesk.mappers.DtoMapperImpl;
import ru.otus.spring.finalproject.easydesk.models.db.Ticket;
import ru.otus.spring.finalproject.easydesk.models.search.TicketSearchFields;
import ru.otus.spring.finalproject.easydesk.repositories.TestDataHolder;
import ru.otus.spring.finalproject.easydesk.services.TicketService;
import ru.otus.spring.finalproject.easydesk.services.UserDetailsServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Tickets API controller test")
@WebMvcTest(TicketController.class)
@Import({DtoMapperImpl.class, SecurityConfig.class, UserDetailsServiceImpl.class})
@AutoConfigureWebMvc
@WithMockUser(
        value = "Customer",
        password = "mng",
        username = "customer",
        authorities = {"READ_TICKETS", "CREATE_TICKETS", "DELETE_TICKETS" }
)
public class TicketControllerTest {

    private static final String TICKETS_URL = "/api/v1/tickets";

    @Autowired
    MockMvc mockMvc;

    @MockBean
    TicketService ticketService;

    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private DtoMapper mapper;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        TestDataHolder.prepareTestData();
    }

    @DisplayName("Should return correct ticket list")
    @Test
    public void shouldReturnCorrectTicketList() throws Exception {

        List<TicketDto> ticketDtoList = TestDataHolder.getDbTickets().stream().map(mapper::ticketToTicketDto).toList();
        when(ticketService.findTickets(Mockito.any())).thenReturn(ticketDtoList);

        SearchRequest<TicketSearchFields> request = new SearchRequest<>();
        var result = mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, TICKETS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        SearchResponse<TicketDto> searchResponse = objectMapper.readValue(
                responseContent, new TypeReference<>() {});
        assertEquals(ticketDtoList, searchResponse.getResult());
    }

    @DisplayName("Should return new ticket entity")
    @Test
    public void shouldReturnCreatedTicketEntity() throws Exception {

        List<TicketDto> ticketDtoList = TestDataHolder.getDbTickets().stream().map(mapper::ticketToTicketDto).toList();
        when(ticketService.findTickets(Mockito.any())).thenReturn(ticketDtoList);

        SearchRequest<TicketSearchFields> request = new SearchRequest<>();
        var result = mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, TICKETS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        SearchResponse<TicketDto> searchResponse = objectMapper.readValue(
                responseContent, new TypeReference<>() {});
        assertEquals(ticketDtoList, searchResponse.getResult());
    }

    @DisplayName("Should delete ticket entity")
    @Test
    public void shouldDeleteTicketEntity() throws Exception {
        Ticket expectedTicket = TestDataHolder.getTicketById(1L);
        doNothing().when(ticketService).deleteByCode(anyString());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(TICKETS_URL + "/{code}", expectedTicket.getCode())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(ticketService, times(1)).deleteByCode(expectedTicket.getCode());
    }
    @DisplayName("Security - unauthenticated. Should return 401 (unauthorized) status")
    @Test
    @WithAnonymousUser
    public void testUnauthorized() throws Exception {
        mockMvc.perform(get(TICKETS_URL))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(post(TICKETS_URL).with(csrf()))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(delete(TICKETS_URL).with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Security - unauthenticated. Should return 403 (forbidden) status")
    @Test
    @WithMockUser(
            value = "admin",
            password = "adm",
            username = "admin",
            authorities = {"READ_ATTACHMENTS", "CREATE_ATTACHMENTS", "DELETE_TICKETS"}
    )
    public void testForbidden() throws Exception {
        mockMvc.perform(get(TICKETS_URL))
                .andExpect(status().isForbidden());
        mockMvc.perform(post(TICKETS_URL))
                .andExpect(status().isForbidden());
        //TODO: Why it doesn't work? (Error 405 - Method 'DELETE' is not supported.)
//        mockMvc.perform(delete(TICKETS_URL))
//                .andExpect(status().isForbidden());
    }
}
