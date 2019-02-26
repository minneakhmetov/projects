/*
 * Developed by Razil Minneakhmetov on 12/26/18 1:13 AM.
 * Last modified 12/26/18 1:13 AM.
 * Copyright © 2018. All rights reserved.
 */

package tools;

import context.MyApplicationContext;
import dto.UserAndFriends;
import repositories.UsersRepository;
import services.UsersServices;

public class UserSaveTool {
    private UsersServices usersServices;

    public UserSaveTool(UsersServices usersServices) {
        this.usersServices = usersServices;
    }

    public void saveAll(UserAndFriends userAndFriends){
        MyApplicationContext.getInstance().setObject("all", 4);
        MyApplicationContext.getInstance().setObject("current", 0);
        usersServices.addUser(userAndFriends);
        MyApplicationContext.getInstance().setObject("current", 1);
        usersServices.addUsersGroups(userAndFriends);
        MyApplicationContext.getInstance().setObject("current", 2);
        usersServices.addUsersFriendsGroups(userAndFriends);
        MyApplicationContext.getInstance().setObject("current", 3);
        usersServices.addUserFriends(userAndFriends);
    }
}