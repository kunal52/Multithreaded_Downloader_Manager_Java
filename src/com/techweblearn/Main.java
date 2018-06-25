package com.techweblearn;


import java.util.ArrayList;

public class Main {

    //Accept-Ranges: none // When No Partial Request
    public static void main(String[] args) {

        String URL_FILE = "https://dl1.filehippo.com/971b7921bc384095b05a28bb7794b30b/vlc-3.0.3-win64.exe?ttl=1529913024&token=cbba104d708693a71705bcaf98205450";
        Downloader downloader = new Downloader();

        downloader.startDownload();
        downloader.addDownloadURL(URL_FILE);






    }
}
