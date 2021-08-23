package com.epam.model;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalInfo {
    private String firstName;
    private String lastName;
    private String city;
}
