package com.example.customerotpservice.mapper;

import com.example.customerotpservice.dto.CustomerDto;
import com.example.customerotpservice.model.Customer;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-07T20:47:53+0530",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.14 (Amazon.com Inc.)"
)
@Component
public class CustomerMapperImpl implements CustomerMapper {

    @Override
    public Customer toEntity(CustomerDto dto) {
        if ( dto == null ) {
            return null;
        }

        Customer customer = new Customer();

        customer.setEmail( dto.getEmail() );
        customer.setFname( dto.getFname() );
        customer.setLname( dto.getLname() );
        customer.setAge( dto.getAge() );
        customer.setAddress( dto.getAddress() );
        customer.setPhone( dto.getPhone() );

        return customer;
    }

    @Override
    public CustomerDto toDto(Customer entity) {
        if ( entity == null ) {
            return null;
        }

        CustomerDto customerDto = new CustomerDto();

        customerDto.setId( entity.getId() );
        customerDto.setEmail( entity.getEmail() );
        customerDto.setFname( entity.getFname() );
        customerDto.setLname( entity.getLname() );
        customerDto.setAge( entity.getAge() );
        customerDto.setAddress( entity.getAddress() );
        customerDto.setPhone( entity.getPhone() );

        return customerDto;
    }
}
