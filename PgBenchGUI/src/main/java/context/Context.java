/*
 * Developed by Razil Minneakhmetov on 11/20/18 12:31 PM.
 * Last modified 11/20/18 12:31 PM.
 * Copyright © 2018. All rights reserved.
 */

package context;

import java.util.HashMap;
import java.util.Map;

public class Context {

    private static Context instance;
    public Map<String, Object> contextMap;

    private Context() {
        this.contextMap = new HashMap<String, Object>();
    }

    public static Context getInstance() {
        if (instance == null) {
            instance = new Context();
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