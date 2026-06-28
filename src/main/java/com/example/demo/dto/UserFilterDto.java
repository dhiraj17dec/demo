package com.example.demo.dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserFilterDto {

    private String name;

    private String email;
    
    private Integer minAge;

    private Integer maxAge;

    private LocalDate createdAfter;

    private LocalDate createdBefore;
}