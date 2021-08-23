package com.epam.services.interfaces;

import java.util.Optional;

public interface Asset {
    Optional<Double> evaluateAssetPerCity(String city);
}
