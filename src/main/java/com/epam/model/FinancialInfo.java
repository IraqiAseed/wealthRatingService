package com.epam.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinancialInfo {
    private double cash;
    private Integer numberOfAssets;
}
