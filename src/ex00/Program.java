public class Program {

    public static void main(String[] args) throws InterruptedException {
        int count = 0;
        if (args[0].contains("--count=")){
            String counter = args[0].substring("--count=".length());
            count = Integer.parseInt(counter);
        }
        MyThread firstThread = new MyThread(count, "Egg");
        MyThread secondThread = new MyThread(count, "Hen");
        firstThread.start();
        secondThread.start();
        firstThread.join();
        secondThread.join();
        for (int i = 0; i < count; i++) {
            System.out.println("Human");
        }

    }
}

class MyThread extends Thread {

    int count;
    String name;

    public MyThread(int count, String name) {
        this.count = count;
        this.name = name;
    }

    @Override
    public void run() {
        if (count > 0) {
            for (int i = 0; i < count; i++) {
                System.out.println(name);
            }
        }
    }
}