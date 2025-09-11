package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Customer.AddCustomerAddressRequest;
import com.haocp.tilab.dto.response.Customer.CustomerAddressResponse;
import com.haocp.tilab.dto.response.Customer.CustomerResponse;
import com.haocp.tilab.dto.response.User.UserResponse;
import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.CustomerAddress;
import com.haocp.tilab.entity.Membership;
import com.haocp.tilab.exception.AppException;
import com.haocp.tilab.exception.ErrorCode;
import com.haocp.tilab.mapper.CustomerMapper;
import com.haocp.tilab.mapper.MembershipMapper;
import com.haocp.tilab.mapper.UserMapper;
import com.haocp.tilab.repository.CustomerAddressRepository;
import com.haocp.tilab.repository.CustomerRepository;
import com.haocp.tilab.repository.UserRepository;
import com.haocp.tilab.service.CustomerService;
import com.haocp.tilab.service.MembershipService;
import com.haocp.tilab.utils.IdentifyUser;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    @Autowired
    UserMapper userMapper;
    @Autowired
    MembershipMapper membershipMapper;


    @Override
    @Transactional
    public Page<CustomerResponse> getAllCustomer(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Customer> customers = customerRepository.findAll(pageRequest);
        return customers.map(this::buildCustomerResponseOverview);
    }

    @Override
    @Transactional
    public CustomerResponse getCustomerById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.CUSTOMER_NOT_FOUND));
        CustomerResponse response = buildCustomerResponseOverview(customer);
        return buildCustomerResponseDetail(response, customer.getId(), customer.getMembership());
    }

    @Override
    public CustomerAddressResponse addCustomerAddress(AddCustomerAddressRequest addCustomerAddressRequest) {
        Customer customer = IdentifyUser.getCurrentCustomer(customerRepository, userRepository);
        CustomerAddress address = customerMapper.toCustomerAddress(addCustomerAddressRequest);
        address.setCustomer(customer);
        return customerMapper.customerToCustomerAddressResponse(customerAddressRepository.save(address));
    }

    @Override
    public List<CustomerAddressResponse> getAllMyAddress() {
        Customer customer = IdentifyUser.getCurrentCustomer(customerRepository, userRepository);
        return buildCustomerAddressResponse(customer.getId());
    }

    @Override
    @Transactional
    public CustomerResponse getMyProfile() {
        Customer customer = IdentifyUser.getCurrentCustomer(customerRepository, userRepository);
        CustomerResponse response = buildCustomerResponseOverview(customer);
        return buildCustomerResponseDetail(response, customer.getId(), customer.getMembership());
    }

    @Transactional
    CustomerResponse buildCustomerResponseOverview(Customer customer) {
        CustomerResponse customerResponse = customerMapper.customerToCustomerResponse(customer);
        customerResponse.setUserResponse(userMapper.toUserResponse(customer.getUser()));
        return customerResponse;
    }

    CustomerResponse buildCustomerResponseDetail(CustomerResponse response, String id, Membership membership) {
        response.setAddresses(buildCustomerAddressResponse(id));
        response.setMembershipResponse(membershipMapper.toResponse(membership));
        return response;
    }

    List<CustomerAddressResponse> buildCustomerAddressResponse(String customerId) {
        List<CustomerAddress> addresses = customerAddressRepository.findByCustomer_Id(customerId);
        return addresses.stream()
                .map(address -> customerMapper.customerToCustomerAddressResponse(address))
                .toList();
    }

}
