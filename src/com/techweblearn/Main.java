package com.techweblearn;


import java.util.ArrayList;

public class Main {

    //Accept-Ranges: none // When No Partial Request
    public static void main(String[] args) {

        String URL_FILE = "https://get.videolan.org/vlc/3.0.3/win32/vlc-3.0.3-win32.exe";
        String URL_2="http://central.maven.org/maven2/org/mongodb/mongo-java-driver/3.7.1/mongo-java-driver-3.7.1.jar";
        Downloader downloader = new Downloader();

        downloader.startDownload();
        downloader.addDownloadURL(URL_FILE);
        downloader.addDownloadURL(URL_2);





    }
}
