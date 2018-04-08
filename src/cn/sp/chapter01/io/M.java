package cn.sp.chapter01.io;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * Created by 2YSP on 2017/12/16.
 */
public class M {

    public static void main(String[] args) throws Exception{
        //用户工作目录
//        System.out.println(System.getProperty("user.dir"));
//        FileInputStream fin = new FileInputStream("employee.txt");
//        DataInputStream din = new DataInputStream(fin);
//        while (din.read() != -1){
//            double d = din.readDouble();
//            System.out.println(d);
//        }
        /**文件添加字符串,true时保留以前的**/
//        OutputStream outputStream = new FileOutputStream("employee.txt",true);
//        outputStream.write("你好".getBytes("UTF-8"));
//        outputStream.close();

//        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
//        BufferedReader bf = new BufferedReader(inputStreamReader);
//        String s = "";
//        System.out.println("开始输入。。。。");
//        while (!"stop".equals(s)){
//             s = bf.readLine();
//            System.out.println(s);
//        }
//        System.out.println("结束。。。。");

        /*******向文件重写内存********/
//        PrintWriter out = new PrintWriter("employee.txt");
//
//        String name = "Harry Hacker";
//        double salary = 7000;
//        out.print(name);
//        out.print("\r\n");//换行符,linux是 \n == System.getProperty("line.separator")
//        out.println(salary);
//        out.flush();
//        out.close();

        /****获取所有别名****/
        Charset charset = Charset.forName("UTF-8");
//        Set<String> aliases = charset.aliases();
//        for (String alias : aliases){
//            System.out.println(alias);
//        }

        String str = "你好";
        //加密
        ByteBuffer buffer = charset.encode(str);
        byte[] bytes = buffer.array();
        //解密
        ByteBuffer buf = ByteBuffer.wrap(bytes,0,bytes.length);
        CharBuffer charBuffer = charset.decode(buf);
        System.out.println(charBuffer.toString());

    }

}
