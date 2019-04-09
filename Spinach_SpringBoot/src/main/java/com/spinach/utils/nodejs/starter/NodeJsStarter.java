package com.spinach.utils.nodejs.starter;

import com.spinach.utils.PathUtil;
import com.spinach.utils.nodejs.threads.NodeJSThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;

@Service
public class NodeJsStarter {

    @Autowired
    public NodeJsStarter(PathUtil pathUtil) {
//        Thread thread = new NodeJSThread(pathUtil);
//        thread.start();
    }

}
