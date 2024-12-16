package com.akimi.issue_tracking.security;

import com.akimi.issue_tracking.security.dto.EngineerRegistration;
import com.akimi.issue_tracking.security.dto.UserRegistration;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        return "registerUser";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute UserRegistration userRegistration) {
        registrationService.register(userRegistration.toEntity());
        return "redirect:/login";
    }

    @GetMapping("/engineer/login")
    public String engineerLogin() {
        return "engineerLogin";
    }

    @GetMapping("/engineer/register")
    public String engineerRegister() {
        return "engineerRegister";
    }

    @PostMapping("/engineer/register")
    public String engineerRegister(@ModelAttribute EngineerRegistration engineerRegistration) {
        registrationService.register(engineerRegistration.toEntity());
        return "redirect:/engineer/login";
    }

}
