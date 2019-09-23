package com.share.data.api.trending.price;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trending.web.closingprice.ClosingPriceService;
import com.types.date.localdate.FormatterTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class TrendingPriceController {
    final static Logger LOGGER = LoggerFactory.getLogger(TrendingPriceController.class);

    @Autowired
    private ClosingPriceService closingPriceService;

    @GetMapping(value = "/trending/price/index")
    public String getPage(Model model) throws JsonProcessingException {
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

        List<Map<String,Object>> trendingResult = closingPriceService.getTrendingResult(params);
        model.addAttribute("trendingResult", new ObjectMapper().writeValueAsString(trendingResult));
        model.addAttribute("startDate", strStartDate);
        model.addAttribute("endDate", strEndDate);
        return "/trending/price/index";
    }

    @GetMapping(value = "/api/trending/default")
    public @ResponseBody Object getDefaultData(@RequestParam String startDate, @RequestParam String endDate){
        Map<String, Object> params = new HashMap<>();
        LocalDate endLDate = LocalDate.now();
        LocalDate startLDate = endLDate.minus(5, ChronoUnit.YEARS);

        params.put("startDate", startLDate.format(FormatterTypes.YYYY0101.ofPattern()));
        params.put("endDate", endLDate.format(FormatterTypes.YYYYMMDD.ofPattern()));

        List<Map<String, Object>> trendingResult = closingPriceService.getTrendingResult(params);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("data", trendingResult);
        return resultMap;
    }
}
