package com.kursach.dto;

import com.kursach.models.Product;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Builder
@Data
@EqualsAndHashCode
public class ProductDtoString {
    String id;
    String name;
    String price;
    String avatar;
    String activity;

    public static ProductDtoString from(Product product) {
        return ProductDtoString.builder()
                .id(String.valueOf(product.getId()))
                .activity(product.getActivity())
                .name(product.getName())
                .price(product.getPrice())
                .avatar(product.getAvatar())
                .build();
    }

    public static List<ProductDtoString> from(List<Product> products) {
        List<ProductDtoString> result = new ArrayList<>();
        for (Product product : products) {
            result.add(ProductDtoString.builder()
                    .id(String.valueOf(product.getId()))
                    .activity(product.getActivity())
                    .name(product.getName())
                    .price(product.getPrice())
                    .avatar(product.getAvatar())
                    .build());
        }
        return result;
    }
}

