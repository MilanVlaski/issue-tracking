package com.akimi.issue_tracking.application.purchase;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class PurchaseId implements Serializable {
    private static final long serialVersionUID = 3200987778489601282L;
    @Column(name = "ID_APP", nullable = false)
    private Integer idApp;

    @Column(name = "ID_KOR", nullable = false)
    private Integer idKor;

    public Integer getIdApp() {
        return idApp;
    }

    public PurchaseId setIdApp(Integer idApp) {
        this.idApp = idApp;
        return this;
    }

    public Integer getIdKor() {
        return idKor;
    }

    public PurchaseId setIdKor(Integer idKor) {
        this.idKor = idKor;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PurchaseId entity = (PurchaseId) o;
        return Objects.equals(this.idApp, entity.idApp) &&
                Objects.equals(this.idKor, entity.idKor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idApp, idKor);
    }

}