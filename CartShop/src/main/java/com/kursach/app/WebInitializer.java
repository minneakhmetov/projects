package com.kursach.app;

import com.kursach.filters.AuthFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

public class WebInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        AnnotationConfigWebApplicationContext context
                = new AnnotationConfigWebApplicationContext();
        context.register(AppConfig.class);

        context.setServletContext(container);
        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        ServletRegistration.Dynamic servlet = container.addServlet("dispatcher", dispatcherServlet);
        servlet.setLoadOnStartup(1);
        servlet.addMapping("/");
        container.addFilter("authFilter", AuthFilter.class).addMappingForUrlPatterns(null, false, "/cart", "/deleteallcart", "/deletecart", "/logout", "/search", "/usercart", "/getUser", "/deleteBuyer", "/deleteSeller", "/checkouted", "/createservice" );
    }


}
