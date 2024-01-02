package com.devesta.demo.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Data
@Table(name = "course")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Course {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE , generator = "course_id_seq")
    private Long id;

    private String name;

    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    private Topic topic;

}
