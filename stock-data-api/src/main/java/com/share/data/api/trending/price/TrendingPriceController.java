package com.share.data.api.trending.price;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.share.data.api.trending.price.convertor.MapProcessor;
import com.share.data.api.trending.price.entity.TrendingPriceEntity;
import com.types.date.localdate.FormatterTypes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;

@Controller
@SessionAttributes("trendingPrice")
public class TrendingPriceController {
    final static Logger LOGGER = LoggerFactory.getLogger(TrendingPriceController.class);

    @Autowired
    private TrendingPriceService trendingPriceService;

    @GetMapping(value = "/trending/price/index")
    public String getPage(Model model) throws JsonProcessingException {
        return "/trending/price/index";
    }

    @GetMapping(value = "/api/trending/default")
    public @ResponseBody Object getDefaultData(@RequestParam(value = "startDate", required = false) String startDate,
                                               @RequestParam(value = "endDate", required = false) String endDate,
                                               Model model){

        List<Map<String, Object>> trendingResult = getDefaultTrendingPrice();
        model.addAttribute("trendingResult", trendingResult);

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("chart", trendingResult);
        return resultMap;
    }

    @GetMapping(value = "/api/trending/kospi/minmax")
    public @ResponseBody Object getMinMaxKospi( @RequestParam(value = "startDate", required = false) String startDate,
                                                @RequestParam(value = "endDate", required = false) String endDate,
                                                Model model){

        List<Map<String, Object>> trendingResult = (List<Map<String, Object>>) model.asMap().get("trendingPrice");

        if(trendingResult == null){
            trendingResult = getDefaultTrendingPrice();
        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("max", trendingPriceService.getMaxKospi(trendingResult));
        resultMap.put("min", trendingPriceService.getMinKospi(trendingResult));
        return resultMap;
    }

    private List<Map<String, Object>> getDefaultTrendingPrice(){
        Map<String, Object> params = new HashMap<>();
        LocalDate endLDate = LocalDate.now();
        LocalDate startLDate = endLDate.minus(5, ChronoUnit.YEARS);

        params.put("startDate", startLDate.format(FormatterTypes.YYYY0101.ofPattern()));
        params.put("endDate", endLDate.format(FormatterTypes.YYYYMMDD.ofPattern()));

        List<Map<String, Object>> trendingResult = trendingPriceService.getTrendingResult(params);
        return trendingResult;
    }
}
