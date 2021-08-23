package com.epam.services.impl;

import com.epam.services.interfaces.WealthThreshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
public class CentralBankWealthThreshold implements WealthThreshold {

    @Autowired
    RestTemplate restTemplate;

    @Value("${central_bank_url}")
    private String centralBankUrl;

    @Override
    public Optional<Double> wealthThreshold() {

        ResponseEntity<Double> response = restTemplate.getForEntity(centralBankUrl + "/wealth-threshold", Double.class);
        return Optional.ofNullable(response.getBody());


    }
}


