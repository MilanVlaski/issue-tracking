package com.akimi.issue_tracking.security;

import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.problem.engineer.Engineer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
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
        var query = em.createQuery("select u from User u where u.email = :email", User.class);
        return userWithEmail(query, email);
    }

    @Transactional
    public Engineer currentEngineer() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        var query = em.createQuery("select e from Engineer e where e.email = :email", Engineer.class);
        return userWithEmail(query, email);
    }

    private <T> T userWithEmail(TypedQuery<T> query, String email) {
        return query
                .setParameter("email", email)
                .getSingleResult();
    }
}
