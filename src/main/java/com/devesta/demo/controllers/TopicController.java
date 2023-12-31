package com.devesta.demo.controllers;

import com.devesta.demo.domain.dao.TopicDao;
import com.devesta.demo.domain.entities.Topic;
import com.devesta.demo.mappers.Mapper;
import com.devesta.demo.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/topics")
public class TopicController {

    private final TopicService topicService;

    private final Mapper<Topic, TopicDao> mapper;

    @Autowired
    public TopicController(TopicService topicService, Mapper<Topic, TopicDao> mapper) {
        this.topicService = topicService;
        this.mapper = mapper;
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDao> getTopic(@PathVariable Long id) {
        Optional<Topic> topicFounded = Optional.ofNullable(topicService.getTopic(id));
        return topicFounded.map(topic ->
                        new ResponseEntity<>(mapper.mapTo(topic), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<TopicDao> addTopic(@RequestBody TopicDao topicDao) {
        Topic topic = mapper.mapFrom(topicDao);
        Topic savedTopic = topicService.addTopic(topic);
        return new ResponseEntity<>(mapper.mapTo(savedTopic), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<TopicDao> getAllTopics(Pageable pageable) {
        Page<Topic> allTopics = topicService.getAllTopics(pageable);
        return allTopics.map(mapper::mapTo);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TopicDao> updateTopic(@RequestBody TopicDao topicDao, @PathVariable Long id) {

        if (!topicService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        topicDao.setId(id);
        Topic topic = mapper.mapFrom(topicDao);
        Topic updated = topicService.updateTopic(topic, id);

        return new ResponseEntity<>(mapper.mapTo(updated), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeTopic(@PathVariable Long id) {
        topicService.removeTopic(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    private ResponseEntity<TopicDao> partialUpdate(@RequestBody TopicDao topicDao, @PathVariable Long id) {

        if (!topicService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Topic topic = mapper.mapFrom(topicDao);
        Topic updated = topicService.partialUpdate(id, topic);
        return new ResponseEntity<>(mapper.mapTo(updated), HttpStatus.OK);
    }
}
