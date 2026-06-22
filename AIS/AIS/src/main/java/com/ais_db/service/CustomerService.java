package com.ais_db.service;

import com.ais_db.dto.CustomerDto;
import com.ais_db.mapper.CustomerMapper;
import com.ais_db.model.Customer;
import com.ais_db.repository.CustomerRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;

    public List<CustomerDto> getAllCustomers() {
        List<Customer> customers = customerRepo.findAll();

        return customers.stream()
                .map(CustomerMapper::convertToDto)
                .toList();
    }
}
