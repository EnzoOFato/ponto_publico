package com.fiap.ponto.dto.output;

public record QuestionOutDto(
    Long id,
    String text,
    Boolean isCorrect
) {
    
}
