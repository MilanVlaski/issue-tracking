package com.akimi.issue_tracking.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "ZAKRPA", indexes = {
        @Index(name = "RELATIONSHIP_18_FK", columnList = "ID_PRB, ID_INZ_RJESAVAOCA"),
        @Index(name = "INSTALIRA_FK", columnList = "ID_KOR"),
        @Index(name = "PRAVI_FK", columnList = "ID_INZ_KRPIOCA")
})
public class Patch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_KRP", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "ID_PRB", referencedColumnName = "ID_PRB"),
            @JoinColumn(name = "ID_INZ_RJESAVAOCA", referencedColumnName = "ID_INZ")
    })
    private ProblemSolver problemSolver;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_KOR", nullable = false)
    private User user;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_INZ_KRPIOCA")
    private Engineer helperEngineer;

    @Column(name = "VELICINA_KB", precision = 10)
    private BigDecimal sizeKb;

    @Column(name = "DATUM_OBJAVE")
    private LocalDate publishDate;

    @Column(name = "VRSTA_KOMUNIKACIJE", length = 200)
    private String communicationType;

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

    public Engineer getHelperEngineer() {
        return helperEngineer;
    }

    public Patch setHelperEngineer(Engineer helperEngineer) {
        this.helperEngineer = helperEngineer;
        return this;
    }

    public BigDecimal getSizeKb() {
        return sizeKb;
    }

    public Patch setSizeKb(BigDecimal sizeKb) {
        this.sizeKb = sizeKb;
        return this;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public Patch setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
        return this;
    }

    public String getCommunicationType() {
        return communicationType;
    }

    public Patch setCommunicationType(String communicationType) {
        this.communicationType = communicationType;
        return this;
    }

}