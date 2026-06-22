package com.ais_db.dto;



public record CustomerDto(Integer id,
                          String name,
                          String email,
                          String contactNumber,
                          String address) {
}
