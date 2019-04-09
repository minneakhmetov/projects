package com.spinach.utils.python.logger;

import com.spinach.utils.PathUtil;
import lombok.SneakyThrows;

import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class PythonLogger {

    private PrintWriter writer;

    @SneakyThrows
    public PythonLogger(PathUtil pathUtil) {
        String logPath = pathUtil.getDecodedPath() + pathUtil.getProperty("spinach.python.logpath");
        String fileName = LocalDateTime.now().toString().replace(":", "-").replace(".", "S");
        File file = new File(logPath + fileName + ".log");
        file.createNewFile();
        writer = new PrintWriter(file, "UTF-8");
    }

    public void close(){
        writer.close();
    }

    private void log(MessageTypes types, String c){
        writer.println(format(types, c));
    }

    private String format(MessageTypes types, String message){
        return LocalDateTime.now().toString() + " " + types.toString() + " : " + message;
    }

    public void info(String c){
        log(MessageTypes.INFO, c);
    }

    public void debug(String c){
        log(MessageTypes.DEBUG, c);
    }

    public void trace(String c){
        log(MessageTypes.TRACE, c);
    }

    public void error(String c){
        log(MessageTypes.ERROR, c);
    }

    public void warn(String c){
        log(MessageTypes.WARN, c);
    }


}
