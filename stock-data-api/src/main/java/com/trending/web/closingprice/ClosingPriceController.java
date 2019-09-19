package com.trending.web.closingprice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.types.date.localdate.FormatterTypes;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ClosingPriceController {

    @Autowired
    private ClosingPriceService closingPriceService;

    @GetMapping("/trending/web/closing_price/index")
    public String getIndex(HttpServletRequest request, Model model) throws JsonProcessingException {
//        return "/trending/web/closing_price/index";
        Map<String,Object> params = new HashMap<>();

//        LocalDate endDate = LocalDate.now();
//        LocalDate startDate = endDate.minus(5, ChronoUnit.YEARS);
//        LocalDate startDate = LocalDate.of(2014,1,1);

        LocalDate endDate = LocalDate.now();
        LocalDate startDate = LocalDate.parse(
                                    endDate.minus(5, ChronoUnit.YEARS)
                                            .format(FormatterTypes.YYYY0101.ofPattern()),
                                    FormatterTypes.YYYYMMDD.ofPattern());

        String strStartDate = startDate.format(FormatterTypes.YYYYMMDD.ofPattern());
        // OR
//        String strStartDate2 = startDate.format(FormatterTypes.YYYY0101.ofPattern());
        String strEndDate = endDate.format(FormatterTypes.YYYYMMDD.ofPattern());

        params.put("startDate", strStartDate);
        params.put("endDate", strEndDate);

        List<Map<String,Object>> kospiResult = closingPriceService.getKospiResult(params);
        List<Map<String,Object>> trendingResult = closingPriceService.getTrendingResult(params);
        model.addAttribute("kospiResult", new ObjectMapper().writeValueAsString(kospiResult));
        model.addAttribute("trendingResult", new ObjectMapper().writeValueAsString(trendingResult));
        model.addAttribute("startDate", strStartDate);
        model.addAttribute("endDate", strEndDate);

        return "/trending/web/closing_price/index";
    }
}
