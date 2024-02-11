package com.devesta.curricula.controllers;

import com.devesta.curricula.DataTestUtil;
import com.devesta.curricula.domain.dao.CourseDao;
import com.devesta.curricula.domain.dao.TopicDao;
import com.devesta.curricula.domain.entities.Course;
import com.devesta.curricula.domain.entities.Topic;
import com.devesta.curricula.mappers.Mapper;
import com.devesta.curricula.services.CourseService;
import com.devesta.curricula.services.TopicService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

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
                MockMvcRequestBuilders.post("/api/v1/topics/{topicId}/courses", topic.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
        ).andExpect(status().isCreated());
    }

    @Test
    public void testThatCoursePostReturnSavedCourse() throws Exception {
        Topic topic = DataTestUtil.createTopicInstance();
        topicService.addTopic(topic);
        Course course = DataTestUtil.createCourseInstance(topic);
        String courseJson = objectMapper.writeValueAsString(course);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/topics/{topicId}/courses", topic.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
        ).andExpect(
                jsonPath("$.id").isNumber()
        ).andExpect(
                jsonPath("$.name").value("OOP")
        ).andExpect(
                jsonPath("$.description").value("Design")
        );
    }

    @Test
    public void testThatCourseGetAllReturnHttp200() throws Exception {
        Topic topic = DataTestUtil.createTopicInstance();
        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/topics/{topicId}/courses", topic.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void testThatCourseGetAllReturnCoursesBody() throws Exception {
        Topic topic = DataTestUtil.createTopicInstance();
        topicService.addTopic(topic);
        Course course = DataTestUtil.createCourseInstance(topic);
        courseService.addCourse(course, topic.getId());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/topics/{topicId}/courses", topic.getId())
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(
                jsonPath("$.content[0].id").isNumber()
        ).andExpect(
                jsonPath("$.content[0].name").value("OOP")
        ).andExpect(
                jsonPath("$.content[0].description").value("Design")
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
                MockMvcRequestBuilders.put("/api/v1/topics/{topicId}/courses/{id}", topic.getId(), 99999)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
        ).andExpect(status().isCreated());
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
                MockMvcRequestBuilders.put("/api/v1/topics/{topicId}/courses/{id}", topic.getId(), added.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
        ).andExpect(status().isOk());
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
                MockMvcRequestBuilders.put("/api/v1/topics/{topicId}/courses/{id}", topic.getId(), added.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseJson)
        ).andExpect(
                jsonPath("$.id").value(courseDao.getId())
        ).andExpect(
                jsonPath("$.name").value(courseDao.getName())
        ).andExpect(
                jsonPath("$.description").value(courseDao.getDescription())
        );
    }

    @Test
    public void testThatInvalidCourseBlankNameInBodyReturnBadRequest() throws Exception {

        String courseAsJson = """
                {
                "id": 1,
                "name": "",
                "description": "pointer,struct,recursion,files,linked list",
                "topic": {
                        "id": 1,
                        "name": "pointer",
                        "description": "Memory Management Efficiently"
                        }
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/topics/1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseAsJson)
        ).andExpect(status().isBadRequest());

    }

    @Test
    public void testThatInvalidCourseNullTopicInBodyReturnBadRequest() throws Exception {

        String courseAsJson = """
                {
                "id": 1,
                "name": "Imperative",
                "description": "pointer,struct,recursion,files,linked list"
                }
                """;

        mockMvc.perform(
                MockMvcRequestBuilders.post("/api/v1/topics/1/courses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(courseAsJson)
        ).andExpect(status().isBadRequest());
    }

}
