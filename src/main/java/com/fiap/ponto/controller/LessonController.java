package com.fiap.ponto.controller;

import java.net.http.HttpClient;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @DeleteMapping("/delete/{lessonId}")
    public ResponseEntity<String> deleteLesson(@PathVariable Long lessonId) {
        return lessonService.deleteLesson(lessonId);
    }

    @PutMapping("/put_tag/lessonId={lessonid}")
    public ResponseEntity<String> putTagLesson(@RequestBody Set<Long> tagIds, @PathVariable Long lessonid) {
        return lessonService.updateLessonTag(tagIds, lessonid);
    }
}
