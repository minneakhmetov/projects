/*
 * Developed by Razil Minneakhmetov on 12/26/18 11:00 PM.
 * Last modified 12/26/18 11:00 PM.
 * Copyright © 2018. All rights reserved.
 */

package servlets;

import context.JavaBeans;
import models.Auth;
import models.SimilarityUsers;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.SignInService;
import services.SimilarityService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/similarity")
public class SimilarityServlet extends HttpServlet {
    private SimilarityService similarityService;
    private SignInService signInService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        for (int i = 0; i <cookies.length; i++){
            if (cookies[i].getName().equals("auth"))
                cookie = cookies[i];

        }
        Auth auth = signInService.auth(cookie.getValue());
        List<SimilarityUsers> users = similarityService.getWithNoAndPercents(auth.getVkId());
        List<SimilarityUsers> usersDistinct = new ArrayList<SimilarityUsers>();
        for(int i = 0; i < users.size(); i++){
            if (!usersDistinct.contains(users.get(i)))
                usersDistinct.add(users.get(i));
        }
        request.setAttribute("similarityUsers", usersDistinct);
        request.getRequestDispatcher("ftl/similarities.ftl").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init() throws ServletException {
        ApplicationContext contextBean = new
                AnnotationConfigApplicationContext(JavaBeans.class);
        similarityService = contextBean.getBean("similarityService", SimilarityService.class);
        signInService = contextBean.getBean("signInService", SignInService.class);
    }
}