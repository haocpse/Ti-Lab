package com.haocp.tilab.service.impl;

import com.haocp.tilab.dto.request.Report.SendReportRequest;
import com.haocp.tilab.dto.response.Report.ReportResponse;
import com.haocp.tilab.entity.Customer;
import com.haocp.tilab.entity.Report;
import com.haocp.tilab.repository.CustomerRepository;
import com.haocp.tilab.repository.OrderRepository;
import com.haocp.tilab.repository.ReportRepository;
import com.haocp.tilab.repository.UserRepository;
import com.haocp.tilab.service.ReportService;
import com.haocp.tilab.utils.IdentifyUser;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReportServiceImpl implements ReportService {

    @Autowired
    ReportRepository reportRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    OrderRepository orderRepository;
//    @Autowired
//    ReportMapper reportMapper;

    @Override
    @Transactional
    public ReportResponse sendReport(SendReportRequest request) {
        Customer customer = IdentifyUser.getCurrentCustomer(customerRepository, userRepository);
        Report report = reportRepository.save(Report.builder()
                        .order(orderRepository.getReferenceById(request.getOrderId()))
                        .customer(customer)
                        .reason(request.getReason())
                .build());
//        return reportMapper.toReportResponse(report);
        return null;
    }
}
