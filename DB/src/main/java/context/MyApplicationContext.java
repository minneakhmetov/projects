/*
 * Developed by Razil Minneakhmetov on 12/27/18 12:38 AM.
 * Last modified 12/27/18 12:38 AM.
 * Copyright © 2018. All rights reserved.
 */

package context;

import java.util.HashMap;
import java.util.Map;

public class MyApplicationContext {
    private static MyApplicationContext instance;
    public Map<String, Object> contextMap;

    private MyApplicationContext() {
        this.contextMap = new HashMap<String, Object>();
    }

    public static MyApplicationContext getInstance() {
        if (instance == null) {
            instance = new MyApplicationContext();
        }
        return instance;
    }

    public Object getObject(String parameter){
        return contextMap.get(parameter);
    }
    public void setObject(String parameter, Object object){
        contextMap.put(parameter, object);
    }
}