package com.techweblearn;

import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

public class DownloadingThread implements Runnable {


    private static final int BUFFER_SIZE=8192;

    private ByteBuffer byteBuffer;
    private long downloaded=0;
    private boolean pause=false;
    private HttpURLConnection httpURLConnection;
    private ReadableByteChannel readableByteChannel;
    private WritableByteChannel writableByteChannel;
    private FileOutputStream fileOutputStream;
    private int responseCode;
    private BufferedInputStream bufferedInputStream;
    private BufferedOutputStream bufferedOutputStream;



    private DownloadTask downloadTask;
    private int partNo;
    private PartDownloadListener partDownloadListener;
    private byte[]buffer=new byte[8192];

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
            URL url=new URL(downloadTask.getUrl());
            httpURLConnection= (HttpURLConnection) url.openConnection();
            httpURLConnection.addRequestProperty("Range",downloadTask.getRange());
            httpURLConnection.setConnectTimeout(60000);
            httpURLConnection.setReadTimeout(60000);
            httpURLConnection.connect();
            responseCode=httpURLConnection.getResponseCode();
            System.out.println(httpURLConnection.getHeaderFields().toString());
            fileOutputStream=new FileOutputStream(new File(downloadTask.getFilename()),true);

            bufferedInputStream=new BufferedInputStream(httpURLConnection.getInputStream());
            bufferedOutputStream=new BufferedOutputStream(fileOutputStream);

            int read;

          //  readableByteChannel=Channels.newChannel(httpURLConnection.getInputStream());

            //writableByteChannel=fileOutputStream.getChannel();

            while (true)
            {
               // byteBuffer.clear();
               /* if(pause)
                {
                    break;
                }*/

                read=bufferedInputStream.read(buffer);

                bufferedOutputStream.write(buffer);
                if(read==-1)
                    break;

                System.out.println(read);

                downloaded+=read;
                if(partDownloadListener!=null)
                partDownloadListener.update(downloaded,partNo);

            }



            bufferedOutputStream.close();
            bufferedInputStream.close();
            byteBuffer.clear();
            fileOutputStream.close();
            writableByteChannel.close();
            readableByteChannel.close();
            httpURLConnection.disconnect();

            if(pause)
            partDownloadListener.pause(partNo,downloaded);
            else partDownloadListener.completed(partNo);

        }
        catch (ClosedChannelException pause)
        {

            partDownloadListener.pause(partNo,downloaded);
            pause.printStackTrace();
        }
        catch (ConnectException c)
        {
            c.printStackTrace();
            partDownloadListener.error(partNo);

        }catch (IOException e) {
            e.printStackTrace();
            partDownloadListener.error(responseCode,e.getLocalizedMessage(),partNo);

        }


    }

    public void pause()
    {
        pause=true;
        try {
            byteBuffer.clear();
            fileOutputStream.close();
            writableByteChannel.close();
            readableByteChannel.close();
            httpURLConnection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
