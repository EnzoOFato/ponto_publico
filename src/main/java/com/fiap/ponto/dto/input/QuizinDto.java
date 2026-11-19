package com.fiap.ponto.dto.input;

import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record QuizinDto(
    String doubt,
    Set<QuestionInDto> questions
) {
}
