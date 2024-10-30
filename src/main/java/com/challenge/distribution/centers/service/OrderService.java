package com.challenge.distribution.centers.service;


import com.challenge.distribution.centers.dto.DistributionCentersDTO;
import com.challenge.distribution.centers.dto.OrderRequestDTO;
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

    public Order processOrder(OrderRequestDTO order) {
        validateOrderItems(order);

        Order orderItem = new Order();
        List<Item> items = order.getItems().stream()
                .map(this::createItem)
                .collect(Collectors.toList());
        orderItem.setItems(items);

        return orderRepository.save(orderItem);
    }

    private void validateOrderItems(OrderRequestDTO order) {
        if (order.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must have at least one item");
        }
        if (order.getItems().size() > 100) {
            throw new IllegalArgumentException("Order must have at most 100 items");
        }
    }

    private Item createItem(String itemId) {
        DistributionCentersDTO distributionCenters = distributionCenterService.getDistributionCentersByItem(itemId);
        if (distributionCenters == null) {
            throw new IllegalArgumentException("No distribution centers found for item " + itemId);
        }
        log.info("Centers: {}", distributionCenters);

        Item item = new Item();
        item.setId(itemId);
        item.setDistributionCenters(distributionCenters.getDistributionCenters());
        return item;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
