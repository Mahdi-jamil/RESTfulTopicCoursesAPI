package com.devesta.demo.services;

import com.devesta.demo.domain.entities.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TopicService {
    List<Topic> getAllTopics();

    Page<Topic> getAllTopics(Pageable pageable);

    Topic getTopic(Long id);

    Topic addTopic(Topic topic);

    Topic updateTopic(Topic updatedTopic, Long id);

    void removeTopic(Long id);

    boolean isExist(Long id);

    Topic partialUpdate(Long id, Topic topic);
}
