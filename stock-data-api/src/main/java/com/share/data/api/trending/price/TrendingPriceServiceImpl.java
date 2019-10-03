package com.share.data.api.trending.price;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TrendingPriceServiceImpl implements TrendingPriceService {

    @Autowired
    private TrendingPriceDao trendingPriceDao;

    @Override
    public List<Map<String, Object>> getKospiResult(Map<String, Object> params) {
        return trendingPriceDao.getKospiResult(params);
    }

    @Override
    public List<Map<String, Object>> getTrendingResult(Map<String, Object> params) {
        return trendingPriceDao.getTrendingResult(params);
    }
}
