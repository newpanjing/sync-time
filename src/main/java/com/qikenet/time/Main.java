package com.qikenet.time;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Main {
    public static void main(String[] args) throws IOException {

        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {

                        long timestamp = NTPServer.getTime();
                        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
                        DateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
                        String date = formatDate.format(timestamp);
                        String time = formatTime.format(timestamp);
                        System.out.println(date+" "+time);
                        Runtime.getRuntime().exec("cmd /c time "+time);
                        Runtime.getRuntime().exec("cmd /c date "+date);

                        Thread.sleep(1000*60);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        System.in.read();
    }
}
