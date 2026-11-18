package com.fiap.ponto.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fiap.ponto.dto.input.QuizinDto;
import com.fiap.ponto.dto.output.QuestionOutDto;
import com.fiap.ponto.dto.output.QuizOutDto;
import com.fiap.ponto.exception.PontoException;
import com.fiap.ponto.model.Lesson;
import com.fiap.ponto.model.Quiz;
import com.fiap.ponto.repository.QuizRepository;

import jakarta.transaction.Transactional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionService questionService;

    @Transactional
    protected ResponseEntity<String> addQuiz(Lesson lesson, QuizinDto quizinDto) {
        try {
            quizRepository.save(dtoToQuiz(quizinDto, lesson));
            return ResponseEntity.ok("Quiz added with succes to lesson: %s".formatted(lesson.getTitle()));
        }
        catch (PontoException p) {
            return ResponseEntity.badRequest().body(p.getMessage());
        }
    }

    private Quiz dtoToQuiz(QuizinDto quizinDto, Lesson lesson) {
        if (quizinDto.doubt() == null || quizinDto.doubt().isEmpty()) throw new PontoException("Doubt from the quiz is empty");

        if (quizinDto.questions().size() < 3) throw new PontoException("Quiz must have at least 4 questions");

        Quiz quiz = new Quiz();
        quiz.setDoubt(quizinDto.doubt());
        quiz.setQuestions(questionService.questionsForQuiz(quiz, quizinDto.questions()));
        quiz.setLesson(lesson);

        return quiz;
    }

    public QuizOutDto quizToDto(Quiz quiz) {
        Set<QuestionOutDto> questionsOut = quiz.getQuestions().stream().map(q -> questionService.questionToDto(q)).collect(Collectors.toSet());
        QuizOutDto quizOutDto = new QuizOutDto(quiz.getId(), quiz.getDoubt(), questionsOut);
        return quizOutDto;
    }

    public Set<QuizOutDto> getAll() {
        return quizRepository.findAll().stream().map(q -> quizToDto(q)).collect(Collectors.toSet());
    }
}
