/*
 * Developed by Razil Minneakhmetov on 12/25/18 5:57 PM.
 * Last modified 12/22/18 4:12 PM.
 * Copyright © 2018. All rights reserved.
 */

package servlets;

//import context.MyApplicationContext;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import context.JavaBeans;
import context.MyApplicationContext;
import dto.UserWithoutGroups;
import dto.VKUser;
import lombok.SneakyThrows;
//import models.User;
//import vk.LoginServiceImpl;
import models.Auth;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import services.SignInService;
import threads.VKGetGroupsThread;
import tools.UserSaveTool;
import vk.VKAuthService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private VKAuthService vkAuthService;
    private SignInService signInService;

    @Override
    public void init() throws ServletException {
        ApplicationContext contextBean = new
                AnnotationConfigApplicationContext(JavaBeans.class);
        vkAuthService = contextBean.getBean("vkAuthService", VKAuthService.class);
        signInService = contextBean.getBean("signInService", SignInService.class);

    }

    @Override
    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        vkAuthService.init(code);
        MyApplicationContext.getInstance().setObject("vkAuthService", vkAuthService);
        UserWithoutGroups user = vkAuthService.auth();

        if(!signInService.checkRegistered(user.getId())) {
            VKUser vkUser = vkAuthService.getUser();
            List<UserXtrCounters> friends = vkAuthService.getFriends();
            VKGetGroupsThread thread = new VKGetGroupsThread(vkUser, friends);
            thread.start();
            Auth auth = Auth.builder()
                    .auth(UUID.randomUUID().toString())
                    .vkId(user.getId())
                    .build();
            signInService.signIn(auth);
            Cookie cookie = new Cookie("auth", auth.getAuth());
            cookie.setMaxAge(60*60*60*24 * 360);
            response.addCookie(cookie);

        }
        response.sendRedirect("/loading");






        //ServletContext context = getServletContext();
        //VKAuthService vkAuthServiceImpl = new VKAuthService(); //(VKAuthService) MyApplicationContext.getMyContext().getAttribute("vkAuthService");
        //UserXtrCounters VKUser = vkAuthServiceImpl.auth(code);
        //UserAndFriends userAndFriends = vkAuthServiceImpl.getUsers(code);
        //userSaveTool.saveAll(userAndFriends);

        //System.out.println(ids);
        //PrintWriter writer = response.getWriter();
        //writer.print(new Gson().toJson(ids));

//        LoginForm loginForm = LoginForm.builder()
//                .vkId(Long.valueOf(VKUser.getId()))
//                .name(VKUser.getFirstName())
//                .photoURL(VKUser.getPhoto50())
//                .build();
//        LoginServiceImpl loginServiceImpl = (LoginServiceImpl) MyApplicationContext.getMyContext().getAttribute("loginService");
//
//        User user = loginServiceImpl.login(loginForm);
//        Cookie vkId = new Cookie("vk_id", user.getVkId().toString());
//        vkId.setMaxAge(60*60*24);
//        Cookie userName = new Cookie("userName", user.getName());
//        userName.setMaxAge(60*60*24);
//        Cookie userPhoto = new Cookie("userPhoto", user.getPhotoURL());
//        userName.setMaxAge(60*60*24);
//        response.addCookie(vkId);
//        response.addCookie(userPhoto);
//        response.addCookie(userName);
//        response.sendRedirect("/main");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //super.doPut(req, resp);
    }
}