/*
 * Developed by Razil Minneakhmetov on 12/27/18 3:12 AM.
 * Last modified 12/27/18 3:10 AM.
 * Copyright © 2018. All rights reserved.
 */

package filters;

import context.JavaBeans;
import models.Auth;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import services.SignInService;
import services.SimilarityService;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/loading")
public class LoadingFilter implements Filter {

    private SignInService signInService;

    public void init(FilterConfig filterConfig) throws ServletException {
        ApplicationContext contextBean = new
                AnnotationConfigApplicationContext(JavaBeans.class);

        signInService = contextBean.getBean("signInService", SignInService.class);
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        Cookie[] cookies = request.getCookies();
        Cookie cookie = null;
        if (cookies == null){
            response.sendRedirect("/signin");
            return;
        }
        for (int i = 0; i <cookies.length; i++){
            if (cookies[i].getName().equals("auth"))
                cookie = cookies[i];

        }
        if(cookie == null){
            response.sendRedirect("/signin");
        } else {
            try {
                signInService.auth(cookie.getValue());
                filterChain.doFilter(request, response);;
            }catch (EmptyResultDataAccessException e){
                response.sendRedirect("/signin");
            }
        }
    }

    public void destroy() {

    }
}