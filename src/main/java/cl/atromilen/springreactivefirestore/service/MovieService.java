package cl.atromilen.springreactivefirestore.service;

import cl.atromilen.springreactivefirestore.model.Director;
import cl.atromilen.springreactivefirestore.model.Movie;
import cl.atromilen.springreactivefirestore.repository.MovieRepository;
import com.google.cloud.spring.data.firestore.FirestoreReactiveOperations;
import com.google.cloud.spring.data.firestore.FirestoreTemplate;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscriber;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@Slf4j
@Service
public record MovieService(FirestoreTemplate firestoreTemplate, MovieRepository movieRepository) {

    public Flux<Movie> findAll() {
        return firestoreTemplate.findAll(Director.class)
                .flatMap(
                        director -> withDirectorId(director.getName()).findAll(Movie.class)
                ).sort(Comparator.comparing(Movie::getName));
    }

    public Flux<Movie> findByDirector(String directorName) {
        return withDirectorId(directorName)
                .findAll(Movie.class)
                .sort(Comparator.comparingInt(Movie::getYear));
    }

    public Mono<Boolean> exists(String directorName, String movieName) {
        return withDirectorId(directorName)
                .existsById(subscriber -> subscribeWithDocumentId(movieName, subscriber), Movie.class);
    }


    public Mono<Movie> save(String directorName, Movie movie) {
        return exists(directorName, movie.getName())
                .flatMap(exists -> {
                    if (exists) {
                        log.warn("Movie \"{}\" for director \"{}\" already exists", movie.getName(), directorName);
                        return Mono.empty();
                    }

                    return withDirectorId(directorName).save(movie);
                });
    }

    public Mono<Void> delete(String directorName, String movie) {
        return exists(directorName, movie)
                .flatMap(exists -> {
                    if (!exists) {
                        log.warn("Movie \"{}\" for director \"{}\" doesn't exists", movie, directorName);
                        return Mono.empty();
                    }

                    return withDirectorId(directorName)
                            .deleteById(subscriber ->
                                    subscribeWithDocumentId(movie, subscriber), Movie.class
                            );
                });
    }

    private FirestoreReactiveOperations withDirectorId(String directorId) {
        return firestoreTemplate.withParent(directorId, Director.class);
    }

    private static Subscriber<? super String> subscribeWithDocumentId(
            String documentId, Subscriber<? super String> subscriber) {
        return Mono.just(documentId).subscribeWith(subscriber);
    }


}
