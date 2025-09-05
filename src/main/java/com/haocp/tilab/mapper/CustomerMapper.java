package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Customer.AddCustomerAddressRequest;
import com.haocp.tilab.dto.response.Customer.CustomerAddressResponse;
import com.haocp.tilab.dto.response.Customer.CustomerResponse;
import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.CustomerAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(source = "customer.id", target = "customerId")
    CustomerAddressResponse customerToCustomerAddressResponse(CustomerAddress address);
    CustomerAddress toCustomerAddress(AddCustomerAddressRequest request);

}
