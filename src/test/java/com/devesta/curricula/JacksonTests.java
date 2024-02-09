package com.devesta.curricula;

import com.devesta.curricula.domain.entities.Topic;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class JacksonTests {

    ObjectMapper mapper = new ObjectMapper();
    Topic topic = Topic.builder()
            .id(123L)
            .name("java")
            .description("programming language")
            .build();

//    String topicAsJsonWithModification = "{\"id\":123,\"name\":\"java\",\"desc\":\"programming language\",\"relatedTo\":\"Cs\"}";
//    test for  @JsonProperty("desc") annotation
    String topicAsJson = "{\"id\":123,\"name\":\"java\",\"description\":\"programming language\"}";


    @Test
    public void testConvertFromObjectToJson() throws JsonProcessingException {

        String value = mapper.writeValueAsString(topic);
        Assertions.assertThat(value).isEqualTo(topicAsJson);

    }

    @Test
    public void testConvertFromJsonToJavaObject() throws JsonProcessingException {

        Topic result = mapper.readValue(topicAsJson, Topic.class);
        Assertions.assertThat(result).isEqualTo(topic);

    }
}
