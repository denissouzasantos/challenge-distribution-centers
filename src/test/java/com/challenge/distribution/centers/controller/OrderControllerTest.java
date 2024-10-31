package com.challenge.distribution.centers.controller;

import com.challenge.distribution.centers.dto.ItemResponseDTO;
import com.challenge.distribution.centers.dto.OrderRequestDTO;
import com.challenge.distribution.centers.dto.OrderResponseDTO;
import com.challenge.distribution.centers.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    private OrderResponseDTO orderResponseDTO;

    @BeforeEach
    public void setUp() {
        orderResponseDTO = createOrderResponseDTO();
    }

    private OrderResponseDTO createOrderResponseDTO() {
        OrderResponseDTO responseDTO = new OrderResponseDTO();
        responseDTO.setOrderId("1");
        ItemResponseDTO itemResponseDTO = new ItemResponseDTO();
        itemResponseDTO.setItem("123");
        itemResponseDTO.setDistributionCenters(List.of("CD1", "CD2", "CD3"));
        responseDTO.setItems(Collections.singletonList(itemResponseDTO));
        return responseDTO;
    }

    @Test
    public void testProcessOrder() throws Exception {
        when(orderService.processOrder(any(OrderRequestDTO.class))).thenReturn(orderResponseDTO);

        mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\n" +
                                "    \"items\": [\n" +
                                "        \"123\"\n" +
                                "    ]\n" +
                                "}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("{\n" +
                        "    \"orderId\": \"1\",\n" +
                        "    \"items\": [\n" +
                        "        {\n" +
                        "            \"item\": \"123\",\n" +
                        "            \"distributionCenters\": [\n" +
                        "                \"CD1\",\n" +
                        "                \"CD2\",\n" +
                        "                \"CD3\"\n" +
                        "            ]\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}"));
    }

    @Test
    public void testGetAllOrders() throws Exception {
        when(orderService.getAllOrders()).thenReturn(Collections.singletonList(orderResponseDTO));

        mockMvc.perform(get("/order")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[{\n" +
                        "    \"orderId\": \"1\",\n" +
                        "    \"items\": [\n" +
                        "        {\n" +
                        "            \"item\": \"123\",\n" +
                        "            \"distributionCenters\": [\n" +
                        "                \"CD1\",\n" +
                        "                \"CD2\",\n" +
                        "                \"CD3\"\n" +
                        "            ]\n" +
                        "        }\n" +
                        "    ]\n" +
                        "}]"));
    }
}
