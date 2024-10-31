package com.challenge.distribution.centers.service;

import com.challenge.distribution.centers.dto.DistributionCentersDTO;
import com.challenge.distribution.centers.dto.ItemResponseDTO;
import com.challenge.distribution.centers.dto.OrderRequestDTO;
import com.challenge.distribution.centers.dto.OrderResponseDTO;
import com.challenge.distribution.centers.model.Item;
import com.challenge.distribution.centers.model.Order;
import com.challenge.distribution.centers.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private DistributionCenterService distributionCenterService;

    @InjectMocks
    private OrderService orderService;

    private OrderRequestDTO orderRequestDTO;
    private DistributionCentersDTO distributionCentersDTO;

    @BeforeEach
    public void setUp() {
        orderRequestDTO = new OrderRequestDTO();
        orderRequestDTO.setItems(Collections.singletonList("123"));

        distributionCentersDTO = new DistributionCentersDTO();
        distributionCentersDTO.setDistributionCenters(List.of("CD1", "CD2", "CD3"));

        OrderResponseDTO orderResponseDTO = new OrderResponseDTO();
        orderResponseDTO.setOrderId("1");
        ItemResponseDTO itemResponseDTO = new ItemResponseDTO();
        itemResponseDTO.setItem("123");
        itemResponseDTO.setDistributionCenters(List.of("CD1", "CD2", "CD3"));
        orderResponseDTO.setItems(Collections.singletonList(itemResponseDTO));
    }

    @Test
    public void testProcessOrder() {
        when(distributionCenterService.getDistributionCentersByItem(anyString())).thenReturn(distributionCentersDTO);
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> {
            Order order = invocation.getArgument(0);
            order.setId("1");
            return order;
        });

        OrderResponseDTO response = orderService.processOrder(orderRequestDTO);

        assertNotNull(response);
        assertEquals("1", response.getOrderId());
        assertEquals(1, response.getItems().size());
        assertEquals("123", response.getItems().get(0).getItem());
        assertEquals(List.of("CD1", "CD2", "CD3"), response.getItems().get(0).getDistributionCenters());
    }

    @Test
    public void testValidateOrderItems_EmptyItems() {
        orderRequestDTO.setItems(Collections.emptyList());
        assertThrows(IllegalArgumentException.class, () -> orderService.validateOrderItems(orderRequestDTO), "Order must have at least one item");
    }

    @Test
    public void testValidateOrderItems_TooManyItems() {
        orderRequestDTO.setItems(Collections.nCopies(101, "item"));
        assertThrows(IllegalArgumentException.class, () -> orderService.validateOrderItems(orderRequestDTO), "Order must have at most 100 items");
    }

    @Test
    public void testValidateOrderItems_ValidItems() {
        orderRequestDTO.setItems(Collections.singletonList("item"));
        orderService.validateOrderItems(orderRequestDTO); // Should not throw any exception
    }

    @Test
    public void testCreateItem_NoDistributionCentersFound() {
        when(distributionCenterService.getDistributionCentersByItem(anyString())).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            orderService.createItem("nonexistentItem");
        }, "No distribution centers found for requestItem nonexistentItem");
    }

    @Test
    public void testGetAllOrders() {
        Order order = new Order();
        order.setId("1");
        Item item = new Item();
        item.setName("123");
        item.setDistributionCenters(List.of("CD1", "CD2", "CD3"));
        order.setItems(Collections.singletonList(item));

        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));

        List<OrderResponseDTO> responseList = orderService.getAllOrders();

        assertNotNull(responseList);
        assertEquals(1, responseList.size());
        assertEquals("1", responseList.get(0).getOrderId());
        assertEquals(1, responseList.get(0).getItems().size());
        assertEquals("123", responseList.get(0).getItems().get(0).getItem());
        assertEquals(List.of("CD1", "CD2", "CD3"), responseList.get(0).getItems().get(0).getDistributionCenters());
    }
}