package com.akimi.issue_tracking.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "ZAKRPA", indexes = {
        @Index(name = "RELATIONSHIP_18_FK", columnList = "ID_PRB, ID_INZ"),
        @Index(name = "NA_PROBLEMU_FK", columnList = "ID_PRB"),
        @Index(name = "PRAVI_FK", columnList = "ID_INZ"),
        @Index(name = "INSTALIRA_FK", columnList = "ID_KOR")
})
public class Patch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_KRP", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    private ProblemSolver problemSolver;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_KOR", nullable = false)
    private User user;

    @Column(name = "VELICINA_KB", precision = 10)
    private BigDecimal sizeInKb;

    @Column(name = "DATUM_OBJAVE")
    private LocalDate datePublished;

    @Column(name = "VRSTA_KOMUNIKACIJE", length = 200)
    private String typeOfCommunication;

    public Integer getId() {
        return id;
    }

    public Patch setId(Integer id) {
        this.id = id;
        return this;
    }

    public ProblemSolver getProblemSolver() {
        return problemSolver;
    }

    public Patch setProblemSolver(ProblemSolver problemSolver) {
        this.problemSolver = problemSolver;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Patch setUser(User user) {
        this.user = user;
        return this;
    }

    public BigDecimal getSizeInKb() {
        return sizeInKb;
    }

    public Patch setSizeInKb(BigDecimal sizeInKb) {
        this.sizeInKb = sizeInKb;
        return this;
    }

    public LocalDate getDatePublished() {
        return datePublished;
    }

    public Patch setDatePublished(LocalDate datePublished) {
        this.datePublished = datePublished;
        return this;
    }

    public String getTypeOfCommunication() {
        return typeOfCommunication;
    }

    public Patch setTypeOfCommunication(String typeOfCommunication) {
        this.typeOfCommunication = typeOfCommunication;
        return this;
    }

}