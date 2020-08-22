# 今日内容
- Web相关概念
- web服务器软件：Tomcat
- Servlet入门学习

## Web相关概念
- 软件架构
    - C/S：客户端/服务器端
    - B/S：浏览器/服务器端
- 资源分类
    - 静态资源：所有用户访问后，得到的结果都是一样的，称为静态资源，静态资源可以直接被浏览器解析
        - 如：HTML，CSS，JavaScript
    - 动态资源：每个用户访问相同资源后，得到的结果可能不一样，称为动态资源，动态资源被访问后，需要先转换为静态资源，再返回给浏览器
        - 如：servlet/jsp,php,asp...
- 网络通信三要素
    - ip地址：电子设备(计算机)在网络中的唯一标示
    - 端口：应用程序在计算机中的唯一标示(0~65536之间)
    - 传输协议：规定了数据传输的规则
        - 基础协议
            - TCP：安全协议，三次握手，速度稍慢
            - UDP：不安全的协议，速度快

## Web服务器软件
- 服务器：安装了服务器软件的计算机
- 服务器软件：接收用户的请求，处理请求，作出响应
- Web服务器软件：接收用户的请求，处理请求，作出响应
    - 在Web服务器软件中，可以部署Web项目，让用户通过浏览器来访问这些项目
    - Web容器
- 常见的Java相关的Web服务器软件
    - webLogic：Oracle公司，大型的JavaEE服务器，支持所有的JavaEE规范，收费
    - webSphere：IBM公司，大型的JavaEE服务器，支持所有的JavaEE规范，收费
    - JBOSS：JBOSS公司，大型的JavaEE服务器，支持所有的JavaEE规范，部分收费
    - Tomcat：Apache基金组织，中小型JavaEE服务器，仅仅支持少量的JavaEE规范
- JavaEE：JAVA语言在企业及开发中使用的技术规范的总和，一共规定了13项大的规范servlet/jsp，开源免费

- Tomcat：Web服务器软件
    - 下载安装：sudo apt-get install tomcat8
    - 卸载：sudo apt-get remove tomcat8
    - 启动：service tomcat8 start
    - 访问：localhost:8080
    - 关闭：service tomcat8 stop
    - 查看状态：service tomcat8 status
    - 配置：
        - 部署项目的方式
            - 直接将项目放入webapps目录中即可
                - /hello：项目的访问路径-->虚拟目录
                - hello.html：资源文件
                - 简化部署
                    - 将项目打成一个war包，将war包放置到webapps目录下，这个war包将会自动解压为一个项目，删除war包后这个项目也将被自动删除
                    - 将当前目录下的所有文件打包为test.war包：jar -cvfM0 test.war ./
                - 配置server.xml文件
                    - 在host标签体中配置
                        <!--项目部署-->
                        <Context docBase="/var/lib/tomcat8/webapps/hello" path="/test" />
                    - path：虚拟目录
                -  在conf/Catalina/localhost下创建任意名称的xml文件，在文件中编写：
                    - <Context docBase="/var/lib/tomcat8/webapps/hello" />
                    - 虚拟目录就是：XML文件的名称
            - 静态项目和动态项目
                - 目录结构
                    - Java动态项目目录结构
                        - 项目的根目录
                            - WEB-INF目录
                                - web.xml：Web核心配置文件
                                - classed目录：放置字节码文件的目录
                                - lib目录：放置依赖jar包
            - 将Tomcat集成到idea中，并且创建JavaEE项目，部署项目
            
              ​          
        
    - 修改端口号
        * conf/server.xml
        * <Connector port="8888" protocol="HTTP/1.1"
           connectionTimeout="20000"
           redirectPort="8445" />
        * 一般会将tomcat的默认端口号修改为80。80端口号是http协议的默认端口号。
            * 好处：在访问时，就不用输入端口号
    - 目录结构
        - bin：可执行文件
        - conf：配置文件
        - lib：依赖jar包
        - logs：日志文件
        - temp：临时文件
        - webapps：存放Web项目
        - work：存放运行时的数据
        
## servlet：server applet
- 概念：运行服务器端的小程序
    - servlet：就是一个接口，定义了Java类被浏览器访问到(tomcat识别)的规则
    - 自定义一个类，实现servlet接口，复写方法
- 快速入门
    - 创建Javaee的项目
    - 定义一个类，实现servlet接口
        - public class ServletDemo01 implements Servlet
    - 实现接口中的抽象方法
    - 配置servlet，在web.xml中
            <!--配置Servletxiugaizhi-->
            <servlet>
                <servlet-name>test1</servlet-name>
                <servlet-class>web.servlet.ServletDemo01</servlet-class>
            </servlet>
            <servlet-mapping>
                <servlet-name>test1</servlet-name>
                <url-pattern>/test1</url-pattern>
            </servlet-mapping>
- 执行原理：
    - 当服务器接收到客户端浏览器的请求后，会解析请求的url路径，获取访问的servlet的资源路径
    - 查找web.xml文件，是否有对应的标签<url-pattern>标签体内容
    - 如果有，则在找到对应的<servlet-class>全类名
    - Tomcat会将字节码文件加载进内存并且创建其对象
    - 调用其方法
- servlet中的生命周期
    - 被创建：执行init方法，只执行一次(只会被创建一次)
        - Servlet什么时候被创建？
            - 在web.xml的<servlet>标签下配置
            - 默认情况下，第一次被访问时，Servlet被创建
            - 可以配置指定Servlet的创建时机
                - 第一次被访问时，创建
                    - <load-on-startup>的值为复数
                - 在服务器启动时创建
                    - <load-on-startup>的值为0或正整数
        - Servlet的init方法，只执行一次，说明一个Servlet在内存中只存在一个对象，Servlet是单例的
            - 多个用户同时访问时，可能存在线程安全问题
            - 解决：尽量不要在Servlet中定义成员变量。即使定义了成员变量，也不要对其修改值
    - 提供服务：执行servlet方法，会执行多次
        - 每次访问Servlect时，Service方法都会被调用一次
    - 销毁：执行destroy，只执行一次
        - servket被销毁时执行。服务器被关闭时，Servlet被销毁
        - 只有服务器正常关闭时，才会执行destroy
        - destroy方法在Servlet被销毁之前执行，一般用于释放资源
- Servlet3.0
    - 好处
        - 支持注解配置，可以不需要web.xml
    - 步骤
        - 创建JavaEE项目，选择Servlet的版本3.0或以上，可以不创建web.xml
        - 定义一个类，实现Servlet接口
        - 复写方法
        - 在类上使用@webServlet注解，进行配置
            - @WebServlet("/资源路径")



## idea与Tomcat的相关配置

- idea会为每一个Tomcat部署的项目单独建立一份配置文件
  - 查看控制台的log输出：Using CATALINA_BASE:
- 工作空间项目和Tomcat部署的Web项目
  - Tomcat正真访问的是“Tomcat部署的Web项目”，这个项目对应着“工作空间项目”的Web目录下的所有资源
  - WEB-INF目录下的资源不能被浏览器直接访问
  - 断点调试：Debug方式启动