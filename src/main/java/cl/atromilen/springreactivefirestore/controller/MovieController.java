package cl.atromilen.springreactivefirestore.controller;

import cl.atromilen.springreactivefirestore.model.Movie;
import cl.atromilen.springreactivefirestore.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping(value = "/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public Flux<Movie> getAll() {
        return movieService.findAll();
    }

    @GetMapping("/{director}")
    public Flux<Movie> getMoviesByDirector(@PathVariable String director) {
        return movieService.findByDirector(director);
    }

    @PostMapping("/{director}")
    public Mono<Movie> save(@PathVariable String director, @RequestBody Movie movie) {
        return movieService.save(director, movie);
    }

    @DeleteMapping("/{director}/{movie}")
    public Mono<Void> delete(@PathVariable String director, @PathVariable String movie) {
        return movieService.delete(director, movie);
    }
}
