package com.devesta.curricula;

import com.devesta.curricula.domain.dto.CourseDto;
import com.devesta.curricula.domain.dto.TopicDto;
import com.devesta.curricula.domain.entities.Course;
import com.devesta.curricula.domain.entities.Topic;

public class DataTestUtil {

    private DataTestUtil(){}

    public static Topic createTopicInstance(){
        return Topic.builder()
                .id(123L)
                .name("java")
                .description("language")
                .build();
    }

    public static Course createCourseInstance(Topic topic){
        return Course.builder()
                .id(topic.getId())
                .name("OOP")
                .description("Design")
                .topic(topic)
                .build();
    }

    public static TopicDto createTopicDaoA(){
        return TopicDto.builder()
                .id(123L)
                .name("java")
                .description("language")
                .build();
    }

    public static TopicDto createTopicDaoB(){
        return TopicDto.builder()
                .id(1234L)
                .name("C++")
                .description("language")
                .build();
    }


    public static CourseDto createCourseDaoA() {
        return CourseDto.builder()
                .id(createTopicDaoA().getId())
                .name("OOP")
                .description("Designs")
                .topic(createTopicDaoA())
                .build();
    }
}
