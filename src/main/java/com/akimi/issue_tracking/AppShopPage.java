package com.akimi.issue_tracking;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppShopPage {

    @GetMapping("/")
    public String index(Model model) {
        return "index";
    }
}
