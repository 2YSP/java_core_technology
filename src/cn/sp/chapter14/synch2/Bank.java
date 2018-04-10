package cn.sp.chapter14.synch2;


/**
 * 使用synchronized 实现同步
 * Created by 2YSP on 2018/4/10.
 */
public class Bank {

    private final double[] accounts;


    /**
     * 创建每个账户
     *
     * @param n
     * @param initialBalance
     */
    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        for (int i = 0; i < accounts.length; i++) {
            accounts[i] = initialBalance;
        }
    }

    /**
     * transfer money from one account to another
     *
     * @param from
     * @param to
     * @param amount
     */
    public synchronized void transfer(int from, int to, double amount) throws Exception {

        while (accounts[from] < amount) {
            wait();
        }
        System.out.print(Thread.currentThread());
        accounts[from] -= amount;
        System.out.printf(" %10.2f from %d to %d", amount, from, to);
        accounts[to] += amount;
        System.out.printf(" Total Balance:%10.2f%n", getTotalBalance());
        notifyAll();
    }

    private synchronized double getTotalBalance() {
        double sum = 0;
        for (double a : accounts) {
            sum += a;
        }
        return sum;
    }

    public int size() {
        return accounts.length;
    }
}
