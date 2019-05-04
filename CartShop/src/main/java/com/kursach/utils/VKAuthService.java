/*
 * Developed by Razil Minneakhmetov on 10/28/18 12:36 PM.
 * Last modified 10/28/18 12:36 PM.
 * Copyright © 2018. All rights reserved.
 */

package com.kursach.utils;

import com.kursach.app.Constants;
import com.kursach.dto.UserDto;
import com.vk.api.sdk.client.Lang;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.queries.users.UserField;
import lombok.Getter;
import lombok.SneakyThrows;

import java.util.List;

public class VKAuthService {

    private VkApiClient vk;
    private UserAuthResponse authResponse;

    @Getter
    private Integer userId;

    @SneakyThrows
    public VKAuthService(String code) {
        TransportClient transportClient = HttpTransportClient.getInstance();
        vk = new VkApiClient(transportClient);
        authResponse = vk.oauth()
                .userAuthorizationCodeFlow(Constants.APP_ID, Constants.CLIENT_SECRET, Constants.REDIRECT_URI + "/login", code)
                .execute();
        userId = authResponse.getUserId();
    }

    @SneakyThrows
    public UserDto auth() {
        UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
        List<UserXtrCounters> users = vk.users().get(actor)
                .userIds(authResponse.getUserId().toString())
                .fields(UserField.VERIFIED, UserField.SEX, UserField.CITY, UserField.PHOTO_200)
                .lang(Lang.EN)
                .execute();
        return UserDto.builder()
                .name(users.get(0).getFirstName())
                .surname(users.get(0).getLastName())
                .photoURL(users.get(0).getPhoto200())
                .vkId(users.get(0).getId())
                .build();
    }



}