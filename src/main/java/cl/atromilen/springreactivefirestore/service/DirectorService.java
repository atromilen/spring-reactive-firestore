package cl.atromilen.springreactivefirestore.service;

import cl.atromilen.springreactivefirestore.model.Director;
import cl.atromilen.springreactivefirestore.repository.DirectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorRepository repository;

    public Flux<Director> findAll(){
        return repository.findAll();
    }

}
