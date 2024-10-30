package com.challenge.distribution.centers.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ItemResponseDTO {

    private String item;
    private List<String> distributionCenters;
}
