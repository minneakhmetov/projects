/*
 * Developed by Razil Minneakhmetov on 12/25/18 11:41 PM.
 * Last modified 12/25/18 11:41 PM.
 * Copyright © 2018. All rights reserved.
 */

package app;

import context.JavaBeans;
import context.MyPropertiesList;
import lombok.SneakyThrows;
import models.Similarity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import repositories.ActivityRepository;
import services.SimilarityService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {

    @SneakyThrows
    public static void main(String[] args) {
//        ApplicationContext contextBean = new
//                AnnotationConfigApplicationContext(JavaBeans.class);
//        SimilarityService similarityService = contextBean.getBean("similarityService", SimilarityService.class);
//        //ActivityRepository repository = contextBean.getBean("activityRepository", ActivityRepository.class);
////        File file = new File("C:\\Users\\razil\\Desktop\\activities.txt");
////        List<String> list = new ArrayList<String>();
////        Scanner scanner = new Scanner(file);
////        while (scanner.hasNext()){
////            String string = scanner.nextLine();
////            if (!list.contains("\"" + string + "\""))
////                list.add("\"" + string + "\"");
////        }
////        repository.loadData(list);
//
//        similarityService.computeSimilarity(193108295l);

        MyPropertiesList propertiesList = MyPropertiesList.getInstance();

        propertiesList.write("vnsvi", "jvsnvne");
        propertiesList.write("jbsjd", "oivnov");
        propertiesList.set("jbsjd", "nsrmo6789vs");
        //propertiesList.set("vnsvi", "knfoswkn");
        propertiesList.delete("vnsvi");


    }
}