package com.share.data.api.trending.price.convertor;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class NullEntityProcessor {

    public static <T> T processNull(T trendingPriceEntity){
        return trendingPriceEntity;
    }

    public static Map<String,Object> processNullMap(final Map<String, Object> input, final List<String> keyList){

//        Optional.ofNullable(input)
//                .orElseGet(Collections::emptyMap)
//                .entrySet().parallelStream()
//                .map(m->{
////                    keyList.parallelStream().forEach(k->{
////                        m.get
////                    });
//                    if(m.getValue() == null){
//                        m.setValue(0);
//                    }
//                    return m;
//                })
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        keyList.parallelStream()
                .forEach(k->{
                    if(input.get(k) == null){
                        input.put(k, 0);
                    }
                });

//        keyList.parallelStream()
//                .forEach(k->{
//                    Optional.ofNullable(input)
//                            .orElseGet(Collections::emptyMap)
//                            .entrySet().parallelStream()
//                            .map(entry->{
//
//                                if(entry.getValue() == null){
//                                    entry.setValue(0);
//                                }
//                                return entry;
//                            })
//                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//                });
        return input;
    }
}
