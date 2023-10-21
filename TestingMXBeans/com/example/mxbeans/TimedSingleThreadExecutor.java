package com.example.mxbeans;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TimedSingleThreadExecutor {
    private final ExecutorService executorService;
    private volatile boolean taskRunning = false;
    private long lastTaskCompletionTime = System.currentTimeMillis();
    private long dropThreshold; // The threshold for dropping tasks (in milliseconds)

    public TimedSingleThreadExecutor(long dropThreshold) {
        this.dropThreshold = dropThreshold;
        executorService = Executors.newSingleThreadExecutor();
    }

    public void submitTask(Runnable task) {
        synchronized (this) {
            if (!taskRunning || (System.currentTimeMillis() - lastTaskCompletionTime) > dropThreshold) {
                executorService.submit(() -> {
                    synchronized (this) {
                        taskRunning = true;
                    }
                    task.run();
                    synchronized (this) {
                        taskRunning = false;
                        lastTaskCompletionTime = System.currentTimeMillis();
                    }
                });
            } else {
                System.out.println("Task dropped - Another task is currently running or arrived too soon.");
            }
        }
    }

    public void shutdownNow() {
        executorService.shutdownNow();
    }
}


