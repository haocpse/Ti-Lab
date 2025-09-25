package com.haocp.tilab.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dash-boards")
@PreAuthorize("hasAuthority('ADMIN')")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DashboardController {
}
