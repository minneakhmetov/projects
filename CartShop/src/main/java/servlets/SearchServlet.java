/*
 * Developed by Razil Minneakhmetov on 11/11/18 1:41 PM.
 * Last modified 11/11/18 1:41 PM.
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

@WebServlet("/search")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductServiceImpl service = (ProductServiceImpl) MyApplicationContext.getMyContext().getAttribute("productService");
        List<ProductActivity> products = service.find(request.getParameter("query"));
        request.setAttribute("products", products);
        request.setAttribute("query", request.getParameter("query"));
        request.setAttribute("size", products.size());
        request.getRequestDispatcher("/ftl/search.ftl").forward(request, response);

        //request.getRequestDispatcher("/jsp/search.jsp").forward(request, response);
    }
}