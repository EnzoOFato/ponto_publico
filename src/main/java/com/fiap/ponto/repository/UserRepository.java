package com.fiap.ponto.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.ponto.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
