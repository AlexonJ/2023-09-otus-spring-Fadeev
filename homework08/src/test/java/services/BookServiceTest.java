package services;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring.bookstore.models.Author;
import ru.otus.spring.bookstore.models.Book;
import ru.otus.spring.bookstore.repositories.AuthorRepository;
import ru.otus.spring.bookstore.repositories.BookRepository;
import ru.otus.spring.bookstore.repositories.TestDataHolder;
import ru.otus.spring.bookstore.services.BookServiceImpl;

import java.util.List;
import java.util.Optional;

@DisplayName("Сервис для работы с книгами")
@SpringBootTest(classes = {BookServiceImpl.class, AuthorRepository.class})
@ActiveProfiles("test")
class BookServiceTest {

    private static final int FIRST_BOOK_INDEX = 0;

    private static final String TEST_BOOK_ID = "1";

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @Autowired
    @InjectMocks
    BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        TestDataHolder.prepareTestData();
    }

    @DisplayName("должен возвращать коллекцию книг")
    @Test
    void findAllBooksTest() {

        var expectedBooks = TestDataHolder.getBooks();
        Mockito.when(bookRepository.findAll()).thenAnswer(invocation -> expectedBooks);
        var actualBooks = bookService.findAll();

        Assertions.assertThat(actualBooks).isNotNull().containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @DisplayName("должен возвращать книгу по id")
    @Test
    void findBookByIdTest() {

        var expectedBook = TestDataHolder.getBooks().get(FIRST_BOOK_INDEX);
        Mockito.when(bookRepository.findById(TEST_BOOK_ID)).thenAnswer(invocation -> Optional.of(expectedBook));
        var actualBooks = bookService.findById(TEST_BOOK_ID);

        Assertions.assertThat(actualBooks)
                .isPresent().get()
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedBook);
    }

    @DisplayName("должен возвращать заполненную книгу c установленным id")
    @Test
    void insert() {

        List<Author> authors = List.of(new Author("1", "George Orwell"));
        authorRepository.saveAll(authors);

        var expectedBook = TestDataHolder.getBooks().get(FIRST_BOOK_INDEX);
        Mockito.when(authorRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(expectedBook.getAuthor()));
        Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenAnswer(invocation -> {
            Book sourceBook = invocation.getArgument(0);
            return new Book("1", sourceBook.getTitle(), sourceBook.getAuthor(), sourceBook.getGenres(), sourceBook.getComments());
        });
        var actualBookDto = bookService.insert(expectedBook.getTitle(), expectedBook.getAuthor().getId(), expectedBook.getGenres());

        Assertions.assertThat(actualBookDto)
                .isNotNull()
                .matches(book -> !book.getId().isEmpty())
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(expectedBook);
    }

}
