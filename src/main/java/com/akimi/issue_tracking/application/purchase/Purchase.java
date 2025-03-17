package com.akimi.issue_tracking.application.purchase;

import com.akimi.issue_tracking.application.Application;
import com.akimi.issue_tracking.application.User;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_KUP", nullable = false)
    private Integer id;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_APP", nullable = false)
    private Application application;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_KOR", nullable = false)
    private User user;

    @ManyToOne(optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ID_POD", nullable = false)
    private SupportType supportType;

    public Purchase(User user, Application application, SupportType support) {
        this.user = user;
        this.application = application;
        this.supportType = support;

        user.getPurchases().add(this);
        application.getPurchases().add(this);
        support.getPurchases().add(this);
    }

    public Purchase() {

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

}