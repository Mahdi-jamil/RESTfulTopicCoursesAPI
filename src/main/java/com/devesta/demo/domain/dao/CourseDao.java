package com.devesta.demo.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDao {
    private Long id;

    private String name;

    private String description;

    private TopicDao topic;

}
