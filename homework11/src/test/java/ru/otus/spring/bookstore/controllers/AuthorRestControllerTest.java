package ru.otus.spring.bookstore.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.bookstore.dtos.AuthorDto;
import ru.otus.spring.bookstore.mappers.DtoMapperImpl;
import ru.otus.spring.bookstore.repositories.TestDataHolder;
import ru.otus.spring.bookstore.services.AuthorService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@WebFluxTest(controllers = AuthorRestController.class)
@Import({DtoMapperImpl.class})
public class AuthorRestControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    private static final int FIRST_AUTHOR_INDEX = 0;

    private static final String AUTHOR_API_PATH = "/api/authors";

    @MockBean
    private AuthorService authorService;

    @Autowired
    private DtoMapperImpl mapper;

    @BeforeEach
    void setUp() {
        TestDataHolder.prepareTestData();
    }

    @Test
    @DisplayName("Should return correct author list")
    public void ShouldReturnCorrectAuthorList() {
        var authorDtoList = TestDataHolder.getAuthors().stream().map(author -> mapper.authorToAuthorDto(author)).toList();
        given(authorService.findAll()).willReturn(Flux.fromIterable(authorDtoList));

        webTestClient.get().uri(AUTHOR_API_PATH).exchange()
                .expectStatus().is2xxSuccessful()
                .expectBodyList(AuthorDto.class)
                .hasSize(authorDtoList.size())
                .value(authorDtos -> assertThat(authorDtos).containsAll(authorDtoList));

    }

    @Test
    @DisplayName("Should return saved author")
    public void ShouldReturnSavedAuthor() {
        var expectedAuthor = TestDataHolder.getAuthors().get(FIRST_AUTHOR_INDEX);
        var expectedAuthorDto = mapper.authorToAuthorDto(expectedAuthor);
        when(authorService.update(anyLong(), anyString())).thenAnswer(invocation ->
                Mono.just(new AuthorDto(invocation.getArgument(0), invocation.getArgument(1))));

        webTestClient.post().uri(AUTHOR_API_PATH).bodyValue(expectedAuthorDto).exchange()
                .expectStatus().is2xxSuccessful()
                .expectBody(AuthorDto.class)
                .isEqualTo(expectedAuthorDto);

    }

    @Test
    @DisplayName("Should return error when send invalid author object")
    public void ShouldReturnErrorInvalidId() {

        var expectedAuthorDto = new AuthorDto();

        webTestClient.post().uri(AUTHOR_API_PATH).bodyValue(expectedAuthorDto).exchange()
                .expectStatus().is4xxClientError()
                .expectBody()
                .jsonPath("$").isMap()
                .jsonPath("$.fullName").hasJsonPath()
                .jsonPath("$.fullName", containsString("cannot be empty"));

    }

    @Test
    @DisplayName("Should call delete method in repository")
    public void ShouldCallDeleteMethod() {

        var expectedAuthor = TestDataHolder.getAuthors().get(FIRST_AUTHOR_INDEX);
        var expectedAuthorDto = mapper.authorToAuthorDto(expectedAuthor);
        when(authorService.findById(1L)).thenReturn(Mono.just(expectedAuthorDto));
        when(authorService.deleteById(1L)).thenReturn(Mono.empty());

        webTestClient.delete().uri(AUTHOR_API_PATH + "/%d".formatted(expectedAuthor.getId())).exchange()
                .expectStatus().isNoContent();
        verify(authorService, times(1)).deleteById(expectedAuthor.getId());
    }
}