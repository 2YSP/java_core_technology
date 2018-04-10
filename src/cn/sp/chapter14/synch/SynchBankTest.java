package cn.sp.chapter14.synch;



/**
 * 未同步情况模拟银行转账
 * Created by 2YSP on 2018/4/8.
 */
public class SynchBankTest {

    private static final int NACCOUNTS = 100;
    private static final int INITIAL_BALANCE = 1000;
    public static void main(String[] args) {
       Bank bank = new Bank(NACCOUNTS,INITIAL_BALANCE);
        for(int i=0;i<NACCOUNTS;i++){
            Runnable r = new TransferRunnable(bank,i,INITIAL_BALANCE);
            Thread t = new Thread(r);
            t.start();
        }
    }
}
