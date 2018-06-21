package com.techweblearn;

import com.techweblearn.Utils.CombiningPartFiles;
import com.techweblearn.Utils.DeleteFiles;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Downloader
{
    private FileDownloadInfo fileDownloadInfo;
    private int noOfThreads;
    private int parts_Completed;
    private DownloadTask[]downloadTasks;

    public void downloadFile(FileDownloadInfo fileDownloadInfo,int noOfThreads,DownloadListener downloadListener)
    {

        this.fileDownloadInfo=fileDownloadInfo;
        this.noOfThreads=noOfThreads;
        this.downloadTasks=PartialDownloadTasks.getPartialDownloadTasks(fileDownloadInfo,noOfThreads);
        ExecutorService executorService=Executors.newFixedThreadPool(noOfThreads);
        PartDownload partDownload=new PartDownload(downloadListener);


        for(int i=0;i<noOfThreads;i++) {

            DownloadingThread downloadingThread=new DownloadingThread(downloadTasks[i],i,partDownload);
            executorService.submit(downloadingThread);
        }
        executorService.shutdown();


    }

    private class PartDownload implements PartDownloadListener{

        DownloadListener downloadListener;

        public PartDownload(DownloadListener downloadListener) {
            this.downloadListener = downloadListener;
        }

        @Override
        public void update(long downloaded, int speed) {

        }

        @Override
        public void completed() {
            parts_Completed++;
            if(parts_Completed==noOfThreads)
            {
                downloadListener.onCompleted();
              /*  try {
                    path.toFile().createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }*/

                CombiningPartFiles.combineFiles(new File(fileDownloadInfo.getFileName()), downloadTasks, new CombiningFileListener() {
                    @Override
                    public void combineCompleted() {
                        DeleteFiles.delete(downloadTasks);
                    }

                    @Override
                    public void combineProgress(long combining) {

                    }

                    @Override
                    public void combineError() {

                    }
                });
            }
        }

        @Override
        public void error() {



        }
    }
}
