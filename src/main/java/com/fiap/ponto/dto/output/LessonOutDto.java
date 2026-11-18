package com.fiap.ponto.dto.output;

import java.util.Set;
public record LessonOutDto(
    Long id,
    String title,
    String text,
    Set<TagOutDto> tags,
    Set<QuizOutDto> quizs
) {
    
}
