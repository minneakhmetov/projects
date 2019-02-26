/*
 * Developed by Razil Minneakhmetov on 11/18/18 8:48 PM.
 * Last modified 11/18/18 8:48 PM.
 * Copyright © 2018. All rights reserved.
 */

package services;

import constants.Constants;
import lombok.SneakyThrows;

import java.io.File;
import  com.google.common.io.Files;
import java.nio.file.Path;

public class Executor {

    private static final String PGBENCH_EXE = "pgbench.exe";
    private static final String LIBINITL8_DLL = "libintl-8.dll";
    private static final String LIBICONV2 = "libiconv-2.dll";
    private static final String LIBPQ = "libpq.dll";
    private static final String path = "\\src\\main\\resources\\pgbench\\";

    @SneakyThrows
    public void execute(){
        String dir = System.getProperty("user.dir");
        Files.copy(new File(dir + path + PGBENCH_EXE), new File(Constants.TEMP_PATH + PGBENCH_EXE));
        Files.copy(new File(dir + path + LIBICONV2), new File(Constants.TEMP_PATH + LIBICONV2));
        Files.copy(new File(dir + path + LIBINITL8_DLL), new File(Constants.TEMP_PATH + LIBINITL8_DLL));
        Files.copy(new File(dir + path + LIBPQ), new File(Constants.TEMP_PATH + LIBPQ));
    }

    public boolean tempMkdir(){
       return (new File(Constants.TEMP_PATH)).mkdir();
    }

    public boolean tempDirExist(){
        return (new File(Constants.TEMP_PATH)).exists();
    }

    public boolean deleteTempDir(){
        File dir = new File(Constants.TEMP_PATH);
        for(File file : dir.listFiles())
             file.delete();
        return dir.delete();
    }

    @SneakyThrows
    public void scriptCopier(File source){
        Files.copy(source, new File(Constants.TEMP_PATH + Constants.SCRIPT_NAME));
    }
}