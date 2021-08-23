package com.epam.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private long id;
    private PersonalInfo personalInfo;
    private FinancialInfo financialInfo;
}
