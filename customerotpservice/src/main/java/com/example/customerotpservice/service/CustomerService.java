package com.example.customerotpservice.service;

import com.example.customerotpservice.dto.CustomerDto;
import com.example.customerotpservice.exception.DuplicateEmailException;
import com.example.customerotpservice.mapper.CustomerMapper;
import com.example.customerotpservice.model.Customer;
import com.example.customerotpservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerDto createCustomer(CustomerDto customerDto) {
        if (customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new DuplicateEmailException("Email already exists: " + customerDto.getEmail());
        }

        Customer customer = customerMapper.toEntity(customerDto);
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.toDto(savedCustomer);
    }
}