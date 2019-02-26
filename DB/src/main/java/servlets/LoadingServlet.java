/*
 * Developed by Razil Minneakhmetov on 12/27/18 3:18 AM.
 * Last modified 12/27/18 3:18 AM.
 * Copyright © 2018. All rights reserved.
 */

package servlets;

import context.MyApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/loading")
public class LoadingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String string = (String) MyApplicationContext.getInstance().getObject("status");
        if(!string.equals("Completed")) {
            request.setAttribute("status", MyApplicationContext.getInstance().getObject("status"));
            request.setAttribute("current", MyApplicationContext.getInstance().getObject("current"));
            request.setAttribute("all", MyApplicationContext.getInstance().getObject("all"));
            request.getRequestDispatcher("ftl/loading.ftl").forward(request, response);
        } else response.sendRedirect("/similarity");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}