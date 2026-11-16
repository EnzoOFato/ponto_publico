package com.fiap.ponto.dto.input;

import java.util.Set;

public record LessonInDto(
    String title,
    String text,
    Set<Long> tagIds
) {
    
}
