package com.devesta.curricula.aspect;

import com.devesta.curricula.domain.dto.CourseDto;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.springframework.http.HttpStatus;

@Aspect
@Component
public class CourseLoggingInterceptionAspect {

    private static final Logger logger = LoggerFactory.getLogger(CourseLoggingInterceptionAspect.class);


    @Before("execution(* com.devesta.curricula.controllers.CourseController.getCourse(Long, Long)) && args(courseId, topicId)")
    public void beforeGetCourse(JoinPoint joinPoint, Long courseId, Long topicId) {
        logger.info("Course with id: " + courseId + " requested for topic with id: " + topicId);
    }

    @AfterReturning(pointcut = "execution(* com.devesta.curricula.controllers.CourseController.getCourse(Long, Long))",
            returning = "result")
    public void afterReturningGetCourse(JoinPoint joinPoint, ResponseEntity<CourseDto> result) {
        if (result.getStatusCode().is2xxSuccessful()) {
            logger.info("Course found and returned with status code " + result.getStatusCode());
            logger.info("CourseDao returned: " + result.getBody());
        } else {
            logger.warn("GetCourse Return: " + result.getStatusCode());
        }
    }

    @Before("execution(* com.devesta.curricula.controllers.CourseController.addCourse(..)) && args(courseDao, topicId)")
    public void beforeAddCourse(JoinPoint joinPoint, CourseDto courseDto, Long topicId) {
        logger.info("Adding new course: " + courseDto.toString() + " for topic with id: " + topicId);
    }

    @AfterReturning(pointcut = "execution(* com.devesta.curricula.controllers.CourseController.addCourse(..))",
            returning = "result")
    public void afterReturningAddCourse(JoinPoint joinPoint, ResponseEntity<CourseDto> result) {
        if (result.getStatusCode() == HttpStatus.CREATED) {
            logger.info("New course added successfully with status code " + result.getStatusCode());
            logger.info("CourseDao returned: " + result.getBody());
        } else {
            logger.warn("Failed to add new course with status code: " + result.getStatusCode());
        }
    }
}
