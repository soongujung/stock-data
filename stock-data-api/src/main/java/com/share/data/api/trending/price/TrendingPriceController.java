package com.share.data.api.trending.price;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TrendingPriceController {
    final static Logger LOGGER = LoggerFactory.getLogger(TrendingPriceController.class);

    @GetMapping(value = "/trending/index")
    public String getPage(){
        return "";
    }

    @GetMapping(value = "/api/trending/default")
    public @ResponseBody Object getDefaultData(){
        return null;
    }
}
