package Q3.Q4.Q5;

import java.util.Random;

public class SensorGridMonitorApp {
    public static void main(String[] args) throws InterruptedException {
        int size = 50000;
        double[] telemetryData = new double[size];
        Random rand = new Random(100);
        for (int i = 0; i < size; i++) {
            telemetryData[i] = -20 + rand.nextDouble() * 140;
        }

        int numThreads = 4;
        int chunkSize = size / numThreads;

        ParallelMinSearch[] minTasks = new ParallelMinSearch[numThreads];
        ParallelMaxSearch[] maxTasks = new ParallelMaxSearch[numThreads];
        Thread[] threads = new Thread[numThreads * 2];

        long startTime = System.nanoTime();

        for (int i = 0; i < numThreads; i++) {
            int start = i * chunkSize;
            int end = (i == numThreads - 1) ? size : (i + 1) * chunkSize;

            minTasks[i] = new ParallelMinSearch(telemetryData, start, end);
            maxTasks[i] = new ParallelMaxSearch(telemetryData, start, end);

            threads[i] = new Thread(minTasks[i]);
            threads[i + numThreads] = new Thread(maxTasks[i]);

            threads[i].start();
            threads[i + numThreads].start();
        }

        for (Thread t : threads) {
            t.join();
        }

        double globalMin = Double.MAX_VALUE;
        double globalMax = Double.MIN_VALUE;

        for (int i = 0; i < numThreads; i++) {
            if (minTasks[i].getResultMin() < globalMin) globalMin = minTasks[i].getResultMin();
            if (maxTasks[i].getResultMax() > globalMax) globalMax = maxTasks[i].getResultMax();
        }

        long endTime = System.nanoTime();

        System.out.println("=== IOT TELEMETRY PARALLEL REDUCTION ENGINE ===");
        System.out.println("Global Minimum Recorded Sensor Value: " + String.format("%.2f", globalMin));
        System.out.println("Global Maximum Peak Spike Value: " + String.format("%.2f", globalMax));
        System.out.println("Parallel Grid Processing Time across 4 Workers: " + ((endTime - startTime) / 1e6) + " ms");
    }
}