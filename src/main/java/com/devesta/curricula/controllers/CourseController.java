package com.devesta.curricula.controllers;

import com.devesta.curricula.domain.dto.CourseDto;
import com.devesta.curricula.domain.entities.Course;
import com.devesta.curricula.mappers.Mapper;
import com.devesta.curricula.services.CourseService;
import com.devesta.curricula.services.impl.CourseServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/topics")
public class CourseController {

    private final CourseService courseService;

    private final Mapper<Course, CourseDto> mapper;

    @Autowired
    public CourseController(CourseServiceImpl courseService, Mapper<Course, CourseDto> mapper) {
        this.courseService = courseService;
        this.mapper = mapper;
    }

    @GetMapping("/{topicId}/courses")
    public Page<CourseDto> getAllCourses(@PathVariable Long topicId, Pageable pageable) {
        Page<Course> allCourses = courseService.getAllCourses(pageable, topicId);
        return allCourses.map(mapper::mapTo);
    }

    @GetMapping("/{topicId}/courses/{courseId}")
    public ResponseEntity<CourseDto> getCourse(@PathVariable Long courseId, @PathVariable Long topicId) {
        Optional<Course> courseFounded = Optional.ofNullable(courseService.getCourse(courseId, topicId));
        return courseFounded.map(course ->
                        new ResponseEntity<>(mapper.mapTo(course), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/{topicId}/courses")
    public ResponseEntity<CourseDto> addCourse(@RequestBody @Valid CourseDto courseDto, @PathVariable Long topicId) {
        if(courseService.isExist(courseDto.getId())){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(getCourse(courseDto.getId(), topicId).getBody());
        }

        Course course = mapper.mapFrom(courseDto);
        Course savedCourse = courseService.addCourse(course, topicId);
        return new ResponseEntity<>(mapper.mapTo(savedCourse), HttpStatus.CREATED);
    }

    @PutMapping("/{topicId}/courses/{courseId}")
    public ResponseEntity<CourseDto> updateCourse(@PathVariable Long topicId
            , @RequestBody CourseDto courseDto
            , @PathVariable Long courseId) {

        courseDto.setId(courseId);
        Course course = mapper.mapFrom(courseDto);
        boolean exist = courseService.isExist(courseId);

        if (exist) {
            Course updated = courseService.updateCourse(course, courseId);
            return new ResponseEntity<>(mapper.mapTo(updated), HttpStatus.OK);
        } else {
            Course addedCourse = courseService.addCourse(course, topicId);
            return new ResponseEntity<>(mapper.mapTo(addedCourse), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{topicId}/courses/{courseId}")
    public ResponseEntity<Void> removeCourse(@PathVariable Long courseId) {
        courseService.removeCourse(courseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
