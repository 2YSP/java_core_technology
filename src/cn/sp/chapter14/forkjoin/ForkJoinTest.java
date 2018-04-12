package cn.sp.chapter14.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Fork-Join框架
 * Created by 2YSP on 2018/4/12.
 */
public class ForkJoinTest {

    public static void main(String[] args) {
        final int SIZE = 10000000;
        double[] numbers = new double[SIZE];
        for (int i=0;i<SIZE;i++){
            numbers[i] = Math.random();
        }
        Counter counter = new Counter(numbers, 0, numbers.length, new Filter() {
            @Override
            public boolean accept(double t) {
                return t > 0.5;
            }
        });
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(counter);
        System.out.println(counter.join());
    }
}

interface Filter{
    boolean accept(double t);
}

class Counter extends RecursiveTask<Integer>{

    private static final int THRESHOLD = 1000;
    private double[] values;
    private int from;
    private int to;
    private Filter filter;


    public Counter(double[] values,int from,int to,Filter filter){
        this.values = values;
        this.from = from;
        this.to = to;
        this.filter = filter;
    }
    @Override
    protected Integer compute() {
        if (to-from < THRESHOLD){
            //直接开始,筛选符合条件的元素
            int count = 0;
            for(int i = from;i<to;i++){
                if (filter.accept(values[i])){
                    count++;
                }
            }
            return count;
        }else {
            //任务分解，递归计算
            int mid = (from+to)/2;
            Counter first = new Counter(values,from,mid,filter);
            Counter second = new Counter(values,mid,to,filter);
            invokeAll(first,second);
            return first.join() + second.join();
        }
    }
}
