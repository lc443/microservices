package com.leron.productservice.controller;

import com.leron.productservice.model.dto.ProductRequest;
import com.leron.productservice.model.dto.ProductResponse;
import com.leron.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void  createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }

    @GetMapping

    public List<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }
}
