/*
 * Developed by Razil Minneakhmetov on 12/26/18 12:22 AM.
 * Last modified 12/26/18 12:22 AM.
 * Copyright © 2018. All rights reserved.
 */

package services;

import com.vk.api.sdk.objects.account.UserSettings;
import context.JavaBeans;
import dto.Group;
import dto.UserAndFriends;
import dto.VKUser;
import models.GroupModel;
import models.GroupModelFriends;
import models.User;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repositories.*;

import java.util.ArrayList;
import java.util.List;

public class UsersServices {

    private UsersRepository usersRepository;
    private UserGroupsRepository userGroupsRepository;
    private UserGroupsFriendsRepository userGroupsFriendsRepository;
    private FriendsRepository friendsRepository;

    public UsersServices(UsersRepository usersRepository,
                         UserGroupsRepository userGroupsRepository,
                         UserGroupsFriendsRepository userGroupsFriendsRepository,
                         FriendsRepository friendsRepository) {
        this.usersRepository = usersRepository;
        this.userGroupsRepository = userGroupsRepository;
        this.userGroupsFriendsRepository = userGroupsFriendsRepository;
        this.friendsRepository = friendsRepository;

    }

    public void addUser(UserAndFriends userAndFriends) {
        VKUser vkUser = userAndFriends.getUser();
        User user = User.builder()
                .name(vkUser.getName())
                .surname(vkUser.getSurname())
                .vkId(vkUser.getId())
                .photoUrl(vkUser.getPhotoUrl())
                .build();
        usersRepository.save(user);
    }

    public void addUsersGroups(UserAndFriends userAndFriends) {
        List<Group> groups = userAndFriends.getUser().getGroups();
        List<GroupModel> groupModels = new ArrayList<GroupModel>();
        for (int i = 0; i < groups.size(); i++) {
            GroupModel groupModel = GroupModel.builder()
                    .groupVkId(groups.get(i).getId())
                    .activity(groups.get(i).getActivity())
                    .name(groups.get(i).getName())
                    .photoUrl(groups.get(i).getPhotoUrl())
                    .userVkId(userAndFriends.getUser().getId())
                    .build();
            groupModels.add(groupModel);
        }
        userGroupsRepository.saveAll(groupModels);

    }

    public void addUsersFriendsGroups(UserAndFriends userAndFriends) {
        List<VKUser> friends = userAndFriends.getFriends();
        List<GroupModelFriends> groupModels = new ArrayList<GroupModelFriends>();
        for (int i = 0; i < friends.size(); i++) {
            List<Group> groups = friends.get(i).getGroups();
            for (int j = 0; j < groups.size(); j++) {
                GroupModelFriends groupModel = GroupModelFriends.builder()
                        .groupVkId(groups.get(j).getId())
                        .friendVkId(friends.get(i).getId())
                        .activity(groups.get(j).getActivity())
                        .name(groups.get(j).getName())
                        .photoUrl(groups.get(j).getPhotoUrl())
                        .userVkId(userAndFriends.getUser().getId())
                        .build();
                groupModels.add(groupModel);
            }
        }
        userGroupsFriendsRepository.saveAll(groupModels);
    }

    public void addUserFriends(UserAndFriends userAndFriends) {
        List<VKUser> friends = userAndFriends.getFriends();
        List<User> users = new ArrayList<User>();
        for (int i = 0; i < friends.size(); i++) {
            User user = User.builder()
                    .name(friends.get(i).getName())
                    .surname(friends.get(i).getSurname())
                    .photoUrl(friends.get(i).getPhotoUrl())
                    .vkId(friends.get(i).getId())
                    .build();
            users.add(user);
        }
        friendsRepository.saveAll(users, userAndFriends.getUser().getId());

    }

}