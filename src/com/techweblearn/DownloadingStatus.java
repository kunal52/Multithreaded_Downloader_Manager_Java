package com.techweblearn;

import java.util.ArrayList;

public class DownloadingStatus {

    private String filename;
   private long downloaded=0;
   private int noOfParts;
   private ArrayList<Long>individualPartDownloadedStatus;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public long getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(long downloaded) {
        this.downloaded = downloaded;
    }

    public int getNoOfParts() {
        return noOfParts;
    }

    public void setNoOfParts(int noOfParts) {
        this.noOfParts = noOfParts;
    }

    public ArrayList<Long> getIndividualPartDownloadedStatus() {
        return individualPartDownloadedStatus;
    }

    public void setIndividualPartDownloadedStatus(ArrayList<Long> individualPartDownloadedStatus) {
        this.individualPartDownloadedStatus = individualPartDownloadedStatus;
    }

    public void incrementDownload()
    {
        downloaded+=8192;
    }
}
