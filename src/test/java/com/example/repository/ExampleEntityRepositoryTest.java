package com.example.repository;

import com.example.model.ExampleEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@MicronautTest
public class ExampleEntityRepositoryTest {

    @Inject
    ExampleEntityRepository exampleEntityRepository;
    @Inject
    ObjectMapper objectMapper;

    //This works
    @Test
    public void testJSONFieldRead() {
        UUID userId = UUID.randomUUID();
        Map<String, String> rawData = new HashMap<>();
        rawData.put("type", "stepCount");
        rawData.put("stepCount", "21");
        exampleEntityRepository.save(ExampleEntity.builder()
                .userId(userId)
                .rawData(rawData)
                .build());

        assertThat(exampleEntityRepository.count()).isEqualTo(1);
        assertThat(exampleEntityRepository.findAll().iterator().next().getUserId()).isEqualTo(userId);

    }

    //This works
    @Test
    public void testJSONFieldReadWithJSONArrayFieldTypeAsMap() {
        UUID userId = UUID.randomUUID();
        Map<String, String> rawData = new HashMap<>();
        rawData.put("type", "stepCount");
        rawData.put("stepCount", "29");
        rawData.put("dataSources","  [\n" +
                "    {\n" +
                "      \"identifier\": \"189db1e7\",\n" +
                "      \"deviceType\": 3,\n" +
                "      \"manufacturer\": \"Compal\",\n" +
                "      \"model\": \"Fossil Sport\"\n" +
                "    }\n" +
                "  ]");
        exampleEntityRepository.save(ExampleEntity.builder()
                .userId(userId)
                .rawData(rawData)
                .build());

        assertThat(exampleEntityRepository.count()).isEqualTo(1);
        assertThat(exampleEntityRepository.findAll().iterator().next().getUserId()).isEqualTo(userId);

    }

    //This works
    @Test
    public void testJSONFieldReadFromFile() throws Exception {
        UUID userId = UUID.randomUUID();
        String jsonString = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("testSimpleFields.json").toURI())));
        Map<String, String> rawData = objectMapper.readerFor(Map.class).readValue(jsonString);
        exampleEntityRepository.save(ExampleEntity.builder()
                .userId(userId)
                .rawData(rawData)
                .build());

        assertThat(exampleEntityRepository.count()).isEqualTo(1);
        assertThat(exampleEntityRepository.findAll().iterator().next().getUserId()).isEqualTo(userId);

    }

    //This does not work
    @Test
    public void testJSONFieldReadWithJSONArrayFieldTypeFromFile() throws Exception {
        UUID userId = UUID.randomUUID();
        String jsonString = new String(Files.readAllBytes(Paths.get(ClassLoader.getSystemResource("test.json").toURI())));
        Map<String, String> rawData = objectMapper.readerFor(Map.class).readValue(jsonString);
        exampleEntityRepository.save(ExampleEntity.builder()
                .userId(userId)
                .rawData(rawData)
                .build());

        assertThat(exampleEntityRepository.count()).isEqualTo(1);
        assertThat(exampleEntityRepository.findAll().iterator().next().getUserId()).isEqualTo(userId);

    }

    //This does not work
    @Test
    public void testJSONFieldReadWithJSONArrayFieldTypeFromString() throws Exception {
        UUID userId = UUID.randomUUID();
        String jsonString =
                "{\n" +
                        "  \"type\": \"stepCount\",\n" +
                        "  \"stepCount\": 29,\n" +
                        "  \"dataSources\": [\n" +
                        "    {\n" +
                        "      \"identifier\": \"189db1e7\",\n" +
                        "      \"deviceType\": 3,\n" +
                        "      \"manufacturer\": \"Compal\",\n" +
                        "      \"model\": \"Fossil Sport\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";
        Map<String, String> rawData = objectMapper.readerFor(Map.class).readValue(jsonString);
        exampleEntityRepository.save(ExampleEntity.builder()
                .userId(userId)
                .rawData(rawData)
                .build());

        assertThat(exampleEntityRepository.count()).isEqualTo(1);
        assertThat(exampleEntityRepository.findAll().iterator().next().getUserId()).isEqualTo(userId);

    }

}
