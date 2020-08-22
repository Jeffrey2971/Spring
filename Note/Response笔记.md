# 今日内容
- HTTP协议：响应消息

- response对象

- ServletContext对象

## HTTP协议
- 请求消息：客户端发送给服务器端的数据
    - 数据格式
        - 请求行
        - 请求头
        - 请求空行
        - 请求体
- 响应消息：服务器端发送给客户端的数据
    - 数据格式
        - 响应行
            - 组成：协议/版本 HTTP/1.1 200 响应状态码 状态码描述
            - 响应的状态码：服务器告诉浏览器本次请求和响应的状态
                - 状态码都是三位数字
                     分类
                        - 1xx：服务器接收客户端消息，但没有接收完成，等待一段时间后发送1xx状态码
                        - 2xx：成功200
                        - 3xx：重定向，代表：302(重定向)，304(访问缓存)
                        - 4xx：客户端错误
                            - 404：请求路径没有对应的资源
                            - 405：请求方式没有对应的doXXX方法
                        - 5xx：服务器端错误，代表500(服务器内部出现异常)
        - 响应头
            - 格式：头名称:值
            - 常见的响应头
                - Content-Type：告诉客户端浏览器本次的响应体数据格式以及编码格式
                - Content-disposition：服务器告诉客户端以什么格式打开响应体数据
                    - 值
                        - in-line：默认值，在当前页面内打开
                        - attachment;filename=xxx：以附件形式打开响应体，文件下载
        - 响应空行
        - 响应体：传输的数据
    - 响应字符串格式
        HTTP/1.1 200 
        Set-Cookie: JSESSIONID=549E6DDE2390C71CB41377962379AD6D; Path=/; HttpOnly
        Content-Type: text/html;charset=UTF-8
        Content-Length: 101
        Date: Wed, 20 May 2020 13:19:08 GMT
        
        <html>
          <head>
            <title>$Title$</title>
          </head>
          <body>
          Hello，response
          </body>
        </html>
        
## Response对象
- 功能：设置响应消息
    - 设置响应行
        - 格式：HTTP/1.1 200 ok
        - 设置状态码：setStatus(int sc)
    - 设置响应头：setHeader(String name, String value)
    - 设置响应体
        - 使用步骤
            - 获取输出流
                - 字符输出流：PrintWriter getWriter()
                - 字节输出流：ServletOutputStream getOutputStream()
            - 使用输出流，将数据输出到客户端浏览器中

- 案例
    - 完成重定向
        // 访问/servletDemo1自动跳转到servletDemo2
            // 1、设置状态码为302
        response.setStatus(302);
            // 2、设置响应头location
        response.setHeader("location", "/response/responseDemo2");
        // 简化重定向方法
        response.sendRedirect("/response/responseDemo2");
        - 重定向的特点
            - 转发地址发生变化
            - 重定向可以访问其他站点的资源
            - 重定向是两次请求，不能使用request对象共享数据
        - 转发的特点
            - 转发地址栏路径不变
            - 转发只能访问当前服务器下的资源
            - 转发是一次请求，可以使用request对象共享数据
        - 路径的写法
            - 路径的分类
                - 相对路径：通过相对路径不可以确定唯一资源
                    - 如：./index.html
                    - 不以/开头，以.开头路径
                    - 规则：找到访问的当前资源和目标资源之间的相对位置关系
                        - ./当前目录
                        - ../后退一级目录
                - 绝对路径：通过绝对路径可以确定唯一资源
                    - 如：http://localhost:8080/response/responseDemo2
                    - 以/开头的路径
                    
                    - 规则：判断定义的路径是给谁用的，判断路径对应的将来从哪发出
                        - 给客户端浏览器：需要加虚拟目录(项目的访问路径)
                            - 虚拟目录应该动态获取：request.getContextPath()
                            - <a>, <form>重定向...
                        - 给服务器使用：不需要加虚拟目录
                            - 转发路径              
    - 服务器输出字符数据到浏览器
        - 步骤
            - 获取字符输出流
            - 输出数据
        - 注意
            - 乱码问题
                - 客户端浏览器和服务器的编码不一致所导致
                - PrintWriter pw = response.getWriter(); 获取的编码是ISO-8859-1
                - 高速浏览器响应体使用的编码，简单的形式，设置编码是在获取流之间
                    - response.setContentType("text/html;charset=utf-8");
    - 服务器输出字节数据到浏览器
        - 步骤
            - 获取字节输出流
            - 输出数据
    - 验证码
        - 本质：图片
        - 目的：防止表单被恶意注册
        
## ServletContext对象
- 概念：代表整个Web应用，可以和Servlet程序的容器(服务器)进行通信
- 获取
    - 通过request对象获取
        request.getServletContext();
    - 通过httpServlet获取
        this.getServletContext();
- 功能
     - 获取MIME类型
        - MIME类型：在互联网通信过程中定义的一种文件数据类型
            - 格式：大类型/小类型  text/html     image/jpeg
        - 获取String getMimeType(String file)  
     - 域对象：共享数据
        - ServletContext对象范围：所用用户的请求数据
     - 获取文件的真实(服务器)路径
        - 方法：String getRealPath(String path)
            -  servletContext
            
## 案例
- 文件下载需求：
	- 1. 页面显示超链接
	- 2. 点击超链接后弹出下载提示框
	- 3. 完成图片文件下载

- 分析
    - 超链接指定的资源如果能够被浏览器所解析，则在浏览器中直接展示，如果不能解释，则弹出下载提示框，不满足需求
    - 任何资源都需要弹出下载提示框
    - 使用响应头设置资源的打开方式
        - content-disposition:attachment;filename=xxx
    
    - 步骤
        - 定义页面，编辑超链接的href属性，指向一个servlet类，传递资源的名称filename 
        - 定义Servlet
            - 获取文件的名称
            - 使用字节输入流加载文件进内存
            - 指定response的header响应头：content-disposition:attachment;filename=xxx
            - 将数据写出到response的输出流
    - 问题
        - 中文文件问题
            - 解决思路
                - 获取客户端使用的浏览器版本信息
                - 根据不同的版本信息，响应不同的数据，设置filename的编码方式