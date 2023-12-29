package com.example.demo.courses;

import com.example.demo.topics.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CourseController {
    @Autowired
    private CourseService courseService;

    @RequestMapping("/topics/{id}/courses")
    public List<Course> getAllCourses(@PathVariable String id) {
        return courseService.getAllCourses(id);
    }

    @RequestMapping("/topics/{Tid}/courses/{Cid}")
    public Course getcourse(@PathVariable("Cid") String id) {
        return courseService.getCourse(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/topics/{Tid}/courses") // default is Get
    public void addCourse(@RequestBody Course course,@PathVariable String Tid) {
        course.setTopic(new Topic(Tid,"",""));
        courseService.addCourse(course);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/topics/{tid}/courses/{cid}")
    public void updateCourse(@RequestBody Course course, @PathVariable String tid, @PathVariable String cid) {
        course.setTopic(new Topic(tid,"",""));
        courseService.updateCourse(course);

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/topics/{tid}/courses/{cid}")
    public void removeCourse(@PathVariable String cid) {
        courseService.removeCourse(cid);
    }

}
