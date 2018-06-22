package com.techweblearn;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    //Accept-Ranges: none // When No Partial Request
    public static void main(String[] args) {

        String URL_FILE = "https://dl2.filehippo.com/8057bed8a8c54591b034b87c3fbdaecd/hma_pro_vpn_setup.exe?ttl=1529684468&token=6187f02114159078ac16dffb77217074";
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

            }

            @Override
            public void onPartError(int code, String message, int partNo) {

            }


        });
        downloader.startDownload();


        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                downloader.pause();
            }
        },5000);






    }
}
