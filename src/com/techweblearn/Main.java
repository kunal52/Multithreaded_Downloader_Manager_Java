package com.techweblearn;


public class Main {

    //Accept-Ranges: none // When No Partial Request
    public static void main(String[] args) {


        String URL_FILE = "https://dl2.filehippo.com/5bca7ee0a2bd422b97ac6cd339eba2c7/ccsetup543.exe?ttl=1529682553&token=b5c401d5acb22f406f31e151de1a20d0";
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
            public void onPause() {

            }
        });
        downloader.startDownload();






    }
}
