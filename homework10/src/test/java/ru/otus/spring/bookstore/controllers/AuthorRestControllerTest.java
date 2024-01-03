package ru.otus.spring.bookstore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.spring.bookstore.dtos.AuthorDto;
import ru.otus.spring.bookstore.mappers.DtoMapperImpl;
import ru.otus.spring.bookstore.repositories.TestDataHolder;
import ru.otus.spring.bookstore.services.AuthorService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AuthorRestController.class)
@AutoConfigureMockMvc
@Import({DtoMapperImpl.class})
public class AuthorRestControllerTest {

    private static final int FIRST_AUTHOR_INDEX = 0;

    private static final String AUTHOR_API_PATH = "/api/authors";

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
        mockMvc.perform(MockMvcRequestBuilders.get(AUTHOR_API_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(authorDtoList.size()))
                .andExpect(content().json(objectMapper.writeValueAsString(authorDtoList), true));
    }

    @Test
    @DisplayName("Should return saved author")
    public void ShouldReturnSavedAuthor() throws Exception {
        var expectedAuthor = TestDataHolder.getAuthors().get(FIRST_AUTHOR_INDEX);
        var expectedAuthorDto = mapper.authorToAuthorDto(expectedAuthor);
        when(authorService.update(anyLong(), anyString())).thenAnswer(invocation ->
                new AuthorDto(invocation.getArgument(0), invocation.getArgument(1)));
        mockMvc.perform(MockMvcRequestBuilders
                        .post(AUTHOR_API_PATH)
                        .content(objectMapper.writeValueAsString(expectedAuthorDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedAuthorDto), true));
    }

    @Test
    @DisplayName("Should return error when send invalid author object")
    public void ShouldReturnErrorInvalidId() throws Exception {

        var expectedAuthorDto = new AuthorDto();
        mockMvc.perform(MockMvcRequestBuilders.post(AUTHOR_API_PATH)
                        .content(objectMapper.writeValueAsString(expectedAuthorDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.fullName", containsString("cannot be empty")));

    }

    @Test
    @DisplayName("Should call delete method in repository")
    public void ShouldCallDeleteMethod() throws Exception {
        long authorId = 1L;

        var expectedAuthor = TestDataHolder.getAuthors().get(FIRST_AUTHOR_INDEX);
        var expectedAuthorDto = mapper.authorToAuthorDto(expectedAuthor);
        when(authorService.findById(1L)).thenReturn(expectedAuthorDto);

        mockMvc.perform(delete(AUTHOR_API_PATH + "/{id}", authorId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(authorId));
        verify(authorService, times(1)).deleteById(authorId);
    }
}