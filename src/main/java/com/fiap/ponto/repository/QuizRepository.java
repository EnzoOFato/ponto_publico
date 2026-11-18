package com.fiap.ponto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.ponto.model.Quiz;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    
}
