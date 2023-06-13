package cl.atromilen.springreactivefirestore.model;

import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.IgnoreExtraProperties;
import com.google.cloud.spring.data.firestore.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collectionName = "movies")
@IgnoreExtraProperties
public class Movie {
    @DocumentId
    private String name;
    private Integer year;
}
