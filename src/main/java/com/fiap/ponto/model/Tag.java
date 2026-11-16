package com.fiap.ponto.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="tag_ponto")
@Getter 
@Setter 
@NoArgsConstructor 
public class Tag {
    @Id
    @GeneratedValue (strategy=GenerationType.IDENTITY)
    @Column (name="tag_id")
    private Long id;

    @Column (name="tag", unique=true)
    private String tag;

    @ManyToMany(mappedBy="tags")
    private Set<Lesson> lessons = new HashSet<>();
}
