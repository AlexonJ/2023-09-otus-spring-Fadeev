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
import ru.otus.spring.bookstore.dtos.BookDto;
import ru.otus.spring.bookstore.mappers.DtoMapperImpl;
import ru.otus.spring.bookstore.repositories.TestDataHolder;
import ru.otus.spring.bookstore.services.AuthorService;
import ru.otus.spring.bookstore.services.BookService;
import ru.otus.spring.bookstore.services.CommentService;
import ru.otus.spring.bookstore.services.GenreService;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookRestController.class)
@AutoConfigureMockMvc
@Import({DtoMapperImpl.class})
public class BookRestControllerTest {

    private static final String BOOK_API_PATH = "/api/books";
    private static final int FIRST_BOOK_INDEX = 0;

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CommentService commentService;

    @Autowired
    private DtoMapperImpl mapper;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        TestDataHolder.prepareTestData();
    }

    @Test
    @DisplayName("Should return correct book list")
    public void ShouldReturnCorrectBookList() throws Exception {
        var expectedBookDtoList = TestDataHolder.getBooks().stream().map(mapper::bookToBookDTO).toList();
        given(bookService.findAll()).willReturn(expectedBookDtoList);
        mockMvc.perform(MockMvcRequestBuilders.get(BOOK_API_PATH))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(expectedBookDtoList.size()))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBookDtoList), true));
    }

    @Test
    @DisplayName("Should return saved book")
    public void ShouldReturnSavedBook() throws Exception {

        var expectedBook = TestDataHolder.getBooks().get(FIRST_BOOK_INDEX);
        var expectedBookDto = mapper.bookToBookDTO(expectedBook);
        var expectedBookDtoIds = mapper.bookDtoToBookDtoIds(expectedBookDto);

        when(bookService.update(anyLong(), anyString(), anyLong(), anyList(), anyList())).thenReturn(expectedBookDto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post(BOOK_API_PATH)
                        .content(objectMapper.writeValueAsString(expectedBookDtoIds))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBookDto), true));
    }

    @Test
    @DisplayName("Should return error when send invalid book object")
    public void ShouldReturnErrorInvalidBookContent() throws Exception {

        var expectedBookDto = new BookDto();
        mockMvc.perform(MockMvcRequestBuilders.post(BOOK_API_PATH)
                        .content(objectMapper.writeValueAsString(expectedBookDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isMap())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title", containsString("cannot be empty")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genreIds", containsString("cannot be empty")));
    }


    @Test
    @DisplayName("Should call delete method in repository")
    public void ShouldCallDeleteMethod() throws Exception {

        var expectedBook = TestDataHolder.getBooks().get(FIRST_BOOK_INDEX);
        var expectedBookDto = mapper.bookToBookDTO(expectedBook);
        when(bookService.findById(1L)).thenReturn(expectedBookDto);

        mockMvc.perform(delete(BOOK_API_PATH + "/{id}", expectedBook.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedBook.getId()));
        verify(bookService, times(1)).deleteById(expectedBook.getId());
    }
}