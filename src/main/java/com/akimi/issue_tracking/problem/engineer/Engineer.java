package com.akimi.issue_tracking.problem.engineer;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.problem.Problem;
import com.akimi.issue_tracking.problem.ProblemState;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "INZENJER")
public class Engineer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_INZ", nullable = false)
    private Integer id;

    @Column(name = "NAZIV_INZ", length = 50)
    private String name;

    @Column(name = "OBRAZOVANJE", length = 50)
    private String education;

    @Column(name = "ZAPOSLEN_OD")
    private LocalDate employedFrom;

    @Column(name = "ZAPOSLEN_DO")
    private LocalDate employedUntil;

    @Column(name = "MJESECNA_PLATA", precision = 10, scale = 2)
    private BigDecimal monthlySalary;

    @Column(name = "MEJL_ADRESA_INZ", length = 50)
    private String email;

    @Column(name = "SIFRA_INZ", length = 60)
    private String password;

    @OneToMany(mappedBy = "engineer")
    private Set<Answer> answers = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "RJESAVALAC",
            joinColumns = @JoinColumn(name = "ID_INZ"),
            inverseJoinColumns = @JoinColumn(name = "ID_PRB"))
    private Set<Problem> problems = new LinkedHashSet<>();

    @OneToMany(mappedBy = "engineer")
    private Set<ProblemSolver> problemSolvers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "helperEngineer")
    private Set<Patch> patches = new LinkedHashSet<>();

    public Engineer(String name, String education, LocalDate employedFrom,
            double monthlySalary, String email, String password) {
        this.name = name;
        this.education = education;
        this.employedFrom = employedFrom;
        this.monthlySalary = new BigDecimal(monthlySalary);
        this.email = email;
        this.password = password;
    }

    public Engineer() {

    }

    public Integer getId() {
        return id;
    }

    public Engineer setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Engineer setName(String name) {
        this.name = name;
        return this;
    }

    public String getEducation() {
        return education;
    }

    public LocalDate getEmployedFrom() {
        return employedFrom;
    }

    public LocalDate getEmployedUntil() {
        return employedUntil;
    }

    public BigDecimal getMonthlySalary() {
        return monthlySalary;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public Set<Problem> getProblems() {
        return problems;
    }

    public Set<Patch> getPatches() {
        return patches;
    }

    public void add(Answer answer) {
        answers.add(answer);
        answer.setEngineer(this);
    }

    public Application patchProblem(Patch patch, Problem problem) {
        if (notAssignedTo(problem)) {
            throw new IllegalStateException("The engineer MUST be working on a problem in order to patch it!");
        }
        problem.setState(ProblemState.SOLVED);

        Application problematicApp = problem.getApplication();
        var user = problem.getUser();

        patch.setUser(user)
             .setPublishDate(LocalDate.now())
             .setProblemSolver(new ProblemSolver(this, problem, patch));

        return problematicApp.copyWithIncrementedVersion();
    }

    private boolean notAssignedTo(Problem problem) {
        return !(problem.getEngineers().contains(this));
    }

    public Set<ProblemSolver> getProblemSolvers() {
        return problemSolvers;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}