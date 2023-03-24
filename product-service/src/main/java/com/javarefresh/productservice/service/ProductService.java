package com.javarefresh.productservice.service;

import com.javarefresh.productservice.dto.ProductRequest;
import com.javarefresh.productservice.dto.ProductResponse;
import com.javarefresh.productservice.model.Product;
import com.javarefresh.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

        public void createProduct(ProductRequest productRequest){
            Product product = Product.builder()
                    .name(productRequest.getName())
                    .description(productRequest.getDescription())
                    .price(productRequest.getPrice())
                    .build();
productRepository.save(product);
log.info("Product #{} is saved successfully!",product.getId());
        }

    public List<ProductResponse> getAllProducts() {
            return productRepository.findAll().stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product) {
            return ProductResponse.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .build();
    }
}
