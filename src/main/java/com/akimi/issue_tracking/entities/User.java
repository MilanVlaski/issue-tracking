package com.akimi.issue_tracking.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "KORISNIK")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_KOR", nullable = false)
    private Integer id;

    @Column(name = "NAZIV_KOR", nullable = false, length = 50)
    private String name;

    @Column(name = "GODINA_RODJENJA")
    private LocalDate birthYear;

    @Column(name = "MEJL_ADRESA_KOR", length = 50)
    private String email;

    @Column(name = "SIFRA_KOR", length = 20)
    private String password;

    @Column(name = "BROJ_TELEFONA", length = 20)
    private String phoneNumber;

    @Column(name = "LOKACIIJA", length = 50)
    private String location;

    @OneToMany(mappedBy = "user")
    private Set<Purchase> purchases = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Problem> problems = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Patch> installedPatches = new LinkedHashSet<>();


    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public LocalDate getBirthYear() {
        return birthYear;
    }

    public User setBirthYear(LocalDate birthYear) {
        this.birthYear = birthYear;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public String getLocation() {
        return location;
    }

    public User setLocation(String location) {
        this.location = location;
        return this;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public User setPurchases(Set<Purchase> purchases) {
        this.purchases = purchases;
        return this;
    }

    public Set<Problem> getProblems() {
        return problems;
    }

    public User setProblems(Set<Problem> problems) {
        this.problems = problems;
        return this;
    }

    public Set<Patch> getInstalledPatches() {
        return installedPatches;
    }

    public User setInstalledPatches(Set<Patch> installedPatches) {
        this.installedPatches = installedPatches;
        return this;
    }

}