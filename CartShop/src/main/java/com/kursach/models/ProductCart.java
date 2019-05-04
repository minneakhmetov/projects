package com.kursach.models;
import lombok.*;
@Getter
@Setter
@Builder
@Data
@EqualsAndHashCode
public class ProductCart {
    Long id;
    String price;
    Long productId;
    User user;
    String activity;
}

