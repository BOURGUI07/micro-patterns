package com.example.micro_patterns.sec06.service;


import com.example.micro_patterns.sec06.dto.ProductAggregate;
import com.example.micro_patterns.sec06.dto.ProductInformation;
import com.example.micro_patterns.sec06.dto.ProductReview;
import com.example.micro_patterns.sec06.client.ProductClient;
import com.example.micro_patterns.sec06.client.ReviewClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAggregationService {
        private final ProductClient productClient;
        private final ReviewClient reviewClient;

        public Mono<ProductAggregate> getProductAggregate(Integer productId) {
            return Mono.zip(productClient.getProductInformation(productId),
                    reviewClient.getProductReview(productId))
                    .map(x->toDTO(x.getT1(),x.getT2(),productId));

        }

    private ProductAggregate toDTO(ProductInformation info, List<ProductReview> reviews,Integer productId) {
        return ProductAggregate.builder()
                .category(info.category())
                .description(info.description())
                .reviews(reviews)
                .id(productId)
                .build();
    }
}
