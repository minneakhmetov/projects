/*
 * Developed by Razil Minneakhmetov on 12/25/18 11:36 PM.
 * Last modified 12/25/18 11:36 PM.
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
import services.SignInService;
import services.SimilarityService;
import services.UsersServices;
import tools.UserSaveTool;
import vk.VKAuthService;

@Configuration
@PropertySource("classpath:application.properties")
public class JavaBeans {

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

    @Bean
    @SneakyThrows
    public DriverManagerDataSource dataSource() {
        Class.forName(driver);
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(databaseUrl + databaseName);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean
    public ActivityRepository activityRepository(){
        return new ActivityRepository(dataSource());
    }

    @Bean
    public UserGroupsFriendsRepository userGroupsFriendsRepository(){
        return new UserGroupsFriendsRepository(dataSource());
    }

    @Bean
    public UserGroupsRepository userGroupsRepository(){
        return new UserGroupsRepository(dataSource());
    }

    @Bean
    public UsersRepository usersRepository(){
        return new UsersRepository(dataSource());
    }

    @Bean
    public FriendsRepository friendsRepository(){return new FriendsRepository(dataSource()); }

    @Bean
    public SimilarityRepository similarityRepository(){
        return new SimilarityRepository(dataSource());
    }

    @Bean
    public DeleteRepository deleteRepository(){
        return new DeleteRepository(dataSource());
    }
    @Bean
    public UsersServices usersServices(){
        return new UsersServices(usersRepository(), userGroupsRepository(), userGroupsFriendsRepository(), friendsRepository());
    }
    @Bean
    public AuthRepository authRepository(){
        return new AuthRepository(dataSource());
    }
    @Bean
    public SignInService signInService(){
        return new SignInService(authRepository(), deleteRepository());
    }
    @Bean
    public VKAuthService vkAuthService(){
        return new VKAuthService();
    }

    @Bean
    public SimilarityService similarityService(){
        return new SimilarityService(similarityRepository(), userGroupsRepository(), userGroupsFriendsRepository(), friendsRepository());
    }

    @Bean
    public UserSaveTool userSaveTool(){
        return new UserSaveTool(usersServices());
    }
}