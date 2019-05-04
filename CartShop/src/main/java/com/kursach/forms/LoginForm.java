/*
 * Developed by Razil Minneakhmetov on 10/28/18 12:43 PM.
 * Last modified 10/28/18 12:43 PM.
 * Copyright © 2018. All rights reserved.
 */

package com.kursach.forms;

import lombok.*;

@Builder
@Setter
@Getter
@Data
@EqualsAndHashCode
public class LoginForm {
    Long vkId;
    String name;
    String photoURL;
}