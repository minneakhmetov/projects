package com.spinach.app;

import com.spinach.utils.PathUtil;
import lombok.SneakyThrows;
//import org.slf4j.LoggerFactory;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ComponentScan(basePackages = "com.spinach")
public class Application {

    @Autowired
    private PathUtil pathUtil;

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);

       // LoggerFactory.getLogger(Application.class).info("info");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    @SneakyThrows
    public DriverManagerDataSource dataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(pathUtil.getProperty("spinach.datasource.driver"));
        dataSource.setUsername(pathUtil.getProperty("spinach.datasource.username"));
        dataSource.setPassword(pathUtil.getProperty("spinach.datasource.password"));
        dataSource.setUrl(pathUtil.getProperty("spinach.datasource.url"));
        return dataSource;
    }

//@Bean
//    public EmbeddedServletContainerFactory servletContainer() {
//        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
//            @Override
//            protected void postProcessContext(Context context) {
//                SecurityConstraint securityConstraint = new SecurityConstraint();
//                securityConstraint.setUserConstraint("CONFIDENTIAL");
//                SecurityCollection collection = new SecurityCollection();
//                collection.addPattern("/*");
//                securityConstraint.addCollection(collection);
//                context.addConstraint(securityConstraint);
//            }
//        };
//        tomcat.addAdditionalTomcatConnectors(getHttpConnector());
//        return tomcat;
//    }
//
//    private Connector getHttpConnector() {
//        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
//        connector.setScheme("http");
//        connector.setPort(80);
//        connector.setSecure(false);
//        connector.setRedirectPort(8443);
//        return connector;
//    }

//    @Bean
//    @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
//    public SimilaritySingleton getSimilarity(){
//        return new SimilaritySingleton();
//    }


}
