package com.akimi.issue_tracking.application;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import com.akimi.issue_tracking.application.purchase.Purchase;
import com.akimi.issue_tracking.application.purchase.SupportType;
import com.akimi.issue_tracking.problem.Problem;
import com.akimi.issue_tracking.problem.dto.ProblemReport;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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

    @Column(name = "SIFRA_KOR", length = 60)
    private String password;

    @Column(name = "BROJ_TELEFONA", length = 20)
    private String phoneNumber;

    @Column(name = "LOKACIJA", length = 50)
    private String location;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @OneToMany(mappedBy = "user")
    private Set<Purchase> purchases = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<Problem> problems = new LinkedHashSet<>();

//    @OneToMany(mappedBy = "user")
//    private Set<Patch> installedPatches = new LinkedHashSet<>();

    public User(String name, String email, String password, LocalDate birthYear,
            String location, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.birthYear = birthYear;
        this.location = location;
        this.phoneNumber = phoneNumber;
    }

    public User() {

    }


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

    public String getLocation() {
        return location;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    public Set<Problem> getProblems() {
        return problems;
    }

//    public Set<Patch> getInstalledPatches() {
//        return installedPatches;
//    }

    public boolean ownsApplication(Application app) {
        return purchases.stream()
                        .anyMatch(purchase -> purchase
                                .getApplication().equals(app));
    }

    public Purchase purchase(Application app, SupportType support) {
	return new Purchase(this, app, support);
    }

    public Problem reportProblemWithApp(ProblemReport problemReport, Application app) {
	return new Problem(problemReport, app, this);
    }
}