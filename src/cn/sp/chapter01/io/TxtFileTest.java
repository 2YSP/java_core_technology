package cn.sp.chapter01.io;

import cn.sp.Employee;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Scanner;

/**
 * Created by 2YSP on 2017/12/16.
 */
public class TxtFileTest {
    public static void main(String[] args)throws Exception {
        Employee[] staff = new Employee[3];

        staff[0] = new Employee("Carl Cracker",75000,1987,12,15);
        staff[1] = new Employee("Harry Hacker",50000,1989,10,1);
        staff[2] = new Employee("Tony Tester",40000,1990,3,15);

        //save all employee records to the file employee.txt
        PrintWriter out = new PrintWriter("employee.txt","UTF-8");
        writerData(staff,out);
        out.close();
        //retrieve all records into a new array
        Scanner in = new Scanner(new FileInputStream("employee.txt"),"UTF-8");

        Employee[] newStaff = readData(in);
        for (Employee e :newStaff){
            System.out.println(e);
        }
    }

    private static Employee[] readData(Scanner in) {
        //返回数组长度
        int n = in.nextInt();
        in.nextLine();//consume newline

        Employee[] newStaff = new Employee[n];
        for (int i = 0 ; i<n ; i++){
            newStaff[i] = readEmployee(in);
        }
        return newStaff;
    }

    private static Employee readEmployee(Scanner in) {
        String line = in.nextLine();
        String[] tokens = line.split("\\|");
        String name = tokens[0];
        double salary = Double.parseDouble(tokens[1]);
        int year = Integer.parseInt(tokens[2]);
        int month = Integer.parseInt(tokens[3]);
        int day = Integer.parseInt(tokens[4]);
        return new Employee(name,salary,year,month,day);
    }

    private static void writerData(Employee[] staff, PrintWriter out) {
        //write the number of employees
        out.println(staff.length);

        for (Employee employee : staff){
            writeEmployee(employee,out);
        }
    }

    private static void writeEmployee(Employee employee, PrintWriter out) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(employee.getHireDay());
        out.println(employee.getName()+ "|" + employee.getSalary() + "|" + calendar.get(Calendar.YEAR) + "|"
         + (calendar.get(Calendar.MONTH)+1) + "|" + calendar.get(Calendar.DAY_OF_MONTH));

    }
}
