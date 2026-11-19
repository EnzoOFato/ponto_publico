package com.fiap.ponto.dto.input;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public record LessonInDto(
    String title,
    String text,
    Set<Long> tagIds
) {
    
}
