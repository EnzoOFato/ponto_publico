package com.fiap.ponto.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fiap.ponto.dto.input.QuestionInDto;
import com.fiap.ponto.dto.input.QuizinDto;
import com.fiap.ponto.dto.output.QuestionOutDto;
import com.fiap.ponto.dto.output.QuizOutDto;
import com.fiap.ponto.exception.PontoException;
import com.fiap.ponto.model.Lesson;
import com.fiap.ponto.model.Quiz;
import com.fiap.ponto.repository.QuizRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionService questionService;

    @Transactional
    public ResponseEntity<String> addQuiz(Lesson lesson, QuizinDto quizinDto) {
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

        if (quizinDto.questions() == null) throw new PontoException("Quiz must have questions");

        if (quizinDto.questions().size() < 3 || quizinDto.questions().size() > 5) {
            throw new PontoException("Quiz must have at least 4 questions and no more than 5");
        }

        verifyQuestions(quizinDto.questions());

        Quiz quiz = new Quiz();
        quiz.setDoubt(quizinDto.doubt());
        quiz.setLesson(lesson);

        quiz.setQuestions(questionService.questionsForQuiz(quiz, quizinDto.questions()));

        return quiz;
    }

    public QuizOutDto quizToDto(Quiz quiz) {
        // Pegar as questões para o Quiz
        Set<QuestionOutDto> questionsOut = quiz.getQuestions().stream().map(q -> questionService.questionToDto(q)).collect(Collectors.toSet());

        // Gerar o DTO do quiz
        QuizOutDto quizOutDto = new QuizOutDto(quiz.getId(), quiz.getDoubt(), questionsOut);

        return quizOutDto;
    }

    @Transactional
    public Set<QuizOutDto> getAll() {
        return quizRepository.findAll().stream().map(q -> quizToDto(q)).collect(Collectors.toSet());
    }

    @Transactional
    public ResponseEntity<String> deleteQuiz(Long quizId) {
        try {
            quizRepository.findById(quizId).orElseThrow(() -> new PontoException("Quiz not found"));

            quizRepository.deleteById(quizId);
            return ResponseEntity.ok("Quiz (%d) deleted".formatted(quizId));
        }
        catch (PontoException p) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(p.getMessage());
        }
    }

    private void verifyQuestions(Set<QuestionInDto> questions) {
        int counterTrue = 0;
        for (QuestionInDto q : questions) {
            if (Boolean.TRUE.equals(q.isCorrect())) counterTrue++;
        }

        if (counterTrue == 0) throw new PontoException("Quiz must have at least one correct question");

        if (counterTrue > 1) throw new PontoException("Quiz must have only one correct question");
    }
}
