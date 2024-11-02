package com.example.micro_patterns.sec02.controller;

import com.example.micro_patterns.sec02.dto.FlightResult;
import com.example.micro_patterns.sec02.service.FlightService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/sec02")
@RequiredArgsConstructor
public class FlightController {
    private final FlightService flightService;

    @GetMapping(value = "/{from}/{to}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<FlightResult> getFlights(@PathVariable("from") String from,@PathVariable("to") String to) {
        return flightService.getFlights(from, to);
    }
}
