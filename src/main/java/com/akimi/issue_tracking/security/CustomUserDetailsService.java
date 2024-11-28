package com.akimi.issue_tracking.security;

import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.problem.engineer.Engineer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;
    static final String ENGINEER = "ENGINEER";
    static final String USER = "USER";

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // we use email instead!
        var user = em.createQuery("select u from User u where u.email = :username", User.class)
                     .setParameter("username", username)
                     .getResultStream()
                     .findFirst();
        if (user.isPresent()) {
            return userWithRole(username, user.get().getPassword(), USER);
        } else {
            var engineer = em.createQuery("select e from Engineer e where e.email = :username", Engineer.class)
                             .setParameter("username", username)
                             .getResultStream()
                             .findFirst();
            if (engineer.isPresent()) {
                return userWithRole(username, engineer.get().getPassword(), ENGINEER);
            } else {
                throw new UsernameNotFoundException(username);
            }
        }

    }

    private UserDetails userWithRole(String username, String password, String role) {
        return org.springframework.security.core.userdetails.User
                .withUsername(username)
                .password(password)
                .roles(role)
                .build();
    }

}
