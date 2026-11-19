package com.fiap.ponto.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fiap.ponto.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long>{
    Optional<Tag> findByTagIgnoreCase(String tag);
}
