package com.akimi.issue_tracking.problem.engineer;

import com.akimi.issue_tracking.problem.Problem;
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
    private ProblemSolverId id;

    @MapsId("idPrb")
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_PRB", nullable = false)
    private Problem problem;

    @MapsId("idInz")
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_INZ", nullable = false)
    private Engineer engineer;

    @OneToMany(mappedBy = "problemSolver")
    private Set<Patch> patches = new LinkedHashSet<>();

    public ProblemSolver(Engineer engineer, Problem problem, Patch patch) {
        this.id = new ProblemSolverId(problem, engineer);
        this.engineer = engineer;
        this.problem = problem;
        this.patches.add(patch);
        problem.getProblemSolvers().add(this);
        engineer.getProblemSolvers().add(this);
    }

    public ProblemSolver() {

    }

    public ProblemSolverId getId() {
        return id;
    }

    public Problem getProblem() {
        return problem;
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

}