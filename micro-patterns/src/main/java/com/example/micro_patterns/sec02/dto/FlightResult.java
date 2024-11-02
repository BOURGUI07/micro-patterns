package com.example.micro_patterns.sec02.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@Data
public class FlightResult{
    private String airline;
    private String from;
    private String to;
    private Integer price;
    private LocalDate date;
}
