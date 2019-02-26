/*
 * Developed by Razil Minneakhmetov on 11/18/18 8:06 PM.
 * Last modified 11/18/18 8:06 PM.
 * Copyright © 2018. All rights reserved.
 */

package services;

import constants.Constants;
import lombok.SneakyThrows;

import java.io.FileWriter;

public class BatCreator {
    private DBConnector connector;


    @SneakyThrows
    public void createBatFile(int time) {
        connector = DBConnector.getInstance();
        FileWriter writer = new FileWriter(Constants.TEMP_PATH + Constants.BAT_NAME, false);

        String text = "set PGPASSWORD=" + connector.getPassword() + "\n";
        writer.write(text);
        text = "pgbench -U " + connector.getUsername() + " -T " +
                time + " -r -l -f "+ Constants.SCRIPT_NAME + " " + connector.getDatabaseName();
        writer.write(text);
        writer.flush();
    }
}