/*
 * Developed by Razil Minneakhmetov on 12/13/18 7:34 PM.
 * Last modified 12/13/18 7:34 PM.
 * Copyright © 2018. All rights reserved.
 */

package context;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import repositories.*;
import services.*;
import services.CartService;

import java.lang.reflect.Constructor;

@Configuration
@PropertySource("classpath:application.properties")
public class JavaConfig {

    @Value("${database.name}")
    private String databaseName;

    @Value("${database.url}")
    private String databaseUrl;

    @Value("${database.username}")
    private String userName;

    @Value("${database.password}")
    private String password;

    @Value("${database.driver}")
    private String driver;

    @Value("${repository.users}")
    private String usersRepository;

    @Value("${repository.cart}")
    private String cartRepository;

    @Value("${repository.product}")
    private String productRepository;

    @Value("${service.cart}")
    private String cartService;

    @Value("${service.login}")
    private String loginService;

    @Value("${service.product}")
    private String productService;

    @Value("${service.vkAuth}")
    private String vkAuthService;

    @Value("${repository.activity}")
    private String activityRepository;


    @Bean
    @SneakyThrows
    public DriverManagerDataSource dataSource(){
        Class.forName(driver);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(databaseUrl + databaseName);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    @SneakyThrows
    public repositories.CartRepository cartRepository(){
        Class<repositories.CartRepository> cartRepositoryClass = (Class<repositories.CartRepository>) Class.forName(cartRepository);
        Constructor<repositories.CartRepository> terminalConstructor = cartRepositoryClass.getConstructor(DriverManagerDataSource.class);
        return terminalConstructor.newInstance(dataSource());
    }

    @Bean
    @SneakyThrows
    public ActivityRepository activityRepository(){
        Class<repositories.ActivityRepository> cartRepositoryClass = (Class<repositories.ActivityRepository>) Class.forName(activityRepository);
        Constructor<repositories.ActivityRepository> terminalConstructor = cartRepositoryClass.getConstructor(DriverManagerDataSource.class);
        return terminalConstructor.newInstance(dataSource());
    }

    @Bean
    public AvatarRepository avatarRepository(){
        return new AvatarRepository(dataSource());
    }

    @Bean
    @SneakyThrows
    public ProductRepository productRepository(){
        Class<ProductRepository> productRepositoryClass = (Class<ProductRepository>) Class.forName(productRepository);
        Constructor<ProductRepository> terminalConstructor = productRepositoryClass.getConstructor(DriverManagerDataSource.class);
        return terminalConstructor.newInstance(dataSource());
    }

    @Bean
    @SneakyThrows
    public UserRepository userRepository(){
        Class<UserRepository> userRepositoryClass = (Class<UserRepository>) Class.forName(usersRepository);
        Constructor<UserRepository> terminalConstructor = userRepositoryClass.getConstructor(DriverManagerDataSource.class);
        return terminalConstructor.newInstance(dataSource());
    }

    @Bean
    @SneakyThrows
    public CartService cartService(){
        Class<CartService> cartServiceClass = (Class<CartService>) Class.forName(cartService);
        Constructor<CartService> terminalConstructor = cartServiceClass.getConstructor(repositories.CartRepository.class);
        return terminalConstructor.newInstance(cartRepository());
    }

    @Bean
    @SneakyThrows
    public ProductService productService(){
        Class<ProductService> cartServiceClass = (Class<ProductService>) Class.forName(productService);
        Constructor<ProductService> terminalConstructor = cartServiceClass.getConstructor(ProductRepository.class, CartRepository.class, ActivityRepository.class);
        return terminalConstructor.newInstance(productRepository(), cartRepository(), activityRepository());
    }

    @Bean
    @SneakyThrows
    public LoginService loginService(){
        Class<LoginService> cartServiceClass = (Class<LoginService>) Class.forName(loginService);
        Constructor<LoginService> terminalConstructor = cartServiceClass.getConstructor(UserRepository.class);
        return terminalConstructor.newInstance(userRepository());
    }

    @Bean
    @SneakyThrows
    public VKAuthService vkAuthService(){
        Class<VKAuthService> cartServiceClass = (Class<VKAuthService>) Class.forName(vkAuthService);
        Constructor<VKAuthService> terminalConstructor = cartServiceClass.getConstructor();
        return terminalConstructor.newInstance();
    }

}