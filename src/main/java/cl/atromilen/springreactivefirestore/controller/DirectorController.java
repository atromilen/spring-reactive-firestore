package cl.atromilen.springreactivefirestore.controller;

import cl.atromilen.springreactivefirestore.model.Director;
import cl.atromilen.springreactivefirestore.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/director")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping
    public Flux<Director> findAll(){
        return directorService.findAll();
    }

    @PostMapping
    public Mono<Director> save(@RequestBody Director director){
        return directorService.save(director);
    }


}
