package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Customer.AddCustomerAddressRequest;
import com.haocp.tilab.dto.request.Customer.LoginRequest;
import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.response.Customer.CustomerAddressResponse;
import com.haocp.tilab.dto.response.Customer.CustomerResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CustomerService {

    Page<CustomerResponse> getAllCustomer(int page, int size);
    CustomerResponse getCustomerById(String id);
    CustomerAddressResponse addCustomerAddress(AddCustomerAddressRequest addCustomerAddressRequest);
    List<CustomerAddressResponse> getAllMyAddress();
}
