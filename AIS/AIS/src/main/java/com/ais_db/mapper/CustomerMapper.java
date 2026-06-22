package com.ais_db.mapper;

import com.ais_db.dto.CustomerDto;
import com.ais_db.model.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {
    public static CustomerDto  convertToDto(Customer c) {
        return new CustomerDto(
                c.getId(),
                c.getName(),
                c.getEmail(),
                c.getContactNumber(),
                c.getAddress());
    }
}
