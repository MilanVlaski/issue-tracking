package com.akimi.issue_tracking;

import com.akimi.issue_tracking.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Controller
public class RegisterEndpoint {


    @PersistenceContext
    private EntityManager em;

    @GetMapping("/login")
    public String loginUser(Model model) {
        return "login";
    }

//    @PostMapping("/user/register")
//    public ResponseEntity<String> register(@RequestBody RegisterUser registerRequest) {
//        em.persist(registerRequest.toEntity());
//        return ResponseEntity.ok("");
//    }
//
//    @PostMapping("/user/login")
//    public ResponseEntity<String> login(@RequestBody LoginUser loginRequest) {
//        var user = em.createQuery("select u from User u" +
//                " where u.name = :username and u.password = :password", User.class)
//                .getResultList();
//
//        if(user.isEmpty()) {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//        } else {
//            return ResponseEntity.ok("");
//        }
//    }
//
//    @Data
//    class RegisterUser {
//        private String name;
//        private String email;
//        private String password;
//        private LocalDate birthYear;
//        private String location;
//
//        public User toEntity() {
//            return new User(name, email, password, birthYear, location);
//        }
//    }
//
//    @Data
//    class LoginUser {
//        private String email;
//        private String password;
//    }
}
