package com.fiap.ponto.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table (name="question_ponto")
@Getter 
@Setter 
@NoArgsConstructor 
public class Question {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column (name="question_id")
    private Long id;

    @Column (name="question_correct")
    private Boolean isCorrect;

    @ManyToOne (optional=false, fetch=FetchType.LAZY)
    private Quiz quiz;
}
