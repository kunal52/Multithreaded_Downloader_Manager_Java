package com.techweblearn;


import java.util.ArrayList;

public class Main {

    //Accept-Ranges: none // When No Partial Request
    public static void main(String[] args) {

        String URL_FILE = "https://dl1.filehippo.com/971b7921bc384095b05a28bb7794b30b/vlc-3.0.3-win64.exe?ttl=1529913024&token=cbba104d708693a71705bcaf98205450";
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
