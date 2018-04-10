package cn.sp.chapter14.synch;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by 2YSP on 2018/4/10.
 */
public class Bank {

    private final double[] accounts;
    private Lock bankLock;
    //条件 余额充足
    private Condition sufficientFunds;


    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = initialBalance;
        }
        bankLock = new ReentrantLock();
        sufficientFunds = bankLock.newCondition();
    }

    public void transfer(int from, int to, double amount) {
        bankLock.lock();
        try {
            while (accounts[from] < amount) {
                //余额不足，一直等待别人给自己转账
                sufficientFunds.await();
            }
            //转账
            accounts[from] -= amount;
            System.out.printf(" %10.2f from %d to %d", amount, from, to);
            accounts[to] += amount;
            System.out.printf(" Total Balance:%10.2f%n", getTotalBalance());
            sufficientFunds.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bankLock.unlock();
        }
    }

    public double getTotalBalance() {
        bankLock.lock();
        double sum = 0;
        try {

            for (double a : accounts) {
                sum += a;
            }
        } finally {
            bankLock.unlock();
        }
        return sum;
    }


    public int size() {
        return accounts.length;
    }
}
