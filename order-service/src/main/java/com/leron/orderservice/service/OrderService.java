package com.leron.orderservice.service;

import com.leron.orderservice.model.Order;
import com.leron.orderservice.model.OrderLineItems;
import com.leron.orderservice.model.dto.InventoryResponse;
import com.leron.orderservice.model.dto.OrderLineItemsDto;
import com.leron.orderservice.model.dto.OrderRequest;
import com.leron.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private  final WebClient webClient;
    private final OrderRepository orderRepository;
    public void placeOrder(OrderRequest orderRequest) {

       List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsList()
               .stream()
               .map(this::mapToDto)
               .toList();

        Order order = Order.builder()
                .orderNumber(UUID.randomUUID().toString())
                .orderLineItemsList(orderLineItemsList)
                .build();
        List<String> skuCodes = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        //We are calling the Inventory microservice's endpoint to check if there are items in Inventory
       InventoryResponse[] inventoryResponses  = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodes).build())
                        .retrieve()
                                .bodyToMono(InventoryResponse[].class)
                               .block();


        boolean allProductsInStock = Arrays.stream(inventoryResponses).
               allMatch(InventoryResponse::isInStock);

        if(allProductsInStock) {
            orderRepository.save(order);
        }else {
            throw  new IllegalArgumentException("Product is not in stock. Please try again later");
        }

    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .price(orderLineItemsDto.getPrice())
                .quantity(orderLineItemsDto.getQuantity())
                .skuCode(orderLineItemsDto.getSkuCode()).build();
    }
}