package com.fiap.ponto.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name="quiz_ponto")
@Getter
@Setter
@NoArgsConstructor 
public class Quiz {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name="quiz_id")
    private Long id;

    @Column(name="quiz_doubt")
    private String doubt;

    @ManyToOne (optional=false, fetch=FetchType.LAZY)
    private Lesson lesson;

    @OneToMany(mappedBy="quiz", cascade=CascadeType.ALL, orphanRemoval=true)
    private Set<Question> questions = new HashSet<>();
}
