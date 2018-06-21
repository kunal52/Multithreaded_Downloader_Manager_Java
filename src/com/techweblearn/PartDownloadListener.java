package com.techweblearn;

public interface PartDownloadListener {

    void update(long downloaded,int speed);
    void completed();
    void error();


}
