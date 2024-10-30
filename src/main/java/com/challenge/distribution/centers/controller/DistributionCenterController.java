package com.challenge.distribution.centers.controller;

import com.challenge.distribution.centers.dto.DistributionCentersDTO;
import com.challenge.distribution.centers.service.DistributionCenterService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/distributionCenters")
public class DistributionCenterController {

    private final DistributionCenterService distributionCenterService;

    @GetMapping(value = "/{itemId}")
    public DistributionCentersDTO getDistributionCentersByItem(@PathVariable String itemId) {
        return distributionCenterService.getDistributionCentersByItem(itemId);
    }
}
