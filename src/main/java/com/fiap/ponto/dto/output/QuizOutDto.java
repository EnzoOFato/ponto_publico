package com.fiap.ponto.dto.output;

import java.util.Set;

public record QuizOutDto(
    Long id, 
    String doubt,
    Set<QuestionOutDto> questionOutDtos
) {
    
}
