/*
 * Developed by Razil Minneakhmetov on 12/26/18 11:35 PM.
 * Last modified 12/26/18 11:35 PM.
 * Copyright © 2018. All rights reserved.
 */

package servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet({"/signin", ""})
public class SignInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("ftl/signin.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("ftl/signin.ftl").forward(request, response);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}