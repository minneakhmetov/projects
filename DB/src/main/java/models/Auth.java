/*
 * Developed by Razil Minneakhmetov on 12/27/18 12:48 AM.
 * Last modified 12/27/18 12:48 AM.
 * Copyright © 2018. All rights reserved.
 */

package models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Auth {
    long vkId;
    String auth;

}