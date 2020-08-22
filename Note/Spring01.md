# SpringDay01



## 程序的耦合
- 耦合：可以理解为程序间的依赖关系
    - 包括
        - 类之间的依赖
        - 方法之间的依赖
- 解耦：降低程序间的依赖关系(无法做到完全避免)
    - 在实际开发中应该做到：编译器不依赖，运行时才依赖
    - 解耦思路
        - 第一步：使用反射创建对象，避免使用new关键字
        - 第二部：通过读取配置文件读取要创建的对象权限定类名
- IOC控制反转
    - 控制反转(Inversion of Control，英文缩写IOC)，把创建对象的权利交给框架，是框架的重要特征，并非面向对象编程的专用术语。
    它包括依赖注入(Dependency injection，英文简称DI)和依赖查找(Dependency Lookup)
    - 明确IOC的作用可消减计算机程序的耦合(解除代码中的依赖关系)，但并不能完全消除耦合，推荐使用工厂模式

        

## 创建Bean对象工厂
- 一个创建Bean对象的工厂
    - Bean：在计算机英语中，可重用组件的含义
    - JavaBean：用Java语言编写的可重用组件
        - JavaBean > 实体类
        - JavaBean != 实体类
        - 它就是创建service和dao对象的
- 需要一个配置文件配置service和dao
    - 配置内容部分
        - 全唯一标示 = 限定类名(key = value)
- 通过读取配置文件中配置的内容，反射创建对象
    - 配置方式可以是xml或properties

## 使用spring的IOC解决程序耦合
- 编写bean.xml
    <?xml version="1.0" encoding="UTF-8"?>
    <!--把对象的创建交给Spring管理-->
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans.xsd">
    <
    <bean id="accountService" class="service.impl.AccountServiceImpl1"></bean>
    <bean id="accountDao" class="dao.impl.AccountDaoImpl"></bean>
    </beans>
- 获取spring的IOC核心容器，并根据<bean>标签获取id对象
    - 1、获取核心容器对象
        ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
    - 2、根据id获取bean对象
        - 两种方式
            - IAccountService as = (IAccountService) ac.getBean("accountService"); 强转为object
            - IAccountDao dao = ac.getBean("accountDao", IAccountDao.class); 传入字节码文件返回对象
- ApplicationContext的三个常用实现类
    - ClassPathXmlApplicationContext：它可以加载类路径下的配置文件，要求配置文件必须在类路径下，否则加载不了创建不了容器
    - FileSystemXmlApplicationContext：它可以加载磁盘下任意路径下的配置文件(需有访问权限)
    - AnnotationConfigApplicationContext：它是用于读取注解创建容器的
- 核心容器的两个接口引发出的问题
    - ApplicationContext
        - 它在构建核心容器时，创建对象采取的策略是立即加载的，只要一读取完配置文件就马上创建配置文件中配置的对象
        - 使用场景：单利对象适用，实际开发中更多采用此接口
    - BeanFactory：
        - 它在构建核心容器时，创建对象采取的策略是延迟加载的，什么时候根据id获取对象，什么时候才真正的创建对象
        - 使用场景：多利对象适用
        - 
- Spring对bean的管理细节
    - 创建bean的三种方式
        - 1、使用默认构造函数创建
            - 在spring的配置文件中使用bean标签，配以id和class属性之后，且没有其他属性和标签时，采用的就是默认构造函数bean对象，此时如果类中没有默认构造函数，则对象无法创建
            - <bean id="accountService" class="service.impl.AccountServiceImpl1"></bean>
        - 2、使用普通工厂中的方法创建对象(使用某个类中的方法创建对象，并存入spring容器
            - <bean id="InstanceFactory" class="factory.InstanceFactory"></bean>
            <bean id="accountService" factory-bean="InstanceFactory" factory-method="getAccountService"></bean>
        - 3、使用工厂中的静态方法创建对象(使用某个类中的静态方法创建对象，并存入spring容器)
            - <bean id="accountService" class="factory.StaticFactory" factory-method="getAccountService"></bean>
            
- bean对象的作用范围
    - bean标签的scope属性
        - 作用：就用于指定bean的作用范围
        - 取值：单例和多例较为常用
            - singleton：单例的(默认值)
            - prototype：多例的
            - request：作用于web应用的请求范围
            - session：作用于web应用的会话范围
            - global-session：作用于集群环境的会话范围(全局会话范围)，当不是集群环境时，它就是session
            
- bean对象的生命周期
    - 单例对象
        - 出生：当容器创建时对象出生
        - 活着：只要容器还在，对象一直存活
        - 死亡：容器销毁，对象销毁
        - 总结：单例对象的生命周期和容器相同
    - 多例对象
        - 出生：当使用对象时，spring框架再创建
        - 活着：对象只要是在使用过程中就一直存活
        - 死亡：当对象长时间不使用切没有别的对象引用时，由Java的垃圾回收器进行回收
        
- Spring中的依赖注入
    - 依赖注入
        - Dependency Injection
    - IOC的作用
        - 降低程序间的依赖关系（依赖关系）
    - 依赖关系的管理
        - 交给Spring来维护
        - 在当前类需要使用到的其他类的对象，由Spring提供，只需要修改配置文件中的说明
    - 依赖关系的维护
        - 能注入的数据，分三类
            - 基本类型和String
            - 其他的Bean类型（在配置文件中或者注解配置过的的bean）
            - 复杂类型/其他类型
        - 注入的方式，有三种
            - 使用构造函数
            - 使用set方法提供
            - 使用注解提供
            
    - 构造函数注入：
        - 使用的标签：constructor-arg
        - 标签出现的位置：bean标签的内部
        - 标签中的属性
            - 以下三个用于指定给构造函数中哪个参数赋值，其中name属性是最为常用的
                - type：用于指定要注入的数据的数据类型，该数据类型也是构造函数中的某个或某些参数的类型
                - index：用于指定要注入的数据给构造函数中指定索引位置的参数，索引从0开始
                - name：用于指定给构造函数中指定名称的参数赋值
            -
            - value：用于提供基本类型和Sting类型的数据
            - ref：用于指定其他的bean类型数据，它指的就是在Spring的IOC核心容器中出现过的bean对象
        - 特点
            - 优势
                - 在获取bean对象时，注入数据时必须的操作，否则对象无法创建成功
            - 缺点
                - 改变了bean对象的实例化方式，在创建对象时，如果用不到这些数据，也必须提供
    - set方法注入，更为常用
        - 涉及标签：property
        - 出现的位置：bean标签的内部
        - 标签的属性
            - name：用于指定注入时所调用的set方法名称
            - value：用于提供基本类型和Sting类型的数据
            - ref：用于指定其他的bean类型数据，它指的就是在Spring的IOC核心容器中出现过的bean对象
        - 优势
            - 创建对象时，没有明确的限制，可以直接使用默认构造函数
        - 弊端
            - 如果有某个成员必须有值，则获取对象是有可能没有值
    - 复杂类型/集合注入
        - 用于给List集合注入的标签有：list array set
        - 用于给map结构集合注入的标签有：map props
        - 结构相同，标签可以互换