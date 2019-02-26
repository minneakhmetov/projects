/*
 * Developed by Razil Minneakhmetov on 12/21/18 6:47 PM.
 * Last modified 12/21/18 6:47 PM.
 * Copyright © 2018. All rights reserved.
 */

package services;

import forms.LoginForm;
import models.User;

public interface LoginService extends Service {
    User login(LoginForm loginForm);
}