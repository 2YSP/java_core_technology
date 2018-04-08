package cn.sp.chapter10.annotation;

import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by 2YSP on 2017/12/16.
 * 分析注解以及安装行为监听器
 */
public class ActionListenerInstaller {

    /**
     * Process all ActionListenerFor annotation in the given object
     *
     * @param obj an object whose methods may have ActionListenerFor annotations
     */
    public static void processAnnotations(Object obj) {
        try {
            //获取类
            Class<?> c1 = obj.getClass();
            for (Method m : c1.getDeclaredMethods()) {
                ActionListenerFor a = m.getAnnotation(ActionListenerFor.class);//获取注解
                if (a != null) {
                    Field f = c1.getDeclaredField(a.source());
                    f.setAccessible(true);//字段私有的 添加权限
                    addListener(f.get(obj),obj,m);// field.get(obj) 返回指定对象上此 Field 表示的字段的值 :yellowButton
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds an action listener that calls a given method
     * @param source the event source th which an action listener is added
     * @param param the implicit parameter of the method that the listener calls
     * @param m the method that the listener calls
     * @throws Exception
     */
    public static void addListener(Object source,final Object param,final Method m)throws Exception{
//        yellowButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                doSomething();
//            }
//        });
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return m.invoke(param);//执行将按钮背景变色的方法
            }
        };
        //创建代理对象
        Object listener = Proxy.newProxyInstance(null,new Class[]{java.awt.event.ActionListener.class},handler);
        Method adder = source.getClass().getMethod("addActionListener", ActionListener.class);
        adder.invoke(source,listener);
    }
}
