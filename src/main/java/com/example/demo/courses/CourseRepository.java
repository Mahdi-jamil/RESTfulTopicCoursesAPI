package com.example.demo.courses;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course,String> {
    List<Course> findByTopicId(String tid);
}
