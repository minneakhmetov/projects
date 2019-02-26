/*
 * Developed by Razil Minneakhmetov on 11/11/18 2:44 PM.
 * Last modified 11/11/18 2:44 PM.
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

@WebServlet("/deleteallcart")
public class DeleteAllCartServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Long userId = Long.valueOf(request.getParameter("userId"));
        CartServiceImpl cartServiceImpl = (CartServiceImpl) MyApplicationContext.getMyContext().getAttribute("cartService");
        cartServiceImpl.deleteAllCart(userId);
    }
}