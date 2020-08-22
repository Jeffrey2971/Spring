# 今日内容
- JSP
    - 指令
    
    - 注释
    
    - 内置对象
    
- MVC开发模式

- EL表达式

- JSTL标签

- 三层架构

## JSP
- 指令
    - 作用：用于配置JSP页面，导入资源文件
    - 格式
        <%@ 指令名称 属性名1=属性值1 属性名2=属性值2 ... %>
    
    - 分类
        - page      ：配置JSP页面
            - contentType：等同于response.setContentType()
                - 设置响应体的mime类型以及字符集
                - 设置当前JSP页面的编码(只能是高级的ide才能生效，如果使用低级工具，则需要设置pageEncoding属性设置当前页面的字符集)
            - import：导包
            - errorPage：当前页面发生异常后，会自动跳转到指定的错误页面
            - isErrorPage：表示当前页面是否为错误页面
                - true：可以使用内置对象Exception
                - false：默认值，不能使用内置对象Exception
        - include   ：页面包含的，导入页面的资源文件
            - <%include file="top" %>
        - taglib    ：导入资源
            - <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
                - prefix：前缀，可自定义
    
- 注释
    - HTML注释
        - <!-- -->：只能注释HTML代码片段
    - JSP特有注释(推荐使用)
        - <%-- --%>：可以注释所有
        - 服务器不会发送被JSP注释的内容
        
- 内置对象
    - 在JSP页面中不需要创建，可直接使用的对象
    - 一共有9个
            变量名                 类型                          作用
        - pageContext           PageContext                 当前页面共享数据，还可获取其他八个内置对象
        - request               HttpServletRequest          一次请求访问的多个资源(转发)
        - session               HttpSession                 一次会话的多次请求间
        - application           ServletContext              所有用户间共享数据
        
        - response              HttpServletResponse         响应对象
        - page                  Object                      当前页面(Servlet)的对象 this
        - out                   JspWriter                   输出对象，数据输出到页面上
        
        - config                ServletConfig               Servlet的配置对象
        - exception             Throwable                   异常对象

## MVC开发模式
- JSP演变历史
    - 早期并没有JSP，只有servlet，只能使用response输出标签数据，较为麻烦
    - 后来有了JSP，简化了Servlet的开发，但如果过度使用JSP，在JSP中既写大量的Java代码，又写了HTML标签，这将会造成难于维护，难于分工协作
    - 再后来，Java的Web开发，借鉴了MVC的开发模式，使得程序的设计更加合理性

- MVC
    - M：Model，模型，JavaBean
        - 完成具体的业务操作，如查询数据，封装对象等
    - V：View，视图，JSP
        - 展示数据
    - C：Controller：控制器，servlet
        - 获取用户输入
        - 调用模型
        - 将数据交给视图进行展示
    - 优缺点
        - 耦合性低，方便维护，利与分工协作
        - 重用姓高
    - 缺点
        - 使得项目架构变得复杂，对开发人员高

## EL表达式
- 概念：expression Language 表达式语言
- 作用：替换和简化JSP页面中Java代码的编写
- 语法：${表达式}
- 注意：
    - JSP默认是支持el表达式，如果需要忽略el表达式
        - 设置JSP中page指令中属性为isELIgnored="true"，是否忽略当前页面所有的el表达式，默认为false
        - \${表达式}：表示当前这个表达式被忽略
