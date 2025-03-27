package com.javaProject.JavaProject.controller;


import com.javaProject.JavaProject.dto.CustomerDto;
import com.javaProject.JavaProject.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<CustomerDto> registerCustomer(@Valid @RequestBody CustomerDto customerDto) {
        CustomerDto registeredCustomer = customerService.registerCustomer(customerDto);
        return new ResponseEntity<>(registeredCustomer, HttpStatus.CREATED);
    }
}
