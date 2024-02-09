package com.devesta.curricula.mappers.impl;

import com.devesta.curricula.domain.dao.TopicDao;
import com.devesta.curricula.domain.entities.Topic;
import com.devesta.curricula.mappers.Mapper;
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
