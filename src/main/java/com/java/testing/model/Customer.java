package com.java.testing.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
public class Customer {
    private int id;
    private String name;
    private String email;
    private double balance;
}
