/*
 * Developed by Razil Minneakhmetov on 1/2/19 11:46 AM.
 * Last modified 1/2/19 11:46 AM.
 * Copyright © 2019. All rights reserved.
 */

package context;

import lombok.SneakyThrows;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class MyPropertiesList {
    private static MyPropertiesList instance;
    private Properties properties;
    private InputStream inputStream;
    private OutputStream outputStream;
    private static final String PATH = "C:\\Users\\razil\\Desktop\\DB\\src\\main\\resources\\users.properties";

    @SneakyThrows
    private MyPropertiesList() {
        this.inputStream = new FileInputStream(PATH);
        this.outputStream = new FileOutputStream(PATH);
        this.properties = new Properties();
        properties.load(inputStream);
    }

    public static MyPropertiesList getInstance() {
        if (instance == null) {
            instance = new MyPropertiesList();
        }
        return instance;
    }

    public String get(String parameter){
        return properties.getProperty(parameter);
    }

    @SneakyThrows
    public void write(String key, String object){
        properties.put(key, object);
        clear();
        properties.store(outputStream, "");
    }

    @SneakyThrows
    public void set(String key, String object){
        properties.setProperty(key, object);
        clear();
        properties.store(outputStream, "");
    }

    @SneakyThrows
    public void delete(String key){
        properties.remove(key);
        clear();
        properties.store(outputStream, "");
    }

    @SneakyThrows
    private void clear(){
        FileWriter fileWriter = new FileWriter(new File(PATH));
        fileWriter.write("");
        fileWriter.close();
    }

}