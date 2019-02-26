/*
 * Developed by Razil Minneakhmetov on 11/19/18 1:39 PM.
 * Last modified 11/19/18 1:35 PM.
 * Copyright © 2018. All rights reserved.
 */

package benchs.methodTypes;

import benchs.benchThreads.PgbenchThread;
import forms.ResultForm;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.File;

@Getter
public class Pgbench implements MethodType {

    PgbenchThread thread;

    @SneakyThrows
    @Override
    public void bench(int time, File script) {
        thread = new PgbenchThread(time, script);
        thread.start();
    }

    @Override
    public ResultForm getResult(){
        return thread.getResult();
    }
}