package org.cay.play.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
    static ObjectMapper objectMapper = new ObjectMapper();

    public static String getJsonStringFromObject(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }
}
