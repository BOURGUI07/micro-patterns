package com.example.micro_patterns.sec03.util;

import com.example.micro_patterns.sec03.dto.orchestration.OrchestrationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DebugUtil {

    public static void debugOrchestrator(OrchestrationRequest req) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(req));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
