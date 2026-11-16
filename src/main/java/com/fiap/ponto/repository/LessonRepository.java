package com.fiap.ponto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.ponto.model.Lesson;

public interface LessonRepository extends JpaRepository<Lesson, Long>{
    
}
