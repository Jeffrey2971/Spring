# SpringDay03

## 分析案例中的问题
- 问题：转账过程中如果出现异常，则会导致转出账户扣钱，转入账户不加钱
- 原因：没有给对应的方法事务管理，遇到错误无法rollback
## 回顾动态代理
- 动态代理
    - 特点：字节码随用随创建，谁用谁加载
    - 作用：在不修改源码的情况下，实现方法的增强
- 分类
    - 基于接口的动态代理
    - 基于子类的动态代理
- 基于接口的动态代理
    - 涉及的类：Proxy
    - 提供者：JDK官方
- 如何创建基于Proxy的代理对象
    - 使用Proxy类中的newProxyInstance
- 创建代理对象的要求
    - 被代理的类最少需要实现一个接口，如果没有则不能使用
- newProxyInstance参数
    - ClassLoader：类加载器
        - 用于加载代理对象的字节码，和被代理对象使用相同的类加载器，规定写法
    - Class[]：字节码数组
        - 用于代理对象和被代理对象有相同的方法，固定写法
    - InvocationHandler：用于提供增强的代码
        - 用于写如何代理，一般写该接口的实现类，通常是匿名内部类，但不是必须的。此接口的实现类都是谁用谁写的
        - 参数
            - 作用：执行被代理对象接口中的方法都会经过该方法
                - @param proxy：代理对象的应用
                - @param method：当前执行的方法
                - @param args：当前方法所需的参数
                - @return：和被代理对象有相同的返回值
                - @throws Throwable

## 动态代理的另一种方式
- 基于子类的动态代理
    - 实际的类：Enhancer
    - 提供者：第三方cglib库
- 创建基于Proxy的代理对象
    - 使用Enhancer类中的Enhancer
- 代理对象的要求
    - 被代理的类不能是一个最终类
- 参数
    - create参数
        - ClassLoader：字节码
            - 用于指定被代理对象的字节码
        - Callback：用于提供增强的代码
            - 用于写如何代理，一般写该接口的实现类，通常是匿名内部类，但不是必须的。此接口的实现类都是谁用谁写的，一般写的都是该接口的字节口实现类，MethodInterceptor(方法拦截)
- exp：
    ```text
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
          });
        ```
  
## 解决案例中的问题

## AOP
- AOP概念
    - AOP(面向切面变成)，将程序中重复的代码提取出来，在需要执行的时候，使用动态代理的技术，再不修改源码的基础上，对方法进行增强，降低业务逻辑部分之间的耦合度，提高重用性
- 作用
    - 在程序的运行期间，不修改源码的情况下，对方法进行增强
- 优势
    - 减少重复代码
    - 提高开发效率
    - 方便维护
- 实现方法
    - 使用动态代理技术
## Spring中的AOP相关术语
- 连接点和切入点
    - Joinpoint(连接点)：指的是被拦截的点，在Spring中指的是方法，因为Spring只支持方法类型的连接点，例如业务层接口中所有的方法
    - Ponintcut(切入点)：指的是要对哪些连接点进行拦截的定义，换句话说就是被增强的方法，例
    如在业务层接口中定义一个void test()方法，这个方法不被代理所增强，所以说这就不是一个切入点但它是一个连接点，因为它未被代理所增强
    - 连接点和切入点的总结：所有的切入点都是连接点，但所有的连接点不一定都是切入点
- Advice(通知/增强)
    - 指的是拦截到连接点之后要做的事情就是通知
    - 通知的类型：前置通知、后置通知、异常通知、环绕通知
        ```text
              public IAccountService getAccountService() {
                  return (IAccountService) Proxy.newProxyInstance(accountService.getClass().getClassLoader(),
                          accountService.getClass().getInterfaces(),
                          new InvocationHandler() {
                              public Object invoke(Object proxy, Method method, Object[] args) throws Throwable { 整个invoke方法在执行时就是环绕通知
                                  if("test".equals(method.getName())){
                                      return method.invoke(accountService,args);
                                  }
                                  Object rtValue = null;
                                  try {
                                      //1.开启事务
                                      txManager.beginTransaction(); // 前置通知
                                      //2.执行操作
                                      rtValue = method.invoke(accountService, args); // 在环绕通知中有明确的切入点方法调用
                                      //3.提交事务
                                      txManager.commit(); // 后置通知
                                      //4.返回结果
                                      return rtValue;
                                  } catch (Exception e) {
                                      //5.回滚操作
                                      txManager.rollback();  
                                      throw new RuntimeException(e); // 异常通知
                                  } finally {
                                      //6.释放连接
                                      txManager.release(); 最终通知
                                  }
                              }
                          });
              }

        ```
