package com.share.data.api.trending.price;

import com.share.data.api.trending.price.entity.TrendingPriceEntity;

import java.util.List;
import java.util.Map;

public interface TrendingPriceService {
    public List<Map<String, Object>> getKospiResult(Map<String, Object> params);

    public List<Map<String, Object>> getTrendingResult(Map<String, Object> params);

    public TrendingPriceEntity getMinKospi(List<Map<String, Object>> trendingResult);

    public TrendingPriceEntity getMaxKospi(List<Map<String, Object>> trendingResult);
}
