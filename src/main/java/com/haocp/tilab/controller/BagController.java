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
    public ApiResponse<List<BagResponse>> getAllBag(){
        return ApiResponse.<List<BagResponse>>builder()
                .data(bagService.getAllBag())
                .build();
    }

    @GetMapping
    public ApiResponse<List<BagResponse>> getAllAvailableBag(){
        return ApiResponse.<List<BagResponse>>builder()
                .data(bagService.getAllAvailableBag())
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
