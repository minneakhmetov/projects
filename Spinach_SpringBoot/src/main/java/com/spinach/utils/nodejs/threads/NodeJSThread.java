package com.spinach.utils.nodejs.threads;

import com.spinach.utils.PathUtil;
import lombok.SneakyThrows;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.time.LocalDateTime;
import org.slf4j.Logger;


public class NodeJSThread extends Thread {

    private PathUtil pathUtil;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public NodeJSThread(PathUtil pathUtil){
        this.pathUtil = pathUtil;
      //  this.logger = logger;
    }

    @Override
    @SneakyThrows
    public void run() {

        scriptGenerator(pathUtil.getProperty("spinach.nodejs.port"), new File( "C:\\Users\\razil\\Desktop\\spinach_gitlab\\Spinach_node\\index.js"));

        Runtime runtime =  Runtime.getRuntime();
        Process process = runtime.exec("node index.js", null, new File("C:\\Users\\razil\\Desktop\\spinach_gitlab\\Spinach_node\\"));
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String c;
     //   System.out.println();
        while ((c = stdInput.readLine()) != null){
            logger.info(c);
          //  System.out.println(c);
            //builder.append(LocalDateTime.now().toString().replace("T", " ") + " " + "\u001B[35m" + c + "\u001B[0m");
        }
       // System.out.println();


    }

    @SneakyThrows
    private void scriptGenerator(String port, File index){
        String script = "const express = require('express');\n" +
                "const app = express();\n" +
                "app.use(express.static('public'));\n" +
                "app.listen(" + port + ");\n" +
                "console.log(\"Static NodeJS server started at "+ port + "\");";
        PrintWriter writer = new PrintWriter(index, "UTF-8");
        writer.print(script);
        writer.close();
    }
}
