package cn.sp.chapter07.draw;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * 绘制几何图形
 * Created by 2YSP on 2018/3/17.
 */
public class DrawTest {
    public static void main(String[] args) {
        EventQueue.invokeLater(()->{
            JFrame frame = new DrawFrame();
            frame.setTitle("DrawTest");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

class DrawFrame extends JFrame{
    public DrawFrame(){
        add(new DrawComponent());
        pack();
    }
}

class DrawComponent extends JComponent{

    private static final int DEFAULT_WIDTH = 400;
    private static final int DEFAULT_HEIGHT = 400;

    public DrawComponent(){

    }

    @Override
    protected void paintComponent(Graphics g) {
       Graphics2D g2 = (Graphics2D) g;

       //画矩形
        double leftX = 100;
        double topY = 100;
        double width = 200;
        double height = 150;
        Rectangle2D rect = new Rectangle2D.Double(leftX, topY, width, height);
        g2.draw(rect);

        //画内部椭圆
        Ellipse2D ell = new Ellipse2D.Double();
        ell.setFrame(rect);
        //填充颜色
        g2.setPaint(Color.RED);
        g2.fill(ell);
//        g2.draw(ell);
        g2.setPaint(Color.BLACK);
        //画对角线
        g2.draw(new Line2D.Double(leftX,topY,leftX+width,topY+height));

        //画同中心园
        double centerX = rect.getCenterX();
        double centerY = rect.getCenterY();
        double radius = 150;

        Ellipse2D circle = new Ellipse2D.Double();
        circle.setFrameFromCenter(centerX,centerY,centerX+radius,centerY+radius);
        g2.draw(circle);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(DEFAULT_WIDTH,DEFAULT_HEIGHT);
    }
}
