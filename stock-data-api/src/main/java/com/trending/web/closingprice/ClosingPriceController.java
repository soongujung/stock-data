package com.trending.web.closingprice;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.share.data.api.trending.price.TrendingPriceService;
import com.types.date.localdate.FormatterTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ClosingPriceController {

    @Autowired
    private TrendingPriceService trendingPriceService;

    @GetMapping("/trending/web/closing_price/index")
    public String getIndex(HttpServletRequest request, Model model) throws JsonProcessingException {
//        return "/trending/web/closing_price/index";
        Map<String,Object> params = new HashMap<>();

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

        List<Map<String,Object>> trendingResult = trendingPriceService.getTrendingResult(params);
        model.addAttribute("trendingResult", new ObjectMapper().writeValueAsString(trendingResult));
        model.addAttribute("startDate", strStartDate);
        model.addAttribute("endDate", strEndDate);

        return "/trending/web/closing_price/index";
    }
}
