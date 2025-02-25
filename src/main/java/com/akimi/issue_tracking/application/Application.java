package com.akimi.issue_tracking.application;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

import com.akimi.issue_tracking.application.purchase.Purchase;
import com.akimi.issue_tracking.problem.Problem;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "APLIKACIJA")
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_APP", nullable = false)
    private Integer id;

    @Column(name = "NAZIV_APP", nullable = false, length = 50)
    private String name;

    @Convert(converter = VersionConverter.class)
    @Column(name = "VERZIJA", nullable = false, length = 20)
    private Version version;

    @Column(name = "OPIS", length = 100)
    private String description;

    @Column(name = "GODINA_IZDAVANJA")
    private LocalDate releaseYear;

    @Column(name = "LOGO_URL", length = 500)
    private String logoUrl;

    @OneToMany(mappedBy = "application", cascade = CascadeType.PERSIST)
    private Set<Purchase> purchases = new LinkedHashSet<>();

    @OneToMany(mappedBy = "application")
    private Set<Problem> problems = new LinkedHashSet<>();

    public Application(String name, String version, String description, LocalDate releaseYear, String logoUrl) {
	this.name = name;
	this.version = Version.fromString(version);
	this.description = description;
	this.releaseYear = releaseYear;
	this.logoUrl = logoUrl;
    }

    public Application(String name, Version version, String description, LocalDate releaseYear, String logoUrl) {
	this.name = name;
	this.version = version;
	this.description = description;
	this.releaseYear = releaseYear;
	this.logoUrl = logoUrl;
    }

    public Application() {

    }

    public Application(int id, String name, String version) {
	this.id = id;
	this.name = name;
	this.version = Version.fromString(version);
    }

    public Integer getId() {
	return id;
    }

    public Application setId(Integer id) {
	this.id = id;
	return this;
    }

    public String getName() {
	return name;
    }

    public Application setName(String name) {
	this.name = name;
	return this;
    }

    public Version getVersion() {
	return version;
    }

    public Application setVersion(String version) {
	this.version = Version.fromString(version);
	return this;
    }

    public String getDescription() {
	return description;
    }

    public Application setDescription(String description) {
	this.description = description;
	return this;
    }

    public LocalDate getReleaseYear() {
	return releaseYear;
    }

    public Set<Purchase> getPurchases() {
	return purchases;
    }

    public Set<Problem> getProblems() {
	return problems;
    }

    public Application setProblems(Set<Problem> problems) {
	this.problems = problems;
	return this;
    }

    public String getLogoUrl() {
	return logoUrl;
    }

    public Application copyWithIncrementedVersion() {
	return new Application(this.name, incrementedVersion(), this.description, this.releaseYear, this.logoUrl);
    }

    private Version incrementedVersion() {
	return new Version(this.version.getMajor(), this.version.getMinor(), this.version.getPatch() + 1);
    }

    public boolean equalsExceptVersion(Application other) {
	if (this == other) {
	    return true;
	}

	if (other == null) {
	    return false;
	}

	return this.name.equals(other.name) && this.description.equals(other.description)
		&& this.releaseYear.equals(other.releaseYear) && this.logoUrl.equals(other.logoUrl);
    }

    public boolean isOwnedBy(User user) {
	return user.ownsApplication(this);
    }

    public String getMajorVersion() {
	return String.valueOf(version.getMajor());
    }

}