package com.example.model;

import io.micronaut.data.annotation.*;
import io.micronaut.data.model.DataType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder(toBuilder = true)
@MappedEntity
public class ExampleEntity {
    @NotNull(message = "User ID cannot be null.")
    private final UUID userId;
    @NotNull(message = "Raw data cannot be null")
    @TypeDef(type = DataType.JSON)
    private final Map<String, String> rawData;
    @Id
    @AutoPopulated
    private UUID id;
    @DateCreated
    private OffsetDateTime creationDateTime;
}
