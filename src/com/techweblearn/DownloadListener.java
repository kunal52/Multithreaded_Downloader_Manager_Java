package com.techweblearn;

public interface DownloadListener {
	
	void onPreparedDownloading();
	void onStartDownload(DownloadingStatus downloadingStatus);
	void onErrorDownloading();
	void resumeDownloading();
	void update(long downloaded, int speed);
	void downloadSuccesfull();
	void combiningFiles();
	void onCompleted();
	void errorCombiningFiles();
	void combiningCompleted();
	void deleteCompleted();

}
