package cn.sp.chapter07.simpleframes;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 2YSP on 2018/3/17.
 */
public class SimpleFrameTest {

    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new SimpleFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class SimpleFrame extends JFrame{

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    public SimpleFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }
}
