package ru.otus.spring.bookstore.controllers.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.spring.bookstore.controllers.AuthorController;
import ru.otus.spring.bookstore.mappers.DtoMapperImpl;
import ru.otus.spring.bookstore.repositories.TestDataHolder;
import ru.otus.spring.bookstore.services.AuthorService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Author controller security test")
@WebMvcTest(AuthorController.class)
@AutoConfigureMockMvc
@Import({DtoMapperImpl.class})

public class AuthorControllerSecurityTest {

    private static final int FIRST_AUTHOR_INDEX = 0;
    public static final String AUTHORS_URL = "/authors";
    public static final String AUTHOR_EDIT_URL = "/authors/edit";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DtoMapperImpl mapper;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        TestDataHolder.prepareTestData();
    }

    @DisplayName("Should return successful status")
    @WithMockUser(
            value = "admin",
            password = "pwd",
            username = "admin",
            authorities = {"admin"}
    )
    @Test
    public void testAuthenticatedOnAdmin() throws Exception {
        var expectedAuthor = TestDataHolder.getAuthors().get(FIRST_AUTHOR_INDEX);
        var expectedAuthorDto = mapper.authorToAuthorDto(expectedAuthor);
        given(authorService.findById(1)).willReturn(expectedAuthorDto);
        mockMvc.perform(get(AUTHORS_URL))
                .andExpect(status().isOk());
        mockMvc.perform(get(AUTHOR_EDIT_URL).param("id", Long.toString(expectedAuthorDto.getId())))
                .andExpect(status().isOk());
    }

    @DisplayName("Should return 401 (unauthorized) status")
    @Test
    public void testUnauthorized() throws Exception {
        mockMvc.perform(get(AUTHORS_URL))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(get(AUTHOR_EDIT_URL).param("id", "0"))
                .andExpect(status().isUnauthorized());
    }
}
