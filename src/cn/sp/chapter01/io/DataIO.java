package cn.sp.chapter01.io;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by 2YSP on 2017/12/17.
 * 助手类
 */
public class DataIO {

    public static void writeFixedString(String s, int size, DataOutput out)throws IOException{
        for (int i = 0 ; i < size ;i++){
            char ch = 0;
            if (i < s.length()){
                ch = s.charAt(i);
            }
            out.writeChar(ch);
        }
    }

    public static String readFixedString(int size, DataInput in)throws IOException{
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        boolean more = true;
        while (more && i < size){
            char ch = in.readChar();
            i++;
            if (ch == 0){
                more = false;
            }else {
                stringBuilder.append(ch);
            }
        }
        in.skipBytes(2*(size-i));
        return stringBuilder.toString();
    }
}
