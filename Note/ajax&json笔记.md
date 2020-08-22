# 今日内容
- AJAX：
- JSON： 

## AJAX
- 概念：Asynchronous JavaScript And XML    异步的JavaScript 和 XML
    - 异步和同步：客户端和服务器端通信的基础上
        - 同步：客户端必须等待服务器端的响应，在等待的期间客户端不能做其他的操作
        - 异步：客户端不需要等待服务器端的操作，在服务器处理请求的过程中，客户端可以进行其他的操作
    - ajax是一种无需重新加载整个网页的情况下，能够更新网页部分内容的技术
        - 通过在后台与服务器进行少量的数据交换，ajax可以使网页实现异步更新，这就意味着可以再不重新加载整个网页的情况下，对网页的某部分内容进行更新过
          传统的网页(不使用ajax)如果需要更新内容，必须重新载入整个网页页面
    - 作用：提升用户体验

- 实现方式
    - 原生JS实现方式
         //1.创建核心对象
            var xmlhttp;
            if (window.XMLHttpRequest)
            {// code for IE7+, Firefox, Chrome, Opera, Safari
                xmlhttp=new XMLHttpRequest();
            }
            else
            {// code for IE6, IE5
                xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
            }
    
            //2. 建立连接
            /*
                参数：
                    1. 请求方式：GET、POST
                        * get方式，请求参数在URL后边拼接。send方法为空参
                        * post方式，请求参数在send方法中定义
                    2. 请求的URL：
                    3. 同步或异步请求：true（异步）或 false（同步）
    
             */
            xmlhttp.open("GET","ajaxServlet?username=tom",true);
    
            //3.发送请求
            xmlhttp.send();
    
            //4.接受并处理来自服务器的响应结果
            //获取方式 ：xmlhttp.responseText
            //什么时候获取？当服务器响应成功后再获取
    
            //当xmlhttp对象的就绪状态改变时，触发事件onreadystatechange。
            xmlhttp.onreadystatechange=function()
            {
                //判断readyState就绪状态是否为4，判断status响应状态码是否为200
                if (xmlhttp.readyState==4 && xmlhttp.status==200)
                {
                   //获取服务器的响应结果
                    var responseText = xmlhttp.responseText;
                    alert(responseText);
                }
            }
    - JQuery框架实现方式
        - $.ajax()
            - 语法：$.ajax({键值对});
                $.ajax({
                    url:"ajaxServlet", // 请求的路径
                    type:"POST", // 请求方式
                    // data:"username=mable&age=23", // 请求参数
                    data:{"username":"mable", "age":23},
                    success:function(data){ // 响应成功后的回调函数
                        alert(data)
                    },
                    error:function () {
                        alert("出错啦")
    
                    }, // 表示如果请求响应出现异常，会执行的回调函数
                    dataType:"text" //设置接收到的相应数据的格式
                })
        - $.get()：发送get请求
            - 语法$.get(url, [data], [callback], [type])
                - 参数
                    - url：请求路径
                    - data：请求参数
                    - callback：回调函数
                    - type：响应结果的类型
        - $.post()：发送post请求
        - 语法$.post(url, [data], [callback], [type])
            - 参数
                - url：请求路径
                - data：请求参数
                - callback：回调函数
                - type：响应结果的类型
                
## JSON
- 概念：JavaScript Object Notation     javascript对象表示法
    - Person p = new Person();
    - p.setName("mable");
    - p.setAge(21);
    - p.setGender("女")
    
    var p = {"name":"mable", "age":23, "gender":"女"};
        - json现在多用于存储和交换文本信息的语法
        - 进行数据的传输
    
- 语法
    - 基本规则
        - 数据在名称/值对中：json数据是由键值对构成的
            - 键用引号(单双都行)引起来，也可以不使用引号
            - 值的取值类型
                - 数字（整数或浮点数）
                - 字符串（在双引号中）
                - 逻辑值（true 或 false）
                - 数组（在方括号中）
                - 对象（在花括号中）
                - null
        - 数据由逗号分隔：多个键值对由逗号分割
        - 花括号保存对象：使用{}定义json格式
        - 方括号保存数组：[]
    - 获取数据
        - json对象.键名
        - json对象["键名"]
        - 数组对象[角标]
    
- json数据和Java对象的相互转换
    - 解析器
        - 创建的解析器：JsonLib, Gson, fastJson, jackson
    - json转为JavaScript对象
    - Java对象转为json
        - 使用步骤
            - 导入Jackson的相关jar包
            - 创建Jackson核心对象 ObjectMapper
            - 调用ObjectMapper的相关方法进行转换
                - readValue(json字符串数据, Class)
    - 注解
        - @JsonIgnore：排除属性
        - @JsonFormat：属性值的格式化
    
    - 复杂的java对象转换
        - List：数组
        - Map：对象格式一致
        
## 案例
- 校验用户名是否存在
    - 服务器响应数据，在客户端使用时，要想当做json数据格式使用的
        - $.get(type)：将最后一个参数type指定为"json"
        - 在服务器端设置mime类型
            - response.setContentType("application/json;charset=utf-8")