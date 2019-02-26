/*
 * Developed by Razil Minneakhmetov on 12/25/18 8:48 PM.
 * Last modified 12/25/18 8:48 PM.
 * Copyright © 2018. All rights reserved.
 */

package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@Builder
@ToString
public class VKUser {
    long id;
    String name;
    String surname;
    String photoUrl;
    List<Group> groups;
}