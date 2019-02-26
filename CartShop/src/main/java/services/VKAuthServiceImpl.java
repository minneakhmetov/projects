/*
 * Developed by Razil Minneakhmetov on 10/28/18 12:36 PM.
 * Last modified 10/28/18 12:36 PM.
 * Copyright © 2018. All rights reserved.
 */

package services;

import app.Constants;
import com.vk.api.sdk.actions.Messages;
import com.vk.api.sdk.client.Lang;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.messages.Chat;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import com.vk.api.sdk.queries.messages.MessagesGetByIdQuery;
import com.vk.api.sdk.queries.messages.MessagesGetChatQueryWithChatIds;
import com.vk.api.sdk.queries.messages.MessagesGetQuery;
import com.vk.api.sdk.queries.users.UserField;
import forms.LoginForm;
import lombok.SneakyThrows;
import models.User;

import javax.servlet.annotation.WebServlet;
import java.util.ArrayList;
import java.util.List;

public class VKAuthServiceImpl implements VKAuthService{


    public VKAuthServiceImpl() {

    }

    @SneakyThrows
    public UserXtrCounters auth(String code) {
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);


        UserAuthResponse authResponse = vk.oauth()
                .userAuthorizationCodeFlow(Constants.APP_ID, Constants.CLIENT_SECRET, Constants.REDIRECT_URI + "/login", code)
                .execute();

        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());

        List<UserXtrCounters> users = vk.users().get(actor)
                .userIds(authResponse.getUserId().toString())
                .fields(UserField.VERIFIED, UserField.SEX, UserField.CITY, UserField.PHOTO_50)
                .lang(Lang.EN)
                .execute();
        System.out.println(authResponse.getAccessToken());

//        Messages messages = new Messages(vk);
//        MessagesGetQuery query = messages.get(actor);
//        GetResponse response = query.execute();


//        GetResponse getResponse = vk.wall().get(actor)
//                .ownerId(1)
//                .count(100)
//                .offset(5)
//                .execute();
//
//        MessagesGetChatQueryWithChatIds a = vk.messages().getChat(actor, 136);
//        List<Chat> chats = a.execute();
//        System.out.println(chats.toString());


        return users.get(0);
    }

    @SneakyThrows
    public List<LoginForm> getUsers(String code, Integer chatId) {
        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);

        UserAuthResponse authResponse = vk.oauth()
                .userAuthorizationCodeFlow(Constants.APP_ID, Constants.CLIENT_SECRET, Constants.REDIRECT_URI_VK, code)
                .execute();

        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
        MessagesGetChatQueryWithChatIds a = vk.messages().getChat(actor, chatId);

        List<Chat> chats = a.execute();
        Chat chat = chats.get(0);
        List<Integer> userIds = chat.getUsers();
        List<LoginForm> users = new ArrayList<>();

        StringBuilder ids = new StringBuilder();

        for (int i = 0; i < userIds.size(); i++) {
            ids.append(userIds.get(i).toString());
            if (i != userIds.size() - 1) {
                ids.append(",");
            }
        }


        List<UserXtrCounters> userXtrCounters = vk.users().get(actor)
                .userIds(ids.toString())
                .fields(UserField.VERIFIED, UserField.SEX, UserField.CITY, UserField.PHOTO_100)
                .lang(Lang.EN)
                .execute();

        for (int i = 0; i < userXtrCounters.size(); i++) {
            UserXtrCounters user = userXtrCounters.get(i);
            LoginForm form = LoginForm.builder()
                    .name(user.getFirstName() + " " + user.getLastName())
                    .vkId(Long.valueOf(user.getId()))
                    .photoURL(user.getPhoto100())
                    .build();
            users.add(form);
        }
        return users;
    }

}