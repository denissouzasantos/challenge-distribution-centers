package com.challenge.distribution.centers.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequestDTO {

    private List<String> items;
}
