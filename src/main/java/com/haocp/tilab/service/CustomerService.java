package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Customer.LoginRequest;
import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.response.Customer.CustomerResponse;

import java.util.List;

public interface CustomerService {

    CustomerResponse register(RegisterRequest registerRequest);
    CustomerResponse login(LoginRequest loginRequest);
    List<CustomerResponse> getAllCustomer();
    CustomerResponse getCustomerById(String id);
}
