package ru.otus.spring.bookstore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.spring.bookstore.exceptions.EntityNotFoundException;
import ru.otus.spring.bookstore.mappers.DtoMapperImpl;
import ru.otus.spring.bookstore.repositories.TestDataHolder;
import ru.otus.spring.bookstore.services.AuthorService;

import static org.mockito.BDDMockito.given;

@WebMvcTest(AuthorPagesController.class)
@AutoConfigureMockMvc
@Import({DtoMapperImpl.class})
public class AuthorControllerTest {

    private static final int FIRST_AUTHOR_INDEX = 0;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @Autowired
    private DtoMapperImpl mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        TestDataHolder.prepareTestData();
    }

    @Test
    @DisplayName("Should return correct author list")
    public void ShouldReturnCorrectAuthorList() throws Exception {
        var authorDtoList = TestDataHolder.getAuthors().stream().map(author -> mapper.authorToAuthorDto(author)).toList();
        given(authorService.findAll()).willReturn(authorDtoList);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/list"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("author-list-ajax"));
    }

    @Test
    @DisplayName("Should return correct edit page with attributes")
    public void ShouldReturnCorrectBookEditPageWithAttributes() throws Exception {
        var expectedAuthor = TestDataHolder.getAuthors().get(FIRST_AUTHOR_INDEX);
        var expectedAuthorDto = mapper.authorToAuthorDto(expectedAuthor);
        given(authorService.findById(1)).willReturn(expectedAuthorDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/edit").param("id", Long.toString(expectedAuthorDto.getId())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("author-edit-ajax"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("author"))
                .andExpect(MockMvcResultMatchers.model().attribute("author", Matchers.is(expectedAuthorDto)));
    }

    @Test
    @DisplayName("Should return error when send invalid id")
    public void ShouldReturnErrorInvalidId() throws Exception {

        given(authorService.findById(2)).willThrow(new EntityNotFoundException("Author with id %d not found".formatted(2)));
        mockMvc.perform(MockMvcRequestBuilders.get("/authors/edit").param("id", "2"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

}