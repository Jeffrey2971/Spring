# SpringDay02：Spring基于注解的IOC以及IOC的案例

## Spring中的IOC的常用注解
- 账户的业务层实现类
    - xml的配置
        <bean id="accountService" class="service.impl.AccountServiceImpl" scope="" init-method="" destroy-method="">
            <property name="" value="" | ref=""></property>
        </bean>
 
    - 用于创建对象的   
        - 概述：他们的作用就和在XML配置文件中便携一个<bean></bean>实现的功能是一样的
        - 语法：@Component
            - 作用：用于把当前类对象存入Spring容器中，需在官网复制相对应的标签
            - 属性
                - value：用于指定bean的ID值，当不写时，他的默认值时当前类名，且首字母改小写
                    <!--告知spring在创建容器时要扫描的包，配置所需要的标签不是在beans的约束中，而是一个名称为
                    context名称空间和约束中-->
                    <context:component-scan base-package="jeffrey"></context:component-scan>
                    </beans>
            - 以下三个注解他们的作用与Component是一致的，这三个是Spring框架提供明确三层使用的注解，使三层对象更急清晰
            - @Controller：一般用于表现层
            - @Service：一般用于业务层
            - @Repository：一般用于持久层
                
    - 用于注入数据的注解
        - 语法：@Autowired
        - 概述：他的作用就和在XML配置文件中bean标签中写一个<property></property>标签的作用是一样的
            - 作用：自动按照类型注入，只要容器中有唯一的一个bean对象类型和要注入的变量类型，就可以注入成功，如果IOC容器中没有任何bean的类型和要
            注入的变量类型匹配，则会报错。如果IOC容器中有多个类型匹配时，
            - 出现位置：可以是变量上，也可以是方法上
            - 细节：在使用注解注入时，set方法就不是必须的
        - 语法：Qualifier
            - 作用：在按照类型注入的基础之上再按照名称注入，它在给类成员注入时不能单独使用，在给方法参数注入时可以
            - 属性
                - value：用于指定注入bean的ID
            - 细节：需和@Autowired配合使用
        - 语法：@Resource
            - 作用：直接按照bean的ID注入，可独立使用
            - 属性
                - name：用于指定bean的ID
        - 以上三个注解只能注入其他bean类型的数据，而基本类型和String类型无法使用上述注解实现，另外，集合类型的注入只能通过XML实现
        
        - 语法：@Value
            - 作用：用于注入基本类型和String类型的数据
            - 属性
                - value：用于指定数据的值，它可以使用Spring中额spEL(Spring的EL表达式)
                    - SPEL的写法：${表达式}  
    - 用于改变作用范围的注解
        - 概述：他们的作用就和bean标签中使用scope属性实现功能是一样的
        - Scope
            - 作用：用于指定bean的作用范围
            - 属性
                - value：指定范围的取值，常用取值
                    - singleton
                    - prototype
    - 和生命周期相关
        - 概述：他们的作用就和bean标签中使用init-method和destroy-method的作用是一样的
        - 语法
            - @PreDestroy
                - 作用：用于指定销毁方法
            - @PostConstruct
                - 作用：用于指定初始化方法
        
## 案例使用XML方式和注解方式实现表单的CRUD操作
- 持久层技术选择：dbutils
- bean.xml
    ```text
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans
            http://www.springframework.org/schema/beans/spring-beans.xsd">
        <!--配置Service对象-->
        <bean id="accountService" class="jeffrey.service.impl.AccountImpl">
            <!--注入Dao对象-->
            <property name="accountDao" ref="accountDao"></property>
        </bean>
    
        <!--配置Dao对象-->
        <bean id="accountDao" class="jeffrey.dao.impl.AccountDaoImpl">
            <!--注入QueryRunner-->
            <property name="runner" ref="runner"></property>
        </bean>
    
        <!--配置QueryRunner-->
        <bean id="runner" class="org.apache.commons.dbutils.QueryRunner" scope="prototype">
            <!--注入数据源-->
            <constructor-arg name="ds" ref="dataSource"></constructor-arg>
        </bean>
        <!--配置数据源-->
        <bean id="dataSource" class="com.mchange.v2.c3p0.ComboPooledDataSource">
            <!--连接数据库的必备信息-->
            <property name="driverClass" value="com.mysql.jdbc.Driver"></property>
            <property name="jdbcUrl" value="jdbc:mysql:///eesy?characterEncoding=utf-8"></property>
            <property name="user" value="root"></property>
            <property name="password" value="Aa664490254"></property>
        </bean>
    </beans>
```
```

## 改造基于注解的IOC案例，使用纯注解的实现方式
- Spring的一些新注解使用
    - 概述：用一个配置类代替xml配置文件
        - 语法：@Configuration
             - 作用：指定当前类是一个配置类
             - 细节：当配置类作为AnnotationConfigApplicationContext对象创建的参数时，该注解可以省略
        - 语法：@ComponentScan
             - 作用：用于通过注解指定Spring在创建容器时要扫描的包
             - 属性
                 - value：它和basePackages的作用是一样的，都是用于指定创建容器时要扫描的包，使用此注解就等同于在xml中配置了
                 ```<context:component-scan base-package="jeffrey"></context:component-scan>```
        - 语法：@Bean
             - 作用：用于把当前方法的返回值作为bean对象存入Spring的IOC容器中
             - 属性
                 - name：用于指定bean的ID，当不写时，默认值为当前方法的名称
             - 细节：当使用注解配置方法时，如果方法有参数，Spring框架就会去容重中查找有没有可用的Bean对象
             - 查找的方式和Autowired注解的作用时一样的
        - 语法：@Import
            - 作用：用于倒入其他的配置类
            - 属性
                - value：用于指定其他配置类的字节码，当使用@Import注解之后，有该注解的类就是副配置类，而导入的都是子配置类
        - 语法：@PropertySource
            - 作用：用于指定Properties文件的位置
            - 属性
                - value：指定文件名称和指定的路径
                - 关键字：classpath，表示该类路径下



## Spring和Junit整合
- 问题
    - 应用程序的入口
        - main方法
    - junit单元测试中，没有main方法也能执行
        - junit继承了一个main方法，该方法会判断当前测试类中，那些方法有@Test注解，如果有junit就会让有Test注解的方法执行
    - junit不会关注是否采用了Spring框架
        - 在测试方法时，junit不知是否使用Spring框架，所以不会自动读取配置文件/配置类创建Spring核心容器
    - 结论：由以上三点可知
        - 当测试方法执行时，没有IOC容器，就算写了AutoWired注解，也无法实现注入
- Spring整合junit的配置
    - 导入Spring整合junit的jar包坐标：spring-test
    - 使用junit提供的一个注解把原有的main方法替换成spring提供的
        - 语法
            - @RunWith
    - 告知Spring的运行器，Spring和ioc创建是基于xml还是注解的，并且说明文职
        - 语法
            - ContextConfiguration
                - Locations：指定xml文件的指定位置，加上classpath关键字，表示在类路径下
                - Classes：指定注解类所在的位置
    - 当使用Spring 5.x版本的时候，要求junit的jar包必须是4.12以上
    
