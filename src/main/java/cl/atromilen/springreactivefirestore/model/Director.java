package cl.atromilen.springreactivefirestore.model;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import lombok.Data;

import java.util.List;

@Data
@Document(collectionName = "director")
public class Director {

    @DocumentId
    private String nombre;
    private String birthdate;
    private List<Movie> movies;

}
