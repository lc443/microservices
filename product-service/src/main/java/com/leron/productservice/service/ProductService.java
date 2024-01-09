package com.leron.productservice.service;

import com.leron.productservice.model.Product;
import com.leron.productservice.model.dto.ProductRequest;
import com.leron.productservice.model.dto.ProductResponse;
import com.leron.productservice.reposittory.ProductRepository;
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
    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice()).build();

        productRepository.save(product);
        log.info("Product {} saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
      List< Product> products =  productRepository.findAll();
      return products.stream().map(this::mapToProductResponse).collect(Collectors.toList());
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder().name(product.getName())
                .description(product.getDescription())
                .id(product.getId()).build();
    }
}
