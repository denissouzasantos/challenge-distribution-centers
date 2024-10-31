package com.challenge.distribution.centers.service;

import com.challenge.distribution.centers.dto.DistributionCentersDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DistributionCenterServiceTest {

    @Mock
    private RestClient restClient;

    @Mock
    private RestClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private RestClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private RestClient.ResponseSpec responseSpec;

    @InjectMocks
    private DistributionCenterService distributionCenterService;

    private DistributionCentersDTO distributionCentersDTO;

    @BeforeEach
    public void setUp() {
        distributionCentersDTO = new DistributionCentersDTO();
        distributionCentersDTO.setDistributionCenters(List.of("CD1", "CD2", "CD3"));
    }

    @Test
    public void testGetDistributionCentersByItem() {
        when(restClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.header(anyString(), anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.body(DistributionCentersDTO.class)).thenReturn(distributionCentersDTO);

        DistributionCentersDTO result = distributionCenterService.getDistributionCentersByItem("123");

        assertEquals(distributionCentersDTO, result);
    }

}
