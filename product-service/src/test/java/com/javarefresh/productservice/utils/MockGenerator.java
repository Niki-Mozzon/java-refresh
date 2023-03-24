package com.javarefresh.productservice.utils;

import com.javarefresh.productservice.dto.ProductRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

public class MockGenerator {
    public ProductRequest createProductRequest(){
        return ProductRequest
                .builder()
                .name("Socks")
                .description("Brand new pair of socks")
                .price(BigDecimal.valueOf(2.99))
                .build();
    }
}
