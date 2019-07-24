package com.share.data.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class UserAuthRunner implements ApplicationRunner {

    @Autowired
    UserAuthService userAuthService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UserAuthVo userAuthVo = userAuthService.createUserAuthVo("scrapper", "1111");
        System.out.println(userAuthVo.getUsername() + "password : " + userAuthVo.getPassword());
    }
}
