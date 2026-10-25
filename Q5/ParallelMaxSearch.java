package Q3.Q4.Q5;

public class ParallelMaxSearch extends SensorSearchEngine implements Runnable {
    private double resultMax = Double.MIN_VALUE;

    public ParallelMaxSearch(double[] sensorData, int startIdx, int endIdx) {
        super(sensorData, startIdx, endIdx);
    }

    @Override
    public double processGrid() {
        double max = Double.MIN_VALUE;
        for (int i = startIdx; i < endIdx; i++) {
            if (sensorData[i] > max) {
                max = sensorData[i];
            }
        }
        return max;
    }

    @Override
    public void run() {
        resultMax = processGrid();
    }

    public double getResultMax() {
        return resultMax;
    }
}