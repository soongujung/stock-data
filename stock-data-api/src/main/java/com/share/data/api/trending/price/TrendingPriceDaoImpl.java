package com.share.data.api.trending.price;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class TrendingPriceDaoImpl implements TrendingPriceDao {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public List<Map<String, Object>> getKospiResult(Map<String, Object> params) {
        return sqlSession.selectList("trending.getKospiResult",params);
    }

    @Override
    public List<Map<String, Object>> getTrendingResult(Map<String, Object> params) {
        return sqlSession.selectList("trending.getTrendingResult", params);
    }
}
