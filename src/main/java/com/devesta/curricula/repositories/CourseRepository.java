package com.devesta.curricula.repositories;

import com.devesta.curricula.domain.entities.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long>,
        PagingAndSortingRepository<Course, Long> {
//    List<Course> findAllByTopicId(Long tid);

    Page<Course> findAllByTopicId(Pageable pageable, Long tid);

    Course findByIdAndTopicId(Long courseId, Long topicId);
}
