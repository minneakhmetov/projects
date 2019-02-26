/*
 * Developed by Razil Minneakhmetov on 10/28/18 8:39 PM.
 * Last modified 10/28/18 8:39 PM.
 * Copyright © 2018. All rights reserved.
 */

package servlets;

import context.MyApplicationContext;
import forms.LoginForm;
import services.ProductServiceImpl;
import services.VKAuthServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/getusers")
public class UsersServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        Integer chatId = Integer.valueOf(request.getParameter("chatId"));
        VKAuthServiceImpl vkAuthServiceImpl = (VKAuthServiceImpl) MyApplicationContext.getMyContext().getAttribute("vkAuthService");
        ProductServiceImpl service
                = (ProductServiceImpl) MyApplicationContext.getMyContext().getAttribute("productService");
        List<LoginForm> users = vkAuthServiceImpl.getUsers(code, chatId);
        service.addAll(users);
        Cookie cookie = new Cookie("code", "kjcbahsc");
        response.addCookie(cookie);
        response.sendRedirect("/main");
    }
}