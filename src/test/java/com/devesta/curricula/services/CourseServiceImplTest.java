package com.devesta.curricula.services;

import com.devesta.curricula.DataTestUtil;
import com.devesta.curricula.domain.entities.Course;
import com.devesta.curricula.domain.entities.Topic;
import com.devesta.curricula.repositories.CourseRepository;
import com.devesta.curricula.services.impl.CourseServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;

import static org.mockito.Mockito.*;

@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CourseServiceImplTest {
    @Mock
    private CourseRepository courseRepository;

    @Mock
    private TopicService topicService;

    @InjectMocks
    private CourseServiceImpl underTest;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetCourse() {
        Topic topic = DataTestUtil.createTopicInstance();
        Course course = DataTestUtil.createCourseInstance(topic);
        when(courseRepository.findByIdAndTopicId(course.getId(), topic.getId())).thenReturn(course);

        Course getCourse = underTest.getCourse(course.getId(), topic.getId());

        Assertions.assertThat(getCourse).isNotNull();
        Assertions.assertThat(getCourse).isEqualTo(course);
    }

    @Test
    public void testAddCourse() {
        Topic topic = DataTestUtil.createTopicInstance();
        Course course = DataTestUtil.createCourseInstance(topic);
        when(topicService.getTopic(topic.getId())).thenReturn(topic);
        when(courseRepository.save(course)).thenReturn(course);

        Course addedCourse = underTest.addCourse(course, topic.getId());

        Assertions.assertThat(addedCourse).isNotNull();
        Assertions.assertThat(addedCourse).isEqualTo(course);
        verify(courseRepository, times(1)).save(any(Course.class));
    }


    @Test
    public void testUpdateCourse() {
        Topic topic = DataTestUtil.createTopicInstance();
        Course originalCourse = DataTestUtil.createCourseInstance(topic);
        Course updatedCourse = DataTestUtil.createCourseInstance(topic);
        updatedCourse.setName("updatedName");

        when(topicService.getTopic(topic.getId())).thenReturn(topic);
        when(courseRepository.save(any(Course.class))).thenReturn(updatedCourse);

        Course result = underTest.updateCourse(updatedCourse, topic.getId());

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getName()).isEqualTo("updatedName");

        Assertions.assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("name") // ignore the 'name' field for comparison
                .isEqualTo(originalCourse);

        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    public void testRemoveCourse() {
        long id = 1L;

        courseRepository.deleteById(id);

        verify(courseRepository,times(1)).deleteById(id);
    }


}
