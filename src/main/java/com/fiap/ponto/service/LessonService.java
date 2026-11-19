package com.fiap.ponto.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fiap.ponto.dto.input.LessonInDto;
import com.fiap.ponto.dto.input.QuizinDto;
import com.fiap.ponto.dto.output.LessonOutDto;
import com.fiap.ponto.dto.output.QuizOutDto;
import com.fiap.ponto.dto.output.TagOutDto;
import com.fiap.ponto.exception.PontoException;
import com.fiap.ponto.model.Lesson;
import com.fiap.ponto.model.Tag;
import com.fiap.ponto.repository.LessonRepository;

@Service 
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private QuizService quizService;

    @Transactional
    public ResponseEntity<String> createLesson(LessonInDto lessonIn) {
        try {
            lessonRepository.save(inToLesson(lessonIn));
            return ResponseEntity.ok("Lesson created with success");
        }
        catch (PontoException e) {
            return ResponseEntity.badRequest().body("ERRO during request\nMessage: " + e.getMessage());
        }
    }

    public Set<LessonOutDto> getAll() {
        return lessonRepository.findAll().stream().map(l -> lessonToOut(l)).collect(Collectors.toSet());
    }

    private Lesson inToLesson (LessonInDto lessonIn) {

        if (verifyNull(lessonIn)) throw new PontoException("Null field not available");

        Lesson lesson = new Lesson();
        lesson.setTitle(lessonIn.title());
        lesson.setText(lessonIn.text());
        lesson.setTags(tagService.resolveTags(lessonIn.tagIds()));

        return lesson;
    }

    private LessonOutDto lessonToOut(Lesson lesson) {
        Set<TagOutDto> tagsOut = lesson.getTags().stream().map(t -> tagService.tagToDto(t)).collect(Collectors.toSet());
        Set<QuizOutDto> quizesOut = lesson.getQuizs().stream().map(q -> quizService.quizToDto(q)).collect(Collectors.toSet());
        LessonOutDto lessonOutDto = new LessonOutDto(lesson.getId(), lesson.getTitle(), lesson.getText(), tagsOut, quizesOut);
        return lessonOutDto;
    }

    private boolean verifyNull (LessonInDto lessonIn) {
        return lessonIn.title() == null || lessonIn.text() == null || lessonIn.title().isEmpty() || lessonIn.text().isEmpty();
    }

    @Transactional
    public ResponseEntity<String> addQuizToLesson(Long lessonId, QuizinDto quizinDto) {
        try {
            if (lessonRepository.findById(lessonId).isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Lesson found with %d id".formatted(lessonId));
            }
            Lesson lesson = lessonRepository.findById(lessonId).get();

            return quizService.addQuiz(lesson, quizinDto);
        }
        catch (PontoException p) {
            return ResponseEntity.badRequest().body(p.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<String> deleteLesson(Long lessonId) {
        try {
            lessonRepository.findById(lessonId).orElseThrow(() -> new PontoException("Lesson not found"));

            lessonRepository.deleteById(lessonId);
            return ResponseEntity.ok("Deleted lesson with id " + lessonId);
        }
        catch (PontoException p) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(p.getMessage());
        }
    }

    @Transactional
    public ResponseEntity<String> updateLessonTag(Set<Long> tagids, Long lessonid) {
        try {
            Lesson lesson = lessonRepository.findById(lessonid)
            .orElseThrow(() -> new PontoException("ERRO, Lesson could not be found"));
            Set<Tag> tagsForLesson = tagService.getAllByIds(tagids);

            lesson.getTags().clear();
            lesson.setTags(tagsForLesson);

            return ResponseEntity.ok("Tags Added to lesson: %s".formatted(lesson.getTitle()));
        }
        catch (PontoException p) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(p.getMessage());
        }
    }
}
