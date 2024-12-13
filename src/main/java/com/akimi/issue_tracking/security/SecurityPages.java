package com.akimi.issue_tracking.security;

import com.akimi.issue_tracking.application.User;
import com.akimi.issue_tracking.problem.engineer.Engineer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

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
    public String register(@ModelAttribute User user) {
        registrationService.register(user);
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
    public String engineerRegister(@ModelAttribute Engineer engineer) {
        registrationService.register(engineer);
        return "redirect:/engineer/login";
    }

}
