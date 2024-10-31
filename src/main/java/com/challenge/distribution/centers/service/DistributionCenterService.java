package com.challenge.distribution.centers.service;



import com.challenge.distribution.centers.dto.DistributionCentersDTO;
import lombok.AllArgsConstructor;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;


@Service
@AllArgsConstructor
public class DistributionCenterService {


    private final RestClient restClient;


    public DistributionCentersDTO getDistributionCentersByItem(String itemId) {
        return restClient.get()
                .uri("/distributionCenters?itemId=" + itemId)
                .header("Content-Type", "application/json")
                .retrieve()
                .body(DistributionCentersDTO.class);
    }

}
