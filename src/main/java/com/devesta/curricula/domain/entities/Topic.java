package com.devesta.curricula.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "topic")
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "topic_id_seq")
    private Long id;

    private String name;

    private String description;
}
