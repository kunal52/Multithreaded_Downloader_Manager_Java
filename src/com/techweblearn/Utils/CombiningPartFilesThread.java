package com.techweblearn.Utils;

import com.techweblearn.CombiningFileListener;
import com.techweblearn.DownloadListener;
import com.techweblearn.DownloadTask;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.*;

public class CombiningPartFilesThread implements Runnable {

    private File output;
    private DownloadTask[] downloadTasks;
    private CombiningFileListener combiningFileListener;


    public CombiningPartFilesThread(File output, DownloadTask[] downloadTasks, CombiningFileListener combiningFileListener) {
        this.output = output;
        this.downloadTasks = downloadTasks;
        this.combiningFileListener = combiningFileListener;
    }


    @Override
    public void run() {
        int read;

        if(!output.exists())
        {
            try {
                output.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        byte[]byteBuffer=new byte[8192];



        try(FileOutputStream fileOutputStream=new FileOutputStream(output)) {

            System.out.println("Combining Files");
            for (DownloadTask downloadTask : downloadTasks) {

                File file= new File(downloadTask.getFilename());
                try (FileInputStream fileInputStream = new FileInputStream(file)) {

                    while ((read=fileInputStream.read(byteBuffer))!=-1) {

                        fileOutputStream.write(byteBuffer,0,read);

                    }

                } catch (IOException e) {
                    combiningFileListener.combineError();
                    e.printStackTrace();
                }


            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        DeleteFiles.delete(downloadTasks);
        combiningFileListener.combineCompleted();
    }
}
