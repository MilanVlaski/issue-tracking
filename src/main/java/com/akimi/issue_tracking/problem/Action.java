package com.akimi.issue_tracking.problem;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "AKCIJA", indexes = {
        @Index(name = "UZROKUJU_FK", columnList = "ID_PRB")
})
public class Action {
    @Id
    @Column(name = "BR_AKC", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_PRB", nullable = false)
    private Problem problem;

    @Column(name = "OPIS_AKC", length = 200)
    private String description;

    public Action(int id, String description ) {
        this.id = id;
        this.description = description;
    }

    public Action() {

    }

    public Integer getId() {
        return id;
    }

    public Action setId(Integer id) {
        this.id = id;
        return this;
    }

    public Problem getProblem() {
        return problem;
    }

    public Action setProblem(Problem problem) {
        this.problem = problem;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Action setDescription(String description) {
        this.description = description;
        return this;
    }

}