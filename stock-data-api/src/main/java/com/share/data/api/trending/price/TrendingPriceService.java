package com.share.data.api.trending.price;

import java.util.List;
import java.util.Map;

public interface TrendingPriceService {
    public List<Map<String, Object>> getKospiResult(Map<String, Object> params);

    List<Map<String, Object>> getTrendingResult(Map<String, Object> params);
}
