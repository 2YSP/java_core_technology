package cn.sp.chapter14.future;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * Created by 2YSP on 2018/4/11.
 */
public class FutureTest {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.printf("Enter base directory (e.g /usr/local/jdk1.8/src):");
        String directory = in.nextLine();
        System.out.printf("Enter keyword (e.g volatile):");
        String keyword = in.nextLine();
        MatchCounter counter = new MatchCounter(new File(directory),keyword);
        FutureTask<Integer> task = new FutureTask<Integer>(counter);
        Thread t = new Thread(task);
        t.start();
        try {
            System.out.println(task.get()+" matching files");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}

/**
 * this task counts the files in a directory and its subdirectories that contain a given keyword
 */
class MatchCounter implements Callable<Integer>{

    private File directory;
    private String keyword;
    private int count;

    public MatchCounter(File directory,String keyword){
        this.directory = directory;
        this.keyword = keyword;
    }



    @Override
    public Integer call() throws Exception {

        count = 0;
        try {
            File[] files = directory.listFiles();
            List<Future<Integer>> results = new ArrayList<>();

            for(File file : files){
                if (file.isDirectory()){
                    MatchCounter counter = new MatchCounter(file,keyword);
                    FutureTask<Integer> task = new FutureTask<>(counter);
                    results.add(task);
                    Thread t = new Thread(task);
                    t.start();
                }else {
                    if(search(file)){
                        count++;
                    }
                }
            }

            for(Future<Integer> result:results){
                count += result.get();
            }
        }catch (InterruptedException e){

        }
        return count;
    }

    /**
     * searches a file for a given keyword
     * @param file
     * @return
     */
    private boolean search(File file)throws IOException{
        boolean found = false;
        Scanner in = new Scanner(file);
        while (!found && in.hasNextLine()){
            String line = in.nextLine();
            if (line.contains(keyword)){
                found = true;
            }
        }

        return found;
    }
}