package com.share.data.api.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("name", "sgjung");
        return "index";
    }

    @CrossOrigin(origins = {"http://slack.com", "https://slack.com"})
    @GetMapping(value = "/auth/slack/redirect", produces = "application/json")
    public String redirect(@RequestParam("code") String code , @RequestParam("state") String state, Model model){
        System.out.println("code    :: " + code);
        System.out.println("state   :: " + state);

        model.addAttribute("code", code);
        model.addAttribute("state", state);
        return "test-slack-signin";
    }

    @GetMapping("/auth/slack/redirect2")
    public String redirect2(){
        return "test-slack-signin2";
    }

}
