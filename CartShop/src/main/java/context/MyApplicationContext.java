/*
 * Developed by Razil Minneakhmetov on 11/25/18 2:48 PM.
 * Last modified 11/25/18 2:48 PM.
 * Copyright © 2018. All rights reserved.
 */

package context;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import repositories.Repository;
import services.*;

import javax.sql.DataSource;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.DriverManager;
import java.util.*;

@PropertySource("classpath:application.properties")
public class MyApplicationContext<T> {
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

    private static MyApplicationContext myContext;
    private Map<String, Object> components;

    private MyApplicationContext() {
        components = new HashMap<String, Object>();

        ApplicationContext context =
                new ClassPathXmlApplicationContext("beans/context.xml");

        ApplicationContext contextBean = new
                AnnotationConfigApplicationContext(JavaConfig.class);


        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(databaseUrl + databaseName);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        components.put("dataSource", dataSource);
//
//        List<Class<T>> repoList = getClasses("C:\\Users\\razil\\Desktop\\Java Enterprise\\CartShop\\src\\main\\java\\repositories");
//
//        for (int i = 0; i < repoList.size(); i++) {
//            components.put(repoList.get(i).getSimpleName().split("Impl")[0], loadRepo(repoList.get(i)));
//        }
//
//        List<Class<T>> serviceList = getClasses("C:\\Users\\razil\\Desktop\\Java Enterprise\\CartShop\\src\\main\\java\\services");
//
//        for (int i = 0; i < serviceList.size(); i++) {
//            components.put(serviceList.get(i).getSimpleName().split("Impl")[0], loadService(serviceList.get(i)));
//        }

//        components.put("CartServiceImpl", context.getBean(CartServiceImpl.class));
//        components.put("ProductServiceImpl", context.getBean(ProductServiceImpl.class));
//        components.put("LoginServiceImpl", context.getBean(LoginServiceImpl.class));
//        components.put("VKAuthServiceImpl", context.getBean(VKAuthServiceImpl.class));

        components.put("cartService", contextBean.getBean("cartService", CartService.class));
        components.put("productService", contextBean.getBean("productService", ProductService.class));
        components.put("loginService", contextBean.getBean("loginService", LoginService.class));
        components.put("vkAuthService", contextBean.getBean("vkAuthService", VKAuthService.class));
    }

    public static MyApplicationContext getMyContext() {
        if (myContext != null)
            return myContext;
        else {
            myContext = new MyApplicationContext();
            return myContext;
        }
    }

    public <T> T getComponent(Class<T> componentClass) {
        for (Object component : components.values()) {
            if (componentClass.isAssignableFrom(component.getClass())) {
                return (T) component;
            }
        }
        return null;
    }

    public Object getAttribute(String key) {
        return components.get(key);
    }

    public void setAttribute(String key, Object value) {
        components.put(key, value);
    }

    @SneakyThrows
    private <T> T loadRepo(Class<T> userClass) {
        //Class<T> userClass = (Class<T>) Class.forName("repositories." + className);
        try {
            Constructor<T> constructor = userClass.getConstructor(DriverManagerDataSource.class);
            T object = constructor.newInstance(components.get("dataSource"));
            return object;
        } catch (NoSuchMethodException e) {
            return null;
        }
    }


    @SneakyThrows
    private <T> List<Class<T>> getClasses(String path) {
        File[] allClasses = new File(path).listFiles();
        List<Class<T>> classes = new ArrayList<>();
        String root = path.replace("\\", ".").split("java.")[1];

        for (int i = 0; i < allClasses.length; i++) {
            classes.add((Class<T>) Class.forName(root + "." + allClasses[i].getName().split(".java")[0]));
        }

        return classes;
    }

    @SneakyThrows
    private <T> T loadService(Class<T> userClass) {
        //Class<T> userClass = (Class<T>) Class.forName("services." + className);
        Field[] fields = userClass.getDeclaredFields();
        List<Class<T>> classList = new ArrayList<Class<T>>();
        List<Object> instances = new ArrayList<>();
        T object;
        for (int i = 0; i < fields.length
                //& fields[i].getClass().isAssignableFrom(Repository.class)
                ; i++) {
            classList.add((Class<T>) Class.forName(fields[i].getType().getCanonicalName()));
            instances.add(components.get(fields[i].getType().getSimpleName()));
        }
        if (classList.size() == 0) {
            //Class<T>[] classes = new Class[classList.size()];
            try {
                Constructor constructor = userClass.getConstructor();
                //Object[] objects = new Object[instances.size()];
                object = (T) constructor.newInstance();
            } catch (NoSuchMethodException e) {
                return null;
            }
        } else {
            try {
                Class<T>[] classes = new Class[classList.size()];
                Constructor constructor = userClass.getConstructor(classList.toArray(classes));
                Object[] objects = new Object[instances.size()];
                object = (T) constructor.newInstance(instances.toArray(objects));
            } catch (NoSuchMethodException e) {
                return null;
            }
        }
        return object;
    }
}