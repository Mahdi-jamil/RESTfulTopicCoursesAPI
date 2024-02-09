package com.devesta.curricula.domain.dao;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicDao {
    private Long id;

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    private String description;
}
