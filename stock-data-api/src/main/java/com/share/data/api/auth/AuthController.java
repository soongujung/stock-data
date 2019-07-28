package com.share.data.api.auth;

import com.share.data.util.restclient.RestClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
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
    @GetMapping(value = "/auth/slack/proxy/grant")
    public void requestRedirect(HttpServletRequest request, HttpServletResponse response) throws Exception{
        final String clientId = "680595488112.691875062899";
        final String BASE_URL = "https://slack.com/oauth/authorize";
        StringBuffer sbRedirectUrl = new StringBuffer();

        sbRedirectUrl.append(BASE_URL)
                .append("?")
                    .append("scope=").append("identity.basic")
                .append("&")
                    .append("client_id=").append(clientId);

        System.out.println(sbRedirectUrl.toString());

//        response.setHeader("Access-Control-Allow-Origin", "*");

        // TODO :: 추후 적용 (REDIRECT 후에도 URL에 CLIENT ID 가 남는 문제...)
//        RequestDispatcher rd = request.getRequestDispatcher("/");
//        rd.forward(request, response);

        response.sendRedirect(sbRedirectUrl.toString());
    }

    @CrossOrigin(origins = {"http://slack.com", "https://slack.com"})
    @GetMapping(value = "/auth/slack/proxy/redirected", produces = "application/x-www-form-urlencoded")
    public @ResponseBody Object slackProxyRedirect( @RequestParam("code") String code,
                                                    @RequestParam("state") String state,
                                HttpServletRequest request, HttpServletResponse response){

        final String client_secret = "f44c1e5af174e4fbe7ed2c09b6de85cf";
        final String client_id = "680595488112.691875062899";
        final String BASE_URL = "http://slack.com/api/oauth.access";

//        System.out.println("request >>> " + request);
        System.out.println("response :: " + response );

        StringBuffer sbUrl = new StringBuffer();

        sbUrl.append(BASE_URL)
                .append("?")
            .append("code=").append(code)
                .append("&")
            .append("client_id=").append(client_id)
                .append("&")
            .append("client_secret=").append(client_secret);

        final String reqUrl = sbUrl.toString();

        String apiResult = RestClient.getApiResult(reqUrl);

        /**
         * 성공시 home.html
         * 실페시 index.html
         */
        return null;
    }


    /**
     * 이전코드 (테스트용)
     * @param code
     * @param state
     * @param request
     * @param response
     * @param model
     * @return
     */
    @CrossOrigin(origins = {"http://slack.com", "https://slack.com"})
    @GetMapping(value = "/auth/slack/redirect/result", produces = "application/x-www-form-urlencoded")
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

    @GetMapping("/auth/slack/redirect2")
    public String redirect2(){
        return "test-slack-signin2";
    }

}
