package com.trending.web.closingprice;


import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ClosingPriceController {

    @Autowired
    private SqlSession sqlSession;

    @Autowired
    private ClosingPriceService closingPriceService;

    @GetMapping("/trending/web/closing_price/index")
    public String getIndex(){
//        return "/trending/web/closing_price/index";
        Map<String,Object> params = new HashMap<>();
        params.put("startDate", "2018-01-01");
        params.put("endDate", "2018-01-30");
        List<Map<String,Object>> kospiResult = closingPriceService.getKospiResult(params);
        return "/trending/web/closing_price/index";
    }
}
