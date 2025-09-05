package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Customer.AddCustomerAddressRequest;
import com.haocp.tilab.dto.request.Customer.LoginRequest;
import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.response.Customer.CustomerAddressResponse;
import com.haocp.tilab.dto.response.Customer.CustomerResponse;

import java.util.List;

public interface CustomerService {

    List<CustomerResponse> getAllCustomer();
    CustomerResponse getCustomerById(String id);
    CustomerAddressResponse addCustomerAddress(AddCustomerAddressRequest addCustomerAddressRequest);
}
