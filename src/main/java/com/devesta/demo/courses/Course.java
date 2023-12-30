package com.devesta.demo.courses;

import com.devesta.demo.topics.Topic;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "course")
public class Course {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
    @ManyToOne
    private Topic topic;

    public Course(String topicId, String id, String name, String description) {
        this.topic = new Topic(topicId,"","");
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Course() {
    }
}
