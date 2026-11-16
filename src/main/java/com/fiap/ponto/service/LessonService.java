package com.fiap.ponto.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fiap.ponto.dto.input.LessonInDto;
import com.fiap.ponto.dto.output.LessonOutDto;
import com.fiap.ponto.exception.PontoException;
import com.fiap.ponto.model.Lesson;
import com.fiap.ponto.model.Tag;
import com.fiap.ponto.repository.LessonRepository;
import com.fiap.ponto.repository.TagRepository;

@Service 
public class LessonService {
    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private TagRepository tagRepository;

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
        lesson.setTags(resolveTags(lessonIn.tagIds()));

        return lesson;
    }

    private LessonOutDto lessonToOut(Lesson lesson) {
        LessonOutDto lessonOutDto = new LessonOutDto(lesson.getId(), lesson.getTitle(), lesson.getText(), lesson.getTags());
        return lessonOutDto;
    }

    private boolean verifyNull (LessonInDto lessonIn) {
        return lessonIn.title() == null || lessonIn.text() == null;
    }

    private Set<Tag> resolveTags(Set<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) return new HashSet<>();

        List<Tag> found = tagRepository.findAllById(tagIds);

        if (found.size() != tagIds.size()) {
            Set<Long> foundIds = found.stream().map(Tag::getId).collect(Collectors.toSet());
            Set<Long> missing = new HashSet<>(tagIds);
            missing.removeAll(foundIds);
            throw new PontoException("Tags não encontradas: " + missing);
        }

        return new HashSet<>(found);
    }
}
