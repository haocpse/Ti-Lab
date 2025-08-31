package com.haocp.tilab.repository;

import com.haocp.tilab.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {

    List<Staff> findByStaffCode(String staffCode);

}
