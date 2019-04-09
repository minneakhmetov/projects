package com.spinach.utils.python.threads;

import com.spinach.utils.PathUtil;
import com.spinach.utils.python.logger.PythonLogger;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.TimerTask;

public class PythonTimerTask extends TimerTask {

    private PathUtil pathUtil;


    public PythonTimerTask(PathUtil pathUtil){
        this.pathUtil = pathUtil;
    }


    @Override
    @SneakyThrows
    public void run() {
        Logger logger = LoggerFactory.getLogger(this.getClass());

            PythonLogger pythonLogger = new PythonLogger(pathUtil);
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec(
                    pathUtil.getProperty("spinach.python.execute.command") + " " + pathUtil.getProperty("spinach.python.execute.file"),
                    null, new File(pathUtil.getDecodedPath() + pathUtil.getProperty("spinach.python.execute.file.relative.path")));
            logger.info("Python is being started calculating");
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String c;
            while ((c = stdInput.readLine()) != null) {
                pythonLogger.info(c);
            }
            pythonLogger.close();

    }
}