- Introduction(引介)：指的是一种特殊的通知，再不修改类代码的前提下，Introduction可以在运行期为类动态的添加一些方法或者Field(字段)
- Target(目标对象)：被代理的对象
- Weaving(织入)：指的是把增强应用到目标对象来创建新的代理对象过程
- Proxy(代理)：一个类被AOP织入增强后，产生一个结果代理类
- Aspect(切面)：指的是切入点和通知(引介)的结合
## Spring中AOP需要明确的事情
- 开发阶段
    - 编写核心业务代码(开发主线)
    - 把公共代码抽取出来，制作成通知
    - 在配置文件中，声明切入点与通知间的关系，即切面
- 运行阶段(由Spring框架完成)
    - Spring框架监控切入点方法的执行，一旦监控到切入点的方法被运行，使用代理机制，动态茶u你更加爱你目标对象的代理对象，根据通知类别，
    在代理对象的对应位置，将通知对应的功能织入，完整完整的代码逻辑运行
## Spring中基于XML的AOP配置
- 1、把通知的bean交给Spring框架处理
- 2、使用aop:config标签表明开始AOP配置
- 3、使用aop:aspect标签表明配置切面
    - 属性
        - id属性：给切面提供一个唯一的标准
        - ref属性：指定通知类bean的id
- 4、在aop:aspect标签的内部使用对应的标签来配置通知的类型
    - aop:before：表示配置前置通知
        - 属性
            - method属性：用于指定类中哪个方法是前置通知
            - pointcut属性：用于指定切入表达式，该表达式的含义指的是对业务层中哪些方法的增强
        - 切入表达式的写发
            - 关键字：execution(表达式)
                - 访问修饰符 返回值 包名.包名.包名...类名.方法名(参数列表)
        - 标准的表达式写法
            - public void jeffrey.service.impl.AccountServiceImpl.saveAccount()
        - 简化写法
            - 访问修饰符可以省略
                - * void jeffrey.service.impl.AccountServiceImpl.saveAccount()
            - 返回值类型可以用通配符表示任意类型返回值
                - * * jeffrey.service.impl.AccountServiceImpl.saveAccount()
            - 包名可以使用通配符表示任意包，写法是有几层包就需要写几个*.
                - * * *.*.*.*.jeffrey.service.impl.AccountServiceImpl.saveAccount()
            - 包名可以使用..表示当前包及其子包
                - * * *..jeffrey.service.impl.AccountServiceImpl.saveAccount()
            - 类名和方法名都可以使用*实现通配
        - 参数列表
            - 基本类型可以直接写数据名称：int
            - 引用类型写包名.类名的方法：java.lang.String
            - 可使用通配符表示惹你类型，但是必须有参数
            - 可使用..表示有无参数均可，有参数可以是任意类型
        - 全通配写法
            - * *..*.*(..)
        - 在实际开发中切入点表达式通常写法
            - 切到业务层实现类下的所有方法
                - jeffrey.service.impl.*.*(..)
                
## Spring中基于注解的AOP配置
- 声明bean.xml命名空间
    ```text
      <?xml version="1.0" encoding="UTF-8"?>
      <beans xmlns="http://www.springframework.org/schema/beans"
             xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
             xmlns:aop="http://www.springframework.org/schema/aop"
             xmlns:context="http://www.springframework.org/schema/context"
             xsi:schemaLocation="http://www.springframework.org/schema/beans
              http://www.springframework.org/schema/beans/spring-beans.xsd
              http://www.springframework.org/schema/aop
              http://www.springframework.org/schema/aop/spring-aop.xsd
              http://www.springframework.org/schema/context
              http://www.springframework.org/schema/context/spring-context.xsd">
      </beans>
    ```
- AOP注解
    - @Aspect：表示当前类是一个切面类
    - @Pointcut：JoinPoint的表达式，表示拦截哪些方法，一个Pointcut对应多个JoinPoint
                
    - @Before()：表示前置通知
    - @AfterReturning()：表示后置通知
    - @AfterThrowing()：表示异常通知
    - @After()：表示最终通知
    - @Around()：表示环绕通知
        - 细节
            - 括号内的参数填写的是@Pointcut定义的表达式，需注意的是填写方法时需要带上括号()
            - 如果是是基于注解配置的话，不需要添加括号()
- 细节
    - 基于注解的AOP配置顺序会发生混乱，所以一般使用环绕通知
    - 基于注解的AOP配置
- Spring中的环绕通知
    - Spring框架提供了一个名为ProceedingJoinPoint，该方法有一个方法名为proceed()，相当于明确调用切入方法，
    该接口可以作为环绕通知的参数方法，在程序执行时，Spring框架会看提供该接口的实现类使用并返回Object类型返回值，总的来说，
    它是Spring框架提供的一种可以在代码中手动控制增强方法在何时执行的方式
    
    - ```<context:component-scan base-package="jeffrey"></context:component-scan>```
- 在业务层中添加@Service注解从而交给Spring管理
