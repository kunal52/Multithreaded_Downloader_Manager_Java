package com.techweblearn;


import java.util.ArrayList;

public class Main {

    //Accept-Ranges: none // When No Partial Request
    public static void main(String[] args) {

        String URL_FILE = "http://central.maven.org/maven2/org/mongodb/mongo-java-driver/3.7.1/mongo-java-driver-3.7.1.jar";
        Downloader downloader = new Downloader(URL_FILE,4);
        downloader.setDownloadStatusListener(new DownloadListener() {
            @Override
            public void update(long downloaded, int speed) {

                System.out.println(downloaded/1024+" "+speed/1024);

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onPause(ArrayList<Long> downloaded) {
                System.out.println(downloaded);
            }

            @Override
            public void onPartError(int code, String message, int partNo) {

            }

            @Override
            public void onError(String message) {
                System.out.println(message);
            }

            @Override
            public void onPartStatus(long downloaded, int partNo) {

            }

            @Override
            public void onPartCompleted(int partNo) {

            }


        });
        downloader.startDownload();






    }
}
