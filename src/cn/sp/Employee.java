package cn.sp;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by 2YSP on 2017/12/16.
 */
public class Employee
{
    public static final  int NAME_SIZE = 40;
    public static final  int RECORD_SIZE = 2 * NAME_SIZE + 8 + 4+4+4;
    private String name;
    private double salary;
    private Date hireDay;

    public Employee(String name, double salary, int year, int month, int day){
        this.name = name;
        this.salary = salary;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year,month-1,day);
        this.hireDay = calendar.getTime();
    }

    public Employee(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public Date getHireDay() {

        return hireDay;
    }

    public void setHireDay(Date hireDay) {
        this.hireDay = hireDay;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", hireDay=" + hireDay +
                '}';
    }
}
