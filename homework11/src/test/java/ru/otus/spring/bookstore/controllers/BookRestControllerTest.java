package ru.otus.spring.bookstore.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.bookstore.dtos.BookDto;
import ru.otus.spring.bookstore.dtos.BookDtoIds;
import ru.otus.spring.bookstore.mappers.DtoMapperImpl;
import ru.otus.spring.bookstore.repositories.TestDataHolder;
import ru.otus.spring.bookstore.services.BookService;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookRestControllerTest {

    @LocalServerPort
    private int port;

    private static final String BOOK_API_PATH = "/api/books";
    private static final String TEST_SERVICE_PATH = "http://localhost:%d";
    private static final int FIRST_BOOK_INDEX = 0;

    @MockBean
    private BookService bookService;

    @Autowired
    private DtoMapperImpl mapper;

    private WebClient client;

    @BeforeEach
    void setUp() {
        TestDataHolder.prepareTestData();
        client = WebClient.create(TEST_SERVICE_PATH.formatted(port));
    }

    @Test
    @DisplayName("Should return correct book list")
    public void ShouldReturnCorrectBookList() {
        var expectedBookDtoList = TestDataHolder.getBooks().stream().map(mapper::bookToBookDTO).toList();
        Mockito.when(bookService.findAllWithDetails()).thenReturn(Flux.fromIterable(expectedBookDtoList));

        var result = client.get()
                .uri(BOOK_API_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.toEntityList(BookDto.class))
                .block();

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertIterableEquals(result.getBody(), expectedBookDtoList);
    }

    @Test
    @DisplayName("Should return saved book")
    public void ShouldReturnSavedBook() {

        var expectedBook = TestDataHolder.getBooks().get(FIRST_BOOK_INDEX);
        var expectedBookDto = mapper.bookToBookDTO(expectedBook);
        var expectedBookDtoIds = mapper.bookDtoToBookDtoIds(expectedBookDto);

        when(bookService.save(anyLong(), anyString(), anyLong(), anyList(), anyList())).thenReturn(Mono.just(expectedBookDto));

        var result = client.post()
                .uri(BOOK_API_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(expectedBookDtoIds)
                .exchangeToMono(clientResponse -> clientResponse.toEntity(BookDto.class))
                .block();

        assertTrue(result.getStatusCode().is2xxSuccessful());
        assertEquals(result.getBody(), expectedBookDto);

    }

    @Test
    @DisplayName("Should return error when send invalid book object")
    public void ShouldReturnErrorInvalidBookContent() {

        var expectedBookDtoIds = new BookDtoIds();

        var result = client.post()
                .uri(BOOK_API_PATH)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(expectedBookDtoIds)
                .exchangeToMono(clientResponse -> clientResponse.toEntity(Object.class))
                .block();

        assertTrue(result.getStatusCode().is4xxClientError());
        assertTrue(result.getBody() instanceof HashMap<?,?>);

        var body = (HashMap<String, String>) result.getBody();

        assertNotNull(body);
        assertTrue(body.containsKey("title"));
        assertTrue(body.containsKey("authorId"));
        assertTrue(body.containsKey("genreIds"));

    }


    @Test
    @DisplayName("Should call delete method in repository")
    public void ShouldCallDeleteMethod() {

        var expectedBook = TestDataHolder.getBooks().get(FIRST_BOOK_INDEX);
        var expectedBookDto = mapper.bookToBookDTO(expectedBook);
        when(bookService.findById(1L)).thenReturn(Mono.just(expectedBookDto));
        when(bookService.deleteById(1L)).thenReturn(Mono.empty());

        var result = client.delete()
                .uri(BOOK_API_PATH + "/%d".formatted(expectedBook.getId()))
                .accept(MediaType.APPLICATION_JSON)
                .exchangeToMono(clientResponse -> clientResponse.toEntity(BookDto.class))
                .block();

        assertTrue(result.getStatusCode().isSameCodeAs(HttpStatusCode.valueOf(204)));
        verify(bookService, times(1)).deleteById(expectedBook.getId());
    }
}