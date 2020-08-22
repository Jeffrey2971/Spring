package jeffrey.proxy;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 模拟一个消费者
 */

public class Client {
    public static void main(String[] args) {
        final Product product = new Product();
        product.saleProduct(10000f);

/**
 * ## 回顾动态代理
 * - 动态代理
 *     - 特点：字节码随用随创建，谁用谁加载
 *     - 作用：在不修改源码的情况下，实现方法的增强
 * - 分类
 *     - 基于接口的动态代理
 *     - 基于子类的动态代理
 * - 基于接口的动态代理
 *     - 涉及的类：Proxy
 *     - 提供者：JDK官方
 * - 如何创建基于Proxy的代理对象
 *     - 使用Proxy类中的newProxyInstance
 * - 创建代理对象的要求
 *     - 被代理的类最少需要实现一个接口，如果没有则不能使用
 * - newProxyInstance参数
 *     - ClassLoader：类加载器
 *         - 用于加载代理对象的字节码，和被代理对象使用相同的类加载器，规定写法
 *     - Class[]：字节码数组
 *         - 用于代理对象和被代理对象有相同的方法，固定写法
 *     - InvocationHandler：用于提供增强的代码
 *         - 用于写如何代理，一般写该接口的实现类，通常是匿名内部类，但不是必须的。此接口的实现类都是谁用谁写的
 */

        IProduct proxyProduct = (IProduct) Proxy.newProxyInstance(product.getClass().getClassLoader(), Product.class.getInterfaces(), new InvocationHandler() {
            /**
             * 作用：执行被代理对象接口中的方法都会经过该方法
             * @param proxy：代理对象的应用
             * @param method：当前执行的方法
             * @param args：当前方法所需的参数
             * @return：和被代理对象有相同的返回值
             * @throws Throwable
             */
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                // 提供增强的代码

                Object returnValue = null;

                // 获取方法的执行参数
                Float money = (Float)args[0];
                // 判断当前方法是否销售
                if("saleProduct".equals(method.getName())){
                     returnValue = method.invoke(product, money * 0.8f);

                }
                return returnValue;

            }
        });
        proxyProduct.saleProduct(10000f);

    }
}
