/*
 * Developed by Razil Minneakhmetov on 11/19/18 10:10 PM.
 * Last modified 11/19/18 10:10 PM.
 * Copyright © 2018. All rights reserved.
 */

package app;

import context.Context;

import lombok.SneakyThrows;
import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.internal.chartpart.Chart;
//import services.LogParser;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Scanner;

import static org.knowm.xchart.style.Styler.ChartTheme.XChart;

public class Application {


    @SneakyThrows
    public static void main(String[] args) {
//        Scanner scanner = new Scanner(new File("C:\\Users\\razil\\Desktop\\New Text Document.txt"));
//        String c;
//        while (scanner.hasNext()){
//
//            c = scanner.nextLine();
//            System.out.println(c);
//            if(c.indexOf("number of transactions actually processed") == 0){
//                form.setTransactions(c.split("number of transactions actually processed: ")[1]);
//            }
//            if(c.indexOf("latency average") == 0){
//                form.setLatencyAverage(c.split("latency average = ")[1]
//                        .replaceAll("ms", "").replace(" ", ""));
//            }
//            if(c.indexOf("tps = ") == 0 && c.indexOf("(including connections establishing)") > 0){
//                form.setTpsIn(c.split("tps = ")[1].split("(including connections establishing)")[0]
//                        .replace("(", "").replace(" ", ""));
//
//            }
//            if(c.indexOf("tps = ") == 0 && c.indexOf("(excluding connections establishing)") > 0){
//                form.setTpsEx(c.split("tps = ")[1].split("(excluding connections establishing)")[0]
//                        .replace("(", "").replace(" ", ""));
//            }
//        }
//        System.out.println(form);

        //double[] x = new double[]{1, 2, 3};
        //double[] y = new double[]{0, 2, 0};
        DecimalFormat df = new DecimalFormat("#.#####");
        int i = 1200 * 1000000;
        int iteration = 253854;
        double lol =  ((double)i )/ ((double) iteration);

        System.out.println(Double.valueOf(df.format(lol)));
//        Chart chart = QuickChart.getChart("sample chart", "x", "y", "name", x, y);
//
//        //new SwingWrapper(chart).displayChart();
//        BitmapEncoder.saveBitmap(chart, "C:\\Users\\razil\\Desktop\\chart", BitmapEncoder.BitmapFormat.PNG);
    }
}