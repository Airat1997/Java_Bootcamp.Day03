import java.util.Arrays;

public class Program {

    public static void main(String[] args) {
        int arraySize = 0;
        int threadsCount = 0;
        if (args[0].contains("--arraySize=")) {
            String value = args[0].substring("--arraySize=".length());
            arraySize = Integer.parseInt(value);
        }
        if (args[1].contains("--threadsCount=")) {
            String value = args[1].substring("--threadsCount=".length());
            threadsCount = Integer.parseInt(value);
        }

        int[] result = new int[arraySize];
        Arrays.fill(result, 1);
        int finalResult = 0;
        int sum = 0;
        for (int i = 0; i < result.length; i++) {
            sum += result[i];
        }
        System.out.println("Sum: " + sum);

        MyThread[] threads = new MyThread[threadsCount];

        for (int i = 0; i < threadsCount; i++) {
            int start = i * (arraySize / threadsCount);
            int end = (i == threadsCount - 1) ? arraySize : start + (arraySize / threadsCount);
            threads[i] = new MyThread(result, start, end);
            threads[i].start();
        }

        for (MyThread thread : threads){
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        for (int i = 0; i < threadsCount; i++) {
            finalResult += threads[i].resultSum;
        }
        System.out.println("Sum by threads: " + finalResult);
    }
}

class MyThread extends Thread {

    int[] array;

    int start;
    int end;

    int resultSum = 0;

    public MyThread(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        int sum = 0;
        String currentThreadName = Thread.currentThread().getName().replace('-', ' ');
        int countThread = Integer.parseInt(currentThreadName.substring("Thread ".length()));
        countThread++;
        String threadName = currentThreadName.replaceAll("\\d", "") + countThread;
        for (int i = start; i < end; i++) {
            sum += array[i];
        }
        System.out.println(threadName + ": from " + start + " to " + end + " sum is " + sum);
        resultSum += sum;
    }
}
