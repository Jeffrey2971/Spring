# 今日内容：
- Servlet
- HTTP协议
- Request




## Servlet：
- 概念
- 步骤
- 执行原理
- 生命周期
- Servlet3.0 注解配置
- Servlet的体系结构	
    Servlet -- 接口
        |
    GenericServlet -- 抽象类
        |
    HttpServlet  -- 抽象类

    - GenericServlet：将Servlet接口中其他的方法做了默认空实现，只将service()方法作为抽象
        - 将来定义Servlet类时，可以继承GenericServlet，实现service()方法即可

    - HttpServlet：对http协议的一种封装，简化操作
        - 定义类继承HttpServlet
        - 复写doGet/doPost方法
	
	- Servlet相关配置
		- urlpattern:Servlet访问路径
			- 一个Servlet可以定义多个访问路径 ： @WebServlet({"/d4","/dd4","/ddd4"})
			- 路径定义规则：
				- /xxx：路径匹配
				- /xxx/xxx:多层路径，目录结构
				- *.do：扩展名匹配

## HTTP：
- 概念：Hyper Text Transfer Protocol 超文本传输协议
    - 传输协议：定义了，客户端和服务器端通信时，发送数据的格式
    - 特点：
        - 基于TCP/IP的高级协议
        - 默认端口号:80
        - 基于请求/响应模型的:一次请求对应一次响应
        - 无状态的：每次请求之间相互独立，不能交互数据
- 历史版本：
        - 1.0：每一次请求都会建立新的连接
        - 1.1：复用连接
    - 请求消息数据格式
        - 请求行
            - 请求方式 请求url 请求协议/版本
            - 请求方式
                - http协议有七中请求方式，常用的有两种
                    - GET
                        - 请求参数在请求行中，在url后
                        - 请求的url长度是有限制
                        - 不太安全
                    - POST
                        - 请求参数在请求体中
                        - 请求的url长度没有限制
                        - 较为安全
        - 请求头：客户端浏览器告诉服务器一些信息
            - 请求头名称：请求头值
            - 常见的请求头
                - User-Agent：浏览器告诉服务器，当前使用浏览器的版本信息
                    - 可以再服务器端获取该头的信息，解决浏览器的兼容性问题
                - Accept：告诉服务器，当前浏览器可以解析什么类型的文件
                - Accept-Language：告诉服务器，当前浏览器可以解析什么语言
                - Accept-Encoding：告诉浏览器，当前浏览器支持的压缩格式
                - Referer：告诉服务器，当前请求从哪里来
                    - 作用
                        - 防盗链
                        - 统计工作
                - Connection：连接状态
        - 请求空行
            - 空行，用于分割post请求的请求头和请求体
        - 请求体(正文)
            - GET请求没有
            - 封装post请求消息的请求参数
    - 响应消息数据格式



## Request

- request和response对象的原理
  - request和response对象是由服务器创建的，可以直接使用
  - request对象是用于获取请求信息的而reponse对象是用于设置相应消息
- request继承体系结构
  - servletRequest       --- 接口
    - |		继承
  - HttpServletRequest   --- 接口
    - |		继承
  - org.apache.catalina.connector.RequestFacade@3753b7b7 类 (tomcat)
