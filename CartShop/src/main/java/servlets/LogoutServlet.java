/*
 * Developed by Razil Minneakhmetov on 10/28/18 2:19 PM.
 * Last modified 10/28/18 2:19 PM.
 * Copyright © 2018. All rights reserved.
 */

package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        for (int i = 0; i < cookies.length; i++){
            if(cookies[i].getName().equals("vk_id")) {
                cookies[i].setMaxAge(0);
                resp.addCookie(cookies[i]);
            }
            if(cookies[i].getName().equals("userPhoto")){
                cookies[i].setMaxAge(0);
                resp.addCookie(cookies[i]);
            }
            if(cookies[i].getName().equals("userName")){
                cookies[i].setMaxAge(0);
                resp.addCookie(cookies[i]);
            }
            if(cookies[i].getName().equals("code")){
                cookies[i].setMaxAge(0);
                resp.addCookie(cookies[i]);
            }
        }
        resp.sendRedirect("/main");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}