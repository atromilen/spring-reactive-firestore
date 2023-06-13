package cl.atromilen.springreactivefirestore.fixtures;

import cl.atromilen.springreactivefirestore.model.Movie;

import java.util.List;

public final class MovieFixture {

    private MovieFixture() {
    }

    public static List<Movie> moviesTarantino(){
        return List.of(
                new Movie("Pulp Fiction", 1994),
                new Movie("Kill Bill: Volume 1", 2003),
                new Movie("Inglourious Basterds", 2009),
                new Movie("Once Upon a Time in Hollywood", 2014)
        );
    }

    public static List<Movie> moviesNolan(){
        return List.of(
                new Movie("Interstellar", 2014),
                new Movie("The Dark Knight", 2008),
                new Movie("Inception", 2010)
        );
    }


}
