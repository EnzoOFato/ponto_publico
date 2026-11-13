package com.fiap.ponto.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name="lesson_ponto")
@Getter
@Setter
@NoArgsConstructor 
public class Lesson {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="lesson_id")
    private Long id;

    @Column(name="lesson_title")
    private String title;

    @Lob
    @Column(name="lesson_text")
    private String text;

    @ManyToMany (mappedBy="lessons")
    private Set<User> usuarios = new HashSet<>();

    @ManyToMany
    @JoinTable (
        name="lesson_tag",
        joinColumns=@JoinColumn (name="lesson_id"),
        inverseJoinColumns=@JoinColumn(name="tag_id")
    )
    private Set<Tag> tags = new HashSet<>();

    @OneToMany (mappedBy="lesson", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<Quiz> quizs = new HashSet<>();
}
