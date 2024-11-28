package com.akimi.issue_tracking.security;

import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.problem.engineer.Engineer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUser {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public User currentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return em.createQuery("select u from User u where u.email = :email", User.class)
                 .setParameter("email", email)
                 .getSingleResult();
    }

    @Transactional
    public Engineer currentEngineer() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return em.createQuery("select e from Engineer e where e.email = :email", Engineer.class)
                 .setParameter("email", email)
                 .getSingleResult();
    }
}
