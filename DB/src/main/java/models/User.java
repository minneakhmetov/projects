/*
 * Developed by Razil Minneakhmetov on 12/26/18 12:11 AM.
 * Last modified 12/26/18 12:11 AM.
 * Copyright © 2018. All rights reserved.
 */

package models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class User {
    long id;
    long vkId;
    String name;
    String surname;
    String photoUrl;
}