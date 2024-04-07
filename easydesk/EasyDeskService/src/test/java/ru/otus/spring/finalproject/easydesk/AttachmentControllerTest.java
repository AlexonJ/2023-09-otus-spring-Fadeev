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
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;
import ru.otus.spring.finalproject.easydesk.config.SecurityConfig;
import ru.otus.spring.finalproject.easydesk.controllers.AttachmentController;
import ru.otus.spring.finalproject.easydesk.dtos.attachments.AttachmentDto;
import ru.otus.spring.finalproject.easydesk.dtos.attachments.GetAttachmentResponse;
import ru.otus.spring.finalproject.easydesk.dtos.tickets.TicketDto;
import ru.otus.spring.finalproject.easydesk.mappers.DtoMapper;
import ru.otus.spring.finalproject.easydesk.mappers.DtoMapperImpl;
import ru.otus.spring.finalproject.easydesk.models.db.Attachment;
import ru.otus.spring.finalproject.easydesk.repositories.TestDataHolder;
import ru.otus.spring.finalproject.easydesk.services.AttachmentService;
import ru.otus.spring.finalproject.easydesk.services.UserDetailsServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Attachment API controller test")
@WebMvcTest(AttachmentController.class)
@Import({DtoMapperImpl.class, SecurityConfig.class, UserDetailsServiceImpl.class})
@AutoConfigureWebMvc
@WithMockUser(
        value = "Customer",
        password = "mng",
        username = "customer",
        authorities = {"READ_ATTACHMENTS", "CREATE_ATTACHMENTS", "DELETE_ATTACHMENTS" }
)
public class AttachmentControllerTest {

    private static final String ATTACHMENT_URL = "/api/v1/attachments";

    private static final Long FIRST_TICKET_ID = 1L;
    private static final Long FIRST_ATTACHMENT_ID = 1L;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AttachmentService attachmentService;

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

    @DisplayName("Should return correct attachment list for ticket")
    @Test
    public void shouldReturnCorrectAttachmentList() throws Exception {

        TicketDto ticket = mapper.ticketToTicketDto(TestDataHolder.getTicketById(FIRST_TICKET_ID));

        List<AttachmentDto> attachmentDtoList = TestDataHolder.getAttachmentsByTicketCode(
                ticket.getCode()).stream().map(mapper::attachmentToAttachmentDto).toList();

        when(attachmentService.getAttachmentsByTicketCode(Mockito.any())).thenReturn(attachmentDtoList);

        var result = mockMvc.perform(MockMvcRequestBuilders
                        .request(HttpMethod.GET, ATTACHMENT_URL + "/ticket/{ticketCode}", ticket.getCode()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        GetAttachmentResponse searchResponse = objectMapper.readValue(responseContent, new TypeReference<>() {});
        assertEquals(attachmentDtoList, searchResponse.getAttachments());
    }

    @DisplayName("Should return new attachment entity")
    @Test
    public void shouldReturnCreatedAttachmentEntity() throws Exception {

        Attachment expectedAttachment = TestDataHolder.getAttachmentById(1L);
        AttachmentDto expectedAttachmentDto = mapper.attachmentToAttachmentDto(expectedAttachment);
        when(attachmentService.uploadByTicketCode(any(MultipartFile.class), anyString())).thenReturn(expectedAttachmentDto);
        MockMultipartFile file = new MockMultipartFile("file", expectedAttachment.getFilename(), expectedAttachment.getType(), expectedAttachment.getContent());
        var result = mockMvc.perform(MockMvcRequestBuilders
                        .multipart(HttpMethod.POST, ATTACHMENT_URL + "/ticket/{ticketCode}", expectedAttachment.getTicket().getCode())
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        AttachmentDto response = objectMapper.readValue(responseContent, new TypeReference<>() {});
        assertEquals(expectedAttachmentDto, response);
    }

    @DisplayName("Should delete comment entity")
    @Test
    public void shouldDeleteCommentEntity() throws Exception {

        Attachment expectedAttachment = TestDataHolder.getAttachmentById(1L);

        doNothing().when(attachmentService).deleteById(anyLong());

        mockMvc.perform(delete(ATTACHMENT_URL + "/{attachmentId}", expectedAttachment.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(attachmentService, times(1)).deleteById(expectedAttachment.getId());
    }

    @DisplayName("Security - unauthenticated. Should return 401 (unauthorized) status")
    @Test
    @WithAnonymousUser
    public void testUnauthorized() throws Exception {
        mockMvc.perform(get(ATTACHMENT_URL))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(post(ATTACHMENT_URL).with(csrf()))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(delete(ATTACHMENT_URL).with(csrf()))
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Security - unauthenticated. Should return 403 (forbidden) status")
    @Test
    @WithMockUser(
            value = "admin",
            password = "adm",
            username = "admin",
            authorities = {"READ_TICKETS", "CREATE_TICKETS", "DELETE_COMMENTS"}
    )
    public void testForbidden() throws Exception {
        mockMvc.perform(get(ATTACHMENT_URL))
                .andExpect(status().isForbidden());
        mockMvc.perform(post(ATTACHMENT_URL))
                .andExpect(status().isForbidden());
        mockMvc.perform(delete(ATTACHMENT_URL))
                .andExpect(status().isForbidden());
    }
}
