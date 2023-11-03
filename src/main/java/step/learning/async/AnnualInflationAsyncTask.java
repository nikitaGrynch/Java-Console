package step.learning.async;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class AnnualInflationAsyncTask {
    private static final int MAX_THREADS = 6;
    private double sum;
    private final Object sumLocker = new Object();
    private int activeThreadsCount;
    private final Object atcLocker = new Object();

    public void run() {
        long startTime = System.nanoTime();
        int months = 12;
        sum = 100;
        activeThreadsCount = months;
        ExecutorService executor = Executors.newFixedThreadPool(MAX_THREADS);
        List<Callable<String>> tasks = new ArrayList<>();
        for (int i = 0; i < months; i++) {
            tasks.add(new MonthRate(i + 1));
        }
        try {
            List<Future<String>> futures = executor.invokeAll(tasks);
            futures.forEach((future) -> {
                try {
                    future.get();
                } catch(Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        long endTime = System.nanoTime();
        executor.shutdown();
        System.out.printf("\nTotal execution time: %.1f", (endTime - startTime) / 1e6);
    }

    class MonthRate implements Callable<String> {
        int month;

        public MonthRate(int month) {
            this.month = month;
        }

        @Override
        public String call() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
            double percent = 10.0;
            double factor = (1.0 + percent / 100.0);
            double localSum;
            synchronized (sumLocker) {
                localSum =
                        sum = sum * factor;
            }


            boolean isLast;
            synchronized (atcLocker) {
                activeThreadsCount--;
                isLast = activeThreadsCount == 0;
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
            System.out.printf("Month: %02d, percent: %.2f, sum: %.2f %n", this.month, percent, localSum);
            if (isLast) {
                System.out.printf("---------------------\nTotal: %.2f\n", sum);
            }
            return null;
        }
    }

}
