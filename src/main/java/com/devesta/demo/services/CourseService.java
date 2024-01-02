package com.devesta.demo.services;

import com.devesta.demo.domain.entities.Course;
import com.devesta.demo.domain.entities.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface CourseService {
//    public List<Course> getAllCourses(Long topicId);
    Page<Course> getAllCourses(Pageable pageable,Long topicId);
    public Course getCourse(Long id,Long topicId);
    public Course addCourse(Course course,Long topicId);
    public Course updateCourse(Course updatedCourse, Long topicId);
    public void removeCourse(Long id);
    boolean isExist(Long id);
}
