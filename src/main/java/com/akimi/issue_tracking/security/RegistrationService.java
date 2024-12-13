package com.akimi.issue_tracking.security;

import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.problem.engineer.Engineer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegistrationService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void register(User user) {
        // todo unique email
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        em.persist(user);
    }

    @Transactional
    public void register(Engineer engineer) {
        engineer.setPassword(passwordEncoder.encode(engineer.getPassword()));
        em.persist(engineer);
    }
}
