package services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring.bookstore.configuration.SecurityConfiguration;
import ru.otus.spring.bookstore.mappers.DtoMapper;
import ru.otus.spring.bookstore.mappers.DtoMapperImpl;
import ru.otus.spring.bookstore.models.Book;
import ru.otus.spring.bookstore.models.Comment;
import ru.otus.spring.bookstore.models.Genre;
import ru.otus.spring.bookstore.repositories.AuthorRepository;
import ru.otus.spring.bookstore.repositories.BookRepository;
import ru.otus.spring.bookstore.repositories.CommentRepository;
import ru.otus.spring.bookstore.repositories.GenreRepository;
import ru.otus.spring.bookstore.repositories.TestDataHolder;
import ru.otus.spring.bookstore.repositories.UserRepository;
import ru.otus.spring.bookstore.services.BookServiceImpl;
import ru.otus.spring.bookstore.services.UserDetailsServiceImpl;

import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Сервис для работы с книгами")
@SpringBootTest(classes = {
        BookServiceImpl.class,
        AuthorRepository.class,
        GenreRepository.class,
        UserRepository.class,
        CommentRepository.class,
        DtoMapperImpl.class,
        UserDetailsServiceImpl.class,
        SecurityConfiguration.class})
@EnableAutoConfiguration
@ActiveProfiles("test")
class BookServiceTest {

        private static final int FIRST_BOOK_INDEX = 0;

        @MockBean
        private BookRepository bookRepository;

        @MockBean
        private AuthorRepository authorRepository;

        @MockBean
        private GenreRepository genreRepository;

        @MockBean
        private UserDetailsServiceImpl userDetailsService;

        @MockBean
        private CommentRepository commentRepository;

        @Autowired
        private DtoMapper mapper;

        @Autowired
        @InjectMocks
        BookServiceImpl bookService;

        Book expectedBook;

        Executable insertOperation;

        @BeforeEach
        void setUp() {
            TestDataHolder.prepareTestData();
            expectedBook = TestDataHolder.getBooks().get(FIRST_BOOK_INDEX);
            Mockito.when(authorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(expectedBook.getAuthor()));
            Mockito.when(genreRepository.findAllByIdIn(Mockito.anyList())).thenReturn(expectedBook.getGenres());
            Mockito.when(commentRepository.findAllByIdIn(Mockito.anyList())).thenReturn(expectedBook.getComments());
            Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenAnswer(invocation -> {
                Book sourceBook = invocation.getArgument(0);
                return new Book(1, sourceBook.getTitle(), sourceBook.getAuthor(), sourceBook.getGenres(), sourceBook.getComments());
            });

            insertOperation = () -> bookService.insert(expectedBook.getTitle(),
                    expectedBook.getAuthor().getId(),
                    expectedBook.getGenres().stream().map(Genre::getId).collect(Collectors.toList()),
                    expectedBook.getComments().stream().map(Comment::getId).collect(Collectors.toList()));

        }

        @DisplayName("Should successful return saved book with new id with proper authorities")
        @Test
        @WithMockUser(
                value = "admin",
                password = "pwd",
                username = "admin",
                authorities = {"WRITE", "BOOKS_ACCESS"}
        )
        void insertSuccessful() {

            var actualBookDto = bookService.insert(expectedBook.getTitle(),
                    expectedBook.getAuthor().getId(),
                    expectedBook.getGenres().stream().map(Genre::getId).collect(Collectors.toList()),
                    expectedBook.getComments().stream().map(Comment::getId).collect(Collectors.toList()));

            Assertions.assertThat(actualBookDto).isNotNull()
                    .matches(bookDto -> bookDto.getId() > 0)
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(mapper.bookToBookDTO(expectedBook));
        }

    @DisplayName("An exception should be thrown when attempting to save book without proper authorities")
    @Test
    @WithMockUser(
            value = "manager",
            password = "usr",
            username = "manager",
            authorities = {"WRITE", "AUTHOR_ACCESS"}
    )
    void insertAccessDeniedNoBookAccessAuthority() {
        assertThrows(AccessDeniedException.class, insertOperation);
    }

    @DisplayName("An exception should be thrown when attempting to save book without proper authorities")
    @Test
    @WithMockUser(
            value = "manager",
            password = "usr",
            username = "manager",
            authorities = {"READ", "BOOKS_ACCESS"}
    )
    void insertAccessDeniedNoWriteAuthority() {
        assertThrows(AccessDeniedException.class, insertOperation);
    }
    @DisplayName("An exception should be thrown when attempting to save book - anonymous user")
    @Test
    @WithAnonymousUser
    void insertAccessDeniedAnonymousUser() {
        assertThrows(AccessDeniedException.class, insertOperation);
    }

    @DisplayName("An exception should be thrown when attempting to save book - unauthorized")
    @Test
    void insertAccessDeniedUnauthorized() {
        assertThrows(AuthenticationCredentialsNotFoundException.class, insertOperation);
    }


}
