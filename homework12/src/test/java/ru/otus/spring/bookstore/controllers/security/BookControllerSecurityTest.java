package ru.otus.spring.bookstore.controllers.security;

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
import ru.otus.spring.bookstore.controllers.BookController;
import ru.otus.spring.bookstore.mappers.DtoMapperImpl;
import ru.otus.spring.bookstore.repositories.TestDataHolder;
import ru.otus.spring.bookstore.services.AuthorService;
import ru.otus.spring.bookstore.services.BookService;
import ru.otus.spring.bookstore.services.CommentService;
import ru.otus.spring.bookstore.services.GenreService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Book controller security test")
@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
@Import({DtoMapperImpl.class})

public class BookControllerSecurityTest {

    private static final int FIRST_BOOK_INDEX = 0;
    public static final String BOOKS_URL = "/books";
    public static final String BOOKS_EDIT_URL = "/books/edit";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @MockBean
    private CommentService commentService;

    @Autowired
    private DtoMapperImpl mapper;

    @MockBean
    private BookService bookService;

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
        var expectedBookDto = mapper.bookToBookDTO(TestDataHolder.getBooks().get(FIRST_BOOK_INDEX));
        given(bookService.findById(1)).willReturn(expectedBookDto);
        mockMvc.perform(get(BOOKS_URL))
                .andExpect(status().isOk());
        mockMvc.perform(get(BOOKS_EDIT_URL).param("id", Long.toString(expectedBookDto.getId())))
                .andExpect(status().isOk());
    }

    @DisplayName("Should return 401 (unauthorized) status")
    @Test
    public void testUnauthorized() throws Exception {
        mockMvc.perform(get(BOOKS_URL))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(get(BOOKS_EDIT_URL).param("id", "0"))
                .andExpect(status().isUnauthorized());
    }

}
