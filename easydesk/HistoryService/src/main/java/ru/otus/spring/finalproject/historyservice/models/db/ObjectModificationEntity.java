package ru.otus.spring.finalproject.historyservice.models.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "objects")
public class ObjectModificationEntity {

    @NonNull
    @Id
    private long id;

    @NonNull
    @Column
    private String type;

    @NonNull
    @Column("ext_object_id")
    private UUID objectId;

    @NonNull
    @Column
    private LocalDateTime date;

    @NonNull
    @Transient
    private List<FieldModificationEntity> fields;

}
