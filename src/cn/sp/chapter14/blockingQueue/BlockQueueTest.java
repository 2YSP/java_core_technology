package cn.sp.chapter14.blockingQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;

/**
 * Created by 2YSP on 2018/4/14.
 */
public class BlockQueueTest {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.printf("Enter base directory:");
        String directory = in.nextLine();
        System.out.printf("Enter keyword:");
        String keyword = in.nextLine();
        final int FILE_QUEUE_SIZE = 10;
        final int SEARCH_THREADS = 100;
        BlockingQueue<File> queue = new ArrayBlockingQueue<File>(FILE_QUEUE_SIZE);

        FileEnumerationTask enumerator = new FileEnumerationTask(queue,new File(directory));
        new Thread(enumerator).start();
        for (int i=0;i<SEARCH_THREADS;i++){
            new Thread(new SearchTask(queue,keyword)).start();
        }
    }
}

/**
 * this task enumerates all files in a directory and its subdirectories
 */
class FileEnumerationTask implements Runnable{

    public static File DUMMP = new File("");
    private BlockingQueue<File> queue;
    private File startingDirectory;

    public FileEnumerationTask(BlockingQueue<File> queue,File startingDirectory){
        this.queue = queue;
        this.startingDirectory = startingDirectory;
    }


    @Override
    public void run() {
        try {
            enumerate(startingDirectory);
            queue.put(DUMMP);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param directory
     */
    private void enumerate(File directory) throws InterruptedException {
        File[] files = directory.listFiles();
        for(File file: files){
            if (file.isDirectory()){
                enumerate(file);
            }else {
                queue.put(file);
            }
        }
    }
}

class SearchTask implements Runnable{

    private BlockingQueue<File> queue;
    private String keyword;


    public SearchTask(BlockingQueue<File> queue,String keyword){
        this.queue = queue;
        this.keyword = keyword;
    }


    @Override
    public void run() {
        try {
            boolean done = false;
            while (!done){
                File file = queue.take();
                if (file == FileEnumerationTask.DUMMP){
                    queue.put(file);
                    done = true;
                }else {
                    search(file);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * searches a file for a given keyword and prints all matching files
     * @param file
     */
    private void search(File file) {
        try {
            Scanner in = new Scanner(file);
            int lineNumber = 0;
            while (in.hasNextLine()){
                lineNumber++;
                String line = in.nextLine();
                if (line.contains(keyword)){
                    System.out.printf("%s:%d:%s%n",file.getPath(),lineNumber,line);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
