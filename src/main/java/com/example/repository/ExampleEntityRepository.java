package com.example.repository;

import com.example.model.ExampleEntity;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.UUID;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface ExampleEntityRepository extends CrudRepository<ExampleEntity, UUID> {
}
