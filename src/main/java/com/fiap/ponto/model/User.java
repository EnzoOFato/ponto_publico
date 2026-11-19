package com.fiap.ponto.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name="user_ponto")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long id;

    @Column(name="user_nickname")
    private String nickName;

    @Column(name="user_password")
    private String password;

    @Column(name="user_last_login")
    private LocalDateTime lastLogin;

    @ManyToMany
    @JoinTable (
        name="user_lesson",
        joinColumns= @JoinColumn(name="user_id"),
        inverseJoinColumns= @JoinColumn (name="lesson_id")
    )
    private Set<Lesson> lessons = new HashSet<>();

    public void addLesson(Lesson lesson) {
        this.lessons.add(lesson);
    }
}
