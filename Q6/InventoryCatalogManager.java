package Q3.Q4.Q5.Q6;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class InventoryCatalogManager {
    private static final int NUM_THREADS = 10;
    private static final int OPS_PER_THREAD = 50000;

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== E-COMMERCE INVENTORY CATALOG CONCURRENCY BENCHMARK ===");
        benchmarkSynchronizedMap();
        benchmarkConcurrentMap();
    }

    public static void benchmarkSynchronizedMap() throws InterruptedException {
        Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
        for (int i = 0; i < 1000; i++) syncMap.put("PROD_" + i, 100);

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        long startTime = System.currentTimeMillis();

        for (int t = 0; t < NUM_THREADS; t++) {
            executor.execute(() -> {
                for (int i = 0; i < OPS_PER_THREAD; i++) {
                    String key = "PROD_" + (i % 1000);
                    syncMap.put(key, syncMap.getOrDefault(key, 0) + 1);
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long duration = System.currentTimeMillis() - startTime;
        double throughput = ((long) NUM_THREADS * OPS_PER_THREAD / (double) duration) * 1000;

        System.out.println("\n--- Coarse-Grained Locking (Collections.synchronizedMap) ---");
        System.out.println("Execution Time: " + duration + " ms | Throughput: " + String.format("%.2f", throughput) + " ops/sec");
    }

    public static void benchmarkConcurrentMap() throws InterruptedException {
        Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        for (int i = 0; i < 1000; i++) concurrentMap.put("PROD_" + i, 100);

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        long startTime = System.currentTimeMillis();

        for (int t = 0; t < NUM_THREADS; t++) {
            executor.execute(() -> {
                for (int i = 0; i < OPS_PER_THREAD; i++) {
                    String key = "PROD_" + (i % 1000);
                    concurrentMap.compute(key, (k, v) -> (v == null ? 1 : v + 1));
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);
        long duration = System.currentTimeMillis() - startTime;
        double throughput = ((long) NUM_THREADS * OPS_PER_THREAD / (double) duration) * 1000;

        System.out.println("\n--- Fine-Grained Bucket Locking (ConcurrentHashMap) ---");
        System.out.println("Execution Time: " + duration + " ms | Throughput: " + String.format("%.2f", throughput) + " ops/sec");
    }
}
