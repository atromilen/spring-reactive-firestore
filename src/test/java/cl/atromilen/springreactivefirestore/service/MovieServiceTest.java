package cl.atromilen.springreactivefirestore.service;

import cl.atromilen.springreactivefirestore.fixtures.DirectorFixture;
import cl.atromilen.springreactivefirestore.fixtures.MovieFixture;
import cl.atromilen.springreactivefirestore.model.Director;
import cl.atromilen.springreactivefirestore.model.Movie;
import cl.atromilen.springreactivefirestore.repository.MovieRepository;
import com.google.cloud.spring.data.firestore.FirestoreReactiveOperations;
import com.google.cloud.spring.data.firestore.FirestoreTemplate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.reactivestreams.Publisher;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class MovieServiceTest {

    @Mock
    private FirestoreTemplate firestoreTemplate;

    @Mock
    private MovieRepository repository;

    @InjectMocks
    private MovieService movieService;

    @Mock
    private FirestoreReactiveOperations firestoreReactiveOperations;

    @BeforeEach
    void setUp() {
        when(firestoreTemplate.withParent(anyString(), eq(Director.class)))
                .thenReturn(firestoreReactiveOperations);
    }

    @Test
    void whenMoviesAreFound_onFindAll_returnFluxWith7MoviesOrderedAlphabeticallyAscending() {
        when(firestoreTemplate.findAll(eq(Director.class)))
                .thenReturn(Flux.fromIterable(DirectorFixture.existingDirectors()));

        when(firestoreReactiveOperations.findAll(eq(Movie.class)))
                .thenReturn(
                        Flux.fromIterable(MovieFixture.moviesTarantino()),
                        Flux.fromIterable(MovieFixture.moviesNolan())
                );

        StepVerifier.create(movieService.findAll())
                .assertNext(movie -> assertEquals("Inception", movie.getName()))
                .assertNext(movie -> assertEquals("Inglourious Basterds", movie.getName()))
                .assertNext(movie -> assertEquals("Interstellar", movie.getName()))
                .assertNext(movie -> assertEquals("Kill Bill: Volume 1", movie.getName()))
                .assertNext(movie -> assertEquals("Once Upon a Time in Hollywood", movie.getName()))
                .assertNext(movie -> assertEquals("Pulp Fiction", movie.getName()))
                .assertNext(movie -> assertEquals("The Dark Knight", movie.getName()))
                .verifyComplete();
    }

    @Test
    void whenNoMoviesAreFound_onFindAll_returnEmptyFlux() {
        when(firestoreTemplate.findAll(eq(Director.class)))
                .thenReturn(Flux.fromIterable(DirectorFixture.existingDirectors()));

        when(firestoreReactiveOperations.findAll(eq(Movie.class)))
                .thenReturn(Flux.empty());

        StepVerifier.create(movieService.findAll())
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void whenMoviesByDirectorAreFound_onFindByDirector_returnFluxSortedByYearAscending() {
        when(firestoreReactiveOperations.findAll(eq(Movie.class)))
                .thenReturn(Flux.fromIterable(MovieFixture.moviesNolan()));

        StepVerifier.create(movieService.findByDirector("Christopher Nolan"))
                .assertNext(movie -> assertEquals("The Dark Knight", movie.getName()))
                .assertNext(movie -> assertEquals("Inception", movie.getName()))
                .assertNext(movie -> assertEquals("Interstellar", movie.getName()))
                .verifyComplete();
    }

    @Test
    void whenMovieExists_onExists_returnTrue() {
        when(firestoreReactiveOperations.existsById(any(Publisher.class), eq(Movie.class)))
                .thenReturn(Mono.just(true));

        StepVerifier.create(movieService.exists("Unknown director", "Unknown movie"))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void whenMovieDoesntExists_onExists_returnFalse() {
        when(firestoreReactiveOperations.existsById(any(Publisher.class), eq(Movie.class)))
                .thenReturn(Mono.just(false));

        StepVerifier.create(movieService.exists("Unknown director", "Unknown movie"))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void whenMovieExists_onSave_logWarningAndTerminateReturningEmptyMono() {
        when(firestoreReactiveOperations.existsById(any(Publisher.class), eq(Movie.class)))
                .thenReturn(Mono.just(true));

        StepVerifier.create(movieService.save("Unknown director", MovieFixture.moviesNolan().get(0)))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void whenMovieDoesntExists_onSave_finishWithSuccessAndReturnMovieSaved() {
        when(firestoreReactiveOperations.existsById(any(Publisher.class), eq(Movie.class)))
                .thenReturn(Mono.just(false));

        when(firestoreReactiveOperations.save(any(Movie.class)))
                .thenReturn(Mono.just(MovieFixture.moviesNolan().get(0)));

        StepVerifier.create(movieService.save("Existing director", MovieFixture.moviesNolan().get(0)))
                .assertNext(movie -> assertEquals("Interstellar", movie.getName()))
                .verifyComplete();
    }

    @Test
    void whenMovieDoesntExists_onDelete_logWarningAndComplete() {
        when(firestoreReactiveOperations.existsById(any(Publisher.class), eq(Movie.class)))
                .thenReturn(Mono.just(false));

        StepVerifier.create(movieService.delete("Unknown director", "Unknown movie"))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void whenMovieExists_onDelete_finishWithSuccessAndComplete() {
        when(firestoreReactiveOperations.existsById(any(Publisher.class), eq(Movie.class)))
                .thenReturn(Mono.just(true));

        when(firestoreReactiveOperations.deleteById(any(Publisher.class), eq(Movie.class)))
                .thenReturn(Mono.empty());

        StepVerifier.create(movieService.delete("Existing director", "Existing movie"))
                .expectNextCount(0)
                .verifyComplete();
    }

}