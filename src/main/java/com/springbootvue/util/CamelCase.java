package com.springbootvue.util;

import com.google.common.base.CaseFormat;

import java.util.HashMap;
import java.util.Map;

public class CamelCase {
    public Map<String, Object> convertToCamelCase(Map<String, Object> origin) {
        Map<String, Object> result = new HashMap<>();
        for (String key : origin.keySet()) {
            String camelKey = CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, key);
            result.put(camelKey, origin.get(key));
        }
        return result;
    }
}
