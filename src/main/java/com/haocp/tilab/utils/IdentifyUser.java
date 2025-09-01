package com.haocp.tilab.utils;

import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.repository.CustomerRepository;
import com.haocp.tilab.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;

public final class IdentifyUser {

    private IdentifyUser() {}

    public static String getCurrentUsername() {
        var authentication = SecurityContextHolder.getContext();

        if (authentication != null) {
            return authentication.getAuthentication().getName();
        }
        return null;
    }

    public static Customer getCurrentCustomer(CustomerRepository customerRepository, UserRepository userRepository) {
        String username = getCurrentUsername();
        if (username == null) {
            throw new AppException(ErrorCode.HAVE_NOT_LOGIN);
        }
        return customerRepository.findById(userRepository.findByUsername(username)
                        .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXIST)).getId())
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
    }
}
