package com.haocp.tilab.repository;

import com.haocp.tilab.entity.CustomerAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerAddressRepository extends JpaRepository<CustomerAddress, Long> {

    List<CustomerAddress> findByCustomer_Id(String customerId);

}
