package com.api.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * for Test, h2 Dataabase
 */
public class UserAuthRunner implements ApplicationRunner {

    @Autowired
    UserAuthService userAuthService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UserAuthVo userAuthVo = userAuthService.createUserAuthVo("scrapper", "1111");
    }
}
