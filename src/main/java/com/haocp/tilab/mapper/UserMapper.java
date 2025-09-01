package com.haocp.tilab.mapper;

import com.haocp.tilab.dto.request.Customer.RegisterRequest;
import com.haocp.tilab.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toUser(RegisterRequest request);

}
