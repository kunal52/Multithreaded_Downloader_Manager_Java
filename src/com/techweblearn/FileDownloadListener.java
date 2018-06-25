package com.techweblearn;

import com.techweblearn.Utils.CombiningPartFilesThread;

import java.util.ArrayList;

public interface FileDownloadListener {

    void progress(DownloadingStatus downloadingStatus);
    void onCompleted();
    void onPause(ArrayList<Long> downloaded);
    void onPartError(int code, String message, int partNo);
    void onError(String message);
    void onPartStatus(long downloaded, int partNo);
    void onPartCompleted(int partNo);
    void combineFiles(CombiningPartFilesThread combiningPartFilesThread);

}
