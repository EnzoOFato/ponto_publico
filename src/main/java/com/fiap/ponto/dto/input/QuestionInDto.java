package com.fiap.ponto.dto.input;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record QuestionInDto(
    String text,
    Boolean isCorrect
) {

}