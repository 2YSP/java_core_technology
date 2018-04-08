package cn.sp.chapter07.sizedFrame;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * Created by 2YSP on 2018/3/17.
 */
public class SizedFrameTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new SizedFrame();
            frame.setTitle("SizedFrame");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class SizedFrame extends JFrame{

    public SizedFrame(){
        //获取屏幕大小
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        setSize(screenWidth/2,screenHeight/2);
        setLocationByPlatform(true);
        //set frame icon
        Image image = new ImageIcon(System.getProperty("user.dir")+"\\src\\cn\\sp\\chapter07\\sizedFrame\\dog.png").getImage();
        setIconImage(image);
    }
}
