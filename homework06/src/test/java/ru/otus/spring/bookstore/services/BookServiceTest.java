package ru.otus.spring.bookstore.services;

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
import ru.otus.spring.bookstore.mappers.DtoMapper;
import ru.otus.spring.bookstore.mappers.DtoMapperImpl;
import ru.otus.spring.bookstore.models.Book;
import ru.otus.spring.bookstore.models.Genre;
import ru.otus.spring.bookstore.repositories.AuthorRepository;
import ru.otus.spring.bookstore.repositories.AuthorRepositoryJpa;
import ru.otus.spring.bookstore.repositories.BookRepository;
import ru.otus.spring.bookstore.repositories.GenreRepositoryJpa;
import ru.otus.spring.bookstore.repositories.TestDataHolder;

import java.util.Optional;
import java.util.stream.Collectors;

@DisplayName("Сервис для работы с книгами")
@SpringBootTest(classes = {BookServiceImpl.class, AuthorRepositoryJpa.class, GenreRepositoryJpa.class, DtoMapperImpl.class})
@ActiveProfiles("test")
class BookServiceTest {

        private static final int FIRST_BOOK_INDEX = 0;

        @MockBean
        private BookRepository bookRepository;

        @MockBean
        private AuthorRepository authorRepository;

        @MockBean
        private GenreRepositoryJpa genreRepository;

        @Autowired
        private DtoMapper mapper;

        @Autowired
        @InjectMocks
        BookServiceImpl bookService;

        @BeforeEach
        void setUp() {
            TestDataHolder.prepareTestData();
        }

        @DisplayName("должен возвращать заполненную книгу c установленным id")
        @Test
        void insert() {

            var expectedBook = TestDataHolder.getBooks().get(FIRST_BOOK_INDEX);
            Mockito.when(authorRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(expectedBook.getAuthor()));
            Mockito.when(genreRepository.findAllByIds(Mockito.anyList())).thenReturn(expectedBook.getGenres());
            Mockito.when(bookRepository.save(Mockito.any(Book.class))).thenAnswer(invocation -> {
                Book sourceBook = invocation.getArgument(0);
                return new Book(1, sourceBook.getTitle(), sourceBook.getAuthor(), sourceBook.getGenres(), sourceBook.getComments());
            });
            var actualBookDto = bookService.insert(expectedBook.getTitle(),
                    expectedBook.getAuthor().getId(),
                    expectedBook.getGenres().stream().map(Genre::getId).collect(Collectors.toList()));

            Assertions.assertThat(actualBookDto).isNotNull()
                    .matches(bookDto -> bookDto.getId() > 0)
                    .usingRecursiveComparison()
                    .ignoringFields("id")
                    .isEqualTo(mapper.bookToBookDTO(expectedBook));
        }

}
