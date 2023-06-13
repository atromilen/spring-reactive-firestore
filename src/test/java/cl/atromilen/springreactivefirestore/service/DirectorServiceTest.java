package cl.atromilen.springreactivefirestore.service;

import cl.atromilen.springreactivefirestore.fixtures.DirectorFixture;
import cl.atromilen.springreactivefirestore.model.Director;
import cl.atromilen.springreactivefirestore.repository.DirectorRepository;
import com.google.cloud.spring.data.firestore.FirestoreTemplate;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static cl.atromilen.springreactivefirestore.fixtures.DirectorFixture.existingDirectors;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class DirectorServiceTest {

    @Mock
    private DirectorRepository repository;

    @Mock
    private FirestoreTemplate firestoreTemplate;

    @InjectMocks
    private DirectorService directorService;

    @Test
    void whenDirectorIsFound_onFindAll_returnFluxDirector() {
        when(firestoreTemplate.findAll(eq(Director.class)))
                .thenReturn(Flux.fromIterable(existingDirectors()));

        StepVerifier.create(directorService.findAll())
                .assertNext(director -> assertEquals("Quentin Tarantino", director.getName()))
                .assertNext(director -> assertEquals("Christopher Nolan", director.getName()))
                .verifyComplete();
    }

    @Test
    void whenNoDirectorIsFound_onFindAll_returnEmptyFlux() {
        when(firestoreTemplate.findAll(eq(Director.class)))
                .thenReturn(Flux.empty());

        StepVerifier.create(directorService.findAll())
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void whenDirectorExists_inExistsByName_returnTrue() {
        when(repository.existsById(anyString()))
                .thenReturn(Mono.just(Boolean.TRUE));

        StepVerifier.create(directorService.existsByName("mock name"))
                .expectNext(true)
                .verifyComplete();
    }

    @Test
    void whenNoDirectorExists_inExistsByName_returnFalse() {
        when(repository.existsById(anyString()))
                .thenReturn(Mono.just(Boolean.FALSE));

        StepVerifier.create(directorService.existsByName("mock name"))
                .expectNext(false)
                .verifyComplete();
    }

    @Test
    void whenDirectorAlreadyExists_onSave_DontSaveAndReturnEmptyMono() {
        when(repository.existsById(anyString()))
                .thenReturn(Mono.just(Boolean.TRUE));

        var newDirector = new Director("Quentin Tarantino", "1963-03-27");

        StepVerifier.create(directorService.save(newDirector))
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    void whenDirectorNotExists_onSave_proceedToSaveAndReturnSavedDirector() {
        when(repository.existsById(anyString()))
                .thenReturn(Mono.just(Boolean.FALSE));

        when(firestoreTemplate.save(any(Director.class)))
                .thenReturn(Mono.just(DirectorFixture.existingDirectors().get(0)));

        var newDirector = new Director("Quentin Tarantino", "1963-03-27");

        StepVerifier.create(directorService.save(newDirector))
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

}