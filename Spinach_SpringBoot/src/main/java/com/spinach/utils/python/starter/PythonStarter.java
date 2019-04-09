package com.spinach.utils.python.starter;

import com.spinach.utils.PathUtil;
import com.spinach.utils.python.threads.PythonTimerTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Timer;

@Service
public class PythonStarter {

    @Autowired
    PythonStarter(PathUtil pathUtil){
        Logger logger = LoggerFactory.getLogger(this.getClass());
        if(Boolean.valueOf(pathUtil.getProperty("spinach.python.enable"))){
            new Timer().schedule(new PythonTimerTask(pathUtil),
                    Integer.valueOf(pathUtil.getProperty("spinach.python.delay.time.minutes")) * 1000 * 60,
                    Integer.valueOf(pathUtil.getProperty("spinach.python.period.time.hours")) * 1000 * 60 * 60);
            logger.info("Python is initialized");
        } else {
            logger.info("Python is disabled");
        }
    }



}
