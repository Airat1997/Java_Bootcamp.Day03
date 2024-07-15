public class Program {

    public static void main(String[] args) throws InterruptedException {
        int count = 0;
        SyncObject syncObject = new SyncObject();
        if (args[0].contains("--count=")) {
            String counter = args[0].substring("--count=".length());
            count = Integer.parseInt(counter);
        }
        MyThread firstThread = new MyThread(count, "Egg", syncObject);
        MyThread secondThread = new MyThread(count, "Hen", syncObject);
        firstThread.start();
        secondThread.start();
    }
}

class MyThread extends Thread {
    SyncObject syncObj;
    int count;
    String name;

    public MyThread(int count, String name, SyncObject syncObj) {
        this.count = count;
        this.name = name;
        this.syncObj = syncObj;
    }

    @Override
    public void run() {
        synchronized (syncObj) {
            if (count > 0) {
                for (int i = 0; i < count; i++) {
                    System.out.println(name);
                    try {
                        syncObj.wait(1);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }

}

class SyncObject {
}