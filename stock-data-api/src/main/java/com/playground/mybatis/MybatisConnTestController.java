package com.playground.mybatis;

import com.playground.jpa.sampleuser.SampleUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MybatisConnTestController {

    @Autowired
    private MybatisConnTestDaoImpl mybatisConnTestDao;

    @Autowired
    private SampleUserRepository sampleUserRepository;

    @GetMapping("/api/test/mybatis/conn")
    public @ResponseBody Object mybatisConnection(){
        return mybatisConnTestDao.selectConnTest();
    }

    @GetMapping("/api/test/jpa/conn")
    public @ResponseBody Object jpaConnection(){return sampleUserRepository.findByName("sgjung");}
}
