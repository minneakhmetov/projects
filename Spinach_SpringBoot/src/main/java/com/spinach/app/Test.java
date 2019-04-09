package com.spinach.app;

import com.spinach.dao.UsersRepository;
import com.spinach.dto.TimerForUser;
import com.spinach.forms.RegisterForm;
import com.spinach.models.UserModel;
import com.spinach.utils.TimeUtil;
import com.spinach.utils.python.logger.MessageTypes;
import com.spinach.utils.timer.timers.SurveyOneUserTimer;
import lombok.SneakyThrows;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import ru.qatools.properties.Property;
import ru.qatools.properties.Resource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.Timer;


public class Test {

    @SneakyThrows
    public static void main(String[] args) {

        System.out.println(new TimerForUser(null, 1).equals(new TimerForUser(new SurveyOneUserTimer(null, null, null, null,null), 1)));

        //"C:\\Users\\razil\\Desktop\\spinach_gitlab\\Spinach_SpringBoot\\src\\main\\resources\\application.properties")));
        //System.out.println(properties.getProperty("spinach.static.path"));
//        HttpClient client = new DefaultHttpClient();
//        HttpGet request = new HttpGet("http://localhost:8080/usersPhotos/2_200.jpg");
//        HttpResponse response = client.execute(request);
//       // BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
//
//        BufferedImage bi = ImageIO.read(response.getEntity().getContent());
//
//        File file = new File("C:\\Users\\razil\\Desktop\\new folder\\file.jpg");
//
//
////        String line = "";
////        while ((line = rd.readLine()) != null) {
////            System.out.println(line);
////        }
//
//        ImageIO.write(bi, "jpg", file);

       // TimeUtil util = new TimeUtil();
       // System.out.println(util.chatsTime(LocalDateTime.now().plusHours(25)));

//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.postgresql.Driver");
//        dataSource.setUsername("spinach");
//        dataSource.setPassword("spinachpassword");
//        dataSource.setUrl("jdbc:postgresql://147.78.64.103:5432/spinach_java");
//        UsersRepository repository = new UsersRepository(dataSource);
//
//        UserModel model = UserModel.builder()
//                .email("razil0071999@gmail.com")
//                .build();
//        repository.save(model);





    }
}
