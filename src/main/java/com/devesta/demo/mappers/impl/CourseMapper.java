package com.devesta.demo.mappers.impl;

import com.devesta.demo.domain.dao.CourseDao;
import com.devesta.demo.domain.entities.Course;
import com.devesta.demo.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper implements Mapper<Course, CourseDao> {

    private final ModelMapper mapper;

    public CourseMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CourseDao mapTo(Course course) {
        return mapper.map(course, CourseDao.class);

    }

    @Override
    public Course mapFrom(CourseDao courseDao) {
        return mapper.map(courseDao, Course.class);
    }
}
