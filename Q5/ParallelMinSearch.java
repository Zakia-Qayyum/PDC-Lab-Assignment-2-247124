package Q3.Q4.Q5;

public class ParallelMinSearch extends SensorSearchEngine implements Runnable {
    private double resultMin = Double.MAX_VALUE;

    public ParallelMinSearch(double[] sensorData, int startIdx, int endIdx) {
        super(sensorData, startIdx, endIdx);
    }

    @Override
    public double processGrid() {
        double min = Double.MAX_VALUE;
        for (int i = startIdx; i < endIdx; i++) {
            if (sensorData[i] < min) {
                min = sensorData[i];
            }
        }
        return min;
    }

    @Override
    public void run() {
        resultMin = processGrid();
    }

    public double getResultMin() {
        return resultMin;
    }
}
