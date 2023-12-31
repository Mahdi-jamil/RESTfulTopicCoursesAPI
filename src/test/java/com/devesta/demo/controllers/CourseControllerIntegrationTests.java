package com.devesta.demo.controllers;

import com.devesta.demo.DataTestUtil;
import com.devesta.demo.domain.dao.CourseDao;
import com.devesta.demo.domain.dao.TopicDao;
import com.devesta.demo.domain.entities.Course;
import com.devesta.demo.domain.entities.Topic;
import com.devesta.demo.mappers.Mapper;
import com.devesta.demo.services.CourseService;
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
public class CourseControllerIntegrationTests {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final CourseService courseService;
    private final TopicService topicService;
    private final Mapper<Topic, TopicDao> mapper;


    @Autowired
    public CourseControllerIntegrationTests(MockMvc mockMvc, CourseService courseService, TopicService topicService, Mapper<Topic, TopicDao> mapper) {
        this.mockMvc = mockMvc;
        this.courseService = courseService;
        this.topicService = topicService;
        this.mapper = mapper;
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testThatCoursePostReturnHttp200() throws Exception {
        Topic topic = DataTestUtil.createTopicInstance();
        topicService.addTopic(topic);
        Course course = DataTestUtil.createCourseInstance(topic);
        String courseJson = objectMapper.writeValueAsString(course);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/topics/{topicId}/courses", topic.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatCoursePostReturnSavedCourse() throws Exception {
        Topic topic = DataTestUtil.createTopicInstance();
        topicService.addTopic(topic);
        Course course = DataTestUtil.createCourseInstance(topic);
        String courseJson = objectMapper.writeValueAsString(course);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/topics/{topicId}/courses", topic.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value("OOP")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value("Design")
        );
    }

    @Test
    public void testThatCourseGetAllReturnHttp200() throws Exception {
        Topic topic = DataTestUtil.createTopicInstance();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/topics/{topicId}/courses", topic.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatCourseGetAllReturnCoursesBody() throws Exception {
        Topic topic = DataTestUtil.createTopicInstance();
        topicService.addTopic(topic);
        Course course = DataTestUtil.createCourseInstance(topic);
        courseService.addCourse(course, topic.getId());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/topics/{topicId}/courses", topic.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].id").isNumber()
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].name").value("OOP")
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.content[0].description").value("Design")
        );
    }

    @Test
    public void testThatPutCourseReturnHttp201WhenCourseNotFoundAndCreated() throws Exception {
        CourseDao courseDao = DataTestUtil.createCourseDaoA();
        String courseJson = objectMapper.writeValueAsString(courseDao);

        TopicDao daoA = DataTestUtil.createTopicDaoA();
        Topic topic = mapper.mapFrom(daoA);
        topicService.addTopic(topic);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/topics/{topicId}/courses/{id}", topic.getId(), 99999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
        ).andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void testThatPutCourseReturnHttp200WhenCourseFoundAndUpdated() throws Exception {
        Topic topic = DataTestUtil.createTopicInstance();
        topicService.addTopic(topic);
        Course course = DataTestUtil.createCourseInstance(topic);
        Course added = courseService.addCourse(course, topic.getId());

        CourseDao courseDao = DataTestUtil.createCourseDaoA();
        courseDao.setId(added.getId());
        String courseJson = objectMapper.writeValueAsString(courseDao);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/topics/{topicId}/courses/{id}", topic.getId(), added.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testThatUpdateUpdatesExistingCourse() throws Exception {
        Topic topic = DataTestUtil.createTopicInstance();
        topicService.addTopic(topic);
        Course course = DataTestUtil.createCourseInstance(topic);
        Course added = courseService.addCourse(course, topic.getId());

        CourseDao courseDao = DataTestUtil.createCourseDaoA();
        courseDao.setId(added.getId());

        String courseJson = objectMapper.writeValueAsString(courseDao);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/topics/{topicId}/courses/{id}", topic.getId(), added.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.id").value(courseDao.getId())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.name").value(courseDao.getName())
        ).andExpect(
                MockMvcResultMatchers.jsonPath("$.description").value(courseDao.getDescription())
        );
    }

}
