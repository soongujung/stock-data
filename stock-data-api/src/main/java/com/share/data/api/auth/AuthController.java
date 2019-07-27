package com.share.data.api.auth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class AuthController {

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("name", "sgjung");
        return "index";
    }

    /**
     * client_id로 서버에서 slack에 콜,
     * 리턴값으로 slack api 주소가 오는지 확인해야 하고
     * 온다면 그 주소로 redirect 시킨다.
     */
    @CrossOrigin(origins = {"http://slack.com", "https://slack.com"})
    @GetMapping(value = "/auth/slack/proxy/call", produces = "application/x-www-form-urlencoded")
    public void slackProxyCall(){
        String clientId = "680595488112.691875062899";
        String keyName = "client_id";
        // redirect
    }


    @CrossOrigin(origins = {"http://slack.com", "https://slack.com"})
    @GetMapping(value = "/auth/slack/proxy/redirect", produces = "application/x-www-form-urlencoded")
    public @ResponseBody Object slackProxyRedirect(@RequestParam("code") String code, @RequestParam("state") String state,
                                HttpServletRequest request, HttpServletResponse response){

        /**
         * 성공시 home.html
         * 실페시 index.html
         */
        return null;
    }

    @CrossOrigin(origins = {"http://slack.com", "https://slack.com"})
    @GetMapping(value = "/auth/slack/redirect", produces = "application/x-www-form-urlencoded")
    public String redirect(@RequestParam("code") String code , @RequestParam("state") String state,
                           HttpServletRequest request, HttpServletResponse response, Model model){
        System.out.println("code    :: " + code);
        System.out.println("state   :: " + state);

//        response.setHeader("Accept-Encoding", request.getHeader("Accept-Encoding"));
////        response.setHeader("Content-Encoding", "gzip");
//        response.setHeader("Accept", request.getHeader("Accept"));
//        response.setHeader("access-control-allow-origin:", request.getHeader("*"));
//        response.setHeader("content-type", "application/json; charset=utf-8");

        model.addAttribute("code", code);
        model.addAttribute("state", state);
        return "test-slack-signin";
    }

    @CrossOrigin(origins = {"http://slack.com", "https://slack.com"})
    @PostMapping("/auth/slack/redirect")
    public String redirect(@RequestBody Map<String, Object> params, Model model){
        String code = String.valueOf(params.get("code"));
        String state = String.valueOf(params.get("state"));

        model.addAttribute("code", code);
        model.addAttribute("state", state);
        System.out.println("POST REDIRECT...");
        return "test-slack-signin";
    }

    @GetMapping("/auth/slack/redirect2")
    public String redirect2(){
        return "test-slack-signin2";
    }

}
