package com.challenge.distribution.centers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DistributionCentersDTO {

    @JsonProperty("distributionCenters")
    List<String> distributionCenters;
}
