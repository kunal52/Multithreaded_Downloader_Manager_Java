package com.techweblearn;

import com.techweblearn.Utils.CombiningPartFilesThread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class ExecutorThreadFactory implements ThreadFactory {

    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String namePrefix;
    public ExecutorThreadFactory() {

        SecurityManager s = System.getSecurityManager();
        group = (s != null) ? s.getThreadGroup() :
                Thread.currentThread().getThreadGroup();
        namePrefix = "pool-" +
                poolNumber.getAndIncrement() +
                "-thread-";


    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(group, r,
                namePrefix + threadNumber.getAndIncrement(),
                0);
        if (t.isDaemon())
            t.setDaemon(false);


        if(r instanceof DownloadingThread)
        {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        if(r instanceof CombiningPartFilesThread)
        {
            t.setPriority(Thread.MAX_PRIORITY);
        }



        return t;
    }
}
