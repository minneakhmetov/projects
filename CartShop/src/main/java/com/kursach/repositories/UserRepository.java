/*
 * Developed by Razil Minneakhmetov on 12/21/18 5:54 PM.
 * Last modified 12/21/18 5:54 PM.
 * Copyright © 2018. All rights reserved.
 */

package com.kursach.repositories;

import com.kursach.models.User;

public interface UserRepository {
    void create(User user);
    User readOne(Integer id);
}
