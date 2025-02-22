package com.akimi.issue_tracking.application.purchase;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "VRSTA_PODRSKE")
public class SupportType {
    @Id
    @Column(name = "ID_POD", nullable = false, length = 10)
    private String id;

    @Column(name = "NAZIV_POD", nullable = false, length = 50)
    private String name;

    @Column(name = "OPIS_POD", length = 100)
    private String description;

    @Column(name = "CIJENA", precision = 10, scale = 2)
    private BigDecimal price;

    @OneToMany(mappedBy = "supportType")
    private Set<Purchase> purchases = new LinkedHashSet<>();

    public SupportType(String id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public SupportType() {

    }

    public String getId() {
        return id;
    }

    public SupportType setId(String idPod) {
        this.id = idPod;
        return this;
    }

    public String getName() {
        return name;
    }

    public SupportType setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public SupportType setDescription(String description) {
        this.description = description;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Set<Purchase> getPurchases() {
        return purchases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SupportType that = (SupportType) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(name, that.name) &&
                Objects.equals(description, that.description) &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price);
    }
}
