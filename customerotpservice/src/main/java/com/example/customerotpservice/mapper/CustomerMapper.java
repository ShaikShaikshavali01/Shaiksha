package com.example.customerotpservice.mapper;

import com.example.customerotpservice.dto.CustomerDto;
import com.example.customerotpservice.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    @Mapping(target = "id", ignore = true)
    Customer toEntity(CustomerDto dto);

    CustomerDto toDto(Customer entity);
}