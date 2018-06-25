package com.techweblearn;

import com.techweblearn.Utils.CombiningPartFiles;
import com.techweblearn.Utils.FetchDownloadFileInfo;

import java.io.File;
import java.util.ArrayList;

public class DownloadFile {

    private FileDownloadListener fileDownloadListener;
    private int no_of_threads=4;
    private int completedParts;
    private FileDownloadInfo fileDownloadInfo;
    private DownloadTask[]partialDownloadTasks;
    private ExecutorThreadListener executorThreadListener;

    public DownloadFile(ExecutorThreadListener executorThreadListener) {
        this.executorThreadListener=executorThreadListener;
    }

    private ArrayList<DownloadingThread>downloadingThreads;
    private ThreadDownloadListener threadDownloadListener;

    public ArrayList<DownloadingThread> createFileDownloadingThread(String Url)
    {
        threadDownloadListener=new ThreadDownloadListener();
        downloadingThreads=new ArrayList<>();
        fileDownloadInfo= FetchDownloadFileInfo.getUrlFileInfo(Url);
        partialDownloadTasks = PartialDownloadTasks.getPartialDownloadTasks(fileDownloadInfo, 4);
        for(int i=0;i<4;i++)
        {
            downloadingThreads.add(new DownloadingThread(partialDownloadTasks[i],threadDownloadListener));
        }

        return downloadingThreads;

    }

    private class ThreadDownloadListener implements PartDownloadListener
    {

        @Override
        public void update(long downloaded, int partNo) {
           // fileDownloadListener.update(downloaded, partNo);
        }

        @Override
        public void completed(int partNo) {
            completedParts++;

            System.out.println("Completed " + partNo);
          //  fileDownloadListener.onPartCompleted(partNo);

            if(completedParts==no_of_threads)
            {
                executorThreadListener.combineFiles(new CombiningPartFiles(new File(fileDownloadInfo.getFileName()), partialDownloadTasks, new CombiningFileListener() {
                    @Override
                    public void combineCompleted() {



                    }

                    @Override
                    public void combineProgress(long combining) {



                    }

                    @Override
                    public void combineError() {



                    }
                }));

                System.out.println("Full Completed");

            }

        }

        @Override
        public void error(int partNo) {
        }

        @Override
        public void pause(int partNo, long downloaded) {

        }

        @Override
        public void error(int code, String message, int partno) {
            fileDownloadListener.onPartError(code, message, partno);
        }
    }





}
