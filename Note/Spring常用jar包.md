# Spring中常用的Jar包

1.spring-aop-3.2.2.jar 
包含在应用中使用spring的aop特性时所需的类。使用注解必需

2.spring-aspects-3.2.2.jar 
提供对aspectj的支持，以便可以方便的将面向切面的功能集成进ide中，实现springaop必需

3.spring-beans-3.2.2.jar 
springioc（依赖注入）的基础实现，所有应用都要用到的，它包含访问配置文件、创建和管理bean以及进行inversion of control / dependency injection（ioc/di）操作相关的所有类。但是这个是个基础实现，一般我们在实际的开发过程中很少直接用到，它是对起到支撑作用的。

4.spring-context-3.2.2.jar 
为spring核心提供了大量扩展。可以找到使用spring applicationcontext特性时所需的全部类，jdni所需的全部类，ui方面的用来与模板(templating)引擎如 velocity、freemarker、jasperreports集成的类，以及校验validation方面的相关类，还有ejb,cache,format,jms等等。

5.spring-context-support-3.2.2.jar 
spring-context 的扩展支持，用于 mvc 方面

6.spring-core-3.2.2.jar 
spring的核心包，包含spring框架基本的核心工具类，spring其它组件要都要使用到这个包里的类，是其它 
组件的基本核心。包括asm，cglib以及相关的工具类

7.spring-expression-3.2.2.jar 
spring表达式语言。

8.spring-instrument-3.2.2.jar 
spring对服务器的代理接口。

9.spring-instrument-tomcat-3.2.2.jar 
spring对tomcat连接池的集成。

10.spring-jdbc-3.2.2.jar 
spring对jdbc的简单封装

11.spring-jms-3.2.2.jar 
spring对jms(java message service)的封装，为了简化对jms api的使用

12.spring-orm-3.2.2.jar 
包含spring对dao特性集进行了扩展，使其支持 ibatis、jdo、ojb、toplink，因为hibernate已经独立成包了，现在不包含在这个包里了。这个jar文件里大部分的类都要依赖spring-dao.jar里的类，用这个包时你需要同时包含spring-dao.jar包。spring 整合第三方的 orm 映射支持，如 hibernate 、ibatis、jdo 以及spring的jpa的支持。

13.spring-oxm-3.2.2.jar 
spring 对object/xmi 的映射的支持，可以让java与xml之间来回切换。

14.spring-struts-3.2.2.jar 
struts框架支持，可以更方便更容易的集成struts框架。

15.spring-test-3.2.2.jar 
spring对junit框架的简单封装。

16.spring-tx.3.2.2.jar 
spring提供对事务的支持，事务的相关处理以及实现类就在这个jar包中

17.spring-web-3.2.2.jar 
包含web应用开发时，用到spring框架时所需的核心类，包括自动载入webapplicationcontext特性的类、struts与jsf集成类、文件上传的支持类、filter类和大量工具辅助类。

18.spring-webmvc-3.2.2.jar 
spring mvc相关，实现springmvc的操作。

19.spring-webmvc-portlet-3.2.2.jar 
spring mvc的增强扩展。

spring配置文件中中需要导入的约束： 
beans/aop/context/tx