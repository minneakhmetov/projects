/*
 * Developed by Razil Minneakhmetov on 10/24/18 10:28 PM.
 * Last modified 10/24/18 10:28 PM.
 * Copyright © 2018. All rights reserved.
 */

package models;

import lombok.*;

@Getter
@Setter
@Builder
@Data
@EqualsAndHashCode
public class Product {
    Long id;
    String name;
    String price;
    String avatar;
    Long activity;
}