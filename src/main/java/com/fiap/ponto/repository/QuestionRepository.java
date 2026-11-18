package com.fiap.ponto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.ponto.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    
}
