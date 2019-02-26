/*
 * Developed by Razil Minneakhmetov on 10/24/18 10:30 PM.
 * Last modified 10/24/18 10:30 PM.
 * Copyright © 2018. All rights reserved.
 */

package models;


import lombok.*;


@Deprecated
@Getter
@Setter
@Builder
@Data
@EqualsAndHashCode
public class Avatar {
    Long id;
    String URL;
}