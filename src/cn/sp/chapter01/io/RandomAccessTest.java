package cn.sp.chapter01.io;

import cn.sp.Employee;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by 2YSP on 2017/12/17.
 * 使用随机访问分件类RandomAccessFile操作文件
 */
public class RandomAccessTest {

    public static void main(String[] args)throws IOException {
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Carl Cracker",75000,1987,12,15);
        staff[1] = new Employee("Harry Hacker",50000,1989,10,1);
        staff[2] = new Employee("Tony Tester",40000,1990,3,15);

        DataOutputStream out = new DataOutputStream(new FileOutputStream("employee.txt"));
        //save all employee records to the file employee.txt
        for (Employee e : staff){
            writeData(out,e);
        }

        RandomAccessFile in = new RandomAccessFile("employee.txt","r");//只读
        //取回所有的记录到 一个新的数组
        //计算 数组大小
        int n = (int) (in.length()/Employee.RECORD_SIZE);
        Employee[] newStaff = new Employee[n];

        //倒序读取Employee
        for (int i = n-1; i >= 0 ;i--){
            newStaff[i] = new Employee();
            in.seek(i*Employee.RECORD_SIZE);
            newStaff[i] = readData(in);
        }

        //打印读到的最新的记录
        for (Employee e : newStaff){
            System.out.println(e);
        }
    }

    private static void writeData(DataOutputStream out, Employee e)throws IOException {
       DataIO.writeFixedString(e.getName(),Employee.NAME_SIZE,out);
       out.writeDouble(e.getSalary());

        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(e.getHireDay());
        out.writeInt(calendar.get(Calendar.YEAR));
        out.writeInt(calendar.get(Calendar.MONTH-1));
        out.writeInt(calendar.get(Calendar.DAY_OF_MONTH));

    }

    private static Employee readData(DataInput input)throws IOException{
        String name = DataIO.readFixedString(Employee.NAME_SIZE,input);
        double salary = input.readDouble();
        int y = input.readInt();
        int m = input.readInt();
        int d = input.readInt();
        return new Employee(name,salary,y,m,d);
    }
}
