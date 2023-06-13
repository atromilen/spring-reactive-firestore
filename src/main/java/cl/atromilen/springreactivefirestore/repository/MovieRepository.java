package cl.atromilen.springreactivefirestore.repository;

import cl.atromilen.springreactivefirestore.model.Movie;
import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;

public interface MovieRepository extends FirestoreReactiveRepository<Movie> {
}
