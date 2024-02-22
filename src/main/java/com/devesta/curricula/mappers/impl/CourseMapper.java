package com.devesta.curricula.mappers.impl;

import com.devesta.curricula.domain.dto.CourseDto;
import com.devesta.curricula.domain.entities.Course;
import com.devesta.curricula.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class CourseMapper implements Mapper<Course, CourseDto> {

    private final ModelMapper mapper;

    public CourseMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public CourseDto mapTo(Course course) {
        return mapper.map(course, CourseDto.class);

    }

    @Override
    public Course mapFrom(CourseDto courseDto) {
        return mapper.map(courseDto, Course.class);
    }
}
