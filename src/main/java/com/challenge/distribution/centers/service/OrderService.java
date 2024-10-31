package com.challenge.distribution.centers.service;


import com.challenge.distribution.centers.dto.DistributionCentersDTO;
import com.challenge.distribution.centers.dto.ItemResponseDTO;
import com.challenge.distribution.centers.dto.OrderRequestDTO;
import com.challenge.distribution.centers.dto.OrderResponseDTO;
import com.challenge.distribution.centers.model.Item;
import com.challenge.distribution.centers.model.Order;
import com.challenge.distribution.centers.repository.OrderRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class OrderService {


    private final OrderRepository orderRepository;
    private final DistributionCenterService distributionCenterService;

    public OrderResponseDTO processOrder(OrderRequestDTO order) {
        validateOrderItems(order);

        Order orderItem = createOrder(order);
        Order orderResponse = saveOrder(orderItem);
        log.info("Order processed: {}", orderResponse);

        return createOrderResponse(orderResponse);
    }

    private Order createOrder(OrderRequestDTO order) {
        List<Item> items = order.getItems().stream()
                .map(this::createItem)
                .collect(Collectors.toList());
        Order orderItem = new Order();
        orderItem.setItems(items);
        return orderItem;
    }

    private Order saveOrder(Order orderItem) {
        return orderRepository.save(orderItem);
    }

    private OrderResponseDTO createOrderResponse(Order orderResponse) {
        OrderResponseDTO response = new OrderResponseDTO();
        response.setOrderId(orderResponse.getId());
        response.setItems(orderResponse.getItems().stream()
                .map(item -> new ItemResponseDTO(item.getName(), item.getDistributionCenters()))
                .collect(Collectors.toList()));
        return response;
    }

    void validateOrderItems(OrderRequestDTO order) {
        if (order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item");
        }
        if (order.getItems().size() > 100) {
            throw new IllegalArgumentException("Order must have at most 100 items");
        }
    }

    Item createItem(String requestItem) {
        DistributionCentersDTO distributionCenters = distributionCenterService.getDistributionCentersByItem(requestItem);
        if (distributionCenters == null) {
            throw new IllegalArgumentException("No distribution centers found for requestItem " + requestItem);
        }
        log.info("Centers: {}", distributionCenters);

        Item item = new Item();
        item.setName(requestItem);
        item.setDistributionCenters(distributionCenters.getDistributionCenters());
        return item;
    }

    public List<OrderResponseDTO> getAllOrders() {
        return orderRepository.findAll().stream()
                .map(order -> {
                    OrderResponseDTO response = new OrderResponseDTO();
                    response.setOrderId(order.getId());
                    response.setItems(order.getItems().stream()
                            .map(item -> new ItemResponseDTO(item.getName(), item.getDistributionCenters()))
                            .collect(Collectors.toList()));
                    return response;
                })
                .collect(Collectors.toList());
    }
}
