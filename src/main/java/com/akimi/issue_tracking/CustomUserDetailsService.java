package com.akimi.issue_tracking;

import com.akimi.issue_tracking.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = em.createQuery("select u from User u where u.name = :username", User.class)
                     .setParameter("username", username)
                     .getResultList()
                     .getFirst();
        if (user != null) {
            return org.springframework.security.core.userdetails.User
                    .withUsername(username)
                    .password(user.getPassword())
                    .roles("USER")
                    .build();
        } else {
            throw new UsernameNotFoundException(username);
        }
    }
}
