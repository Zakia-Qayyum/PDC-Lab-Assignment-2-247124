package Q3.Q4;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPortfolioApp {
    public static void main(String[] args) {
        int size = 10000;
        double[] assets = new double[size];
        Random rand = new Random(42);
        for (int i = 0; i < size; i++) {
            assets[i] = 100 + rand.nextDouble() * 900;
        }

        ForkJoinPool pool = new ForkJoinPool();

        long startCalc = System.nanoTime();
        ParallelRiskCalculator riskTask = new ParallelRiskCalculator(assets, 0, size);
        double totalRisk = pool.invoke(riskTask);
        long endCalc = System.nanoTime();

        long startStress = System.nanoTime();
        PortfolioThresholdTask stressTask = new PortfolioThresholdTask(assets, 0, size, 500.0);
        pool.invoke(stressTask);
        long endStress = System.nanoTime();

        System.out.println("=== FORK/JOIN FINANCIAL ANALYTICS ===");
        System.out.println("Total Cumulative Risk Score: $" + String.format("%.2f", totalRisk));
        System.out.println("Parallel Risk Calc Time: " + ((endCalc - startCalc) / 1e6) + " ms");
        System.out.println("5% Volatility Stress Penalty Time: " + ((endStress - startStress) / 1e6) + " ms");
    }
}
