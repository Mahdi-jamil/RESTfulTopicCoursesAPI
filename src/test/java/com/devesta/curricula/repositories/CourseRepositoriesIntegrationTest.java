package com.devesta.curricula.repositories;

import com.devesta.curricula.DataTestUtil;
import com.devesta.curricula.domain.entities.Course;
import com.devesta.curricula.domain.entities.Topic;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CourseRepositoriesIntegrationTest {

    private final CourseRepository underTest;

    @Autowired
    public CourseRepositoriesIntegrationTest(CourseRepository underTest) {
        this.underTest = underTest;
    }

    @Test
    public void testThatCourseCanStoredAndGet(){
        Topic topic = DataTestUtil.createTopicInstance();
        Course course = DataTestUtil.createCourseInstance(topic);

        underTest.save(course);
        Optional<Course> result = underTest.findById(course.getId());

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(course);
    }

    @Test
    public void testCourseCanBeUpdated(){
        Topic topic = DataTestUtil.createTopicInstance();
        Course course =DataTestUtil.createCourseInstance(topic);

        underTest.save(course);
        topic.setName("saved name");
        underTest.save(course);
        Optional<Course> result = underTest.findById(course.getId());

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get()).isEqualTo(course);
    }

    @Test
    public void testCourseCanBeDeleted(){
        Topic topic = DataTestUtil.createTopicInstance();
        Course course = DataTestUtil.createCourseInstance(topic);

        underTest.save(course);
        underTest.delete(course);

        Optional<Course> result = underTest.findById(course.getId());
        Assertions.assertThat(result).isEmpty();
    }

    @Test
    public void testCourseCanBeFoundByIdAndTopicId(){
        Topic topic = DataTestUtil.createTopicInstance();
        Course course = DataTestUtil.createCourseInstance(topic);

        underTest.save(course);
        Course courseByIdAndTopicId = underTest.findByIdAndTopicId(course.getId(), topic.getId());

        Assertions.assertThat(courseByIdAndTopicId).isNotNull();
        Assertions.assertThat(courseByIdAndTopicId).isEqualTo(course);
    }
}
