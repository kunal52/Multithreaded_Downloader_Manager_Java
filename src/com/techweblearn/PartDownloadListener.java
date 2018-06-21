package com.techweblearn;

public interface PartDownloadListener {

    void update(long downloaded,int partNo);
    void completed();
    void error(int partNo);


}
