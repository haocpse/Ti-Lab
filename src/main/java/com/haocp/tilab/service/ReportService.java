package com.haocp.tilab.service;

import com.haocp.tilab.dto.request.Report.SendReportRequest;
import com.haocp.tilab.dto.response.Report.ReportResponse;

public interface ReportService {

    ReportResponse sendReport(SendReportRequest request);

}
