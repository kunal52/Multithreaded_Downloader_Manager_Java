package com.techweblearn;

import com.techweblearn.Utils.CombiningPartFiles;
import com.techweblearn.Utils.DeleteFiles;
import com.techweblearn.Utils.FetchDownloadFileInfo;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Downloader
{
    private FileDownloadInfo fileDownloadInfo;
    private int noOfThreads;
    private int parts_Completed;
    private DownloadTask[]downloadTasks;
    private long downloaded=0;
    private long secDownloaded;
    private ScheduledExecutorService scheduledExecutorService;
    private DownloadListener downloadListener=null;
    ArrayList<Long>partDownloadedInfo=new ArrayList<>();

    public Downloader(String url,int noOfThreads) {
        this.noOfThreads = noOfThreads;
        downloadFile(FetchDownloadFileInfo.getUrlFileInfo(url),noOfThreads);

    }

    private void downloadFile(FileDownloadInfo fileDownloadInfo,int noOfThreads)
    {

        this.fileDownloadInfo=fileDownloadInfo;
        System.out.println(fileDownloadInfo.toString());
        this.noOfThreads=noOfThreads;
        this.downloadTasks=PartialDownloadTasks.getPartialDownloadTasks(fileDownloadInfo,noOfThreads);

    }

    public void setDownloadStatusListener(DownloadListener downloadStatusListener)
    {
        this.downloadListener=downloadStatusListener;
    }



    public void startDownload()
    {
        ExecutorService executorService=Executors.newFixedThreadPool(noOfThreads);
        PartDownload partDownload=new PartDownload(downloadListener);


        for(int i=0;i<noOfThreads;i++) {

            DownloadingThread downloadingThread=new DownloadingThread(downloadTasks[i],i,partDownload);
            executorService.submit(downloadingThread);
        }
        executorService.shutdown();


        scheduledExecutorService=Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(new UpdateDownloadInfoSec(downloadListener), 0, 1, TimeUnit.SECONDS);
    }


    private class UpdateDownloadInfoSec implements Runnable
    {

        DownloadListener downloadListener;

        public UpdateDownloadInfoSec(DownloadListener downloadListener) {
            this.downloadListener = downloadListener;
        }

        @Override
        public void run() {
            downloadListener.update(downloaded, (int) (downloaded-secDownloaded));
            secDownloaded=downloaded;
        }
    }


    private class PartDownload implements PartDownloadListener{

        DownloadListener downloadListener;

        PartDownload(DownloadListener downloadListener) {
            this.downloadListener = downloadListener;
        }

        @Override
        public void update(long downloaded,int partNo) {
            Downloader.this.downloaded+=8192;
            System.out.println(Downloader.this.downloaded);
            partDownloadedInfo.add(partNo,downloaded);
        }

        @Override
        public void completed() {
            parts_Completed++;
            if(parts_Completed==noOfThreads)
            {
                if(downloadListener!=null)
                downloadListener.onCompleted();
                scheduledExecutorService.shutdownNow();
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
        public void error(int partNo) {



        }
    }


    public void pause()
    {
        scheduledExecutorService.shutdownNow();
    }

    public void resume()
    {
        //TODO Again Create Downloaded Tasks . And Send Request to Server Specifies the Ranges Need to Be Download And Start Downloading
    }

    public void  stop()
    {
        scheduledExecutorService.shutdownNow();
        DeleteFiles.delete(downloadTasks);
    }

    public void restart()
    {



    }




}
