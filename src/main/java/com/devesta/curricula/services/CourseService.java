package com.devesta.curricula.services;

import com.devesta.curricula.domain.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CourseService {
//    public List<Course> getAllCourses(Long topicId);
    Page<Course> getAllCourses(Pageable pageable,Long topicId);
    public Course getCourse(Long id,Long topicId);
    public Course addCourse(Course course,Long topicId);
    public Course updateCourse(Course updatedCourse, Long topicId);
    public void removeCourse(Long id);
    boolean isExist(Long id);
}
