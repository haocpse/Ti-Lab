package com.haocp.tilab.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ReplaceVariables {

    public String replace(String body, Map<String, Object> values){
            try {
                String result = body;

                if (values.containsKey("order_items")) {
                    List<Map<String, Object>> orderItems = (List<Map<String, Object>>) values.get("order_items");

                    Pattern pattern = Pattern.compile("\\{\\{#each order_items}}([\\s\\S]*?)\\{\\{/each}}");
                    Matcher matcher = pattern.matcher(result);

                    if (matcher.find()) {
                        String rowTemplate = matcher.group(1);
                        StringBuilder rows = new StringBuilder();

                        for (Map<String, Object> item : orderItems) {
                            String row = rowTemplate;
                            for (Map.Entry<String, Object> entry : item.entrySet()) {
                                row = row.replace("{{" + entry.getKey() + "}}", String.valueOf(entry.getValue()));
                            }
                            rows.append(row);
                        }

                        result = matcher.replaceFirst(rows.toString());
                    }
                }

                for (Map.Entry<String, Object> entry : values.entrySet()) {
                    if (!(entry.getValue() instanceof List)) {
                        result = result.replace("{{" + entry.getKey() + "}}", String.valueOf(entry.getValue()));
                    }
                }

                return result;

            } catch (Exception e) {
                throw new RuntimeException("Failed to replace variables", e);
            }
    }

}
