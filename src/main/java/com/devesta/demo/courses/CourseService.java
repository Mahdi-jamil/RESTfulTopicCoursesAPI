package com.devesta.demo.courses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public List<Course> getAllCourses(String id) {
        return new ArrayList<>(courseRepository.findByTopicId(id));
    }


    public Course getCourse(String id) {
        AtomicReference<Course> returnCourse = new AtomicReference<>();
        courseRepository
                .findById(id)
                .ifPresentOrElse(returnCourse::set, ()-> System.out.println("404"));
        return returnCourse.get();
    }

    public void addCourse(Course course) {
        courseRepository.save(course);
    }


    public void updateCourse(Course updatedCourse) {
        courseRepository.save(updatedCourse);
    }

    public void removeCourse(String id) {
        courseRepository.deleteById(id);
    }
}
