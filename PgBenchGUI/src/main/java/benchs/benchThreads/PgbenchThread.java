/*
 * Developed by Razil Minneakhmetov on 11/19/18 1:39 PM.
 * Last modified 11/19/18 1:39 PM.
 * Copyright © 2018. All rights reserved.
 */

package benchs.benchThreads;

import constants.Constants;
import forms.PgbenchResultForm;
import forms.ResultForm;
import lombok.SneakyThrows;
import services.BatCreator;
import services.Executor;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class PgbenchThread extends Thread{
    private File script;
    private int time;
    public PgbenchResultForm form;

    public PgbenchThread(int time, File script) {
        this.script = script;
        this.time = time;
    }


    @Override
    @SneakyThrows
    public void run() {
        form = new PgbenchResultForm();
        Executor executor = new Executor();
        executor.tempMkdir();
        executor.scriptCopier(script);
        executor.execute();
        BatCreator creator = new BatCreator();
        creator.createBatFile(time);

        Runtime runtime =  Runtime.getRuntime();
        Process process = runtime.exec("cmd /c " + Constants.BAT_NAME, null, new File(Constants.TEMP_PATH));
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String c;

        while ((c = stdInput.readLine()) != null){
            if(c.indexOf("number of transactions actually processed") == 0){
                form.setTransactions(c.split("number of transactions actually processed: ")[1]);
            }
            if(c.indexOf("latency average") == 0){
                form.setLatencyAverage(c.split("latency average = ")[1]
                        .replaceAll("ms", "").replace(" ", ""));
            }
            if(c.indexOf("tps = ") == 0 && c.indexOf("(including connections establishing)") > 0){
                form.setTpsIn(c.split("tps = ")[1].split("(including connections establishing)")[0]
                        .replace("(", "").replace(" ", ""));

            }
            if(c.indexOf("tps = ") == 0 && c.indexOf("(excluding connections establishing)") > 0){
                form.setTpsEx(c.split("tps = ")[1].split("(excluding connections establishing)")[0]
                        .replace("(", "").replace(" ", ""));
            }
        }
    }

    public ResultForm getResult(){
        return form;
    }
}