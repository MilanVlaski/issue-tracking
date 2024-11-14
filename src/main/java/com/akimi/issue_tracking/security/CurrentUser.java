package com.akimi.issue_tracking.security;

import com.akimi.issue_tracking.application.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;

public class CurrentUser {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public User find() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return em.createQuery("select u from User u where u.email = :email", User.class)
                 .setParameter("email", email)
                 .getSingleResult();
    }
}
