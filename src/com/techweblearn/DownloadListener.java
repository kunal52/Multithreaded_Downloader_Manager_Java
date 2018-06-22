package com.techweblearn;

import java.util.ArrayList;

public interface DownloadListener {
	void update(long downloaded, int speed);
	void onCompleted();
	void onPause(ArrayList<Long>downloaded);
	void onPartError(int code,String message,int partNo);

}
