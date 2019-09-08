package com.share.data.api.auth;

import com.util.restclient.ApacheHttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@PropertySource(value = {"classpath:${oauth-slack.config}"})
public class AuthController {

    @Value("${slack.client-id}")
    private String slackClientId;

    @Value("${slack.client-secret}")
    private String slackClientSecret;

    @Value("${slack.oauth.authorize.base_url}")
    private String oAuthAuthorizeBaseURL;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("name", "sgjung");
        return "index";
    }

    /**
     * STEP 1) Authorization Request -> Authorization Grant
     *  client_id, scope 로 client id 에 대한 User Server (Resource Owner) 로 리다이렉트 요청
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
                    .append("client_id=").append(slackClientId);

        System.out.println(sbRedirectUrl.toString());

        // TODO :: 추후 적용 (REDIRECT 후에도 URL에 CLIENT ID 가 남는 문제...)
//        RequestDispatcher rd = request.getRequestDispatcher("/");
//        rd.forward(request, response);

        response.sendRedirect(sbRedirectUrl.toString());
    }

    /**
     * STEP 2) Authorization Grant -> Access Token
     *  2-1) User Resource Server 에서 사용자를 미리 등록해놓은 우리측 redirected 페이지로 이동시켜주는데 이때 code라는 임시값을 우리측에 발급해준다.
     *
     *  2-2) User Resource Server 에서 얻은 임시값인 code와 client_secret, client_id를 get parameter로 조합해 oauth.access에 질의를 던진다.
     *      요청이 성공하면 access_token 을 얻는다.
     *
     *      참고)
     *      access_token을 얻고나면 /auth/slack/proxy/redirected 로 또 리다이렉트 되는데 이때 code라는 파라미터가 url에 달려서 온다.
     *      https://slack.com/api/oauth.access?code=680595488112.715091735572.39e0e4d4c76db70c53347491f41cd0a62ac3f3eccac53428867dc60e4cd98b9e&client_id=680595488112.691875062899&client_secret=f44c1e5af174e4fbe7ed2c09b6de85cf
     *      요청이 끝나고 같이 테스트 해보면 {"ok":false,"error":"code_already_used"} 메시지를 접하게 된다.
     *
     *  --- 읭? 아래 내용은 내가 졸면서 쓴건가 ....  ---
     *  리턴값으로 slack api 주소가 오는지 확인해야 하고
     *  온다면 그 주소로 redirect 시킨다.
     * @param code
     * @param state
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ParseException
     */
    @CrossOrigin(origins = {"http://slack.com", "https://slack.com"})
    @GetMapping(value = "/auth/slack/proxy/redirected", produces = "application/x-www-form-urlencoded")
    public String slackProxyRedirect( @RequestParam("code") String code,
                                                    @RequestParam("state") String state,
                                HttpServletRequest request, HttpServletResponse response) throws IOException, ParseException {

        final String client_secret = "f44c1e5af174e4fbe7ed2c09b6de85cf";
        final String client_id = "680595488112.691875062899";
        final String BASE_URL = "http://slack.com/api/oauth.access";

        StringBuffer sbUrl = new StringBuffer();

        sbUrl.append(BASE_URL)
                .append("?")
            .append("code=").append(code)
                .append("&")
            .append("client_id=").append(slackClientId)
                .append("&")
            .append("client_secret=").append(slackClientSecret);

        final String reqUrl = sbUrl.toString();

//        String apiResult = RestClient.getApiResult(reqUrl);
        String apiResult = ApacheHttpClient.getApiResult(reqUrl);

        System.out.println("apiResult >>> " + apiResult);

        JSONParser jsonParser = new JSONParser();
        Object obj = jsonParser.parse(apiResult);
        JSONObject jsonObject = (JSONObject) obj;

        System.out.println("access_token >>> " + jsonObject.get("access_token"));

        /**
         * 인증 성공여부 판단 로직 작성
         * 응답 형식 : {"ok":false,"error":"code_already_used"}
         *
         * 성공
         * jsonObject 내의 ok가 true 이면서 error 를 key로 contains 하지 않을 경우
         *
         * 실패
         * jsonObject 내의 ok가 false 이면서 error를 key로 가지고 있고 에러메시지 문자열값이 있는 경우
         */

        boolean isOk = Boolean.valueOf(String.valueOf(jsonObject.get("ok")));
        boolean isAccessSuccess = false;

        String errMsg = "";

        if(isOk){
            isAccessSuccess = true;
        }
        else{
            errMsg = String.valueOf(jsonObject.get("error"));
        }

        /**
         * 성공시 home.html 로 redirect
         * 실페시 index.html 로 redirect ( Sign in With Slack ) 이 있는 부분으로
         */
        if(isAccessSuccess){
            return "home";
        }
        else{
            return "index";
        }
    }

}
