package ru.otus.spring.finalproject.historyservice.models.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "fields")
public class FieldModificationEntity {

    @NonNull
    @Id
    private long id;

    @NonNull
    @Column
    private Long objectId;

    @NonNull
    @Column(value = "field_name")
    private String name;

    @NonNull
    @Column
    private String value;

}
