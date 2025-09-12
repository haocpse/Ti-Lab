package com.haocp.tilab.dto.request.SePay;

import lombok.*;
import lombok.experimental.FieldDefaults;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SePayWebhookRequest {
    Long id;                // ID giao dịch trên SePay
    String gateway;         // Brand name ngân hàng
    String transactionDate; // Thời gian giao dịch
    String accountNumber;   // Số tài khoản
    String code;            // Mã code thanh toán
    String content;         // Nội dung chuyển khoản
    String transferType;    // "in" hoặc "out"
    Long transferAmount;    // Số tiền giao dịch
    Long accumulated;       // Số dư
    String subAccount;      // TK ngân hàng phụ
    String referenceCode;   // Mã tham chiếu SMS
    String description;     // Nội dung SMS
}
