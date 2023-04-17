package cl.atromilen.springreactivefirestore.repository;

import cl.atromilen.springreactivefirestore.model.Director;
import com.google.cloud.spring.data.firestore.FirestoreReactiveRepository;

public interface DirectorRepository extends FirestoreReactiveRepository<Director> {
}
