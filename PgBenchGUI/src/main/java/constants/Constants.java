/*
 * Developed by Razil Minneakhmetov on 11/18/18 8:25 PM.
 * Last modified 11/18/18 8:25 PM.
 * Copyright © 2018. All rights reserved.
 */

package constants;

public class Constants {

    public static final String TEMP_PATH = "C:\\Windows\\Temp\\TPSMeter\\";
    public static final String TEMP_PATH_XLS = "C:\\Windows\\Temp\\TPSMeterXLS\\file.xlsx";
    public static final String SCRIPT_NAME = "script.sql";
    public static final String BAT_NAME = "start.bat";
    public static final int CHART_QUALITY = 1000;
    public static final String SQL_BATCH_INSERT_QUERY = "INSERT INTO tps_meter_grafana (iteration, ms, times) values (?, ?, ?)";
    public static final String SQL_CREATE_TABLE_QUERY = "CREATE TABLE tps_meter_grafana (iteration int, ms int, times timestamp );";
    public static final String SQL_DELETE_ALL = "DELETE FROM tps_meter_grafana";
    public static final String CHART_FILE_NAME_WITHOUT_EXTENSION = "chart";
    public static final String CHART_FILE_NAME_WITH_EXTENSION = "chart.png";
}