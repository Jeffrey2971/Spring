package factory;


import com.sun.org.apache.regexp.internal.RE;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 一个创建Bean对象的工厂
 * Bean：在计算机英语中，可重用组件的含义
 * JavaBean：用Java语言编写的可重用组件
 *      JavaBean > 实体类
 *      JavaBean != 实体类
 * 它就是创建service和dao对象的
 *
 *
 */
public class BeanFactory {
    // 定义一个Properties对象
    private static Properties properties;
    // 定义一个Map用于存放创建的对象，称之为容器
    private static Map<String, Object> beans;
    // 使用静态代码块为Properties对象赋值
    static {
        try {
            // 实例化对象
            properties = new Properties();
            // 获取properties文件的流对象
            InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties");
            properties.load(is);
            // 实例化容器
            beans = new HashMap<String, Object>();
            // 取出配置文件中所有的key
            Enumeration<Object> keys = properties.keys();
            // 遍历枚举
            while (keys.hasMoreElements()){
                // 取出每个key
                String key = keys.nextElement().toString();
                // 根据key获取value
                String beanPath = properties.getProperty(key);
                // 反射创建对象
                Object value = Class.forName(beanPath).newInstance();
                // 把key和value存入容器之中
                beans.put(key, value);
            }
        } catch (IOException e) {
            throw new ExceptionInInitializerError("初始化properties异常");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据bean的名称获取对象
     * @param beanName
     * @return
     */

    public static Object getBean(String beanName){
        return beans.get(beanName);
    }

    /**
     * 根据Bean的名称获取Bean对象
     * @param beanName
     * @return
     */

    /*public static Object getBean(String beanName){
        Object bean = null;
        try {
            String beanPath = properties.getProperty(beanName);
            bean = Class.forName(beanPath).newInstance();
            return bean;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return bean;

    }*/

}
