package com.share.data.api.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("name", "sgjung");
        return "index";
    }

    @GetMapping("/auth/slack/redirect")
    public String redirect(Model model){
        return "test-slack-signin";
    }

}
