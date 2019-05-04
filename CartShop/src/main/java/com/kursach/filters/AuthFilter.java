package com.kursach.filters;

import com.kursach.models.Auth;
import com.kursach.repositories.AuthRepository;
import com.kursach.repositories.AuthRepositoryImpl;
import com.kursach.services.AuthService;
import com.kursach.services.AuthServiceImpl;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthFilter implements Filter{

    private AuthService authService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setPassword("r1a2z3i4l5");
        dataSource.setUsername("postgres");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/cartshop");
        dataSource.setDriverClassName("org.postgresql.Driver");
        AuthRepository authRepository = new AuthRepositoryImpl(dataSource);
        authService = new AuthServiceImpl(authRepository);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        Cookie cookies[] = request.getCookies();
        if (cookies != null) {
            Integer vkId = null; String authValue = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("auth")) {
                    authValue = cookie.getValue();
                }
                if (cookie.getName().equals("vk_id")) {
                    vkId = Integer.valueOf(cookie.getValue());
                }
            }
            if (vkId != null && authValue != null){
                Auth auth = Auth.builder()
                        .auth(authValue)
                        .userId(vkId)
                        .build();
                if (authService.isExistByCookie(auth)) {
                    filterChain.doFilter(request, response);
                    return;
                }
            }

            response.sendRedirect("/main");
            return;
        }
        response.sendRedirect("/main");
    }

    @Override
    public void destroy() {

    }
}
