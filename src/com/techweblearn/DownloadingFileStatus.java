package com.techweblearn;

public class DownloadingFileStatus {

    long downloaded;
    long total;
    int speed;
    int partsCompleted;

    public long getDownloaded() {
        return downloaded;
    }

    public void setDownloaded(long downloaded) {
        this.downloaded = downloaded;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getPartsCompleted() {
        return partsCompleted;
    }

    public void setPartsCompleted(int partsCompleted) {
        this.partsCompleted = partsCompleted;
    }
}
