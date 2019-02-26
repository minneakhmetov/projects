/*
 * Developed by Razil Minneakhmetov on 11/19/18 10:59 PM.
 * Last modified 11/19/18 10:59 PM.
 * Copyright © 2018. All rights reserved.
 */

package services;

import benchs.benchThreads.PgbenchThread;
import constants.Constants;
import context.Context;
import forms.GrafanaSetup;
import forms.LogResult;
import forms.PgbenchResultForm;
import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.charts.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.charts.XSSFChartLegend;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.internal.chartpart.Chart;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class LogParser extends Thread {
    private Scanner scanner;
    private XSSFWorkbook book;
    private XSSFSheet sheet;
    private Context context;
    private PreparedStatement statement;
    private Connection connection;
    private GrafanaSetup grafanaSetup;

    ArrayList<Double> xAxis;
    ArrayList<Double> yAxis;
    //double time = 0;
    //double increment = 0;


    @SneakyThrows
    public LogParser() {

        xAxis = new ArrayList<Double>();
        yAxis = new ArrayList<Double>();
        //context = Context.getInstance();
        //PgbenchResultForm form = (PgbenchResultForm) context.getObject("pgbenchResult");
        //int allTimeMicroseconds = (int) context.getObject("time");
        //int iterations = Integer.valueOf(form.getTransactions());
        //DecimalFormat df = new DecimalFormat("#.#####");
        //increment = Double.valueOf (df.format(((double) allTimeMicroseconds)/((double) iterations)));

        File dir = new File(Constants.TEMP_PATH);
        File log = null;
        for (File file : dir.listFiles())
            if (file.getName().indexOf("pgbench_log") == 0) {
                log = file;
                break;
            }

        scanner = new Scanner(log);
        book = new XSSFWorkbook();
        sheet = book.createSheet("Report");
        context = Context.getInstance();

        grafanaSetup = (GrafanaSetup) context.getObject("grafana");


        if(grafanaSetup.isAddSupport()) {
            connection = DBConnector.getInstance().getDataSource().getConnection();
            try {
                connection.createStatement().execute(Constants.SQL_CREATE_TABLE_QUERY);
            } catch (SQLException e) {
                connection.createStatement().execute(Constants.SQL_DELETE_ALL);
            }
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(Constants.SQL_BATCH_INSERT_QUERY);
        }

    }

    private int incrementRow = 0;

    @SneakyThrows
    public void parse() {
        PgbenchResultForm form = (PgbenchResultForm) context.getObject("pgbenchResult");

        int iterations = Integer.valueOf(form.getTransactions())/Constants.CHART_QUALITY;
        int millisecondsTime = (int) context.getObject("time") * 1000;
        int increment = millisecondsTime/iterations;
        long time = System.currentTimeMillis();


        XSSFRow row = sheet.createRow(incrementRow++);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue("Querying time:");
        cell = row.createCell(1);
        cell.setCellValue(context.getObject("time") + " seconds");

        row = sheet.createRow(incrementRow++);
        cell = row.createCell(0);
        cell.setCellValue("Iterations:");
        cell = row.createCell(1);
        cell.setCellValue(form.getTransactions());

        row = sheet.createRow(incrementRow++);
        cell = row.createCell(0);
        cell.setCellValue("Latency Average:");
        cell = row.createCell(1);
        cell.setCellValue(form.getLatencyAverage());

        row = sheet.createRow(incrementRow++);
        cell = row.createCell(0);
        cell.setCellValue("TPS with connections");
        cell = row.createCell(1);
        cell.setCellValue(form.getTpsIn());

        row = sheet.createRow(incrementRow++);
        cell = row.createCell(0);
        cell.setCellValue("TPS without connections");
        cell = row.createCell(1);
        cell.setCellValue(form.getTpsEx());

        while (scanner.hasNext()) {
            String c = scanner.nextLine();
            String[] strings = c.split(" ");
            LogResult result = LogResult.builder()
                    .iteration(Integer.valueOf(strings[1]))
                    .time(Integer.valueOf(strings[2]))
                    .build();

            row = sheet.createRow(incrementRow++);
            XSSFCell name = row.createCell(0);
            name.setCellValue(result.getIteration());
            XSSFCell nameCell = row.createCell(1);
            nameCell.setCellValue(result.getTime());
            statementTake(result, time);
            time += increment;
            xAxis.add((double) result.getIteration());
            yAxis.add((double) result.getTime());

            for (int i = 0; i < Constants.CHART_QUALITY & scanner.hasNext(); i++) {
                String s = scanner.nextLine();
                String[] stringsArr = s.split(" ");
                LogResult newLog = LogResult.builder()
                        .iteration(Integer.valueOf(stringsArr[1]))
                        .time(Integer.valueOf(stringsArr[2]))
                        .build();
                XSSFRow rowRow = sheet.createRow(incrementRow++);
                XSSFCell nameNew = rowRow.createCell(0);
                nameNew.setCellValue(newLog.getIteration());
                XSSFCell nameCellNew = rowRow.createCell(1);
                nameCellNew.setCellValue(newLog.getTime());
                //statementTake(statement, newLog);
            }
        }


        Chart chart = QuickChart.getChart("pgBenchResult", "iterations", "time", "name", xAxis, yAxis);
        BitmapEncoder.saveBitmap(chart, Constants.TEMP_PATH + Constants.CHART_FILE_NAME_WITHOUT_EXTENSION, BitmapEncoder.BitmapFormat.PNG);
        XSSFDrawing drawing;
        XSSFClientAnchor anchor;

        BufferedImage image = ImageIO.read(new File(Constants.TEMP_PATH + Constants.CHART_FILE_NAME_WITH_EXTENSION));
        ByteArrayOutputStream byteArrayImg = new ByteArrayOutputStream();
        ImageIO.write(image, "PNG", byteArrayImg);
        int pictureIdx = sheet.getWorkbook().addPicture(byteArrayImg.toByteArray(),
                sheet.getWorkbook().PICTURE_TYPE_JPEG);

        short s1=5, s2=5;
        anchor = new XSSFClientAnchor(0, 0, 0, 0, s1, 5, s2, 5);
        anchor.setCol1(4);
        anchor.setRow1(0);

        drawing = sheet.createDrawingPatriarch();
        Picture pict = drawing.createPicture(anchor, pictureIdx);
        pict.resize();

        if(grafanaSetup.isAddSupport()) {
            statement.executeBatch();
            connection.setAutoCommit(true);
        }
        FileOutputStream stream = new FileOutputStream(new File(Constants.TEMP_PATH_XLS));
        scanner.close();
        book.write(stream);
        book.close();
        stream.close();

    }

    @SneakyThrows
    private void statementTake(LogResult result, long time){
        if(statement != null){
            statement.setLong(1, result.getIteration());
            statement.setInt(2, result.getTime());
            statement.setTimestamp(3, new Timestamp(time));
            statement.addBatch();
        }
    }

    @Override
    public void run() {
        parse();
    }

    public ArrayList<Double> getxAxis() {
        return xAxis;
    }

    public ArrayList<Double> getyAxis() {
        return yAxis;
    }
}