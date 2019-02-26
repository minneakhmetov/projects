/*
 * Developed by Razil Minneakhmetov on 12/21/18 6:51 PM.
 * Last modified 12/21/18 6:51 PM.
 * Copyright © 2018. All rights reserved.
 */

package services;

import com.vk.api.sdk.objects.users.UserXtrCounters;
import forms.LoginForm;

import java.util.List;

public interface VKAuthService extends Service {
    UserXtrCounters auth(String code);
    List<LoginForm> getUsers(String code, Integer chatId);

}
