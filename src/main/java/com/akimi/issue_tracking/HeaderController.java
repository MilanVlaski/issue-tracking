package com.akimi.issue_tracking;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
class HeaderController {

    @ModelAttribute("loginPath")
    public String determineLoginPath(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/engineer") ? "/engineer/login" : "/login";
    }
}