package cl.atromilen.springreactivefirestore.service;

import cl.atromilen.springreactivefirestore.model.Director;
import cl.atromilen.springreactivefirestore.repository.DirectorRepository;
import com.google.cloud.spring.data.firestore.FirestoreTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public record DirectorService(FirestoreTemplate firestoreTemplate, DirectorRepository directorRepository) {

    public Flux<Director> findAll() {
        return firestoreTemplate.findAll(Director.class);
    }

    public Mono<Boolean> existsByName(String name) {
        return directorRepository.existsById(name);
    }

    public Mono<Director> save(Director director) {
        return existsByName(director.getName())
                .flatMap(exists -> {
                    if (exists) {
                        log.warn("Director with name {} already exists!", director.getName());
                        return Mono.empty();
                    }
                    return firestoreTemplate.save(director);
                });
    }

}
