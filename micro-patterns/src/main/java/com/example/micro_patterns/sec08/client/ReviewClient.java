package com.example.micro_patterns.sec08.client;

import com.example.micro_patterns.sec08.dto.ProductReview;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewClient {
    private WebClient webClient;

    public ReviewClient(@Value("${sec08.review.service}") String baseUrl) {
        webClient = WebClient.builder().baseUrl(baseUrl).build();
    }


    @CircuitBreaker(name = "review-service",fallbackMethod = "fallBackReview")
    //while the circuit breaker in open state, he will give the fallBack method to the client
    public Mono<List<ProductReview>> getProductReview(Integer productId) {
        return webClient
                .get()
                .uri("{id}", productId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->Mono.empty())
                .bodyToFlux(ProductReview.class)
                .collectList()
                .retry(5)
                .timeout(Duration.ofMillis(300));
        // the circuit breaker has to see the record exceptions you specified in application yaml file
        // if you write onErrorReturn() He will always assume that the service is up and running
        // you have to record TimeoutException as well, since if all the retries aren't done within 300ms
        // a timeout exception will be thrown
   }

    public Mono<List<ProductReview>> fallBackReview(Integer productId,Throwable throwable) {
        return Mono.just(Collections.emptyList());
    }
}
