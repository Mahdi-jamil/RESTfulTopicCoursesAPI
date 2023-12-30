package com.devesta.demo.topics;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service

public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    public List<Topic> getAllTopics() {
        List<Topic> list = new ArrayList<>();
        topicRepository.findAll().forEach(list::add);
        return list;
    }


    public Topic getTopic(String id) {
        AtomicReference<Topic> returnTopic = new AtomicReference<>();
        topicRepository
                .findById(id)
                .ifPresentOrElse(returnTopic::set, ()-> System.out.println("404"));
        return returnTopic.get();
    }

    public void addTopic(Topic topic) {
        topicRepository.save(topic);
    }


    public void updateTopic(Topic updatedTopic, String id) {
        topicRepository.save(updatedTopic);
    }

    public void removeTopic(String id) {
        topicRepository.deleteById(id);
    }
}
