package com.devesta.curricula.controllers;

import com.devesta.curricula.domain.dto.TopicDto;
import com.devesta.curricula.domain.entities.Topic;
import com.devesta.curricula.mappers.Mapper;
import com.devesta.curricula.services.TopicService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@Validated
@RequestMapping("/api/v1/topics")
public class TopicController {

    private final TopicService topicService;

    private final Mapper<Topic, TopicDto> mapper;

    @Autowired
    public TopicController(TopicService topicService, Mapper<Topic, TopicDto> mapper) {
        this.topicService = topicService;
        this.mapper = mapper;

    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicDto> getTopic(@PathVariable Long id) {
        return Optional.ofNullable(topicService.getTopic(id))
                .map(topic -> new ResponseEntity<>(mapper.mapTo(topic), HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<TopicDto> addTopic(@RequestBody @Valid TopicDto topicDto) {
        Topic topic = mapper.mapFrom(topicDto);
        Topic savedTopic = topicService.addTopic(topic);
        return new ResponseEntity<>(mapper.mapTo(savedTopic), HttpStatus.CREATED);
    }

    @GetMapping
    public Page<TopicDto> getAllTopics(Pageable pageable) {
        Page<Topic> allTopics = topicService.getAllTopics(pageable);
        return allTopics.map(mapper::mapTo);
    }


    @PutMapping("/{id}")
    public ResponseEntity<TopicDto> updateTopic(@RequestBody @Valid TopicDto topicDto, @PathVariable Long id) {

        if (!topicService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        topicDto.setId(id);
        Topic topic = mapper.mapFrom(topicDto);
        Topic updated = topicService.updateTopic(topic, id);

        return new ResponseEntity<>(mapper.mapTo(updated), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeTopic(@PathVariable Long id) {
        topicService.removeTopic(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}")
    private ResponseEntity<TopicDto> partialUpdate(@RequestBody TopicDto topicDto, @PathVariable Long id) {

        if (!topicService.isExist(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Topic topic = mapper.mapFrom(topicDto);
        Topic updated = topicService.partialUpdate(id, topic);
        return new ResponseEntity<>(mapper.mapTo(updated), HttpStatus.OK);
    }
}
