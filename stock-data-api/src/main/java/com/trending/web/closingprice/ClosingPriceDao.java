package com.trending.web.closingprice;

import java.util.List;
import java.util.Map;

public interface ClosingPriceDao {
    public List<Map<String, Object>> getKospiResult(Map<String, Object> params);
}
