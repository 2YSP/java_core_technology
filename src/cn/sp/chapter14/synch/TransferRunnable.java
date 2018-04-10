package cn.sp.chapter14.synch;



/**
 * Created by 2YSP on 2018/4/8.
 */
public class TransferRunnable implements Runnable {

    private Bank bank;
    private int fromAccount;
    private double maxAccount;
    private int DELAY = 1000;

    public TransferRunnable(Bank bank, int from, double max){
        this.bank = bank;
        this.fromAccount = from;
        this.maxAccount = max;
    }

    /**
     * 随机转账（金额随机,对象随机）
     */
    @Override
    public void run() {
        try {
            while (true){
                int toAccount = (int) (bank.size()*Math.random());
                double amount = maxAccount*Math.random();
                bank.transfer(fromAccount,toAccount,amount);
                Thread.sleep(DELAY);
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
