package com.api.trending.price;

import java.util.List;
import java.util.Map;

public interface TrendingPriceDao {
    public List<Map<String, Object>> getKospiResult(Map<String, Object> params);

    List<Map<String, Object>> getTrendingResult(Map<String, Object> params);
}
