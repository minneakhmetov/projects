package com.spinach.utils;

import com.spinach.app.Application;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;
import java.util.Properties;


@Service
public class PathUtil {

    @Autowired
    private Environment environment;

    @Getter
    private String decodedPath;

    @Getter
    private String staticPath;

    //private Properties properties;

    @SneakyThrows
    public PathUtil(){
        String path = Application.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        decodedPath = URLDecoder.decode(path, "UTF-8").split("/Spinach_SpringBoot/target/")[0];

       // decodedPath = "~/spinach/";
        staticPath = decodedPath + "/root/spinach/Spinach_node/public/usersPhotos/";
       // environment.

       // properties.load(new FileInputStream(new File(decodedPath + PROPERTIES_RELATIVES_PATH)));

    }

    public String getProperty(String string){
        return environment.getProperty(string);
    }


}
