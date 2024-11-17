package com.akimi.issue_tracking;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
class HeaderController {

    @ModelAttribute("loginPath")
    public String determineLoginPath(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/engineer") ? "/engineer/login" : "/login";
    }
}