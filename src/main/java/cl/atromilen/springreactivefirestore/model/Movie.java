package cl.atromilen.springreactivefirestore.model;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.spring.data.firestore.Document;
import lombok.Data;

@Data
@Document(collectionName = "movie")
public class Movie {
    @DocumentId
    private Integer movieId;
    private String name;
    private Integer year;
    private Integer director;
}
