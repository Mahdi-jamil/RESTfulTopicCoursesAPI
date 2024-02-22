package com.devesta.curricula.mappers.impl;

import com.devesta.curricula.domain.dto.TopicDto;
import com.devesta.curricula.domain.entities.Topic;
import com.devesta.curricula.mappers.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class TopicMapper implements Mapper<Topic, TopicDto> {
    private final ModelMapper mapper;

    public TopicMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public TopicDto mapTo(Topic topic) {
        return mapper.map(topic, TopicDto.class);
    }

    @Override
    public Topic mapFrom(TopicDto topicDto) {
        return mapper.map(topicDto, Topic.class);
    }
}
