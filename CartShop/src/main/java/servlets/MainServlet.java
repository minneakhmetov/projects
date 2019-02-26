/*
 * Developed by Razil Minneakhmetov on 10/28/18 2:02 PM.
 * Last modified 10/28/18 2:02 PM.
 * Copyright © 2018. All rights reserved.
 */

package servlets;

import context.MyApplicationContext;
import models.Product;
import models.ProductActivity;
import services.ProductServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet({"/main", ""})
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductServiceImpl service = (ProductServiceImpl) MyApplicationContext.getMyContext().getAttribute("productService");
        List<ProductActivity> products = service.getAll();
        request.setAttribute("products", products);
        request.getRequestDispatcher("/ftl/main.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}