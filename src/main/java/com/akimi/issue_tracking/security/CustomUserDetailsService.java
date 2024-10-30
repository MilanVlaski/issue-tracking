package com.akimi.issue_tracking.security;

import com.akimi.issue_tracking.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @PersistenceContext
    private EntityManager em;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // we use email instead!
        var user = em.createQuery("select u from User u where u.email = :username", User.class)
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public static void main(String[] args) {
        System.out.println(new BCryptPasswordEncoder().encode("password"));
    }
}

