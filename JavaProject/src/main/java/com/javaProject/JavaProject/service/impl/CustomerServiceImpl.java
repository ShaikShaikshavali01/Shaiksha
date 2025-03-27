package com.javaProject.JavaProject.service.impl;



import com.javaProject.JavaProject.dto.CustomerDto;
import com.javaProject.JavaProject.exception.CustomerAlreadyExistsException;
import com.javaProject.JavaProject.model.Customer;
import com.javaProject.JavaProject.repository.CustomerRepository;
import com.javaProject.JavaProject.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Override
    public CustomerDto registerCustomer(CustomerDto customerDto) {
        if (customerRepository.existsByEmail(customerDto.getEmail())) {
            throw new CustomerAlreadyExistsException("Customer with email " + customerDto.getEmail() + " already exists");
        }

        Customer customer = modelMapper.map(customerDto, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerDto.class);
    }
}
