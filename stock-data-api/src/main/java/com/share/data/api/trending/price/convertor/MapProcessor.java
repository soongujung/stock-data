package com.share.data.api.trending.price.convertor;

import com.share.data.api.trending.price.convertor.behavior.DoubleProcessBehavior;
import com.share.data.api.trending.price.entity.TrendingPriceEntity;

import java.util.*;


/**
 * TODO : Optional 처리 필요
 */
public class MapProcessor {

    /** TODO: lambda 보다는 커스텀 함수형 인터페이스로 명세화 시킬수 잇도록 더 발전시킬 방법 있는지 찾아볼 것 */
    private static final DoubleProcessBehavior doubleBehavior = (Object o)->{
        return Double.parseDouble(String.valueOf(o));
    };

    public static TrendingPriceEntity processMapToEntity(Map<String, Object> map){
        final List<String> keyList = Arrays.asList("time", "kospi", "exchange_rate_dollar", "corporate_loan_month", "household_loan_month");

        keyList.stream()
                .forEach(k->{
                    if(map.get(k) == null){
                        map.put(k, 0);
                    }
                });

        TrendingPriceEntity t = processTrendingPriceEntity(map);
        return t;
    }

    private static TrendingPriceEntity processTrendingPriceEntity(Map<String, Object> map){
        TrendingPriceEntity entity = new TrendingPriceEntity();

        entity.setsTime(String.valueOf(map.get("time")));
        entity.setdKospi(processDouble(doubleBehavior, map.get("kospi")));
        entity.setdExchangeRateDollar(processDouble(doubleBehavior, map.get("exchange_rate_dollar")));
        entity.setdCorporateLoan(processDouble(doubleBehavior, map.get("corporate_loan_month")));
        entity.setdHouseholdLoan(processDouble(doubleBehavior, map.get("household_loan_month")));

        return entity;
    }

    private static double processDouble(DoubleProcessBehavior p, Object o){
        double d = p.process(o);
        return d;
    }

}
