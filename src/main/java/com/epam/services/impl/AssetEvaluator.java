package com.epam.services.impl;

import com.epam.services.interfaces.Asset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Component
@PropertySource("classpath:application.properties")

public class AssetEvaluator implements Asset {

    @Autowired
    RestTemplate restTemplate;

    @Value("${central_bank_url}")
    private String centralBankUrl;

    @Override
    public Optional<Double> evaluateAssetPerCity(String city) {
        ResponseEntity<Double> response = restTemplate.getForEntity(centralBankUrl + "/regional-info/evaluate?city=" + city, Double.class);
        return Optional.ofNullable(response.getBody());
    }
}
