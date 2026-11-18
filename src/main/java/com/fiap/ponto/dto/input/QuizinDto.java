package com.fiap.ponto.dto.input;

import java.util.Set;

public record QuizinDto(
    String doubt,
    Set<QuestionInDto> questions
) {
}
