/*
 * Developed by Razil Minneakhmetov on 12/27/18 12:26 AM.
 * Last modified 12/27/18 12:26 AM.
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

@WebServlet("/ip")
public class IPServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("ip", Constants.IP);
        request.getRequestDispatcher("ftl/ip.ftl").forward(request, response);
    }
}