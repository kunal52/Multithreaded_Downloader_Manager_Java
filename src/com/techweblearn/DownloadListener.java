package com.techweblearn;

public interface DownloadListener {
	void update(long downloaded, int speed);
	void onCompleted();
	void onPause();

}
