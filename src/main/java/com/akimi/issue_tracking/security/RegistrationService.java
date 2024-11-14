package com.akimi.issue_tracking.security;

import com.akimi.issue_tracking.application.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        log.info("Registering user: " + user.toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        em.persist(user);
    }

    Logger log = LoggerFactory.getLogger(RegistrationService.class);
}