- 使用
    - 运算
        - 运算符
            - 算术运算符：+ - * /(div) %(mod)
            - 比较运算符：> < >= <= == !=
            - 逻辑运算符：&&(and) ||(or) !(not)
            - 空运算符：empty
                - 功能：用于判断字符串、集合、数组长度是否为0或者是否为null
                - ${empty list}：判断字符串、集合、数组对象是否为null或者长度为0
                - ${not empty list}：判断字符串、集合、数组对象是否不为null或者长度不为0
                    
    - 获取值
        - el表达式只能从域对象中获取值
        - 语法：
            - ${域名城.键名称}：从指定的域中获取指定键的值
                - 域名城
                    - pageScope         --> pageContext
                    - requestScope      --> request
                    - sessionScope      --> session
                    - applicationScope  --> application(ServletContext) 
                - 举个栗子：在request域中存储了name=mable
                - 获取：${requestScope.name}
            - ${键名}：表示依次从最小的域中查找，是否有该键对应的值，直到找到为止，建议使用在四个域中键没有重名的情况下
                - ${name} --> pageScope --> requestScope --> sessionScope(找到了，返回结果，不会向下继续找) --> applicationScope
            
            - 获取对象、List集合，Map集合的值 
                - 对象
                    - 对象：${域名城.键名.属性名}
                        - 本质上会去调用对象的getter方法
                        
                    - List集合：${域名城.键名[索引]}
                    
                    - Map集合
                        - ${域名城.键名.key名称}
                        - ${域名城.键名["key名称"]}
        - 隐式对象
            - el表达式中有11个隐式对象
            - pageContext
                - 获取JSP其他八个内置对象
                     - ${pageContext.request.contextPath}：动态获取虚拟目录
            
## JSTL
- 概念：JavaServer Pages Tag Library   JSP标准标签库
    - 是由Apache组织提供的开源免费的JSP标签   <标签>
- 作用：用于简化和替换JSP页面上的Java代码
- 使用步骤
    - 导入JSTL相关的jar包
    - 标签标签库：taglib指令： <%@ taglib %>
    - 使用标签
- 常用的JSTL标签库
    - if：相当于Java的if语句
        - c:if标签
            - 属性：test为必须属性，接收boolean表达式，如果表达式为true则显示if标签体内容，如果为false则不显示if标签体内容
            - 一般情况下，test属性值会结合el表达式一起使用
        - 注意事项
            - c:if标签没有else的情况，如需else则可以再定义一个c:if标签
    - choose：相当于Java的switch语句
        - 使用choose标签取出数字，相当于switch声明
        - 使用when标签做数字的判断，相当于case
        - otherwise标签做其他情况的声明，相当于default
    - foreach：相当于Java代码的for语句
        - 完成重复性的操作
            for(int i = 0; i < 10; i++){
                ...
            }
        - 属性
            - begin：开始值
            - end：结束值
            - var：临时变量
            - step：步长
            - varstatus：循环状态对象
                - index：容器中元素的索引，从0开始
                - count：循环的次数，从1开始
        - 遍历容器
            List<User> list;
            for(User user : list){
                ...
            }
        - 属性
            - items：容器对象
            - var：容器中元素的临时变量
            - varStatus：循环状态对象
                - index：容器中元素的索引，从0开始
                - count：循环的次数，从1开始
                
    - 练习
        - 需求：在request域中有一个存有User对象的List集合，需要使用JSTL+EL将List集合的数据展示到JSP页面的表格table中

## 三层架构：软件设计架构
- 界面层(表示层)：用户看到的界面，用户可以通过界面上组件和服务器进行交互
- 业务逻辑层：处理业务逻辑(一些功能)
- 数据访问层：操作数据库存储文件


## 案例：用户信息查询列表展示
- 需求：用户信息的增删改查操作
- 设计
    - 技术选型：Servlet+JSP+MYSQL+JDBCTemplate+Duird+BeanUtils+tomcat8
    - 数据库设计
        - create database selectUser; -- 创建数据库
        - use selectUser; -- 使用数据库
            create table user( -- 创建表
                id int primary key auto_increment,
                name varchar(32) not null,
                gender varchar(5),
                age int,
                address varchar(32),
                qq varchar(16),
                email varchar(32)
                
            )
    - 开发
        - 环境搭建
            - 创建数据库环境
            - 创建项目，导入需要的jar包
        - 敲代码
    - 测试
    - 部署运维