package com.techweblearn;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class DownloadingThread implements Runnable {


    private static final int BUFFER_SIZE=8192;

    private ByteBuffer byteBuffer;
    private long downloaded=0;


    private DownloadTask downloadTask;
    private int partNo;
    private PartDownloadListener partDownloadListener;

    public DownloadingThread(DownloadTask downloadTask, PartDownloadListener partDownloadListener) {
        this.downloadTask = downloadTask;
        this.partDownloadListener = partDownloadListener;
    }




    public DownloadingThread(DownloadTask downloadTask, int partNo, PartDownloadListener partDownloadListener) {
        this.downloadTask = downloadTask;
        this.partNo = partNo;
        this.partDownloadListener = partDownloadListener;
    }

    @Override
    public void run() {

        try {
            URL url=new URL(downloadTask.url);
            HttpURLConnection httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.addRequestProperty("Range",downloadTask.getRange());
            httpURLConnection.connect();
            System.out.println(httpURLConnection.getHeaderFields().toString());
            byteBuffer=ByteBuffer.allocate(BUFFER_SIZE);
            ReadableByteChannel readableByteChannel=Channels.newChannel(httpURLConnection.getInputStream());
            FileOutputStream fileOutputStream=new FileOutputStream(new File(downloadTask.getFilename()));
            WritableByteChannel writableByteChannel=fileOutputStream.getChannel();

            while (true)
            {
                byteBuffer.clear();
                int read=readableByteChannel.read(byteBuffer);
                byteBuffer.flip();
                writableByteChannel.write(byteBuffer);
                if(read==-1)
                    break;

                downloaded+=read;
                partDownloadListener.update(downloaded,partNo);

               // System.out.println(downloaded+" PART No"+partNo);

            }

            byteBuffer.clear();
            fileOutputStream.close();
            writableByteChannel.close();
            readableByteChannel.close();
            httpURLConnection.disconnect();
            partDownloadListener.completed();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
