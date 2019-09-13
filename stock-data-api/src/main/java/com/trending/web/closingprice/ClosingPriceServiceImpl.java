package com.trending.web.closingprice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClosingPriceServiceImpl implements ClosingPriceService{

    @Autowired
    private ClosingPriceDao closingPriceDao;

    @Override
    public List<Map<String, Object>> getKospiResult(Map<String, Object> params) {
        return closingPriceDao.getKospiResult(params);
    }

    @Override
    public List<Map<String, Object>> getTrendingResult(Map<String, Object> params) {
        return closingPriceDao.getTrendingResult(params);
    }
}
