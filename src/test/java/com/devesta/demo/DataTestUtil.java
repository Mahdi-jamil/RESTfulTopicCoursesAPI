package com.devesta.demo;

import com.devesta.demo.domain.dao.CourseDao;
import com.devesta.demo.domain.dao.TopicDao;
import com.devesta.demo.domain.entities.Course;
import com.devesta.demo.domain.entities.Topic;

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

    public static TopicDao createTopicDaoA(){
        return TopicDao.builder()
                .id(123L)
                .name("java")
                .description("language")
                .build();
    }

    public static TopicDao createTopicDaoB(){
        return TopicDao.builder()
                .id(1234L)
                .name("C++")
                .description("language")
                .build();
    }


    public static CourseDao createCourseDaoA() {
        return CourseDao.builder()
                .id(createTopicDaoA().getId())
                .name("OOP")
                .description("Designs")
                .topic(createTopicDaoA())
                .build();
    }
}
