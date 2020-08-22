package jeffrey.proxy.cglib;


import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 模拟一个消费者
 */

public class Client {
    public static void main(String[] args) {
        final Product product = new Product();
/**
 * ## 回顾动态代理
 * - 动态代理
 *     - 特点：字节码随用随创建，谁用谁加载
 *     - 作用：在不修改源码的情况下，实现方法的增强
 * - 分类
 *     - 基于接口的动态代理
 *     - 基于子类的动态代理
 * - 基于子类的动态代理
 *     - 涉及的类：Enhancer
 *     - 提供者：第三方cglib库
 * - 如何创建基于Proxy的代理对象
 *     - 使用Enhancer类中的Enhancer
 * - 创建代理对象的要求
 *     - 被代理的类不能是最终类
 * - create参数
 *     - ClassLoader：字节码
 *         - 用于指定被代理对象的字节码
 *     - Callback：用于提供增强的代码
 *         - 用于写如何代理，一般写该接口的实现类，通常是匿名内部类，但不是必须的。此接口的实现类都是谁用谁写的，一般写的都是该接口的字节口实现类，MethodInterceptor(方法拦截)
 */

        Product cglibProducer = (Product) Enhancer.create(product.getClass(), new MethodInterceptor() {
            /**
             * 被执行代理的对象的任何方法都会经过该方法
             * @param proxy
             * @param method
             * @param args
             * 以上三个参数和基于接口中的动态代理中invoke方法的参数是一样的
             * @param methodProxy 当前执行方法的代理对象
             * @return
             * @throws Throwable
             */
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                // 提供增强的代码

                Object returnValue = null;

                // 获取方法的执行参数
                Float money = (Float) args[0];
                // 判断当前方法是否销售
                if ("saleProduct".equals(method.getName())) {
                    returnValue = method.invoke(product, money * 0.8f);

                }
                return returnValue;

            }
        });
        cglibProducer.saleProduct(1200000f);
    }
}
