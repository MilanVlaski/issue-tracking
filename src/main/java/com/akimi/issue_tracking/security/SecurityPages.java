package com.akimi.issue_tracking.security;

import com.akimi.issue_tracking.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.logging.Logger;

@Controller
public class SecurityPages {

    @Autowired
    private RegistrationService registrationService;

    @PersistenceContext
    private EntityManager em;

    @GetMapping("/login")
    public String loginUser() {
        return "login";
    }

    @GetMapping("/register")
    public String registerUser(Model model) {
        model.addAttribute("user", new User());
        return "registerUser";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") User user) {
        registrationService.register(user);
        return "redirect:/login";
    }

    @GetMapping("/check-users")
    @ResponseBody
    public List<User> checkUsers() {
        return em.createQuery("SELECT u FROM User u", User.class).getResultList();
    }


}
