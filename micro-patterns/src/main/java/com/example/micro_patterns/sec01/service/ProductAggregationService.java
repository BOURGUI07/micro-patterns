package com.example.micro_patterns.sec01.service;

import com.example.micro_patterns.sec01.client.ProductClient;
import com.example.micro_patterns.sec01.client.PromotionClient;
import com.example.micro_patterns.sec01.client.ReviewClient;
import com.example.micro_patterns.sec01.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductAggregationService {
        private final ProductClient productClient;
        private final PromotionClient promotionClient;
        private final ReviewClient reviewClient;

        public Mono<ProductAggregate> getProductAggregate(Integer productId) {
            return Mono.zip(productClient.getProductInformation(productId),
                    promotionClient.getProductPromotion(productId),
                    reviewClient.getProductReview(productId))
                    .map(x->toDTO(x.getT1(),x.getT3(),x.getT2()));

        }

    private ProductAggregate toDTO(ProductInformation info, List<ProductReview> reviews, ProductPromotion promotion) {
            var discountedPrice = info.price()*(1-promotion.discount()/100);
            var amountSaved = info.price()-discountedPrice;
        var price = Price.builder()
                .listPrice(info.price())
                .discount(promotion.discount())
                .discountedPrice(discountedPrice)
                .amountSaved(amountSaved)
                .build();
        return ProductAggregate.builder()
                .category(info.category())
                .description(info.description())
                .price(price)
                .reviews(reviews)
                .id(promotion.id())
                .build();
    }
}
