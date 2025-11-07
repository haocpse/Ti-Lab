package com.haocp.tilab.utils;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommonHelper {

    public LocalDateTime getFromDate(String range, LocalDateTime now) {
        LocalDateTime fromDate;
        switch (range.toLowerCase()) {
            case "1w" -> fromDate = now.minusWeeks(1);
            case "1m" -> fromDate = now.minusMonths(1);
            case "3m" -> fromDate = now.minusMonths(3);
            case "6m" -> fromDate = now.minusMonths(6);
            case "1y" -> fromDate = now.minusYears(1);
            default -> throw new IllegalArgumentException("Invalid range: " + range);
        }
        return fromDate;
    }

}
