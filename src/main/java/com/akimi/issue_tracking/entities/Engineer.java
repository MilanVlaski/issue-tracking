package com.akimi.issue_tracking.entities;

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

    @Column(name = "SIFRA_INZ", length = 30)
    private String password;

    @OneToMany(mappedBy = "engineer")
    private Set<Answer> answers = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "RJESAVALAC",
            joinColumns = @JoinColumn(name = "ID_INZ"),
            inverseJoinColumns = @JoinColumn(name = "ID_PRB"))
    private Set<Problem> problems = new LinkedHashSet<>();

    @OneToMany(mappedBy = "helperEngineer")
    private Set<Patch> patches = new LinkedHashSet<>();

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

    public Engineer setEducation(String education) {
        this.education = education;
        return this;
    }

    public LocalDate getEmployedFrom() {
        return employedFrom;
    }

    public Engineer setEmployedFrom(LocalDate employedFrom) {
        this.employedFrom = employedFrom;
        return this;
    }

    public LocalDate getEmployedUntil() {
        return employedUntil;
    }

    public Engineer setEmployedUntil(LocalDate employedUntil) {
        this.employedUntil = employedUntil;
        return this;
    }

    public BigDecimal getMonthlySalary() {
        return monthlySalary;
    }

    public Engineer setMonthlySalary(BigDecimal monthlySalary) {
        this.monthlySalary = monthlySalary;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Engineer setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Engineer setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public Engineer setAnswers(Set<Answer> answers) {
        this.answers = answers;
        return this;
    }

    public Set<Problem> getProblems() {
        return problems;
    }

    public Engineer setProblems(Set<Problem> problems) {
        this.problems = problems;
        return this;
    }

    public Set<Patch> getPatches() {
        return patches;
    }

    public Engineer setPatches(Set<Patch> patches) {
        this.patches = patches;
        return this;
    }

}