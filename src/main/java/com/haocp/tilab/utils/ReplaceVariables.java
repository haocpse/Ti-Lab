package com.haocp.tilab.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ReplaceVariables {

    public String replace(String body, Map<String, String> values){
        try {
            String result = body;
            for (Map.Entry<String, String> entry : values.entrySet()) {
                result = result.replace("{{" + entry.getKey() + "}}", entry.getValue());
            }
            return result;
        } catch (Exception e) {
            throw new RuntimeException("Failed to replace variables", e);
        }
    }

}
