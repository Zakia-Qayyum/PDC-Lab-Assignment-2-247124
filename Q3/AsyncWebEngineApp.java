package Q3;

public class AsyncWebEngineApp {
    public static void main(String[] args) {
        System.out.println("Main Application Entry Point Thread: " + Thread.currentThread().getName());

        TelemetryThread telemetryWorker = new TelemetryThread();
        Thread logWorker = new Thread(new LogProcessorTask());

        telemetryWorker.setName("Telemetry-Worker");
        logWorker.setName("Log-Worker");

        System.out.println("Spawning concurrent execution threads...");
        telemetryWorker.start();
        logWorker.start();
        System.out.println("Worker threads successfully dispatched from main().");
    }
}
