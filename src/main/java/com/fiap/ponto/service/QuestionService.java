package com.fiap.ponto.service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fiap.ponto.dto.input.QuestionInDto;
import com.fiap.ponto.dto.output.QuestionOutDto;
import com.fiap.ponto.exception.PontoException;
import com.fiap.ponto.model.Question;
import com.fiap.ponto.model.Quiz;
import com.fiap.ponto.repository.QuestionRepository;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    public Set<Question> questionsForQuiz(Quiz quiz, Set<QuestionInDto> questionInDtos) {
        return questionInDtos.stream()
        .map(q -> {
            verifyNull(q);
            Question question = new Question();
            question.setIsCorrect(q.isCorrect());
            question.setText(q.text());
            question.setQuiz(quiz);
            return question;
        }
        )
        .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private void verifyNull(QuestionInDto questionInDto) {
        if (questionInDto.text() == null || questionInDto.text().isEmpty()) throw new PontoException("Question without text");

        if (questionInDto.isCorrect() == null) throw new PontoException("Must be true or false");
    }

    public QuestionOutDto questionToDto(Question question) {
        return new QuestionOutDto(question.getId(), question.getText(), question.getIsCorrect());
    }
}
