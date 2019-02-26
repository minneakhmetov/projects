/*
 * Developed by Razil Minneakhmetov on 12/25/18 5:47 PM.
 * Last modified 12/21/18 6:52 PM.
 * Copyright © 2018. All rights reserved.
 */

package vk;

import app.Constants;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.vk.api.sdk.client.Lang;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.Actor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiAccessException;
import com.vk.api.sdk.exceptions.ApiAccessGroupsException;
import com.vk.api.sdk.exceptions.ApiTooManyException;
import com.vk.api.sdk.exceptions.ApiUserDeletedException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.friends.FriendsGetQuery;
import com.vk.api.sdk.queries.users.UserField;
import context.MyApplicationContext;
import dto.Group;
import dto.UserAndFriends;
import dto.UserWithoutGroups;
import dto.VKUser;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class VKAuthService {


    UserActor actor = null;
    VkApiClient vk;

    public VKAuthService() {

    }

    @SneakyThrows
    public void init(String code) {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);

        UserAuthResponse authResponse = vk.oauth()
                .userAuthorizationCodeFlow(Constants.APP_ID, Constants.CLIENT_SECRET, Constants.REDIRECT_URI + "/login", code)
                .execute();

        actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
    }

    @SneakyThrows
    public UserWithoutGroups auth() {
        List<UserXtrCounters> users = vk.users().get(actor)
                .userIds(actor.getId().toString())
                .fields(UserField.VERIFIED, UserField.SEX, UserField.CITY, UserField.PHOTO_100)
                .lang(Lang.RU)
                .execute();
        UserWithoutGroups user = UserWithoutGroups.builder()
                .id(users.get(0).getId())
                .name(users.get(0).getFirstName())
                .surname(users.get(0).getLastName())
                .photoUrl(users.get(0).getPhoto100())
                .build();
        return user;
    }


    @SneakyThrows
    public VKUser getUser() {

        JsonElement jsonElement = vk.execute().code(actor, "return API.groups.get({\"user_id\":" + actor.getId() + ",extended:\"1\",\"fields\":\"id,name,activity\"});")
                .execute();

        JsonObject object = jsonElement.getAsJsonObject();

        JsonArray map = object.get("items").getAsJsonArray();

        List<Group> groups = new ArrayList<Group>();

        for (int i = 0; i < map.size(); i++) {
            try {
                Group group = Group.builder()
                        .name(map.get(i).getAsJsonObject().get("name").toString())
                        .activity(map.get(i).getAsJsonObject().get("activity").toString())
                        .id(Long.valueOf(map.get(i).getAsJsonObject().get("id").toString()))
                        .photoUrl(map.get(i).getAsJsonObject().get("photo_100").toString())
                        .build();
                groups.add(group);
            } catch (NullPointerException e) {

            }
        }
        List<UserXtrCounters> userList = vk.users().get(actor)
                .userIds(actor.getId().toString())
                .fields(UserField.VERIFIED, UserField.SEX, UserField.CITY, UserField.PHOTO_100)
                .lang(Lang.RU)
                .execute();
        UserXtrCounters user = userList.get(0);
        //Thread.sleep(1000);
        return VKUser.builder()
                .id(Long.valueOf(actor.getId()))
                .name(user.getFirstName())
                .surname(user.getLastName())
                .photoUrl(user.getPhoto100())
                .groups(groups)
                .build();
    }

    @SneakyThrows
    public List<VKUser> getUserFriends(List<UserXtrCounters> friends) {
        List<VKUser> friendsUsers = new ArrayList<VKUser>();
        Properties properties = (Properties) MyApplicationContext.getInstance().getObject("prop");

        MyApplicationContext.getInstance().setObject("all", friends.size());

        for (int i = 0; i < friends.size(); i++) {
            MyApplicationContext.getInstance().setObject("current", i);
            try {
                JsonElement jsonElement = vk.execute().code(actor, "return API.groups.get({\"user_id\":" + friends.get(i).getId() + ",extended:\"1\",\"fields\":\"id,name,activity\"});")
                        .execute();

                JsonObject object = jsonElement.getAsJsonObject();

                JsonArray map = object.get("items").getAsJsonArray();

                List<Group> groups = new ArrayList<Group>();

                for (int j = 0; j < map.size(); j++) {
                    try {
                        Group group = Group.builder()
                                .name(map.get(j).getAsJsonObject().get("name").toString())
                                .activity(map.get(j).getAsJsonObject().get("activity").toString())
                                .id(Long.valueOf(map.get(j).getAsJsonObject().get("id").toString()))
                                .photoUrl(map.get(j).getAsJsonObject().get("photo_100").toString())
                                .build();
                        groups.add(group);
                    } catch (NullPointerException e) {

                    }
                }
                VKUser user = VKUser.builder()
                        .id(friends.get(i).getId())
                        .name(friends.get(i).getFirstName())
                        .surname(friends.get(i).getLastName())
                        .photoUrl(friends.get(i).getPhoto100())
                        .groups(groups)
                        .build();

                friendsUsers.add(user);
            } catch (ApiUserDeletedException exception) {
            } catch (ApiAccessGroupsException e) {
            } catch (ApiAccessException exception){
            } catch (ApiTooManyException e) {
                Thread.sleep(1000);
                i--;

            }
        }
        return friendsUsers;
    }

    public UserAndFriends getFriendsUsers(VKUser vkUser, List<VKUser> friendsUsers) {
        return UserAndFriends.builder()
                .user(vkUser)
                .friends(friendsUsers)
                .build();
    }

    @SneakyThrows
    public List<UserXtrCounters> getFriends() {
        GetResponse getResponse = vk.friends()
                .get(actor)
                .execute();

        List<Integer> userIds = getResponse.getItems();


        StringBuilder ids = new StringBuilder();

        for (int i = 0; i < userIds.size(); i++) {
            ids.append(userIds.get(i).toString());
            if (i != userIds.size() - 1) {
                ids.append(",");
            }
        }



        List<UserXtrCounters> friends = vk.users().get(actor)
                .userIds(ids.toString())
                .fields(UserField.VERIFIED, UserField.SEX, UserField.CITY, UserField.PHOTO_100)
                .lang(Lang.RU)
                .execute();
        return friends;
    }

}