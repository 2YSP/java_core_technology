package cn.sp.chapter07;

import java.awt.*;

/**
 * Created by 2YSP on 2018/3/18.
 */
public class ListFonts {
    public static void main(String[] args) {
        String[] fontNames = GraphicsEnvironment.
                getLocalGraphicsEnvironment().
                getAvailableFontFamilyNames();
        System.out.println(fontNames.length);
        for(String name : fontNames){
            System.out.println(name);
        }
    }
}
