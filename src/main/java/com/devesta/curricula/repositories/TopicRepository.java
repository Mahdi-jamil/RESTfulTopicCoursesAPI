package com.devesta.curricula.repositories;

import com.devesta.curricula.domain.entities.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends CrudRepository<Topic,Long>,
        PagingAndSortingRepository<Topic,Long> {
}
