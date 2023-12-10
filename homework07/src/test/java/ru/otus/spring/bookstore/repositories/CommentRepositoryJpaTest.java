package ru.otus.spring.bookstore.repositories;

import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import ru.otus.spring.bookstore.mappers.DtoMapper;
import ru.otus.spring.bookstore.mappers.DtoMapperImpl;
import ru.otus.spring.bookstore.models.Book;
import ru.otus.spring.bookstore.models.Comment;

import java.util.List;
import java.util.Objects;

@DisplayName("Репозиторий на основе Jpa для работы с комментариями")
@DataJpaTest
@Import({DtoMapperImpl.class})
@ActiveProfiles("test")
class CommentRepositoryJpaTest {

    private static final int FIRST_COMMENT_INDEX = 0;
    private static final int FIRST_BOOK_INDEX = 0;
    private static final long FIRST_COMMENT_ID = 1L;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private DtoMapper mapper;

    @Autowired
    private EntityManager entityManager;

    @BeforeAll
    static void initialization() {
        TestDataHolder.prepareTestData();
    }

    @BeforeEach
    void setUp() {
        TestDataHolder.prepareTestData();
    }

    @DisplayName("должен получать комментарий по id")
    @Test
    void shouldReturnCorrectCommentById() {
        var expectedCommentDto = mapper.commentToCommentDto(
                TestDataHolder.getBooks().get(FIRST_BOOK_INDEX).getComments().get(FIRST_COMMENT_INDEX));
        var actualCommentDto = commentRepository.findById(expectedCommentDto.getId()).map(mapper::commentToCommentDto);
        Assertions.assertThat(actualCommentDto).isPresent()
                .get().isEqualTo(expectedCommentDto);
    }

    @DisplayName("должен получать все комментарии для книги")
    @ParameterizedTest
    @MethodSource("getBooks")
    void shouldReturnCorrectCommentList(Book expectedBook) {
        var actualComments = commentRepository.findAllByBookId(expectedBook.getId()).stream().map(mapper::commentToCommentDto).toList();
        var expectedComments = expectedBook.getComments().stream().map(mapper::commentToCommentDto).toList();

        Assertions.assertThat(actualComments).usingRecursiveComparison().isEqualTo(expectedComments);
        actualComments.forEach(System.out::println);
    }

    @DisplayName("должен сохранять новый комментарий для книги")
    @Test
    void shouldSaveNewComment() {
        var book = TestDataHolder.getBooks().get(FIRST_BOOK_INDEX);
        var expectedComment = new Comment(0, "Comment_%d_for_book_%d".formatted(1, book.getId()), book);
        var expectedCommentDto = mapper.commentToCommentDto(expectedComment);
        var returnedCommentDto = mapper.commentToCommentDto(commentRepository.save(expectedComment));
        Assertions.assertThat(returnedCommentDto).isNotNull()
                .matches(comment -> comment.getId() > 0)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .ignoringExpectedNullFields()
                .isEqualTo(expectedCommentDto);

        Assertions.assertThat(mapper.commentToCommentDto(entityManager.find(Comment.class, returnedCommentDto.getId())))
                .isEqualTo(returnedCommentDto);

    }

    @DisplayName("должен сохранять измененный комментарий")
    @Test
    void shouldSaveUpdatedComment() {
        var book = TestDataHolder.getBooks().get(FIRST_BOOK_INDEX);
        var expectedComment = new Comment(1L, "Content of comment that not expected", book);
        var expectedCommentDto = mapper.commentToCommentDto(expectedComment);

        Assertions.assertThat(mapper.commentToCommentDto(entityManager.find(Comment.class, expectedCommentDto.getId())))
                .isNotEqualTo(expectedCommentDto);

        var returnedComment = commentRepository.save(expectedComment);
        var returnedCommentDto = mapper.commentToCommentDto(returnedComment);

        Assertions.assertThat(returnedCommentDto).isNotNull()
                .matches(comment -> comment.getId() > 0)
                .usingRecursiveComparison()
                .ignoringExpectedNullFields().isEqualTo(expectedCommentDto);

        Assertions.assertThat(mapper.commentToCommentDto(entityManager.find(Comment.class, returnedCommentDto.getId())))
                .isEqualTo(returnedCommentDto);
    }

    @DisplayName("должен удалять комментарий по id")
    @Test
    void shouldDeleteComment() {
        Assertions.assertThat(entityManager.find(Comment.class, FIRST_COMMENT_ID)).isNotNull();
        commentRepository.deleteById(FIRST_COMMENT_ID);
        Assertions.assertThat(entityManager.find(Comment.class, FIRST_COMMENT_ID)).isNull();
    }

    @DisplayName("должен удалять все комментарии после удаления книги")
    @ParameterizedTest
    @MethodSource("getBooks")
    void shouldDeleteAllCommentsForBook(Book expectedBook) {

        var actualCommentsDto = commentRepository.findAllByBookId(expectedBook.getId()).stream().map(mapper::commentToCommentDto).toList();
        var expectedComments = expectedBook.getComments().stream().map(mapper::commentToCommentDto).toList();

        Assertions.assertThat(actualCommentsDto).usingRecursiveComparison().isEqualTo(expectedComments);
        actualCommentsDto.forEach(System.out::println);

        entityManager.remove(entityManager.find(Book.class, expectedBook.getId()));

        var actualCommentsAfterDelete = expectedComments.stream().map(commentDto -> entityManager.find(Comment.class, commentDto.getId()))
                .filter(Objects::nonNull).toList();

        Assertions.assertThat(actualCommentsAfterDelete).isEmpty();

    }

    public static List<Book> getBooks() {
        return TestDataHolder.getBooks();
    }
}