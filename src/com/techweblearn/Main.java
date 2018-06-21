package com.techweblearn;


public class Main {

    //Accept-Ranges: none // When No Partial Request
    public static void main(String[] args) {


        String URL_FILE = "http://h2vx.com/vcf/https%3A//www.nottingham.ac.uk/biosciences/people/michael.pound";
        Downloader downloader = new Downloader(URL_FILE,8);
        downloader.startDownload();






    }
}
