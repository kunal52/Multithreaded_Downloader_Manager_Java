package com.techweblearn;

import java.util.ArrayList;

public interface DownloadListener {
	void update(ArrayList<DownloadingStatus> downloadingStatusArrayList);
	void onCompleted(FileDownloadInfo fileDownloadInfo);
	void onPause(ArrayList<Long> downloaded);
	void onPartError(int code, String message, int partNo);
	void onError(String message);
	void onPartStatus(long downloaded, int partNo);
	void onPartCompleted(int partNo);

}
