package com.devesta.curricula.services.impl;

import com.devesta.curricula.domain.entities.Topic;
import com.devesta.curricula.repositories.TopicRepository;
import com.devesta.curricula.services.TopicService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {
    private final TopicRepository topicRepository;

    public TopicServiceImpl(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    // Not used - due to efficiency problems
    public List<Topic> getAllTopics() {
        List<Topic> list = new ArrayList<>();
        topicRepository.findAll().forEach(list::add);
        return list;
    }

    @Override
    public Page<Topic> getAllTopics(Pageable pageable) {
        return topicRepository.findAll(pageable);
    }

    public Topic getTopic(Long id) {
        return topicRepository.findById(id).isPresent() ?
                topicRepository.findById(id).get() :
                null;
    }

    public Topic addTopic(Topic topic) {
        return topicRepository.save(topic);
    }

    public Topic updateTopic(Topic updatedTopic, Long id) {
        return topicRepository.save(updatedTopic);
    }

    public void removeTopic(Long id) {
        topicRepository.deleteById(id);
    }

    @Override
    public boolean isExist(Long id) {
        return topicRepository.existsById(id);
    }

    @Override
    public Topic partialUpdate(Long id, Topic topic) {
        topic.setId(id);

        return topicRepository.findById(id).map(
                existing -> {
                    Optional.ofNullable(topic.getName()).ifPresent(existing::setName);
                    Optional.ofNullable(topic.getDescription()).ifPresent(existing::setDescription);
                    return topicRepository.save(existing);
                }
        ).orElseThrow(() -> new RuntimeException("topic not exist"));
    }

}
