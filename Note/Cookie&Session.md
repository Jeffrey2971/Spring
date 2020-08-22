# 今日内容
- 回话技术
    - Cookie
    - Session
- JSP：


## 会话技术
- 会话：一次会话中包含多次请求和响应
    - 一次会话：浏览器第一次给服务器资源发送请求，会话建立，直到有一方断开为止
- 功能：在一次会话的范围内的多次请求间，共享数据
- 方式
    - 客户端会话技术：cookie
    - 服务器端会话技术：session
    
## cookie
- 概念：客户端会话技术，将数据保存到客户端

- 快速入门
    - 使用步骤：
        - 创建cookie对象，绑定数据
            - new Cookie(String name, String value)
        - 发送cookie对象
            - response.addCookie(Cookie cookie)
        - 获取cookie，拿到数据
            - cookie[] request.getCookies()
     
- 实现原理
    - 基于响应头set-cookie和请求头cookie实现
    
- cookie的作用和特点
    - cookie存储数据在客户端浏览器
    - 浏览器对于单个cookie的大小有限制(4kb)以及对同一个域名下的总cookie数量也有限制(20个)
    - 作用
        - cookie一般用于存储少量不太敏感的数据
        - 在不登录的情况下，完成服务器对客户端的身份识别
    
- cookie细节
    - 一次可以发送多个cookie，可创建多个cookie对象，使用response调用多次addCookie发送cookie即可
    - 默认情况下，当浏览器关闭，Cookie数据被销毁
        - 设置cookie的生命周期，持久化存储
            - setMaxAge(int seconds)
                - 正数：将cookie数据化写入到硬盘文件中，持久化存储。cookie存活时间
                - 复数：默认值
                - 零：删除cookie信息
    - 在Tomcat8之前，cookie中不能直接存储中文数据
        - 需要将中文数据转码，一般采用url编码(%E3)
    - 在Tomcat8之后，cookie支持中文数据，但仍然不支持特殊字符，应使用URL编码存储，URL解码
    - 假设在一个Tomcat服务器中，部署了多个web项目，那么在这些web项目中默认情况下cookie不能共享
    - setPath(String path)：设置cookie的获取范围，默认情况下，设置当前的虚拟目录
        - 如果要共享，则可以将path设置为"/"
    - 不同的Tomcat服务器间cookie共享问题
        - setDomain(String path)：如果设置一级域名相同，那么多个服务器之间的cookie可以共享
            - setDomain(".baidu.com")，那么tieba.baidu.com和new.baidu.com中cookie可以共享

- 案例：记住上一次的访问时间
    - 案例需求：
      	1. 访问一个Servlet，如果是第一次访问，则提示：您好，欢迎您首次访问。
      	2. 如果不是第一次访问，则提示：欢迎回来，您上次访问时间为:显示时间字符串
    - 分析
        - 可以采用cookie来完成
        - 在服务器中的Servlet中判断是否有一个名为lastTime的cookie
            - 有：不是第一次访问
                - 相应数据：欢迎回来，您上次的访问时间为：时间
                - 写回cookie：更新时间k
            - 没有：是第一次访问
                - 相应数据：您好，欢迎您首次访问。
                - 写回cookie:lastTime = 时间

## JSP
- 概念
    - Java Server pages：Java服务器端页面
        - 可理解为特殊的页面，其中既可以直接定义HTML标签，又可以定义Java代码
        - 用于简化书写

- 原理
    - JSP本质上就是一个servlet

- JSP脚本：JSP定义Java代码的方式
    - <% Java代码 %>：定义的Java代码，在service方法中。service可以定义，该脚本中就可以定义什么
    - <%! Java代码 %>：定义的Java代码，在JSP转换后的Java类的成员位置中。
    - <%= Java代码 %>：定义的Java代码，会输出到页面上，输出语句中可以定义什么，该脚本中就可以定义什么

- JSP的内置对象
    - 在JSP页面中不需要获取和创建，可以直接使用的对象
    - JSP中一共有9个内置对象
    - request
    - response
    - out：字符输出流对象，可以将数据输出到页面上，和response.getWriter()类似
        - response.getWriter()和out.writer()的区别
            - 在Tomcat服务器正真给客户端做出响应之间，会先找response缓冲区数据，再找out缓冲区的数据
            - 尽量不要使用response.getWriter()在页面上，避免打乱页面布局