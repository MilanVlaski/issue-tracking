package com.akimi.issue_tracking.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProblemSolverId implements Serializable {
    private static final long serialVersionUID = 4255960299841917033L;
    @Column(name = "ID_PRB", nullable = false)
    private Integer idPrb;

    @Column(name = "ID_INZ", nullable = false)
    private Integer idInz;

    public Integer getIdPrb() {
        return idPrb;
    }

    public ProblemSolverId setIdPrb(Integer idPrb) {
        this.idPrb = idPrb;
        return this;
    }

    public Integer getIdInz() {
        return idInz;
    }

    public ProblemSolverId setIdInz(Integer idInz) {
        this.idInz = idInz;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProblemSolverId entity = (ProblemSolverId) o;
        return Objects.equals(this.idPrb, entity.idPrb) &&
                Objects.equals(this.idInz, entity.idInz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPrb, idInz);
    }

}