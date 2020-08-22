# 今日内容
- filter：过滤器
- Listener：监听器



# filter：过滤器
- 概念
    - 生活中的顾虑器：净水器，空气净化器，土匪
    - Web中的过滤器：当访问服务器的资源时，过滤器可以将请求拦截下来，完成一些特殊的功能
    - 过滤器的作用
        - 一般用于完成通用的操作，如：登录验证、统一编码处理、敏感字符过滤等
    
- 快速入门
    - 步骤
        - 定义一个类，实现一个接口Filter
        - 复写方法
        - 配置拦截路径
            - web.xml
            - 注解
    - 过滤器细节
        - web.xml配置
            <?xml version="1.0" encoding="UTF-8"?>
            <web-app xmlns="http://xmlns.jcp.org/xml/ns/javaee"
                     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/javaee http://xmlns.jcp.org/xml/ns/javaee/web-app_3_1.xsd"
                     version="3.1">
            <filter>
                <filter-name>demo1</filter-name>
                <filter-class>web.filter.FilterDemo1</filter-class>
            </filter>
                <filter-mapping>
                    <filter-name>demo1</filter-name>
                    <url-pattern>/*</url-pattern>
                </filter-mapping>
            </web-app>

        - 过滤器执行流程
            - 执行过滤器
            - 执行放行后的资源
            - 回来执行过滤器放行代码下的代码
        - 过滤器生命周期方法
            - init：在服务器启动后会创建Filter对象，然后调用init方法，只执行一次，一般用于加载资源
            - doFilter：每一次请求被拦截资源时，会执行，执行多次
            - destroy：在服务器关闭后，Filter对象被销毁，如果服务器是正常关闭则会执行destroy方法，只执行一次，一般用于释放资源
        - 过滤器配置详解
            - 拦截路径配置
                - 具体资源路径：/index.jsp     只有访问index.jsp资源时，过滤器才会生效
                - 拦截目录：/user/*      访问/user下所有的资源时，过滤器都会生效
                - 后缀名拦截：*.jsp       访问所有后缀名为jsp的资源时，过滤器都会被执行
                - 拦截所有资源：/*     访问所有资源，过滤器都会被执行
            - 拦截方式配置：资源被访问的方式
                - 注解配置
                    - 设置DispatcherType属性
                        - REQUEST：默认值，浏览器直接请求资源
                        - FORWARD：转发访问资源
                        - INCLUDE：包含访问资源
                        - ERROR：错误跳转资源
                        - ASYNC：异步访问资源
                - web.xml配置
                    - 设置<dispatcher></dispatcher>标签即可，内容属性同上
        - 过滤器链(配置多个过滤器)
            - 执行顺序：如果有两个过滤器，过滤器1和过滤器2
                - 过滤器1
                - 过滤器2
                - 资源执行
                - 过滤器2
                - 过滤器1
            - 过滤器先后顺序
                - 注解配置：按照类型的字符串比较规则，值小的先执行
                    - 如：AFilter 和 BFilter，AFilter先执行
                - web.xml配置：</filter-mapping>谁定义在上面，谁先执行
    -  案例1
        - 登录验证
            - 需求：
              	1. 访问day17_case案例的资源。验证其是否登录
              	3. 如果登录了，则直接放行。
              	4. 如果没有登录，则跳转到登录页面，提示"您尚未登录，请先登录"。
    - 案例2
        - 需求：
          	1. 对day17_case案例录入的数据进行敏感词汇过滤
          	2. 敏感词汇参考《敏感词汇.txt》
          	3. 如果是敏感词汇，替换为 ***
        - 分析
            - 对request对象进行增强，增强获取参数相关方法
            - 放行，传递代理对象
        - 增强对象的功能
            - 设计模式：一些通用地解决固定问题的方式
                - 装饰模式
                - 代理模式
                    - 动态代理
                        - 概念
                            - 真实对象：被代理的对象
                            - 代理对象
                            - 代理模式：代理对象代理真是对象，达到增强真实对象功能的目的

                        - 实现方式
                            - 静态代理：有一个类文件描述代理模式
                            - 动态代理：内存中形成代理类
                                - 实现步骤
                                    - 代理对象和真实对象实现相同的接口
                                    - 代理对象 = Proxy.newProxyInstance();
                                    - 使用代理对象调用方法
                                    - 增强方法
                                - 增强方式
                                    - 增强参数列表
                                    - 增强返回值类型
                                    - 增强方法体执行逻辑
                                - Proxy.newProxyInstance(lenovo.getClass().getClassLoader(), lenovo.getClass().getInterfaces(), new InvocationHandler()
## Listener：监听器
- 概念：Web的三大组件之一
    - 事件监听机制
        - 事件：一件事情
        - 事件源：事件发生的地方
        - 监听器：一个对象
        - 注册监听：将事件、事件源、监听器绑定在一起，当事件源上发生某个事件后，执行监听器代码
    - ServletContextListener：监听ServletContext对象的创建和销毁
        - void contextDestroyed(ServletContextEvent sce)：ServletContext对象被销毁之前会调用调用该方法
        - void contextInitialized(ServletContextEvent sce)：ServletContext对象被创建后会调用该方法
    - 步骤
        - 定义一个类，实现ServletContextListener接口
        - 复写方法
        - 配置
            - web.xml
            - 注解

                