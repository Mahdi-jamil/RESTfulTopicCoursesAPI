package com.devesta.curricula.services.impl;

import com.devesta.curricula.domain.entities.Course;
import com.devesta.curricula.domain.entities.Topic;
import com.devesta.curricula.repositories.CourseRepository;
import com.devesta.curricula.services.CourseService;
import com.devesta.curricula.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private final CourseRepository courseRepository;

    @Autowired
    private final TopicService topicService;

    public CourseServiceImpl(CourseRepository courseRepository, TopicService topicService) {
        this.courseRepository = courseRepository;
        this.topicService = topicService;
    }

//    public List<Course> getAllCourses(Long topicId) {
//        return new ArrayList<>(courseRepository.findAllByTopicId(topicId));
//    }

    @Override
    public Page<Course> getAllCourses(Pageable pageable, Long topicId) {
        return courseRepository.findAllByTopicId(pageable, topicId);
    }

    public Course getCourse(Long id, Long topicId) {
        return courseRepository.findByIdAndTopicId(id, topicId);
    }

    public Course addCourse(Course course, Long topicId) {
        Topic topic = topicService.getTopic(topicId);
        course.setTopic(topic);
        return courseRepository.save(course);
    }

    public Course updateCourse(Course updatedCourse, Long topicId) {
        Topic topic = topicService.getTopic(topicId);
        updatedCourse.setTopic(topic);
        return courseRepository.save(updatedCourse);

    }

    public void removeCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public boolean isExist(Long id) {
        return courseRepository.existsById(id);
    }
}
