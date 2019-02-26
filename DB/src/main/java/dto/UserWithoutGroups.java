/*
 * Developed by Razil Minneakhmetov on 12/27/18 1:00 AM.
 * Last modified 12/27/18 1:00 AM.
 * Copyright © 2018. All rights reserved.
 */

package dto;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class UserWithoutGroups {
    long id;
    String name;
    String surname;
    String photoUrl;
}