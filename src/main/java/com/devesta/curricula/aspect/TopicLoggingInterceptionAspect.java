package com.devesta.curricula.aspect;

import com.devesta.curricula.domain.dto.TopicDto;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TopicLoggingInterceptionAspect {

    private static final Logger logger = LoggerFactory.getLogger(TopicLoggingInterceptionAspect.class);

    @Before("execution(* com.devesta.curricula.controllers.TopicController.getTopic(Long)) && args(id)")
    public void beforeGetTopic(JoinPoint joinPoint, Long id) {
        logger.info("Topic with id: "+id+" requested");
    }


    @AfterReturning(pointcut = "execution(* com.devesta.curricula.controllers.TopicController.getTopic(Long))",
            returning = "result")
    public void afterReturningGetTopic(JoinPoint joinPoint, ResponseEntity<TopicDto> result) {
        if (result.getStatusCode().is2xxSuccessful()) {
            logger.info("Topic found and returned with status code "+result.getStatusCode());
            logger.info("TopicDao returned: " + result.getBody());
        } else {
            logger.warn("GetTopic Return: "+result.getStatusCode());
        }
    }

    @Before("execution(* com.devesta.curricula.controllers.TopicController.addTopic(..)) && args(topicDao)")
    public void beforeAddTopic(JoinPoint joinPoint, TopicDto topicDto) {
        logger.info("Adding new topic: " + topicDto.toString());
    }

    @AfterReturning(pointcut = "execution(* com.devesta.curricula.controllers.TopicController.addTopic(..))",
            returning = "result")
    public void afterReturningAddTopic(JoinPoint joinPoint, ResponseEntity<TopicDto> result) {
        if (result.getStatusCode() == HttpStatus.CREATED) {
            logger.info("New topic added successfully with status code " + result.getStatusCode());
            logger.info("TopicDao returned: " + result.getBody());
        } else {
            logger.warn("Failed to add new topic with status code: " + result.getStatusCode());
        }
    }
}
