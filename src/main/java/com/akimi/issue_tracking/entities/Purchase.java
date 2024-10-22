package com.akimi.issue_tracking.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "KUPOVINA", indexes = {
        @Index(name = "SE_KUPUJE_FK", columnList = "ID_APP"),
        @Index(name = "KUPUJE_FK", columnList = "ID_KOR"),
        @Index(name = "PODRSKA_FK", columnList = "ID_POD")
})
public class Purchase {
    @EmbeddedId
    private PurchaseId id;

    @MapsId("idApp")
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_APP", nullable = false)
    private Application application;

    @MapsId("idKor")
    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_KOR", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_POD", nullable = false)
    private SupportType supportType;

    public PurchaseId getId() {
        return id;
    }

    public Purchase setId(PurchaseId id) {
        this.id = id;
        return this;
    }

    public Application getApplication() {
        return application;
    }

    public Purchase setApplication(Application application) {
        this.application = application;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Purchase setUser(User user) {
        this.user = user;
        return this;
    }

    public SupportType getSupportType() {
        return supportType;
    }

    public Purchase setSupportType(SupportType supportType) {
        this.supportType = supportType;
        return this;
    }

}