package jeffrey.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * 用户记录日志的工具类，提供了公共的代码
 */
@Component("logger")
@Aspect // 表示当前类是一个切面类
public class Logger {

    @Pointcut("execution(* jeffrey.service.impl.*.*(..))")
    private void pt1(){}

    /**
     * 环绕通知
     * 问题：当配置了环绕通知之后，切入点方法没有执行，而通知方法执行了
     * 分析：对比动态代理中的环绕通知代码，发现动态代理的环绕通知有明确的切入点方法调用，而这里没有
     * 解决：Spring框架提供了一个接口：ProceedingJoinPoint，该方法又一个方法proceed()，该方法相当于明确调用切入方法
     *      该接口可以作为环绕通知的方法参数，在程序执行时，Spring框架会提供该接口的实现类使用
     * Spring中的环绕通知：
     *      它是Spring框架提供的一种可以在代码中手动控制增强方法在何时执行的方式
     */
    @Around("pt1()")
    public Object aroundPrintLog(ProceedingJoinPoint pjp){
        Object rtValue = null;
        try {
            Object[] args = pjp.getArgs(); // 得到方法执行所需的参数
            System.out.println("Logger类中的aroundPrintLog方法开始记录日志了,,,前置");
            rtValue = pjp.proceed(); // 明确调用业务层方法(切入点方法)
            System.out.println("Logger类中的aroundPrintLog方法开始记录日志了,,,后置");
            return rtValue;
        } catch (Throwable e) {
            System.out.println("Logger类中的aroundPrintLog方法开始记录日志了,,,异常");

            throw new RuntimeException(e);
        } finally {
            System.out.println("Logger类中的aroundPrintLog方法开始记录日志了,,,最终");
        }

    }
}
