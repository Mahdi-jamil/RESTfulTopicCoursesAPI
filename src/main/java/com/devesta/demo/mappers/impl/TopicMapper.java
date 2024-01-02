package com.devesta.demo.mappers.impl;

import com.devesta.demo.domain.dao.TopicDao;
import com.devesta.demo.domain.entities.Topic;
import com.devesta.demo.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper implements Mapper<Topic, TopicDao> {
    private final ModelMapper mapper;

    public TopicMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public TopicDao mapTo(Topic topic) {
        return mapper.map(topic, TopicDao.class);
    }

    @Override
    public Topic mapFrom(TopicDao topicDao) {
        return mapper.map(topicDao, Topic.class);
    }
}
