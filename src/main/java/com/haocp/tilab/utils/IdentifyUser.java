package com.haocp.tilab.utils;

import org.springframework.security.core.context.SecurityContextHolder;

public class IdentifyUser {

    public static String Identify() {
        var authentication = SecurityContextHolder.getContext();

        if (authentication != null) {
            return authentication.getAuthentication().getName();
        }
        return null;
    }
}
