package com.devesta.demo.repositories;

import com.devesta.demo.domain.entities.Topic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends CrudRepository<Topic,Long>,
        PagingAndSortingRepository<Topic,Long> {
}
