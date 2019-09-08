package com.trending.web;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClosingPriceController {

    @Autowired
    private SqlSession sqlSession;


    @GetMapping("/trending/web/closing_price/index")
    public String getIndex(){
//        return "/trending/web/closing_price/index";
        return "/trending/web/closing_price/index";
    }
}
