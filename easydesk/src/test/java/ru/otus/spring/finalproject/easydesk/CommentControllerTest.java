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
import ru.otus.spring.finalproject.easydesk.controllers.CommentController;
import ru.otus.spring.finalproject.easydesk.dtos.SearchRequest;
import ru.otus.spring.finalproject.easydesk.dtos.comments.CommentDto;
import ru.otus.spring.finalproject.easydesk.dtos.comments.GetCommentResponse;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketDto;
import ru.otus.spring.finalproject.easydesk.mappers.DtoMapper;
import ru.otus.spring.finalproject.easydesk.mappers.DtoMapperImpl;
import ru.otus.spring.finalproject.easydesk.models.db.Comment;
import ru.otus.spring.finalproject.easydesk.models.db.Ticket;
import ru.otus.spring.finalproject.easydesk.models.search.TicketSearchFields;
import ru.otus.spring.finalproject.easydesk.repositories.TestDataHolder;
import ru.otus.spring.finalproject.easydesk.services.CommentService;
import ru.otus.spring.finalproject.easydesk.services.UserDetailsServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Comments API controller test")
@WebMvcTest(CommentController.class)
@Import({DtoMapperImpl.class, SecurityConfig.class, UserDetailsServiceImpl.class})
@AutoConfigureWebMvc
@WithMockUser(
        value = "Customer",
        password = "mng",
        username = "customer",
        authorities = {"READ_COMMENTS", "CREATE_COMMENTS", "DELETE_COMMENTS" }
)
public class CommentControllerTest {

    private static final String COMMENTS_URL = "/api/v1/comments";

    private static final Long FIRST_TICKET_ID = 1L;

    private static final Long FIRST_COMMENT_ID = 1L;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CommentService commentService;

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

    @DisplayName("Should return correct comment list for ticket")
    @Test
    public void shouldReturnCorrectCommentList() throws Exception {

        TicketDto ticket = mapper.ticketToTicketDto(TestDataHolder.getTicketById(FIRST_TICKET_ID));

        List<CommentDto> commentDtoList = TestDataHolder.getCommentsByTicketCode(
                ticket.getCode()).stream().map(mapper::commentToCommentDto).toList();

        when(commentService.getByTicketCode(Mockito.any())).thenReturn(commentDtoList);

        SearchRequest<TicketSearchFields> request = new SearchRequest<>();
        var result = mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, COMMENTS_URL + "/ticket/{code}", ticket.getCode())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        GetCommentResponse searchResponse = objectMapper.readValue(responseContent, new TypeReference<>() {});
        assertEquals(commentDtoList, searchResponse.getComments());
    }

    @DisplayName("Should return new comment entity")
    @Test
    public void shouldReturnCreatedCommentEntity() throws Exception {

        Comment expectedComment = TestDataHolder.getCommentById(FIRST_COMMENT_ID);
        CommentDto expectedCommentDto = mapper.commentToCommentDto(expectedComment);
        when(commentService.createByTicketCode(anyString(), anyString())).thenReturn(expectedCommentDto);

        var result = mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.POST, COMMENTS_URL + "/ticket/{ticketCode}", expectedComment.getTicket())
                        .param("commentText", expectedComment.getContent())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        CommentDto response = objectMapper.readValue(responseContent, new TypeReference<>() {});
        assertEquals(expectedCommentDto, response);
    }

    @DisplayName("Should delete comment entity")
    @Test
    public void shouldDeleteCommentEntity() throws Exception {

        Comment expectedComment = TestDataHolder.getCommentById(FIRST_COMMENT_ID);

        doNothing().when(commentService).deleteById(anyLong());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(COMMENTS_URL + "/{code}", expectedComment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(commentService, times(1)).deleteById(expectedComment.getId());
    }

    @DisplayName("Security - unauthenticated. Should return 401 (unauthorized) status")
    @Test
    @WithAnonymousUser
    public void testUnauthorized() throws Exception {
        mockMvc.perform(get(COMMENTS_URL))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(post(COMMENTS_URL).with(csrf()))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(delete(COMMENTS_URL).with(csrf()))
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
        Ticket expectedTicket = TestDataHolder.getTicketById(FIRST_TICKET_ID);
        Comment expectedComment = TestDataHolder.getCommentById(FIRST_COMMENT_ID);
        mockMvc.perform(get(COMMENTS_URL))
                .andExpect(status().isForbidden());
        mockMvc.perform(post(COMMENTS_URL + "/ticket/{ticketCode}", expectedTicket.getCode()))
                .andExpect(status().isForbidden());
        mockMvc.perform(delete(COMMENTS_URL+ "/{commentId}", expectedComment.getId()))
                .andExpect(status().isForbidden());
    }
}
