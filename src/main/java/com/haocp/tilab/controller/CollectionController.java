package com.haocp.tilab.controller;

import com.haocp.tilab.dto.ApiResponse;
import com.haocp.tilab.dto.request.Bag.CreateBagRequest;
import com.haocp.tilab.dto.request.Collection.CreateCollectionRequest;
import com.haocp.tilab.dto.response.Collection.CollectionResponse;
import com.haocp.tilab.service.CollectionService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/collections/")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CollectionController {

    @Autowired
    CollectionService collectionService;

    @GetMapping
    public ApiResponse<List<CollectionResponse>> getAllCollection(@RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "3") int size){
        return ApiResponse.<List<CollectionResponse>>builder()
                .data(collectionService.getAllCollection(page, size))
                .build();
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ApiResponse<CollectionResponse> createCollection(@RequestPart("collection") CreateCollectionRequest request,
                                          @RequestPart(value = "thumbnail", required = false) MultipartFile thumbnail){
        return ApiResponse.<CollectionResponse>builder()
                .data(collectionService.createCollection(request, thumbnail))
                .build();
    }

    @GetMapping("{id}")
    public ApiResponse<CollectionResponse> getCollection(@PathVariable Long id){
        return ApiResponse.<CollectionResponse>builder()
                .data(collectionService.getCollection(id))
                .build();
    }

}
