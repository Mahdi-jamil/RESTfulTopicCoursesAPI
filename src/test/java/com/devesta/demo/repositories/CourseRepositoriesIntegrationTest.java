package com.devesta.demo.repositories;

import com.devesta.demo.DataTestUtil;
import com.devesta.demo.domain.entities.Course;
import com.devesta.demo.domain.entities.Topic;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
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
        Course course =DataTestUtil.createCourseInstance(topic);
        underTest.save(course);
        underTest.delete(course);
        Optional<Course> result = underTest.findById(course.getId());
        Assertions.assertThat(result).isEmpty();
    }


}
