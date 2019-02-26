/*
 * Developed by Razil Minneakhmetov on 10/25/18 7:34 PM.
 * Last modified 10/25/18 7:34 PM.
 * Copyright © 2018. All rights reserved.
 */

package models;

import lombok.*;

@Builder
@Getter
@Setter
@Data
@EqualsAndHashCode
public class Cart {
    Long userId;
    Long productId;
}