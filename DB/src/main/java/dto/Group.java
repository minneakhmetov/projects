/*
 * Developed by Razil Minneakhmetov on 12/25/18 8:46 PM.
 * Last modified 12/25/18 8:46 PM.
 * Copyright © 2018. All rights reserved.
 */

package dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Group {
    long id;
    String name;
    String activity;
    String photoUrl;
}