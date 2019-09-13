package com.trending.web.closingprice;

import java.util.List;
import java.util.Map;

public interface ClosingPriceService {
    public List<Map<String, Object>> getKospiResult(Map<String, Object> params);

    List<Map<String, Object>> getTrendingResult(Map<String, Object> params);
}
