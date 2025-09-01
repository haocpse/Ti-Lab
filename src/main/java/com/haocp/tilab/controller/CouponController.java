package com.haocp.tilab.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/coupons")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CouponController {
}
