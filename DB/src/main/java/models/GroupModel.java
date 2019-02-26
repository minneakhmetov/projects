/*
 * Developed by Razil Minneakhmetov on 12/26/18 12:43 AM.
 * Last modified 12/26/18 12:43 AM.
 * Copyright © 2018. All rights reserved.
 */

package models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
public class GroupModel {
    long id;
    long groupVkId;
    long userVkId;
    String name;
    String activity;
    String photoUrl;
}