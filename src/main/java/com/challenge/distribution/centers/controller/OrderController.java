package com.challenge.distribution.centers.controller;


import com.challenge.distribution.centers.dto.OrderRequestDTO;
import com.challenge.distribution.centers.dto.OrderResponseDTO;
import com.challenge.distribution.centers.model.Order;
import com.challenge.distribution.centers.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public OrderResponseDTO processOrder(@RequestBody OrderRequestDTO orderRequest) {
        return orderService.processOrder(orderRequest);
    }

    @GetMapping
    public List<OrderResponseDTO> getAllOrders() {
        return orderService.getAllOrders();
    }


}
