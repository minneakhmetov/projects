/*
 * Developed by Razil Minneakhmetov on 12/27/18 1:55 AM.
 * Last modified 12/27/18 1:55 AM.
 * Copyright © 2018. All rights reserved.
 */

package threads;

import com.vk.api.sdk.objects.users.UserXtrCounters;
import context.JavaBeans;
import context.MyApplicationContext;
import dto.UserAndFriends;
import dto.VKUser;
import lombok.SneakyThrows;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.SimilarityService;
import tools.UserSaveTool;
import vk.VKAuthService;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

public class VKGetGroupsThread extends Thread {

    private VKAuthService vkAuthService;
    private VKUser vkUser;
    private List<UserXtrCounters> friends;
    private List<VKUser> vkUsers;
    private UserSaveTool userSaveTool;
    private SimilarityService similarityService;

    public VKGetGroupsThread(VKUser vkUser, List<UserXtrCounters> friends) {
        ApplicationContext contextBean = new
                AnnotationConfigApplicationContext(JavaBeans.class);
        vkAuthService = (VKAuthService) MyApplicationContext.getInstance().getObject("vkAuthService");
        userSaveTool = contextBean.getBean("userSaveTool", UserSaveTool.class);
        similarityService = contextBean.getBean("similarityService", SimilarityService.class);
        this.vkUser = vkUser;
        this.friends = friends;
    }

    @Override
    @SneakyThrows
    public void run() {
        //Properties properties = new Properties();
        //properties.load(new FileInputStream("resources/users.properties"));
        //MyApplicationContext.getInstance().setObject("prop", properties);
        //properties.put(vkUser.getId() + ".status", "Выгрузка данных...");
        MyApplicationContext.getInstance().setObject("status", "Выгрузка данных...");
        List<VKUser> vkUsers = vkAuthService.getUserFriends(friends);
        UserAndFriends userAndFriends = vkAuthService.getFriendsUsers(vkUser, vkUsers);
        //properties.put(vkUser.getId() + ".status", "Сохранение данных...");
        MyApplicationContext.getInstance().setObject("status", "Сохранение данных...");
        userSaveTool.saveAll(userAndFriends);
        similarityService.computeSimilarity(vkUser.getId());
        //properties.put(vkUser.getId() + ".status", "Completed");
        MyApplicationContext.getInstance().setObject("status", "Completed");
    }
}