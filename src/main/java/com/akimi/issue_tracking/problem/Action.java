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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BR_AKC", nullable = false)
    private Integer id;

    @Column(name= "RED_BR_AKC", nullable = false)
    private Integer ordinalNumber;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_PRB", nullable = false)
    private Problem problem;

    @Column(name = "OPIS_AKC", length = 200)
    private String description;

    public Action(int ordinalNumber, String description ) {
        this.ordinalNumber = ordinalNumber;
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

    public Integer getOrdinalNumber() {
        return ordinalNumber;
    }
}