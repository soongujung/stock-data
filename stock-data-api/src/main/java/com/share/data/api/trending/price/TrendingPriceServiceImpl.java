package com.share.data.api.trending.price;

import com.share.data.api.trending.price.convertor.MapProcessor;
import com.share.data.api.trending.price.entity.TrendingPriceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingDouble;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.minBy;

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

    @Override
    public TrendingPriceEntity getMinKospi(List<Map<String, Object>> trendingResult) {
        return trendingResult.parallelStream()
                .map(MapProcessor::processMapToEntity)
                .filter(entity->{
                    if(entity.getdKospi() == 0){
                        return false;
                    }
                    else{
                        return true;
                    }
                })
                .collect(minBy(comparingDouble(TrendingPriceEntity::getdKospi)))
                .get();
    }

    @Override
    public TrendingPriceEntity getMaxKospi(List<Map<String, Object>> trendingResult) {
        return trendingResult.parallelStream()
                .map(MapProcessor::processMapToEntity)
                .collect(maxBy(comparingDouble(TrendingPriceEntity::getdKospi)))
                .get();
    }
}
