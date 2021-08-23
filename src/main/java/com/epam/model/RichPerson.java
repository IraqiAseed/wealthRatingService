package com.epam.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RichPerson implements Serializable {
    @Id
    private long id;
    private String firstName;
    private String lastName;
    private double fortune;
}
