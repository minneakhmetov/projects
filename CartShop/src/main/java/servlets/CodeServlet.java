/*
 * Developed by Razil Minneakhmetov on 10/28/18 8:26 PM.
 * Last modified 10/28/18 8:26 PM.
 * Copyright © 2018. All rights reserved.
 */

package servlets;

import app.Constants;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/code")
public class CodeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.sendRedirect("https://oauth.vk.com/authorize?client_id=" + Constants.APP_ID +
//                "&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=messages&response_type=code&v=5.87");
        response.sendRedirect("https://oauth.vk.com/authorize?client_id=" + Constants.APP_ID +
                "&display=page&redirect_uri=" + Constants.REDIRECT_URI_VK +
                "&scope=messages&response_type=code&v=5.87");

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
}