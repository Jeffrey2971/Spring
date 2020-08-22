# 黑马旅游网项目

## 技术选型
- Web层
    - Servlet：前端控制器
    - HTML：视图
        - 不使用jsp，基于项目展示，层需考虑用户访问速度，一般后台的管理系统才使用(OA/办公/财务，给内部人员使用)JSP作为展示层
    - Filter：过滤器
    - BeanUtils：完成数据的封装
    - Jackson：json序列化工具
- Service层
    - JavaMail：Java发送邮件工具
    - Redis：nosql内存数据库
    - Jedis：Java的Redis客户端
- Dao层
    - MySQL：数据库
    - Druid：使用阿里巴巴提供的Druid数据库连接池优化连接
    - JdbcTemplate：jdbc工具
    
## 数据库搭建

- 创建数据库环境
    - 创建数据库
        - create database travel;
    - 使用数据库
        - use travel;
    - 创建表及导入数据
        - ...


## 注册
- 前台效果
    - 表单校验
        - 用户名：单词字符，8到20位
        - 密码：单词字符，8到20位
        - 邮件：把判断扔给email属性处理，只判断非空
        - 姓名：非空
        - 手机号：手机号格式
        - 出生日期：非空
        - 验证码：非空
    - 异步(ajax)提交表单
        - 在此使用异步提交表单是为了获取服务器响应的数据，因为前台使用的是HTML作为视图层，不能够直接从servlet相关的域对象获取值，只能通过ajax方式获取相应数据
- 后台代码实现
    - 编写RegistUserServlet
    - 编写UserService以及UserServiceImpl
    - 编写UserDao以及UserDao实现类

## 邮件激活
- 为了保证用户填写的邮箱是正确的，可以推广一些宣传信息到用户邮箱中
- 发送邮件
    - 使用工具类
- 用户点击邮件
    - 用户激活其实就是修改用户表中的status为Y

- 用户点击邮件并激活
    - 代码实现
- 激活代码实现
    - ActiveUserService
    - UserService: active
    - UserDao
        - findByCode
        - updateStatus


## 登录
- 代码实现
    - 前台
        - 
    - 后台
        - LoginServlet
        - UserService
        - UserDao
- idea用户姓名的提示

## 退出
- 已登录：session中有user对象
- 实现步骤
    - 访问servlet，将session销毁
    - 跳转到登录页面
- 代码实现
    - 编写前台代码

## 优化servlet
- 减少Servlet的数量，现在是一个功能一个servlet，将其优化为一个模块一个Servlet，相当于在数据库中，一张表对应一个Servlet，在Servlet中提供不同的方法，完成用户的请求

## 分类数据展示
- 分析
- 代码实现
    - 后台代码
        - CategoryServlet
        - CategoryService
        - CategoryDao
        
    - 前台代码
        - header.html加载后，发送ajax请求，请求Category/findAll
        - 对分类数据进行缓存优化
            - 分析发现，分类数据在每一次页面加载后都会重新请求数据库，这样会造成数据库的较大，且分类的数据不会产生变化，所以可以使用redis缓存这个数据
## redis缓存查询
- 期望数据库中存储的顺序就是今后展示的顺序

## 旅游线路分页展示功能
- 点击了不同的分类后，将来看到的旅游线路是不一样的，通过分析数据库表结构发现旅游线路和分类表时
- 类别id的传递
    - Redis中查询 score(cid)
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
    - 页面传递cid
        for (var i = 0; i < data.length; i++) {
            var li = '<li><a href="route_list.html?cid=' + data[i].cid + '">' + data[i].cname + '</a></li>'
            lis += li;
        }
    - 传递cid
    - Header.html获取cid
        $(function () {
              var search = location.search;
              // alert(search)
              // 切割字符串，拿到第二个值 //id=id
              var cid = search.split("=")[1];
              alert(cid)
  
        });
    
- 根据id查询不同类别的旅游线路数据并分页

- 分页展示旅游线路数据：
    - 分析
    - 编码
        - 客户端代码编写
        - 服务器端代码编写
            - 创建PageBean
                - RouteServlet
                - RouteService
                - RouteDao
## 旅游线路名称模糊查询
- 查询参数的传递
- RouteServlet
- RouteService
- RouteDao
## 详情页面查询
- 根据前端返回的sid查询
    - 后端代码
    - 前端代码
        - Route_detail.html加载后
            - 获取rid
            - 发送ajax请求获取route对象
            - 解析对象的数据
## 旅游线路旅游线路收藏功能
- 分析
    - 判断当前登录用户是否收藏过该线路
    - 当页面加载完成后，发送ajax请求，判断获取用户是否收藏的标记，根据标记展示不同按钮样式
    
- 代码编写
    - 前端代码
        - route_detail.html
        - 收藏次数的动态展示
    - 后端代码

## 点击按钮收藏线路
- 分析
- 编码
    - 后台代码
    - 前台代码