package step.learning.async;

import com.sun.corba.se.spi.ior.ObjectKey;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class AsyncDemo {
    private double sum;
    private final Object sumLocker = new Object();
    private int activeThreadsCount;
    private final Object atcLocker = new Object();

    private final Random rnd = new Random();
    private String pandigitalNumber;
    private final Object pnLocker = new Object();

    private final List<String> digitsArray = new ArrayList<>();

    public void run() {
        for (int i = 0; i < 10; i++){
            digitsArray.add(String.valueOf(i));
        }
        pandigitalNumber = "";
        activeThreadsCount = digitsArray.size() - 1;

        Thread[] threads = new Thread[digitsArray.size()];
        for(int i = 0; i < digitsArray.size() - 1; i++){
            threads[i] = new Thread(new PandigitalNumber());
            threads[i].start();
        }

    }

    class PandigitalNumber implements Runnable{
        @Override
        public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.err.println(e.getMessage());
                }

                String digit;
                String localResult;
                synchronized (pnLocker){
                    digit = digitsArray.get(rnd.nextInt(digitsArray.size() - 1));
                    digitsArray.remove(digit);
                    localResult = pandigitalNumber += digit;
                }
            System.out.printf("Added: %s; Intermediate result: %s%n", digit, localResult);

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
            if (isLast) {
                System.out.println("---------------------\nResult: " + pandigitalNumber);
            }
        }
    }

    public void run2() {
        int months = 12;
        Thread[] threads = new Thread[months];
        sum = 100;
        activeThreadsCount = months;
        for (int i = 0; i < 12; i++) {
            threads[i] = new Thread(new MonthRate(i + 1));
            threads[i].start();
        }
//        try {
//            for (int i = 0; i < 12; i++) {
//                threads[i].join();
//            }
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("---------------------\nTotal: " + sum);
    }

    class MonthRate implements Runnable{
        int month;

        public MonthRate (int month){
            this.month = month;
        }

        @Override
        public void run() {
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
            System.out.printf(Locale.US, "Month: %02d, percent: %.2f, sum: %.2f %n",
                    this.month, percent, localSum);

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
            if (isLast) {
                System.out.println("---------------------\nTotal: " + sum);
            }
        }
    }
    public void run1() {
        System.out.println("Async Demo");
        Thread thread1 = new Thread(new Runnable() {

            @Override
            public void run() {
                System.out.println("Thread 1 startd");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    System.err.println(ex.getMessage());
                }
                System.out.println("Thread 1 finished");
            }
        }
        );
        thread1.start();

        try{
            thread1.join();
        }
        catch(InterruptedException ex){
            System.err.println(ex.getMessage());
        }

        System.out.println("Async Demo finishes");
    }

}
