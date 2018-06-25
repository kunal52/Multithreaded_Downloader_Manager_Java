package com.techweblearn;

import com.techweblearn.Utils.CombiningPartFilesThread;
import com.techweblearn.Utils.DeleteFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Downloader
{

    private static final int NO_OF_THREADS=4;
    private FileDownloadInfo fileDownloadInfo;
    private int noOfThreads;
    private int parts_Completed;
    private DownloadTask[]downloadTasks;
    private long downloaded=0;
    private long secDownloaded;
    private ScheduledExecutorService scheduledExecutorService;
    private DownloadListener downloadListener=null;
    private String url;
    private int partPaused=0;
    private ArrayList<Long>partsDownloadInfo=new ArrayList<>();
    private ArrayList<DownloadingThread>downloadingThreadArrayList=new ArrayList<>();
    private ExecutorService executorService;
    private PartDownloadListener partDownloadListener;
    private int totalError;
    private BlockingQueue<Runnable>threadQueue;
    private List<Future> runningThreadsTask;
    private ExecutorThread executorThread;
    private FileDownload fileDownload;


    public Downloader() {
        executorThread=new ExecutorThread();
        runningThreadsTask=new ArrayList<>();
        threadQueue=new LinkedBlockingQueue<>();
        executorService=new ThreadPoolExecutor(NO_OF_THREADS,NO_OF_THREADS*4,60,TimeUnit.SECONDS,threadQueue,new ExecutorThreadFactory());
        fileDownload=new FileDownload();
    }


    public void setDownloadStatusListener(DownloadListener downloadStatusListener)
    {
        this.downloadListener=downloadStatusListener;
    }


    public void startDownload()
    {
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
          //  downloadListener.update(downloaded, (int) (downloaded-secDownloaded));
            secDownloaded=downloaded;
        }
    }




    public void pause(int downloadingIndex)
    {

        System.out.println(downloadingThreadArrayList.size()+"  SIZE");

        for(DownloadingThread downloadingThread:downloadingThreadArrayList)
        {
            downloadingThread.pause();
        }

    }

    public void addDownloadURL(String url)
    {
        DownloadFile downloadFile=new DownloadFile(executorThread,fileDownload);
        addDownload(downloadFile.createFileDownloadingThread(url));
    }

    public void addDownload(ArrayList<DownloadingThread>downloadingThreads)
    {
        for(DownloadingThread downloadingThread:downloadingThreads)
        {
            executorService.submit(downloadingThread);
        }
    }

    public void resume()
    {
        downloadingThreadArrayList.clear();
        executorService=Executors.newFixedThreadPool(noOfThreads);
        downloadTasks=PartialDownloadTasks.getResumePartialDownloadTasks(partsDownloadInfo,fileDownloadInfo,noOfThreads);
        for (int i=0;i<noOfThreads;i++)
        {
            DownloadingThread downloadingThread=new DownloadingThread(downloadTasks[i],i,partDownloadListener);
            downloadingThreadArrayList.add(i,downloadingThread);
            executorService.submit(downloadingThread);
        }
        executorService.shutdown();

    }

    public void  stop()
    {
        executorService.shutdownNow();
        scheduledExecutorService.shutdownNow();
        DeleteFiles.delete(downloadTasks);

    }

    public void restart()
    {



    }

    private class FileDownload implements FileDownloadListener
    {


        @Override
        public void progress(DownloadingStatus downloadingStatus) {

        }

        @Override
        public void onCompleted() {



        }

        @Override
        public void onPause(ArrayList<Long> downloaded) {



        }

        @Override
        public void onPartError(int code, String message, int partNo) {



        }

        @Override
        public void onError(String message) {



        }

        @Override
        public void onPartStatus(long downloaded, int partNo) {



        }

        @Override
        public void onPartCompleted(int partNo) {



        }

        @Override
        public void combineFiles(CombiningPartFilesThread combiningPartFilesThread) {



        }
    }


    private class ExecutorThread implements ExecutorThreadListener
    {

        @Override
        public void combineFiles(Runnable runnable) {
            System.out.println("Combining Filesssssssssssssssssssssss");
            runningThreadsTask.add(executorService.submit(runnable));
        }
    }



}
