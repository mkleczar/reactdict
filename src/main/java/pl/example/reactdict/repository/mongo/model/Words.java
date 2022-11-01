package pl.example.reactdict.repository.mongo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@AllArgsConstructor
@Getter
@ToString(onlyExplicitlyIncluded = true)
@Document("words")
public class Words {
    @Id
    @ToString.Include
    private String id;

    @Field(name="word")
    @Indexed(unique = true)
    @ToString.Include
    private String word;

    public static Words from(String word) {
        return new Words(null, word);
    }
}
