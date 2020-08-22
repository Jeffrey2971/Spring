package jeffrey.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 和事务管理相关的工具类，它包含了，开启事务，提交事务，回滚事务和释放连接
 */

@Component("TransactionManager")
@Aspect
public class TransactionManager {

    @Autowired
    private ConnectionUtils connectionUtils;

    @Pointcut("execution (* jeffrey.service.impl.*.*(..))")
    private void pt1(){}

    /**
     * 开启事务
     */
    /*@Before("pt1()")*/
    public  void beginTransaction(){
        try {
            System.out.println("执行了开启事物");
            connectionUtils.getThreadConnection().setAutoCommit(false);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 提交事务
     */
    /*@AfterReturning("pt1()")*/
    public  void commit(){
        try {
            System.out.println("执行了提交事物");
            connectionUtils.getThreadConnection().commit();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 回滚事务
     */
    /*@AfterThrowing("pt1()")*/
    public  void rollback(){
        try {
            System.out.println("执行了回滚事物");

            connectionUtils.getThreadConnection().rollback();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    /**
     * 释放连接
     */
    /*@After("pt1()")*/
    public  void release(){
        try {
            System.out.println("执行了释放连接");

            connectionUtils.getThreadConnection().close();//还回连接池中
            connectionUtils.removeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 环绕通知
     * @param pjp
     * @return
     */

    @Around("pt1()")
    public Object aroundAdvice(ProceedingJoinPoint pjp){
        Object rtValue = null;
        try {
            // 获取参数
            Object[] args = pjp.getArgs();
            // 开启事物
            this.beginTransaction();
            // 执行方法
            rtValue = pjp.proceed(args);
            // 提交事物
            this.commit();
            return rtValue;
        } catch (Throwable throwable) {
            // 回滚事物
            this.rollback();
            throw new RuntimeException(throwable);
        } finally {
            // 释放资源
            this.release();
        }


    }

}
