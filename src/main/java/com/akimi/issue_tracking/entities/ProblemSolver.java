package com.akimi.issue_tracking.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "RJESAVALAC", indexes = {
        @Index(name = "RELATIONSHIP_20_FK", columnList = "ID_PRB"),
        @Index(name = "RELATIONSHIP_19_FK", columnList = "ID_INZ")
})
public class ProblemSolver {
    @EmbeddedId
    private ProblemSolverId problemSolverId;

    @MapsId("idPrb")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_PRB", nullable = false)
    private Problem problem;

    @MapsId("idInz")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_INZ", nullable = false)
    private Engineer engineer;

    @OneToMany(mappedBy = "problemSolver")
    private Set<Patch> patches = new LinkedHashSet<>();

    public ProblemSolverId getProblemSolverId() {
        return problemSolverId;
    }

    public ProblemSolver setProblemSolverId(ProblemSolverId problemSolverId) {
        this.problemSolverId = problemSolverId;
        return this;
    }

    public Problem getProblem() {
        return problem;
    }

    public ProblemSolver setProblem(Problem problem) {
        this.problem = problem;
        return this;
    }

    public Engineer getEngineer() {
        return engineer;
    }

    public ProblemSolver setEngineer(Engineer engineer) {
        this.engineer = engineer;
        return this;
    }

    public Set<Patch> getPatches() {
        return patches;
    }

    public ProblemSolver setPatches(Set<Patch> patches) {
        this.patches = patches;
        return this;
    }

}