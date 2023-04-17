package cl.atromilen.springreactivefirestore.controller;

import cl.atromilen.springreactivefirestore.model.Director;
import cl.atromilen.springreactivefirestore.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping(value = "/director")
@RequiredArgsConstructor
public class DirectorController {

    private final DirectorService directorService;

    @GetMapping
    public Flux<Director> findAll(){
        return directorService.findAll();
    }


}
