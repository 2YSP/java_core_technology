package cn.sp.chapter14.bounceThread;

import cn.sp.chapter14.bounce.Ball;
import cn.sp.chapter14.bounce.BallComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by 2YSP on 2018/4/8.
 */
public class bouceThread {

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new BounceFrame();
            frame.setTitle("BounceThread");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);

        });
    }
}

class BallRunnable implements Runnable{

    private Ball ball;
    private Component component;
    private static final int STEPS = 1000;
    private static final int DELAY = 5;

    public BallRunnable(Ball aBall,Component aCompent){
        ball = aBall;
        component = aCompent;
    }

    @Override
    public void run() {
        try {
            for(int i=0;i<STEPS;i++){
                ball.move(component.getBounds());
                component.repaint();
                Thread.sleep(DELAY);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class BounceFrame extends JFrame{

    private BallComponent component;

    public BounceFrame(){
        component = new BallComponent();
        add(component,BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        addButton(buttonPanel, "Start", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBall();
            }
        });
        addButton(buttonPanel, "Stop", new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        add(buttonPanel,BorderLayout.SOUTH);
        pack();
    }

    private void addBall() {
        Ball b = new Ball();
        component.add(b);
        Thread t = new Thread(new BallRunnable(b,component));
        t.start();
    }

    public void addButton(Container c,String title,ActionListener listener){
        JButton button = new JButton(title);
        c.add(button);
        button.addActionListener(listener);
    }

}
