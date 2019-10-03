package com.util.optional;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class OptionalUtil {
    public static Map<String, Object> processNull( final Map<String, Object> input, final String key ){
        Map<String, Object> data = Optional.ofNullable(input)
                .orElseGet(Collections::emptyMap)
                .entrySet().stream()
                .map(m -> {
                    if (m.getValue() == null) {
                        m.setValue(0);
                    }
                    return m;
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return data;
    }
}
