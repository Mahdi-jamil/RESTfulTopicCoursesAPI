package com.devesta.demo.controllers;

import com.devesta.demo.DataTestUtil;
import com.devesta.demo.domain.dao.TopicDao;
import com.devesta.demo.domain.entities.Topic;
import com.devesta.demo.services.TopicService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class TopicControllerIntegrationTests {

    private final TopicService topicService;

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Autowired
    public TopicControllerIntegrationTests(TopicService topicService, MockMvc mockMvc) {
        this.topicService = topicService;
        this.mockMvc = mockMvc;
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatTopicPostReturnHttp200() throws Exception {
        Topic topic = DataTestUtil.createTopicInstance();
        topic.setId(null);
        String topicJson = objectMapper.writeValueAsString(topic);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(topicJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatTopicPostReturnSavedTopic() throws Exception {
        Topic topic = DataTestUtil.createTopicInstance();
        topic.setId(null);
        String topicJson = objectMapper.writeValueAsString(topic);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/topics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(topicJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("java")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value("language")
        );
    }

    @Test
    public void testThatTopicGetAllReturnHttp200() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/topics")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatTopicGetAllReturnTopicsBody() throws Exception {
        Topic topic = DataTestUtil.createTopicInstance();
        topic.setId(null);
        topicService.addTopic(topic);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/topics")
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].name").value("java")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].description").value("language")
        );
    }

    @Test
    public void testThatGetTopicReturnHttp200() throws Exception {
        Topic topic = DataTestUtil.createTopicInstance();
        Topic added = topicService.addTopic(topic);
        mockMvc.perform(
                MockMvcRequestBuilders.get("/topics/{id}", added.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatGetTopicReturnHttp404WhenNoTopic() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get("/topics/{id}", 99999)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatTopicGetReturnTopicBody() throws Exception {
        Topic topic = DataTestUtil.createTopicInstance();
        Topic added = topicService.addTopic(topic);

        mockMvc.perform(
                MockMvcRequestBuilders.get("/topics/{id}", added.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("java")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value("language")
        );
    }

    @Test
    public void testThatPutTopicReturnHttp404WhenTopicNotFound() throws Exception {
        TopicDao topicDao = DataTestUtil.createTopicDaoA();
        String topicJson = objectMapper.writeValueAsString(topicDao);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/topics/{id}", 99999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(topicJson)
        ).andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testThatPutTopicReturnHttp200WhenTopicFoundAndUpdated() throws Exception {
        Topic topic = DataTestUtil.createTopicInstance();
        Topic added = topicService.addTopic(topic);

        TopicDao topicDao = DataTestUtil.createTopicDaoA();
        String topicJson = objectMapper.writeValueAsString(topicDao);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/topics/{id}", added.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(topicJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdateUpdatesExistingTopic() throws Exception {
        Topic topic = DataTestUtil.createTopicInstance();
        Topic added = topicService.addTopic(topic);

        TopicDao topicDao = DataTestUtil.createTopicDaoB();
        topicDao.setId(added.getId());

        String topicJson = objectMapper.writeValueAsString(topicDao);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/topics/{id}", added.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(topicJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(topicDao.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(topicDao.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value(topicDao.getDescription())
        );
    }


}
