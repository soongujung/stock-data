package com.share.data.api.playground;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {

    @Autowired
    private TestDao testDao;

    @GetMapping("/api/test/db")
    public @ResponseBody Object testDb(){
        return testDao.selectConnTest();
    }
}
