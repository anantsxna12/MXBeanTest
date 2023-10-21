package com.example.mxbeans;

import com.sun.management.OperatingSystemMXBean;
import com.sun.management.ThreadMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    private static boolean measureCPULoad(OperatingSystemMXBean osMxBean) {
        double systemLoadAvg = osMxBean.getSystemLoadAverage();
        double systemCpuLoad = osMxBean.getSystemCpuLoad();
        double processCpuLoad = osMxBean.getProcessCpuLoad();
        long processCpuTime = osMxBean.getProcessCpuTime();

        System.out.println("LoadAvg: " + systemLoadAvg + " | SysCPU: " + systemCpuLoad + " | ProcCPU: " + processCpuLoad + " | ProcTime: " + processCpuTime);

        return (systemCpuLoad > 0.9 && processCpuLoad > 0.65); // PARAM condition to triger thread comparison
    }

    private static ThreadMXBean threadMXBean = (ThreadMXBean) ManagementFactory.getThreadMXBean();
    static int GAP = 3; // PARAM : gap between taking 2 thread snapshots

    public static void main(String[] args) {
        TimedSingleThreadExecutor threadExecutor = new TimedSingleThreadExecutor(6000);
        OperatingSystemMXBean osMxBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        ScheduledExecutorService cpuExecutor = Executors.newScheduledThreadPool(1);

        List<LoadGeneratorThread> threads = new ArrayList<>();

        int LC = 100, MC = 0, HC = 20; // PARAM : number of threads for light, medium heavy
        int LO = 40; // PARAM : time for low load only
        int LM = 40; // PARAM : time for low + medium
        int ALL = 40; // PARAM : time for all threads
        int MEASURE_EVERY = 2; // PARAM : how often to take cpu load meeasurement


        cpuExecutor.scheduleAtFixedRate(() -> {
                    if (measureCPULoad(osMxBean)) {
                        threadExecutor.submitTask(Main::findHeavyThreads);
                    }
                }
                , 0, MEASURE_EVERY, TimeUnit.SECONDS);

        // Start with 10 light load generator threads
        for (int i = 0; i < LC; i++) {
            LightLoadGeneratorThread lightThread = new LightLoadGeneratorThread();
            threads.add(lightThread);
            lightThread.start();
        }

        try {
            Thread.sleep(LO * 1000);

//            // Add medium load generator threads
//            for (int i = 0; i < MC; i++) {
//                MediumLoadGeneratorThread mediumThread = new MediumLoadGeneratorThread();
//                threads.add(mediumThread);
//                mediumThread.start();
//            }
//
//            // Wait for 10 seconds
//            Thread.sleep(LM * 1000);

            // Add heavy load generator threads
            for (int i = 0; i < HC; i++) {
                HeavyLoadGeneratorThread heavyThread = new HeavyLoadGeneratorThread();
                threads.add(heavyThread);
                heavyThread.start();
            }

            // Wait for 10 seconds
            Thread.sleep(ALL * 1000);

            // Stop threads in reverse order (heavy, medium, light)
            for (LoadGeneratorThread thread : threads) {
                thread.stopThread();
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            cpuExecutor.shutdownNow();
            threadExecutor.shutdownNow();
        }
    }

    private static void findHeavyThreads() {

        long[] threadIds = threadMXBean.getAllThreadIds();

        ThreadInfo[] initialThreadInfos = threadMXBean.getThreadInfo(threadIds, Integer.MAX_VALUE);
        long[] initialThreadCpuTimes = threadMXBean.getThreadCpuTime(threadIds);

        try {
            Thread.sleep(GAP * 1000); // Sleep for 1 second
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        long[] finalThreadCpuTimes = threadMXBean.getThreadCpuTime(threadIds);

        ThreadUsage[] threadUsages = new ThreadUsage[threadIds.length];

        for (int i = 0; i < threadIds.length; i++) {
            long cpuTimeDiff = finalThreadCpuTimes[i] - initialThreadCpuTimes[i];
            threadUsages[i] = new ThreadUsage(threadIds[i], initialThreadInfos[i].getThreadName(), cpuTimeDiff);
        }

        Arrays.sort(threadUsages, Comparator.comparingLong(ThreadUsage::getCpuTimeDiff).reversed());

        int TOP = 25; //  PARAM: How many of the heaviest threads to show?
        System.out.println("HERE ARE TOP 25:");
        for (ThreadUsage threadUsage : threadUsages) {
            if(TOP == 0) break;
            TOP--;
            ThreadInfo info = threadMXBean.getThreadInfo(threadUsage.threadId, Integer.MAX_VALUE);
            System.out.println(threadUsage);
//            System.out.println(Arrays.toString(info.getStackTrace()));
//            ^ DISABLED STACK TRACE PRINTING FOR NOW
        }
    }

    private static class ThreadUsage {
        private long threadId;
        private String threadName;
        private long cpuTimeDiff;

        public ThreadUsage(long threadId, String threadName, long cpuTimeDiff) {
            this.threadId = threadId;
            this.threadName = threadName;
            this.cpuTimeDiff = cpuTimeDiff;
        }

        public long getThreadId() {
            return threadId;
        }

        public String getThreadName() {
            return threadName;
        }

        public long getCpuTimeDiff() {
            return cpuTimeDiff;
        }

        @Override
        public String toString() {
            return " Thread Name: " + threadName + " - CPU Time Difference: " + cpuTimeDiff;
        }
    }


}
