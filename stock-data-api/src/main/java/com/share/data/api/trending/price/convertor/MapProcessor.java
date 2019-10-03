package com.share.data.api.trending.price.convertor;

import com.share.data.api.trending.price.entity.TrendingPriceEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * Optional 처리 필요
 */
public class MapProcessor {
    public static TrendingPriceEntity processMapToEntity(Map<String, Object> map){

        final List<String> keyList = Arrays.asList("time", "kospi", "exchange_rate_dollar", "corporate_loan_month", "household_loan_month");
        Map<String, Object> processedMap = NullEntityProcessor.processNullMap(map, keyList);

        TrendingPriceEntity t = new TrendingPriceEntity();
        t.setsTime(String.valueOf(processedMap.get("time")));
        t.setdKospi(processDouble(processedMap, "kospi"));
        t.setdExchangeRateDollar(processDouble(processedMap, "exchange_rate_dollar"));
        t.setdCorporateLoan(processDouble(processedMap, "corporate_loan_month"));
        t.setdHouseholdLoan(processDouble(processedMap, "household_loan_month"));

        return t;
    }

    public static double processDouble(final Map<String, Object> input, final String key){
        String value = String.valueOf(input.get(key));
        return Double.parseDouble(value);
    }

    public static Object getMapEntryByTime(final Map<String, Object> entry){
        return entry.get("time");
    }

    public static Integer getMapEntryByKospi(final  Map<String, Object> entry){
        final String strValue = String.valueOf(entry.get("kospi"));
        return Integer.parseInt(strValue);
    }
}
