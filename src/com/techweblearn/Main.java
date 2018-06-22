package com.techweblearn;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    //Accept-Ranges: none // When No Partial Request
    public static void main(String[] args) {

        String URL_FILE = "https://dl2.filehippo.com/971b7921bc384095b05a28bb7794b30b/vlc-3.0.3-win64.exe?ttl=1529705325&token=fe65cf7f78086b5200b8cfe95814af8e";
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
