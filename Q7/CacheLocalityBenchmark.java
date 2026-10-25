package Q3.Q4.Q5.Q6.Q7;

public class CacheLocalityBenchmark {
    private static final int N = 2000;
    private static double[][] matrix = new double[N][N];

    public static void main(String[] args) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = i + j;
            }
        }

        System.out.println("=== HARDWARE CACHE LOCALITY PROFILING (2000x2000 MATRIX) ===");
        int iterations = 10;

        traverseRowMajor();
        traverseColumnMajor();

        long rStart = System.nanoTime();
        for (int k = 0; k < iterations; k++) traverseRowMajor();
        double avgRowMs = ((System.nanoTime() - rStart) / (double) iterations) / 1e6;

        long cStart = System.nanoTime();
        for (int k = 0; k < iterations; k++) traverseColumnMajor();
        double avgColMs = ((System.nanoTime() - cStart) / (double) iterations) / 1e6;

        double speedup = avgColMs / avgRowMs;

        System.out.println("Average Row-Major Traversal Time: " + String.format("%.3f", avgRowMs) + " ms");
        System.out.println("Average Column-Major Traversal Time: " + String.format("%.3f", avgColMs) + " ms");
        System.out.println("Row-Major Speedup over Column-Major: " + String.format("%.2f", speedup) + "x");
    }

    public static double traverseRowMajor() {
        double sum = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }

    public static double traverseColumnMajor() {
        double sum = 0;
        for (int j = 0; j < N; j++) {
            for (int i = 0; i < N; i++) {
                sum += matrix[i][j];
            }
        }
        return sum;
    }
}