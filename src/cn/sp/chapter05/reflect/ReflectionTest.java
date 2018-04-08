package cn.sp.chapter05.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Scanner;

/**
 * Created by 2YSP on 2018/3/8.
 * this program uses reflection to print all features of a class
 */
public class ReflectionTest {


    public static void main(String[] args) {
        String name;
        if (args.length > 0){
            name = args[0];
        }else {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter class name (e.g. java.util.Date):");
            name = scanner.next();
        }
        try {
            //print class name and superclass name if != Object
            Class<?> cl = Class.forName(name);
            Class<?> supercl = cl.getSuperclass();
            String modifiers = Modifier.toString(cl.getModifiers());
            if (modifiers.length()>0){
                System.out.print(modifiers+" ");
            }
            System.out.print("class "+name);
            if (supercl != null && supercl != Object.class){
                System.out.print(" extends "+supercl.getName());
            }

            System.out.print("\n{\n");
            printConstructors(cl);
            System.out.println();
            printMethods(cl);
            System.out.println();
            printFields(cl);
            System.out.println("}");

        }catch (Exception e){
            e.printStackTrace();
        }
        System.exit(0);
    }

    /**
     * prints all constructors of a class
     * @param c1
     */
    public static void printConstructors(Class c1){
        Constructor[] constructors = c1.getDeclaredConstructors();
        for(Constructor c : constructors){
            String name = c.getName();
            System.out.print("    ");
            String modifiers = Modifier.toString(c.getModifiers());
            if (modifiers.length() > 0){
                System.out.print(modifiers + " ");
            }
            System.out.print(name+"(");
            //print parameter types
            Class[] parameterTypes = c.getParameterTypes();
            for (int j =0;j<parameterTypes.length;j++){
                if (j > 0){
                    System.out.print(", ");
                }
                System.out.print(parameterTypes[j].getName());
            }
            System.out.println(");");
        }
    }

    /**
     * prints all methods of a class
     * @param c1
     */
    public static void printMethods(Class c1){
        Method[] methods = c1.getDeclaredMethods();
        for(Method m : methods){
            Class<?> returnType = m.getReturnType();
            String name = m.getName();

            System.out.print("    ");
            String modifiers = Modifier.toString(m.getModifiers());
            if (modifiers.length() > 0){
                System.out.print(modifiers + " ");
            }
            System.out.print(returnType.getName() + " "+name+"(");

            Class<?>[] parameterTypes = m.getParameterTypes();
            for (int i=0;i<parameterTypes.length;i++){
                if (i>0){
                    System.out.print(", ");
                }
                System.out.print(parameterTypes[i].getName());
            }
            System.out.println(");");
        }
    }

    /**
     * prints all fields of a class
     * @param c1
     */
    public static void printFields(Class c1){
        Field[] declaredFields = c1.getDeclaredFields();
        for (Field f : declaredFields){
            String name = f.getName();
            Class<?> type = f.getType();

            System.out.print("    ");
            String modifiers = Modifier.toString(f.getModifiers());
            if (modifiers.length() > 0){
                System.out.print(modifiers+" ");
            }

            System.out.println(type.getName()+" "+name+";");
        }
    }
}
