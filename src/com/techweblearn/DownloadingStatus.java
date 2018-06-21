package com.techweblearn;

public class DownloadingStatus {

    private boolean isPartialSupported=false;
    private boolean isResumeSupported=false;
    private long totalDownloaded=0;
    private int downloadingSpeed=0;

    public boolean isPartialSupported() {
        return isPartialSupported;
    }

    public void setPartialSupported(boolean partialSupported) {
        isPartialSupported = partialSupported;
    }

    public boolean isResumeSupported() {
        return isResumeSupported;
    }

    public void setResumeSupported(boolean resumeSupported) {
        isResumeSupported = resumeSupported;
    }

    public long getTotalDownloaded() {
        return totalDownloaded;
    }

    public void setTotalDownloaded(long totalDownloaded) {
        this.totalDownloaded = totalDownloaded;
    }

    public int getDownloadingSpeed() {
        return downloadingSpeed;
    }

    public void setDownloadingSpeed(int downloadingSpeed) {
        this.downloadingSpeed = downloadingSpeed;
    }
}
