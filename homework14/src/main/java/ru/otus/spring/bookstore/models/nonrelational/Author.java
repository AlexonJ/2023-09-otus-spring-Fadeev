package ru.otus.spring.bookstore.models.nonrelational;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "authors")
public class Author {

    @Id
    private String id;

    private String fullName;

    @Override
    public String toString() {
        return "Author{" +
                "id='" + id + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
