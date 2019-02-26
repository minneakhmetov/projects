/*
 * Developed by Razil Minneakhmetov on 10/29/18 12:41 PM.
 * Last modified 10/29/18 12:41 PM.
 * Copyright © 2018. All rights reserved.
 */

package servlets;

import context.MyApplicationContext;
import services.CartServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/deletecart")
public class DeleteCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Long productId = Long.valueOf(request.getParameter("productId"));
        Long userId = Long.valueOf(request.getParameter("userId"));
        CartServiceImpl cartServiceImpl = (CartServiceImpl) MyApplicationContext.getMyContext().getAttribute("cartService");
        cartServiceImpl.deleteFromCart(productId, userId);


        response.sendError(200);
    }
}