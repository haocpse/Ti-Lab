package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.Bag.CreateBagRequest;
import com.haocp.tilab.dto.response.Bag.BagResponse;
import com.haocp.tilab.service.BagService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bags")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BagController {

    @Autowired
    BagService bagService;

    @PostMapping
    public ApiResponse<BagResponse> createBag(@RequestBody CreateBagRequest request){
        return ApiResponse.<BagResponse>builder()
                .data(bagService.createBag(request))
                .build();
    }

    @GetMapping
    public ApiResponse<List<BagResponse>> getAllBag(){
        return ApiResponse.<List<BagResponse>>builder()
                .data(bagService.getAllBag())
                .build();
    }

}