- request功能：获取请求消息
    - 获取请求消息数据
        - 获取请求行数据
            - GET /test4/demo1?=mable HTTP/1.1
            - 方法
                - 获取请求方式：GET
                    - String GetMethod()
                - (*) 获取虚拟目录：/test4
                    - String GetContextPath()
                - 获取Servlet路径：/demo1
                    - String ServletPath()
                - 获取get方式的请求参数：name=mable
                    - String getQueryString()
                - (*) 获取请求uri：/test4/demo1
                    - String getRequestURI()
                    - StringBuffer getRequestURL()：http://localhost/test4/demo1
                    - URL：统一资源定位符       中华人民共和国
                    - URI：统一资源标识符       共和国
                - 获取协议及版本：HTTP/1.1
                    - String getProtocol()
                - 获取客户机的ip地址
                    - String getRemoteAddr()
        - 获取请求头数据
            - 方法
                - String getHeader(String name)：通过请求头的名称获取请求头的值
                - Enumeration<String> getHeaderNames()：获取所有的请求头名称
                
        - 获取请求体数据
            - 请求体：只有POST请求方式，才有请求体，在请求体中封装了POST请求的请求参数
            - 步骤
                - 获取流对象
                    - BufferedReader getReader()：获取字符输入流，只能操作字符数据
                    - ServletInputStream getInputStream()：获取字节输入流，可以操作所有类型的数据
                        - 文件上传
                - 再从流对象中拿取数据
                
    - 其他功能
        - 获取请求参数通用方式：不论GET还是post请求方式都可以使用以下方法获取请求参数
            - String getParameter(String name)：根据参数名称获取参数值      username=jeffrey&password=123
            - String[] getParameterValues(String name)：根据参数名称获取参数值的数组       hobby=xx&hobby=game
            - Enumeration<String> getParameterNames()：获取所有请求的参数名称
            - Map<String, String[]> getParameterMap()：获取所有参数的Map集合
            - 中文乱码问题
                - get方式：Tomcat8已将get方法乱码问题解决了
                - post方式：会发生乱码
                    - 解决方式
                        - 在获取参数前，设置request编码：request.setCharacterEncoding("utf-8");
        - 请求转发：一种在服务器内部资源的跳转方式
            - 步骤
                - 通过request对象获取请求转发器对象：RequestDispatcher getRequestDispatcher(String path)
                - 使用RequestDispatcher对象进转发：forward(ServletRequest request, ServletResponse response)
            - 特点
                - 浏览器地址栏路径不发生变化
                - 只能转发到当前的服务器内部资源中
                - 转发是一次请求
        - 共享数据
            - 域对象：一个有作用范围的对象，可以在范围内共享数据
            - request域：代表一次请求的范围，一般用于请求转发的多个资源中共享数据
            - 方法
                - setAttribute(String name, Object obj)：存储数据
                - Object getAttribute(String name)：通过键获取值
                - void removeAttribute(String name)：通过键移除键值对
        - 获取ServletContext对象
            - ServletContext getServletContext()
            
## 用户登录案例
- 用户登录案例需求：
    1.编写login.html登录页面
        username & password 两个输入框
    2.使用Druid数据库连接池技术,操作mysql，day14数据库中user表
    3.使用JdbcTemplate技术封装JDBC
    4.登录成功跳转到SuccessServlet展示：登录成功！用户名,欢迎您
    5.登录失败跳转到FailServlet展示：登录失败，用户名或密码错误

- 分析
- 开发步骤
    - 创建项目，导入HTML页面，配置文件，导入jar包
    - 创建数据库环境
        create table user(
            id INT primary key AUTO_INCREMENT,
            username varchar(32) unique not null,
            password varchar(32) not null
        )
    - 创建包domain，创建user类
        package domain;
        
        /*
            用户的实体类
         */
        
        public class User {
            private int id;
            private String username;
            private String password;
        
            public int getId() {
                return id;
            }
        
            public void setId(int id) {
                this.id = id;
            }
        
            public String getUsername() {
                return username;
            }
        
            public void setUsername(String username) {
                this.username = username;
            }
        
            public String getPassword() {
                return password;
            }
        
            public void setPassword(String password) {
                this.password = password;
            }
        
            @Override
            public String toString() {
                return "User{" +
                        "id=" + id +
                        ", username='" + username + '\'' +
                        ", password='" + password + '\'' +
                        '}';
            }
        }
    - 创建包dao，创建类UserDao，提供login方法
        package dao;
        
        /*
            操作数据库中User表类
         */
        
        import domain.User;
        import org.springframework.dao.DataAccessException;
        import org.springframework.jdbc.core.BeanPropertyRowMapper;
        import org.springframework.jdbc.core.JdbcTemplate;
        import util.JDBCUtils;
        
        public class UserDao {
        
            // 声明JDBCTemplate对象共用
            private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
            /**
             * 登录方法
             * @param loginUser 用户名和密码
             * @return user包含用户全部数据，没有查询到返回null
             */
        
            public User login(User loginUser){
                try {
                    // 编写SQL
                    String sql = "select * from user where username = ? and password = ?";
                    // 调用query方法
                    User user = template.queryForObject(sql,
                            new BeanPropertyRowMapper<User>(User.class),
                            loginUser.getUsername(), loginUser.getPassword());
                    return user;
                } catch (DataAccessException e) {
                    e.printStackTrace(); // 记录日志
                    return null;
                }
            }
        
        }
    - 编写web.servlet.LoginServlet类
    
    - login.html form表单中的action路径的写法
        - 虚拟目录 +　Servlet的资源路径
    
    - BeanUtils工具类，简化完成数据封装
        - 用于封装JavaBean
        - JavaBean：标准的Java类
            - 定义要求
                - 类必须被public修饰
                - 必须提供空参的构造器
                - 成员变量必须使用private修饰
                - 提供公共的setter和getter方法
        
            - 功能：封装数据
        - 概念
            - 成员变量：被private
            - 属性：setter和getter方法截取后的产物
                - 例如：getUsername() --> Username --> username
        - 方法
            - setProperty()
            - getProperty()
            - populate(OObject obj, Map map)：将Map集合的键值对信息封装到对应的JavaBean对象中        