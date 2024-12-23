package com.akimi.issue_tracking.problem;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.problem.engineer.Answer;
import com.akimi.issue_tracking.problem.engineer.Engineer;
import com.akimi.issue_tracking.problem.engineer.ProblemSolver;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "PROBLEM", indexes = {
        @Index(name = "PRIJAVLJUJE_FK", columnList = "ID_KOR"),
        @Index(name = "RELATIONSHIP_17_FK", columnList = "ID_APP")
})
public class Problem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_PRB", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_KOR", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_APP", nullable = false)
    private Application application;

    @Column(name = "STANJE", nullable = false, length = 20)
    private String state;
    // this is a hack to avoid a better solution
    @Transient
    private String engState;

    public String getEngState() {
        return ProblemState.fromDbName(state).engName;
    }

    @Column(name = "OPIS_PRB", length = 200)
    private String description;

    @OrderBy("ordinalNumber ASC")
    @OneToMany(mappedBy = "problem", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Action> actions = new LinkedHashSet<>();

    @OneToMany(mappedBy = "problem")
    private Set<Answer> answers = new LinkedHashSet<>();

    @ManyToMany(mappedBy = "problems")
    private Set<Engineer> engineers = new LinkedHashSet<>();

    @OneToMany(mappedBy = "problem")
    private Set<ProblemSolver> problemSolvers = new LinkedHashSet<>();

    /**
     * Creates a new problem, with the REPORTED state, on user request.
     * @param description
     * @param application
     * @param user
     * @param actions
     */
    public Problem(String description, Application application,
            User user, List<Action> actions) {
        setState(ProblemState.REPORTED);
        this.description = description;
        this.application = application;
        addActions(actions);
        application.getProblems().add(this);
        addUser(user);
    }

    public void setState(ProblemState problemState) {
        this.state = problemState.name;
        this.engState = problemState.engName;
    }

    private void addActions(List<Action> actions) {
        this.actions.addAll(actions);
        actions.forEach(a -> a.setProblem(this));
    }

    public Problem() {

    }

    public Integer getId() {
        return id;
    }

    public Problem setId(Integer id) {
        this.id = id;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Problem addUser(User user) {
        this.user = user;
        user.getProblems().add(this);
        return this;
    }

    public Application getApplication() {
        return application;
    }

    public Problem addApplication(Application application) {
        this.application = application;
        application.getProblems().add(this);
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Problem setDescription(String description) {
        this.description = description;
        return this;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public Problem setActions(Set<Action> actions) {
        this.actions = actions;
        return this;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public Problem setAnswers(Set<Answer> answers) {
        this.answers = answers;
        return this;
    }

    public Set<Engineer> getEngineers() {
        return engineers;
    }

    public Problem setEngineers(Set<Engineer> engineers) {
        this.engineers = engineers;
        return this;
    }


    public void add(Answer answer) {
        answers.add(answer);
        answer.setProblem(this);
    }

    public void assignEngineer(Engineer engineer) {
        engineers.add(engineer);
        engineer.getProblems().add(this);
    }

    public Set<ProblemSolver> getProblemSolvers() {
        return problemSolvers;
    }

    public Problem copy() {
        return new Problem(description, application, user, List.copyOf(getActions()));
    }
}