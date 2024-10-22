package com.akimi.issue_tracking.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "ODGOVOR", indexes = {
        @Index(name = "DAJE_FK", columnList = "ID_INZ"),
        @Index(name = "NA_PROBLEM_FK", columnList = "ID_PRB")
})
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ODG", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_INZ", nullable = false)
    private Engineer engineer;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_PRB", nullable = false)
    private Problem problem;

    @Column(name = "OPIS_ODG", length = 50)
    private String description;

    public Integer getId() {
        return id;
    }

    public Answer setId(Integer id) {
        this.id = id;
        return this;
    }

    public Engineer getEngineer() {
        return engineer;
    }

    public Answer setEngineer(Engineer engineer) {
        this.engineer = engineer;
        return this;
    }

    public Problem getProblem() {
        return problem;
    }

    public Answer setProblem(Problem problem) {
        this.problem = problem;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Answer setDescription(String description) {
        this.description = description;
        return this;
    }

}