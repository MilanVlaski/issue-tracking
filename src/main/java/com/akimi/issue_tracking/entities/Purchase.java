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
    private PurchaseId purchaseId;

    @MapsId("idApp")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_APP", nullable = false)
    private Application application;

    @MapsId("idKor")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_KOR", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_POD", nullable = false)
    private SupportType supportType;

    public PurchaseId getPurchaseId() {
        return purchaseId;
    }

    public Purchase setPurchaseId(PurchaseId purchaseId) {
        this.purchaseId = purchaseId;
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