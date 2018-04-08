package cn.sp.chapter10.annotation;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 2YSP on 2017/12/16.
 */
public class ButtonFrame extends JFrame {

    private static final int DEFAULT_WIDTH = 300;
    private static final int DEFAULT_HEIGHT = 200;

    private JPanel panel;//面板
    private JButton yellowButton;
    private JButton blueButton;
    private JButton redButton;

    public ButtonFrame(){
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);
        panel = new JPanel();
        add(panel);
        yellowButton = new JButton("Yellow");
        blueButton = new JButton("blue");
        redButton = new JButton("red");

        panel.add(yellowButton);
        panel.add(blueButton);
        panel.add(redButton);

        ActionListenerInstaller.processAnnotations(this);
    }

    @ActionListenerFor(source = "yellowButton")
    public void yellowBackground(){
        yellowButton.setBackground(Color.yellow);
    }

    @ActionListenerFor(source = "blueButton")
    public void blueBackground(){
        blueButton.setBackground(Color.blue);
    }

    @ActionListenerFor(source = "redButton")
    public void redBackground(){
        redButton.setBackground(Color.red);
    }
}
