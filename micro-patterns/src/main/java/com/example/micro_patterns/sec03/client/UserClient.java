package com.example.micro_patterns.sec03.client;

import com.example.micro_patterns.sec03.dto.Status;
import com.example.micro_patterns.sec03.dto.product.ProductResponse;
import com.example.micro_patterns.sec03.dto.user.PaymentRequest;
import com.example.micro_patterns.sec03.dto.user.PaymentResponse;
import com.example.micro_patterns.sec03.dto.user.UserResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class UserClient {
    private WebClient webClient;

    public UserClient(@Value("${sec03.user.service}") String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }

    public Mono<UserResponse> getUserInfo(Integer userId) {
        return webClient
                .get()
                .uri("{id}", userId)
                .retrieve()
                .bodyToMono(UserResponse.class)
                .onErrorResume(ex->Mono.empty());
    }

    public Mono<PaymentResponse> deduct(PaymentRequest paymentRequest) {
        return userServicePostRequest("deduct", paymentRequest);
    }

    public Mono<PaymentResponse> refund(PaymentRequest paymentRequest) {
        return userServicePostRequest("refund", paymentRequest);
    }

    private Mono<PaymentResponse> userServicePostRequest(String action,PaymentRequest paymentRequest) {
        return webClient
                .post()
                .uri(action)
                .bodyValue(paymentRequest)
                .retrieve()
                .bodyToMono(PaymentResponse.class)
                .onErrorReturn(errorResponse(paymentRequest));
    }

    private PaymentResponse errorResponse(PaymentRequest paymentRequest) {
        return PaymentResponse.builder()
                .name(null)
                .amount(paymentRequest.amount())
                .status(Status.FAILED)
                .userId(paymentRequest.userId())
                .build();
    }
}
