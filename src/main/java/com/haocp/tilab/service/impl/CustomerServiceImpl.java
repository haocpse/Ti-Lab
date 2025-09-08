package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Customer.AddCustomerAddressRequest;
import com.haocp.tilab.dto.request.Customer.LoginRequest;
import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.dto.response.Customer.CustomerAddressResponse;
import com.haocp.tilab.dto.response.Customer.CustomerResponse;
import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.CustomerAddress;
import com.haocp.tilab.mapper.CustomerMapper;
import com.haocp.tilab.repository.CustomerAddressRepository;
import com.haocp.tilab.repository.CustomerRepository;
import com.haocp.tilab.repository.UserRepository;
import com.haocp.tilab.service.CustomerService;
import com.haocp.tilab.utils.IdentifyUser;
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
    @Autowired
    UserRepository userRepository;
    @Autowired
    CustomerAddressRepository customerAddressRepository;
    @Autowired
    CustomerMapper customerMapper;

    @Override
    public List<CustomerResponse> getAllCustomer() {
        return List.of();
    }

    @Override
    public CustomerResponse getCustomerById(String id) {
        return null;
    }

    @Override
    public CustomerAddressResponse addCustomerAddress(AddCustomerAddressRequest addCustomerAddressRequest) {
        Customer customer = IdentifyUser.getCurrentCustomer(customerRepository, userRepository);
        CustomerAddress address = customerMapper.toCustomerAddress(addCustomerAddressRequest);
        address.setCustomer(customer);
        return customerMapper.customerToCustomerAddressResponse(customerAddressRepository.save(address));
    }

    @Override
    public List<CustomerAddressResponse> getAllAddress() {
        Customer customer = IdentifyUser.getCurrentCustomer(customerRepository, userRepository);
        List<CustomerAddress> addresses = customerAddressRepository.findByCustomer_Id(customer.getId());
        return addresses.stream()
                .map(address -> customerMapper.customerToCustomerAddressResponse(address))
                .toList();
    }
}
