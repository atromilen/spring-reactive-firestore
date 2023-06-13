package cl.atromilen.springreactivefirestore.fixtures;

import cl.atromilen.springreactivefirestore.model.Director;

import java.util.List;

public final class DirectorFixture {

    private DirectorFixture() {
    }

    public static List<Director> existingDirectors(){
        return List.of(
                new Director("Quentin Tarantino", "1963-03-27"),
                new Director("Christopher Nolan", "1970-07-30")
        );
    }

}
