package com.fiap.ponto.dto.output;

import java.util.Set;

import com.fiap.ponto.model.Tag;

public record LessonOutDto(
    Long id,
    String title,
    String text,
    Set<Tag> tags
) {
    
}
