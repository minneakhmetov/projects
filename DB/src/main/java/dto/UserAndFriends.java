/*
 * Developed by Razil Minneakhmetov on 12/25/18 9:34 PM.
 * Last modified 12/25/18 9:34 PM.
 * Copyright © 2018. All rights reserved.
 */

package dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class UserAndFriends {
    VKUser user;
    List<VKUser> friends;
}