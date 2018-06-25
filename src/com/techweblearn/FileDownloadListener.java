package com.techweblearn;

import com.techweblearn.Utils.CombiningPartFiles;

import java.util.ArrayList;

public interface FileDownloadListener {

    void update(long downloaded, int speed);
    void onCompleted();
    void onPause(ArrayList<Long> downloaded);
    void onPartError(int code, String message, int partNo);
    void onError(String message);
    void onPartStatus(long downloaded, int partNo);
    void onPartCompleted(int partNo);
    void combineFiles(CombiningPartFiles combiningPartFiles);

}
