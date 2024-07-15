import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class Program {

    public static void main(String[] args) throws IOException {
        int threadsCount = 0;
        if (args[0].contains("--count=")){
            String counter = args[0].substring("--count=".length());
            threadsCount = Integer.parseInt(counter);
        }
        ArrayList<String> fileUrls = new ArrayList<>();
        readFileAndFillArray(fileUrls);

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        for (String url : fileUrls) {
            executor.execute(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " start download file number " + (fileUrls.indexOf(url) +1));
                    InputStream in = new URL(url).openStream();
                    String FILE_NAME = url.substring(url.lastIndexOf('/')+1);
                    Files.copy(in, Paths.get(FILE_NAME), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println(Thread.currentThread().getName() + " finish download file number " + (fileUrls.indexOf(url) +1));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executor.shutdown();

    }

    public static void readFileAndFillArray(ArrayList<String> fileUrls)
            throws IOException {
        try (FileInputStream fileURL = new FileInputStream("files_urls.txt")) {
            Scanner scanner = new Scanner(fileURL);
            while (scanner.hasNextLine()) {
                String currentString = scanner.nextLine();
                fileUrls.add(currentString.substring(1));
            }
        }
    }
}
