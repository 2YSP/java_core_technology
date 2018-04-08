package cn.sp.chapter07.nothelloworld;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 2YSP on 2018/3/17.
 */
public class NotHelloWorld {

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            NotHelloWorldFrame frame = new NotHelloWorldFrame();
            frame.setTitle("NotHelloWorld");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }

}

class NotHelloWorldFrame extends JFrame{

    public NotHelloWorldFrame(){
        add(new NotHelloWorldComponent());
        pack();
    }
}

class NotHelloWorldComponent extends JComponent{

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    private static final int MESSAGE_X = 75;
    private static final int MESSAGE_Y = 100;

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setPaint(Color.RED);
        g2.drawString("Not a Hello,World program",MESSAGE_X,MESSAGE_Y);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }
}
