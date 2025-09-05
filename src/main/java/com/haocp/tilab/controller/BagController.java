package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.Bag.CreateBagRequest;
import com.haocp.tilab.dto.request.Bag.SaveImageBagRequest;
import com.haocp.tilab.dto.request.Bag.UpdateBagRequest;
import com.haocp.tilab.dto.response.Bag.BagResponse;
import com.haocp.tilab.service.BagService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bags")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BagController {

    @Autowired
    BagService bagService;

    @PostMapping
    public ApiResponse<BagResponse> createBag(@RequestBody CreateBagRequest request,
                                              @RequestBody(required = false)SaveImageBagRequest imageBagRequest){
        return ApiResponse.<BagResponse>builder()
                .data(bagService.createBag(request, imageBagRequest))
                .build();
    }

    @GetMapping
    public ApiResponse<Page<BagResponse>> getAllBag(@RequestParam(required = false) Boolean available,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "10") int size){
        Page<BagResponse> responses;
        if (available != null && available)
            responses = bagService.getAllAvailableBag(page, size);
        else
            responses = bagService.getAllBag(page, size);
        return ApiResponse.<Page<BagResponse>>builder()
                .data(responses)
                .build();
    }

    @DeleteMapping("{id}")
    public ApiResponse<Void> deleteBag(@PathVariable String id){
        bagService.deleteBag(id);
        return ApiResponse.<Void>builder().build();
    }

    @GetMapping("{id}")
    public ApiResponse<BagResponse> getBag(@PathVariable String id){
        return ApiResponse.<BagResponse>builder()
                .data(bagService.getBag(id))
                .build();
    }

    @PutMapping("{id}")
    public ApiResponse<BagResponse> updateBag(@PathVariable String id,
                                              @RequestBody UpdateBagRequest request,
                                              @RequestBody(required = false)SaveImageBagRequest imageBagRequest){
        return ApiResponse.<BagResponse>builder()
                .data(bagService.updateBag(id, request, imageBagRequest))
                .build();
    }

}
