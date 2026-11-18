package com.fiap.ponto.controller;

import java.util.Set;

import javax.sound.midi.VoiceStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fiap.ponto.dto.input.LessonInDto;
import com.fiap.ponto.dto.input.QuizinDto;
import com.fiap.ponto.dto.output.LessonOutDto;
import com.fiap.ponto.service.LessonService;

@RestController
@RequestMapping ("/lesson")
public class LessonController {

    @Autowired
    private LessonService lessonService;

    @PostMapping ("/create")
    public ResponseEntity<String> createLesson(@RequestBody LessonInDto lessonInDto) {
        return lessonService.createLesson(lessonInDto);
    }

    @GetMapping("/getall")
    public Set<LessonOutDto> getAllLessons(){
        return lessonService.getAll();
    }

    @PutMapping("/{lessonId}/quizzes")
    public ResponseEntity<String> addQuiz(@PathVariable Long lessonId, @RequestBody QuizinDto quizinDto) {
        return lessonService.addQuizToLesson(lessonId, quizinDto);
    }
}
