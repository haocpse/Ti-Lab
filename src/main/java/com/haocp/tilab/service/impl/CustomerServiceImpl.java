package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Customer.LoginRequest;
import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.response.Customer.CustomerResponse;
import com.haocp.tilab.repository.CustomerRepository;
import com.haocp.tilab.service.CustomerService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public CustomerResponse register(RegisterRequest registerRequest) {
        return null;
    }

    @Override
    public CustomerResponse login(LoginRequest loginRequest) {
        return null;
    }

    @Override
    public List<CustomerResponse> getAllCustomer() {
        return List.of();
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        return null;
    }
}
