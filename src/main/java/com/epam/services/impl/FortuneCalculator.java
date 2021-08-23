package com.epam.services.impl;

import com.epam.services.interfaces.Fortune;
import org.springframework.stereotype.Component;

@Component
public class FortuneCalculator implements Fortune {

    @Override
    public double fortune(double cash, Integer numberOfAssets, double evaluateResponse) {
        return cash + numberOfAssets * evaluateResponse;
    }
}
