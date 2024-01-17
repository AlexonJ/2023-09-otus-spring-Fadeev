package ru.otus.spring.bookstore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.otus.spring.bookstore.dtos.BookDtoIds;
import ru.otus.spring.bookstore.exceptions.EntityNotFoundException;
import ru.otus.spring.bookstore.mappers.DtoMapperImpl;
import ru.otus.spring.bookstore.repositories.TestDataHolder;
import ru.otus.spring.bookstore.services.AuthorService;
import ru.otus.spring.bookstore.services.BookService;
import ru.otus.spring.bookstore.services.CommentService;
import ru.otus.spring.bookstore.services.GenreService;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Контроллер для работы с книгами")
@WebMvcTest(BookController.class)
@Import({DtoMapperImpl.class})
@WithMockUser(
        value = "admin",
        password = "pwd",
        username = "admin",
        authorities = {"admin"}
)
public class BookControllerTest {

    private static final int FIRST_BOOK_INDEX = 0;

    public static final String BOOKS_URL = "/books";

    public static final String BOOKS_EDIT_URL = "/books/edit";

    public static final String BOOKS_DELETE_URL = "/books/delete";


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
        mockMvc.perform(MockMvcRequestBuilders.get(BOOKS_URL))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("book-list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("books"))
                .andExpect(MockMvcResultMatchers.model().attribute("books", Matchers.containsInAnyOrder(expectedBookDtoList.toArray())));
    }

    @Test
    @DisplayName("Should return correct edit page with attributes")
    public void ShouldReturnCorrectBookEditPageWithAttributes() throws Exception {

        var expectedBook = TestDataHolder.getBooks().get(FIRST_BOOK_INDEX);
        var expectedBookDto = mapper.bookToBookDTO(expectedBook);
        var expectedGenreDtoList = TestDataHolder.getGenres().stream().map(mapper::genreToGenreDto).toList();
        var expectedAuthorDtoList = TestDataHolder.getAuthors().stream().map(mapper::authorToAuthorDto).toList();
        var expectedCommentDtoList = Objects.requireNonNull(expectedBookDto).getComments();

        given(bookService.findById(1)).willReturn(expectedBookDto);
        given(genreService.findAll()).willReturn(expectedGenreDtoList);
        given(authorService.findAll()).willReturn(expectedAuthorDtoList);
        given(commentService.findCommentsByBookId(anyLong())).willReturn(expectedCommentDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get(BOOKS_EDIT_URL).param("id", Long.toString(expectedBook.getId())))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("book-edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("book", "authors", "genres", "comments"))
                .andExpect(MockMvcResultMatchers.model().attribute("book", Matchers.is(mapper.bookDtoToBookDtoIds(expectedBookDto))))
                .andExpect(MockMvcResultMatchers.model().attribute("authors", Matchers.containsInAnyOrder(expectedAuthorDtoList.toArray())))
                .andExpect(MockMvcResultMatchers.model().attribute("genres", Matchers.containsInAnyOrder(expectedGenreDtoList.toArray())))
                .andExpect(MockMvcResultMatchers.model().attribute("comments", Matchers.containsInAnyOrder(expectedCommentDtoList.toArray())));
    }

    @Test
    @DisplayName("Should return error when send invalid id")
    public void ShouldReturnErrorInvalidId() throws Exception {

        given(bookService.findById(2)).willThrow(new EntityNotFoundException("Book with id %d not found".formatted(2)));
        mockMvc.perform(MockMvcRequestBuilders.get(BOOKS_EDIT_URL).param("id", "2"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    }

    @Test
    @DisplayName("Should return error when send invalid book object")
    public void ShouldReturnErrorInvalidBookContent() throws Exception {

        var expectedBookDtoIds = new BookDtoIds();
        mockMvc.perform(MockMvcRequestBuilders.post(BOOKS_EDIT_URL)
                        .param("book", objectMapper.writeValueAsString(expectedBookDtoIds))
                        .param("newCommentContent", "")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.model().attributeHasErrors("book"))
                .andExpect(MockMvcResultMatchers.model().attributeHasFieldErrors("book", "title", "authorId", "genreIds"));
    }

    @Test
    @DisplayName("Should call delete method in repository")
    public void ShouldCallDeleteMethod() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(BOOKS_DELETE_URL)
                        .param("id", "0"));
        verify(bookService, times(1)).deleteById(0L);
    }

    @DisplayName("Security - authenticated. Should return successful status")
    @Test
    public void testAuthenticatedOnAdmin() throws Exception {
        var expectedBookDto = mapper.bookToBookDTO(TestDataHolder.getBooks().get(FIRST_BOOK_INDEX));
        given(bookService.findById(1)).willReturn(expectedBookDto);
        mockMvc.perform(get(BOOKS_URL))
                .andExpect(status().isOk());
        mockMvc.perform(get(BOOKS_EDIT_URL).param("id", Long.toString(expectedBookDto.getId())))
                .andExpect(status().isOk());
    }

    @DisplayName("Security - unauthenticated. Should return 401 (unauthorized) status")
    @Test
    @WithAnonymousUser
    public void testUnauthorized() throws Exception {
        mockMvc.perform(get(BOOKS_URL))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(get(BOOKS_EDIT_URL).param("id", "0"))
                .andExpect(status().isUnauthorized());
    }

}